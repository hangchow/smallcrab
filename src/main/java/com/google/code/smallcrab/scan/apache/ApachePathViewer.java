/**
 * 
 */
package com.google.code.smallcrab.scan.apache;


/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public class ApachePathViewer extends AbstractContainApacheViewer {

	/**
	 * 
	 */
	public ApachePathViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ApachePathViewer(String contain) {
		super(contain);
	}

	/*
	 * /* (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView(com.google.code.smallcrab.scan.apache.ApacheSpliter)
	 */
	@Override
	protected String getAllView(ApacheSpliter spliter) {
		return spliter.getPath();
	}

}
