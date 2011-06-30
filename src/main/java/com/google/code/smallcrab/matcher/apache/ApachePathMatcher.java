/**
 * 
 */
package com.google.code.smallcrab.matcher.apache;

import com.google.code.smallcrab.protocol.apache.ApacheLogPackage;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public class ApachePathMatcher extends AbstractContainApacheMatcher {

	/**
	 * 
	 */
	public ApachePathMatcher() {
		super();
	}

	/**
	 * @param contain
	 */
	public ApachePathMatcher(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheMatcher#getAllView(com.google.code.smallcrab.scan.apache.ApacheSpliter)
	 */
	@Override
	protected String getAllView(ApacheLogPackage spliter) {
		return spliter.getPath();
	}

}
