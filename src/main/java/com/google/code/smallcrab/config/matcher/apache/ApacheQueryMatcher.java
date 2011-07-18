/**
 * 
 */
package com.google.code.smallcrab.config.matcher.apache;

import com.google.code.smallcrab.protocol.apache.ApacheLogPackage;
import com.google.code.smallcrab.utils.UrlKit;

/**
 * @author xalinx at gmail dot com
 * @date Dec 29, 2010
 */
public class ApacheQueryMatcher extends ApacheLogLineMatcher {
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param key
	 * @param value
	 */
	public ApacheQueryMatcher(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.LineMatcher#match(java.lang.String, com.google.code.smallcrab.scan.LineSpliter)
	 */
	public boolean match(ApacheLogPackage spliter) {
		String query = spliter.getQuery();
		String pv = UrlKit.extractParameterValue(query, key);
		return value.equals(pv);
	}
}
