/**
 * 
 */
package com.google.code.smallcrab.viewer.csv;

import com.google.code.smallcrab.protocol.csv.CsvPackage;
import com.google.code.smallcrab.viewer.LineViewer;

/**
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public class CsvLineViewer implements LineViewer<CsvPackage> {
	private int columnIndex;

	private boolean isXAxis = false;

	private boolean isYAxis = false;

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public void setXAxis(boolean isXAxis) {
		this.isXAxis = isXAxis;
	}

	public void setYAxis(boolean isYAxis) {
		this.isYAxis = isYAxis;
	}

	public boolean isXAxis() {
		return this.isXAxis;
	}

	public boolean isYAxis() {
		return this.isYAxis;
	}

	public CsvLineViewer(int columnIndex) {
		super();
		this.columnIndex = columnIndex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.viewer.LineViewer#view(com.google.code.smallcrab.scan.LineSpliter)
	 */
	@Override
	public String view(CsvPackage spliter) {
		return spliter.column(columnIndex);
	}

}
