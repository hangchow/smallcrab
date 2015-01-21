/**
 * 
 */
package com.google.code.smallcrab.config.viewer.accesslog;

import com.google.code.smallcrab.protocol.accesslog.ALPackage;
import com.google.code.smallcrab.utils.StringKit;
import com.google.code.smallcrab.utils.UrlKit;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public class ALQueryViewer extends ALLineViewer {
	private String key;
	private boolean emptyKey;

	/**
	 * 
	 */
	public ALQueryViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ALQueryViewer(String key) {
		this.key = key;
		if (StringKit.isEmpty(key)) {
			this.emptyKey = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView(com.google.code.smallcrab.scan.apache.Apachepac)
	 */
	@Override
	public String view(ALPackage pac) {
		String query = pac.getQuery();
		String v = query;
		if (!emptyKey) {
			v = UrlKit.extractParameterValue(query, key);
		}
		return v;
	}

}
