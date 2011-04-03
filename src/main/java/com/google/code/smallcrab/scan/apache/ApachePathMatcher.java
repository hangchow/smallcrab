/**
 * 
 */
package com.google.code.smallcrab.scan.apache;

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
	protected String getAllView(ApacheSpliter spliter) {
		return spliter.getPath();
	}

}
