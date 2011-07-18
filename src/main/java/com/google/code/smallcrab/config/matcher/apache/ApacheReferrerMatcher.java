/**
 * 
 */
package com.google.code.smallcrab.config.matcher.apache;

import com.google.code.smallcrab.protocol.apache.ApacheLogPackage;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ApacheReferrerMatcher extends AbstractContainApacheMatcher {

	/**
	 * 
	 */
	public ApacheReferrerMatcher() {
		super();
	}

	/**
	 * @param contain
	 */
	public ApacheReferrerMatcher(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView(com.google.code.smallcrab.scan.apache.ApacheSpliter)
	 */
	@Override
	protected String getAllView(ApacheLogPackage spliter) {
		return spliter.getReferrer();
	}

}
