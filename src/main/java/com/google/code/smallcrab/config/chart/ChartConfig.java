package com.google.code.smallcrab.config.chart;

public class ChartConfig {

	private boolean needFrequency;
	
	private boolean needAverage;

	public void setNeedFrequency(boolean needFrequency) {
		this.needFrequency = needFrequency;
	}

	public boolean isNeedFrequency() {
		return needFrequency;
	}

	public void setNeedAverage(boolean needAverage) {
		this.needAverage = needAverage;
	}

	public boolean isNeedAverage() {
		return needAverage;
	}
}
