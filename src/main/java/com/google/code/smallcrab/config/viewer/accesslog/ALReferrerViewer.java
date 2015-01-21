/**
 * 
 */
package com.google.code.smallcrab.config.viewer.accesslog;

import com.google.code.smallcrab.protocol.accesslog.ALPackage;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ALReferrerViewer extends AbstractContainALViewer {

	/**
	 * 
	 */
	public ALReferrerViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ALReferrerViewer(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView()
	 */
	@Override
	protected String getAllView(ALPackage pac) {
		return pac.getReferer();
	}

}
