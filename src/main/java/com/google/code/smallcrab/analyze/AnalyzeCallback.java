/**
 * 
 */
package com.google.code.smallcrab.analyze;

/**
 * @author lin.wangl
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

}
