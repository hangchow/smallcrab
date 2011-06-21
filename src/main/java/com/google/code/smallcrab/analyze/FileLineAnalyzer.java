/**
 * 
 */
package com.google.code.smallcrab.analyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.google.code.smallcrab.utils.ArrayKit;

/**
 * Analyze file one line by one line.
 * 
 * @author seanlinwang at gmail dot com
 * 
 */
public class FileLineAnalyzer implements FileAnalyzer {
	/**
	 * max file line pool size
	 */
	private static final int MAX_LINE_POOL_SIZE = 1024 * 10;

	/**
	 * file line scanner
	 */
	private LineScanner lineScanner;

	public void setLineScanner(LineScanner lineScanner) {
		this.lineScanner = lineScanner;
	}

	/**
	 * analyzing finished flag
	 */
	private volatile boolean finished = false;

	@Override
	public boolean isFinished() {
		return this.finished;
	}

	/**
	 * analyzing paused flag
	 */
	private volatile boolean paused = false;

	@Override
	public boolean isPaused() {
		return this.paused;
	}

	/**
	 * analyzing paused lock
	 */
	private final ReentrantLock pausedLock = new ReentrantLock();

	/**
	 * analyzing paused condition
	 */
	private final Condition pausedCondition = pausedLock.newCondition();

	/**
	 * barrier for analyzing thread and readline thread
	 */
	private final CyclicBarrier barrier = new CyclicBarrier(2);

	/**
	 * total analyzing period
	 */
	private long analyzePeriod;

	public long getAnalyzePeriod() {
		return analyzePeriod;
	}

	/**
	 * file line pool
	 */
	private BlockingQueue<Object> linePool = new ArrayBlockingQueue<Object>(MAX_LINE_POOL_SIZE);

	public void setLinePool(BlockingQueue<Object> linePool) {
		this.linePool = linePool;
	}

	private final Object eof = new Object();

	/**
	 * @param lineScanner
	 */
	public FileLineAnalyzer(LineScanner lineScanner) {
		this.lineScanner = lineScanner;
	}

	/**
	 * 
	 */
	public FileLineAnalyzer() {
	}

	interface LineConsumer {
		void consume(String[] linePackage);
	}

	public void analyzeAppend(final File file, final Map<String, Set<String>> result, final AnalyzeCallback callback) throws IOException {
		this.analyze(file, new LineConsumer() {

			@Override
			public void consume(String[] lineResult) {
				if (ArrayKit.isNotEmpty(lineResult)) {
					String key = lineResult[0];
					String value = lineResult[1];
					Set<String> originValue = result.get(key);
					if (originValue == null) {
						originValue = new HashSet<String>();
						result.put(key, originValue);
					}
					originValue.add(value);
				}

			}
		}, callback);
	}

	@Override
	public void analyzeCount(final File file, final Map<String, Integer> result, final AnalyzeCallback callback) throws IOException {
		this.analyze(file, new LineConsumer() {

			@Override
			public void consume(String[] lineResult) {
				if (ArrayKit.isNotEmpty(lineResult)) {
					StringBuilder keyBuffer = new StringBuilder();
					for (String resultSegment : lineResult) {
						if (keyBuffer.length() > 0) {
							keyBuffer.append(',');
						}
						keyBuffer.append(resultSegment);
					}
					String key = keyBuffer.toString();
					Integer count = result.get(key);
					count = count == null ? 0 : count;
					result.put(key, ++count);
				}

			}
		}, callback);
	}

	public void analyze(final File file, final LineConsumer lineConsumer, final AnalyzeCallback callback) throws IOException {
		final Thread analyzingThread = new Thread(new Runnable() {

			@Override
			public void run() {
				Object strLine = null;
				long lineNumber = 1;
				int invalidLines = 0;
				while (true) {
					try {
						strLine = linePool.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
						continue;
					}
					if (paused) {
						println("paused");
						pausedLock.lock();
						try {
							pausedCondition.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} finally {
							pausedLock.unlock();
						}
					}
					if (strLine == eof) {
						println("readed eof");
						callback.callback();
						if (finished == false) {
							finished = true;
							println("enable finished flag");
						}
						break;
					}
					try {
						String[] lineResult = lineScanner.scan((String) strLine);
						lineConsumer.consume(lineResult);
					} catch (Throwable e) {
						invalidLines++;
					}
					callback.addAnalyzedSize(((String) strLine).length() + 2);// 2 identified the length of /r/n, not very correct. FIXME
					if (lineNumber % callback.getBufferLineSize() == 0) {
						callback.callback();
					}
					lineNumber++;
				}
				callback.setTotalLines(lineNumber);// set total analyzed lines
				callback.setInvalidLines(invalidLines);
				linePool.clear();// clear and unblock the queue
				println("finished");
				awaitFinish();// await for readline thread finished
			}// end run()
		});// end new analyzing thread

		analyzingThread.setName("AnalyzeThread-1");
		println("starting " + analyzingThread.getName());
		analyzingThread.start();
		InputStream in = null;
		long start = System.currentTimeMillis();
		try {
			in = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				if (linePool.size() == MAX_LINE_POOL_SIZE) {
					println("LINE POOL OVERFLOW:" + linePool.size());
				}
				if (!this.finished) { // call finished() when reading lines
					try {
						this.linePool.put(strLine);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					println("finished this analyzing outside");
					break;
				}
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		if (!this.finished) { // not call finished() outside
			this.finish();
		}
		println("finished this analyzing");
		awaitFinish();// await for analyzing thread finished
		analyzePeriod = System.currentTimeMillis() - start;
	}

	private void println(String msg) {
		System.err.println(Thread.currentThread().getName() + " -- " + msg);
	}

	private void awaitFinish() {
		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void pause() {
		this.paused = true;
	}

	public void unpause() {
		this.paused = false;
		pausedLock.lock();
		try {
			pausedCondition.signal();
		} finally {
			pausedLock.unlock();
		}
	}

	public void finish() {
		try {
			linePool.put(eof);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

}
