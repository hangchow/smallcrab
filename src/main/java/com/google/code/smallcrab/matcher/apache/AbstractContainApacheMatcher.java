/**
 * 
 */
package com.google.code.smallcrab.matcher.apache;

import com.google.code.smallcrab.protocol.apache.ApacheLogPackage;
import com.google.code.smallcrab.utils.StringKit;

/**
 * @author xalinx at gmail dot com
 * @date Dec 29, 2010
 */
public abstract class AbstractContainApacheMatcher extends ApacheLogLineMatcher {

	protected String contain;

	protected boolean contained;

	public String getContain() {
		return contain;
	}

	public void setContain(String contain) {
		if (StringKit.isEmpty(contain)) {
			throw new IllegalArgumentException(contain);
		}
		this.contain = contain;
	}

	/**
	 * @param contain
	 */
	public AbstractContainApacheMatcher(String contain) {
		super();
		this.setContain(contain);
	}

	/**
	 * 
	 */
	public AbstractContainApacheMatcher() {
		super();
	}

	@Override
	public boolean match(ApacheLogPackage spliter) {
		String v = getAllView(spliter);
		if (StringKit.isEmpty(v)) {
			return false; // v is empty but contain is not empty, not equals
		}
		return v.contains(contain);
	}

	protected abstract String getAllView(ApacheLogPackage spliter);

}
