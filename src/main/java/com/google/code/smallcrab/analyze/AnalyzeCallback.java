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

	private double xMinValue = Double.MAX_VALUE;

	private double xMaxValue = Double.MIN_VALUE;

	private double yMinValue = Double.MAX_VALUE;

	private double yMaxValue = Double.MIN_VALUE;

	private double yValueSum;

	public double getYValueSum() {
		return this.yValueSum;
	}

	private double yValueCount;

	public double getyValueCount() {
		return this.yValueCount;
	}

	public double getyValueAverage() {
		return this.yValueSum / yValueCount;
	}

	private int xMinCount = Integer.MAX_VALUE;

	private int xMaxCount = Integer.MIN_VALUE;

	public double getxMinValue() {
		return xMinValue;
	}

	public void setxMinValue(double xMinValue) {
		if (xMinValue < this.xMinValue) {
			this.xMinValue = xMinValue;
		}
	}

	public double getxMaxValue() {
		return xMaxValue;
	}

	public void setxMaxValue(double xMaxValue) {
		if (xMaxValue > this.xMaxValue) {
			this.xMaxValue = xMaxValue;
		}
	}

	public double getyMinValue() {
		return yMinValue;
	}

	public double getyMaxValue() {
		return yMaxValue;
	}

	public void setxMinCount(int count) {
		if (count < this.xMinCount) {
			this.xMinCount = count;
		}
	}

	public void setxMaxCount(int count) {
		if (count > this.xMaxCount) {
			this.xMaxCount = count;
		}
	}

	public int getxMinCount() {
		return this.xMinCount;
	}

	public int getxMaxCount() {
		return this.xMaxCount;
	}

	public void setyValue(double y) {
		if (y < this.yMinValue) {
			this.yMinValue = y;
		}
		if (y > this.yMaxValue) {
			this.yMaxValue = y;
		}
		yValueSum += y;
		yValueCount++;
	}
}
