/**
 * 
 */
package com.google.code.smallcrab.scan.apache;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ApacheReferrerViewer extends AbstractContainApacheViewer {

	/**
	 * 
	 */
	public ApacheReferrerViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ApacheReferrerViewer(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView()
	 */
	@Override
	protected String getAllView(ApacheSpliter spliter) {
		return spliter.getReferrer();
	}

}
