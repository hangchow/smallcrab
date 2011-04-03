/**
 * 
 */
package com.google.code.smallcrab.scan.apache;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ApachePeriodViewer extends AbstractContainApacheViewer {

	/**
	 * 
	 */
	public ApachePeriodViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ApachePeriodViewer(String contain) {
		super(contain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView(com.google.code.smallcrab.scan.apache.ApacheSpliter)
	 */
	@Override
	protected String getAllView(ApacheSpliter spliter) {
		return spliter.getPeriod();
	}

}
