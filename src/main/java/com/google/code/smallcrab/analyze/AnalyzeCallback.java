/**
 * 
 */
package com.google.code.smallcrab.analyze;

/**
 * @author seanlinwang at gmail dot com
 * 
 */
public abstract class AnalyzeCallback {
	private long analyzedSize;

	public long getAnalyzedSize() {
		return this.analyzedSize;
	}

	public void addAnalyzedSize(long size) {
		this.analyzedSize += size;
	}

	public abstract void callback();

	public abstract long getBufferLineSize();

	private long totalLines;

	public void setTotalLines(long line) {
		this.totalLines = line;
	}

	public long getTotalLines() {
		return this.totalLines;
	}

	private int invalidLines;

	public int getInvalidLines() {
		return invalidLines;
	}

	public void setInvalidLines(int invalidLines) {
		this.invalidLines = invalidLines;
	}

}
