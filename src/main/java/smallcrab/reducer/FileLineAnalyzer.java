/**
 * 
 */
package smallcrab.reducer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import smallcrab.mapper.Mapper;
import smallcrab.protocol.Format;
import smallcrab.utils.ArrayKit;


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
	private static final int MAX_LINE_POOL_SIZE = 1024 * 100;

	/**
	 * file line scanner
	 */
	private Mapper lineScanner;

	public void setLineScanner(Mapper lineScanner) {
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

	@Override
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
	public FileLineAnalyzer(Mapper lineScanner) {
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

	@Override
	public void analyzeAppend(final File file, final Map<String, Set<String>> result, final AnalyzeCallback callback) throws IOException {
		this.analyze(file, new LineConsumer() {

			@Override
			public void consume(String[] lineResult) {
				if (ArrayKit.isNotEmpty(lineResult)) {
					String key = lineResult[0];// TODO
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
	public void analyzeXYSplots(final File file, final List<List<Double>> result, final Map<Double, Integer> xCount, final AnalyzeCallback callback) throws IOException {
		this.analyze(file, new LineConsumer() {

			private DateFormat dateFormat = Format.getDateFormat();

			/*
			 * @lineResult [X,Y1,Y2...]
			 * 
			 * @see com.google.code.smallcrab.analyze.FileLineAnalyzer.LineConsumer#consume(java.lang.String[])
			 */
			@Override
			public void consume(String[] lineResult) {
				if (ArrayKit.isNotEmpty(lineResult) && lineResult.length >= 2) { // 2 indicate [X,Y1]
					List<Double> xyaxis = new ArrayList<Double>(lineResult.length);
					String x = lineResult[0];
					double xaxis = 0;
					try {
						xaxis = dateFormat.parse(x).getTime();
					} catch (ParseException e) {
						e.printStackTrace();
					}
					xyaxis.add(xaxis);
					callback.setXValue(xaxis);
					for (int i = 1; i < lineResult.length; i++) {
						double y = Double.parseDouble(lineResult[i]);
						xyaxis.add(y);
						callback.setYValue(y, xaxis);
					}
					// do x count
					Integer count = xCount.get(xaxis);
					if (count == null) {
						count = 0;
					}
					count += lineResult.length - 1;
					xCount.put(xaxis, count);
					result.add(xyaxis);
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
						String[] lineResult = lineScanner.map((String) strLine);
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
		BufferedReader br = null;
		long start = System.currentTimeMillis();
		try {
			in = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(in));
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
			if (br != null) {
				br.close();
			}
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
	@Override
	public void pause() {
		this.paused = true;
	}

	@Override
	public void unpause() {
		this.paused = false;
		pausedLock.lock();
		try {
			pausedCondition.signal();
		} finally {
			pausedLock.unlock();
		}
	}

	@Override
	public void finish() {
		try {
			linePool.put(eof);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

}
