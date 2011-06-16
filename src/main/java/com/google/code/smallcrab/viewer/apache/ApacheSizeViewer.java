/**
 * 
 */
package com.google.code.smallcrab.viewer.apache;

import com.google.code.smallcrab.spliter.apache.ApacheLogSpliter;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ApacheSizeViewer extends AbstractContainApacheViewer {

	/**
	 * 
	 */
	public ApacheSizeViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ApacheSizeViewer(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView(com.google.code.smallcrab.scan.apache.ApacheSpliter)
	 */
	@Override
	protected String getAllView(ApacheLogSpliter spliter) {
		return spliter.getSize();
	}

}
