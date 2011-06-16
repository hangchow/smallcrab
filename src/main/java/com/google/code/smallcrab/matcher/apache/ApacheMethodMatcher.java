/**
 * 
 */
package com.google.code.smallcrab.matcher.apache;

import com.google.code.smallcrab.spliter.apache.ApacheLogSpliter;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ApacheMethodMatcher extends AbstractContainApacheMatcher {

	/**
	 * 
	 */
	public ApacheMethodMatcher() {
		super();
	}

	/**
	 * @param contain
	 */
	public ApacheMethodMatcher(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheMatcher#getAllView(com.google.code.smallcrab.scan.apache.ApacheSpliter)
	 */
	@Override
	protected String getAllView(ApacheLogSpliter spliter) {
		return spliter.getMethod();
	}

}
