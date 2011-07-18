/**
 * 
 */
package com.google.code.smallcrab.config.viewer.apache;

import com.google.code.smallcrab.protocol.apache.ApacheLogPackage;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ApacheCodeViewer extends AbstractContainApacheViewer {

	/**
	 * 
	 */
	public ApacheCodeViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ApacheCodeViewer(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView(com.google.code.smallcrab.scan.apache.ApacheSpliter)
	 */
	@Override
	protected String getAllView(ApacheLogPackage spliter) {
		return spliter.getCode();
	}

}
