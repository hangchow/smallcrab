package com.google.code.smallcrab.config.chart;

public class ChartConfig {

	private boolean drawFrequency;
	
	private boolean drawFrequencyAverage;

	private boolean drawY;
	
	private boolean drawYAverage;
	
	public void setDrawFrequency(boolean drawFrequency) {
		this.drawFrequency = drawFrequency;
	}

	public boolean isDrawFrequency() {
		return drawFrequency;
	}

	public void setDrawYAverage(boolean drawYAverage) {
		this.drawYAverage = drawYAverage;
	}

	public boolean isDrawYAverage() {
		return drawYAverage;
	}

	public boolean isDrawFrequencyAverage() {
		return drawFrequencyAverage;
	}

	public void setDrawFrequencyAverage(boolean drawFrequencyAverage) {
		this.drawFrequencyAverage = drawFrequencyAverage;
	}

	public boolean isDrawY() {
		return drawY;
	}

	public void setDrawY(boolean drawY) {
		this.drawY = drawY;
	}
	
}
