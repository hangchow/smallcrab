/**
 * 
 */
package com.google.code.smallcrab.config.matcher.accesslog;

import com.google.code.smallcrab.protocol.accesslog.ALPackage;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public class ALPathMatcher extends AbstractContainALMatcher {

	/**
	 * 
	 */
	public ALPathMatcher() {
		super();
	}

	/**
	 * @param contain
	 */
	public ALPathMatcher(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheMatcher#getAllView(com.google.code.smallcrab.scan.apache.Apachepac)
	 */
	@Override
	protected String getAllView(ALPackage pac) {
		return pac.getPath();
	}

}
