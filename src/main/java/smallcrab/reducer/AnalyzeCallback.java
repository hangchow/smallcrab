/**
 * 
 */
package smallcrab.reducer;

/**
 * @author seanlinwang at gmail dot com
 * 
 */
public abstract class AnalyzeCallback {
	private long analyzedSize;

	private long totalLines;

	private int invalidLines;

	private double xMinValue = Double.MAX_VALUE;

	private double xMaxValue = Double.MIN_VALUE;

	private double yMinValue = Double.MAX_VALUE;

	private double yMaxValue = Double.MIN_VALUE;
	
	private double yMaxXValue = Double.MIN_VALUE;

	private double yValueSum;

	private double yValueCount;
	
	private double frequencySum;
	
	private double frequencyCount;
	
	private int frequencyMinValue = Integer.MAX_VALUE;

	private int frequencyMaxValue = Integer.MIN_VALUE;

	private double frequencyMaxXValue = Double.MIN_VALUE;
	
	public long getAnalyzedSize() {
		return this.analyzedSize;
	}

	public void addAnalyzedSize(long size) {
		this.analyzedSize += size;
	}
	
	public void setTotalLines(long line) {
		this.totalLines = line;
	}

	public long getTotalLines() {
		return this.totalLines;
	}
	
	public int getInvalidLines() {
		return invalidLines;
	}

	public void setInvalidLines(int invalidLines) {
		this.invalidLines = invalidLines;
	}

	public double getYValueCount() {
		return this.yValueCount;
	}
	
	public double getYValueSum() {
		return this.yValueSum;
	}

	public double getYValueAverage() {
		return this.yValueSum / this.yValueCount;
	}
	

	
	public Object getFrequencyAverage() {
		return this.frequencySum / this.frequencyCount;
	}

	public double getFrequencyMaxXValue() {
		return frequencyMaxXValue;
	}

	public double getXMinValue() {
		return xMinValue;
	}

	public double getXMaxValue() {
		return xMaxValue;
	}

	public double getYMinValue() {
		return this.yMinValue;
	}

	public double getYMaxValue() {
		return this.yMaxValue;
	}

	public double getYMaxXValue() {
		return this.yMaxXValue;
	}

	public double getyMaxXValue() {
		return this.yMaxXValue;
	}

	public int getFrequencyMinValue() {
		return this.frequencyMinValue;
	}

	public int getFrequencyMaxValue() {
		return this.frequencyMaxValue;
	}

	public void setYValue(double y, double x) {
		if (y < this.yMinValue) {
			this.yMinValue = y;
		} 
		if (y > this.yMaxValue) {
			this.yMaxValue = y;
			this.yMaxXValue = x;
		}
		yValueSum += y;
		yValueCount ++;
	}
	
	public void setXValue(double x) {
		if (x < this.xMinValue) {
			this.xMinValue = x;
		} 
		if (x > this.xMaxValue) {
			this.xMaxValue = x;
		}
	}

	public void setFrequency(int freq, double x) {
		if(freq < this.frequencyMinValue) {
			this.frequencyMinValue = freq;
		} 
		if (freq > this.frequencyMaxValue) {
			this.frequencyMaxValue = freq;
			this.frequencyMaxXValue = x;
		}
		this.frequencySum += freq;
		this.frequencyCount ++;
	}

	public void setXMaxCount(int count) {
		if (count > this.frequencyMaxValue) {
			this.frequencyMaxValue = count;
		}
	}
	
	public abstract void callback();

	public abstract long getBufferLineSize();
}
