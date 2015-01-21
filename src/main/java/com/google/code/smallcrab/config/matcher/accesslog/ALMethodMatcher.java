/**
 * 
 */
package com.google.code.smallcrab.config.matcher.accesslog;

import com.google.code.smallcrab.protocol.accesslog.ALPackage;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ALMethodMatcher extends AbstractContainALMatcher {

	/**
	 * 
	 */
	public ALMethodMatcher() {
		super();
	}

	/**
	 * @param contain
	 */
	public ALMethodMatcher(String contain) {
		super(contain);
	}

	/*
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheMatcher#getAllView(com.google.code.smallcrab.scan.apache.Apachepac)
	 */
	@Override
	protected String getAllView(ALPackage pac) {
		return pac.getMethod();
	}

}
