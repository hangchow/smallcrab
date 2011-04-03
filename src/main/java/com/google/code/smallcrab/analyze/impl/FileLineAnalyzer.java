/**
 * 
 */
package com.google.code.smallcrab.analyze.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.google.code.smallcrab.analyze.AnalyzeCallback;
import com.google.code.smallcrab.analyze.FileAnalyzer;
import com.google.code.smallcrab.scan.LineScanner;
import com.google.code.smallcrab.utils.ArrayKit;

/**
 * Analyze file one line by one line.
 * 
 * @author lin.wangl
 * 
 */
public class FileLineAnalyzer implements FileAnalyzer {
	/**
	 * 
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
	 * analyzer running flag
	 */
	private boolean finished = false;

	@Override
	public boolean isFinished() {
		return this.finished;
	}

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

	private final ReentrantLock scanLock = new ReentrantLock();

	private final Condition scanned = scanLock.newCondition();

	private final Object eof = new Object();

	private long totalLines = 0;

	/**
	 * 
	 */
	private void incrementTotalLines() {
		totalLines++;
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.analyze.FileAnalyzer#analyze(java.io.File)
	 */
	@Override
	public void analyze(final File file, final Map<String, Integer> result, final AnalyzeCallback callback) throws IOException {
		final Thread scanThread = new Thread(new Runnable() {

			private void consume(String strLine) {
				String[] lineResult = null;
				try {
					lineResult = lineScanner.scan(strLine);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
				callback.addAnalyzedSize(strLine.length() + 2);// 2 identified the length of /r/n
				if (totalLines % callback.getBufferLineSize() == 0) {
					try {
						callback.callback();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				incrementTotalLines();
			}

			@Override
			public void run() {
				Object strLine = null;
				while (true) {
					try {
						strLine = linePool.take();
					} catch (InterruptedException e) {
						continue;
					}
					if (strLine == eof) {
						break;
					}
					consume((String) strLine);
				}
				callback.setTotalLines(totalLines);// set total analyzed lines
				try {
					callback.callback();
				} catch (Exception e) {
					e.printStackTrace();
				}
				scanLock.lock();
				try {
					scanned.signal();
				} finally {
					scanLock.unlock();
				}
			}
		});
		scanThread.setName("sc-scan-1");
		scanThread.start();
		long start = System.currentTimeMillis();
		DataInputStream in = null;
		try {
			in = new DataInputStream(new FileInputStream(file));//FIXME remove datainputstream
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				try {
					if (linePool.size() == MAX_LINE_POOL_SIZE) {
						System.out.println("LINE POOL OVERFLOW:" + linePool.size());
					}
					this.linePool.put(strLine);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			scanLock.lock();
			try {
				linePool.put(eof);
				scanned.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				scanLock.unlock();
			}
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.analyzePeriod = System.currentTimeMillis() - start;
		this.finished = true;
	}

}
