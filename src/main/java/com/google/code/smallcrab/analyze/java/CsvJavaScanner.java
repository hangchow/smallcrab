/**
 * 
 */
package com.google.code.smallcrab.analyze.java;

import com.google.code.smallcrab.analyze.LineScanner;
import com.google.code.smallcrab.protocol.csv.CsvPackage;

/**
 * @author seanlinwang at gmail dot com
 * @date 2011-6-20
 */
public class CsvJavaScanner implements LineScanner {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.analyze.LineScanner#scan(java.lang.String)
	 */
	@Override
	public String[] scan(String line) {
		CsvPackage spliter = new CsvPackage();
		spliter.split(line);
		String uid = spliter.c(0);
		String followingId = spliter.c(1);
		return new String[] { uid, followingId };
	}

}
