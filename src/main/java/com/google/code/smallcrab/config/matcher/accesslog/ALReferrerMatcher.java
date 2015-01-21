/**
 * 
 */
package com.google.code.smallcrab.config.matcher.accesslog;

import com.google.code.smallcrab.protocol.accesslog.ALPackage;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ALReferrerMatcher extends AbstractContainALMatcher {

	/**
	 * 
	 */
	public ALReferrerMatcher() {
		super();
	}

	/**
	 * @param contain
	 */
	public ALReferrerMatcher(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView(com.google.code.smallcrab.scan.apache.Apachepac)
	 */
	@Override
	protected String getAllView(ALPackage pac) {
		return pac.getReferer();
	}

}
