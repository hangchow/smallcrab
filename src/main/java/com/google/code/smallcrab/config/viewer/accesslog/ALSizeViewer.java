/**
 * 
 */
package com.google.code.smallcrab.config.viewer.accesslog;

import com.google.code.smallcrab.protocol.accesslog.ALPackage;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ALSizeViewer extends AbstractContainALViewer {

	public ALSizeViewer() {
		super();
	}

	public ALSizeViewer(String contain) {
		super(contain);
	}

	@Override
	protected String getAllView(ALPackage pac) {
		return pac.getSize();
	}

}
