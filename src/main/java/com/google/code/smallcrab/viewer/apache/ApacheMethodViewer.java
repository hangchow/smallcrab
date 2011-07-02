/**
 * 
 */
package com.google.code.smallcrab.viewer.apache;

import com.google.code.smallcrab.protocol.apache.ApacheLogPackage;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ApacheMethodViewer extends AbstractContainApacheViewer {

	/**
	 * 
	 */
	public ApacheMethodViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ApacheMethodViewer(String contain) {
		super(contain);
	}

	/* (non-Javadoc)
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView(com.google.code.smallcrab.scan.apache.ApacheSpliter)
	 */
	@Override
	protected String getAllView(ApacheLogPackage spliter) {
		return spliter.getMethod();
	}

}
