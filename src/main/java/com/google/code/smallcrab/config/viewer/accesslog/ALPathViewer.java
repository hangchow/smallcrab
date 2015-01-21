/**
 * 
 */
package com.google.code.smallcrab.config.viewer.accesslog;

import com.google.code.smallcrab.protocol.accesslog.ALPackage;


/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public class ALPathViewer extends AbstractContainALViewer {

	/**
	 * 
	 */
	public ALPathViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ALPathViewer(String contain) {
		super(contain);
	}

	/*
	 * /* (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView(com.google.code.smallcrab.scan.apache.Apachepac)
	 */
	@Override
	protected String getAllView(ALPackage pac) {
		return pac.getPath();
	}

}
