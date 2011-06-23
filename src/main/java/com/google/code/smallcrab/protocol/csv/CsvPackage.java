/**
 * 
 */
package com.google.code.smallcrab.protocol.csv;

import com.google.code.smallcrab.protocol.LinePackege;
import com.google.code.smallcrab.utils.StringKit;

/**
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public class CsvPackage implements LinePackege {
	private String[] segs;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.LineSpliter#split(java.lang.String)
	 */
	@Override
	public void split(String line) {
		segs = StringKit.split(line, ',');
	}

	@Override
	public String c(int columnIndex) {
		return segs[columnIndex];
	}

}
