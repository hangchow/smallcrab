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
	
	private double yMaxXValue = Double.MIN_VALUE;

	private double yValueSum;

	public double getyValueSum() {
		return this.yValueSum;
	}

	private double yValueCount;

	public double getyValueCount() {
		return this.yValueCount;
	}

	public double getyValueAverage() {
		return this.yValueSum / this.yValueCount;
	}
	
	private double frequencySum;
	
	private double frequencyCount;
	
	public Object getFrequencyAverage() {
		return this.frequencySum / this.frequencyCount;
	}

	private int frequencyMin = Integer.MAX_VALUE;

	private int frequencyMax = Integer.MIN_VALUE;

	public double getxMinValue() {
		return xMinValue;
	}

	public double getxMaxValue() {
		return xMaxValue;
	}

	public double getyMinValue() {
		return this.yMinValue;
	}

	public double getyMaxValue() {
		return this.yMaxValue;
	}

	public double getyMaxXValue() {
		return this.yMaxXValue;
	}


	public int getFrequencyMin() {
		return this.frequencyMin;
	}

	public int getFrequencyMax() {
		return this.frequencyMax;
	}

	public void setyValue(double y, double x) {
		if (y < this.yMinValue) {
			this.yMinValue = y;
		} else if (y > this.yMaxValue) {
			this.yMaxValue = y;
			this.yMaxXValue = x;
		}
		yValueSum += y;
		yValueCount ++;
	}
	
	public void setxValue(double x) {
		if (x < this.xMinValue) {
			this.xMinValue = x;
		} else if (x > this.xMaxValue) {
			this.xMaxValue = x;
		}
	}

	public void setFrequency(int freq) {
		if(freq < this.frequencyMin) {
			this.frequencyMin = freq;
		} else if (freq > this.frequencyMax) {
			this.frequencyMax = freq;
		}
		this.frequencySum += freq;
		this.frequencyCount ++;
	}

	public void setxMaxCount(int count) {
		if (count > this.frequencyMax) {
			this.frequencyMax = count;
		}
	}
	
}
