/**
 * 
 */
package com.google.code.smallcrab.config.viewer.apache;

import com.google.code.smallcrab.protocol.apache.ApacheLogPackage;
import com.google.code.smallcrab.utils.StringKit;
import com.google.code.smallcrab.utils.UrlKit;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public class ApacheQueryViewer extends ApacheLogLineViewer {
	private String key;
	private boolean emptyKey;

	/**
	 * 
	 */
	public ApacheQueryViewer() {
		super();
	}

	/**
	 * @param contain
	 */
	public ApacheQueryViewer(String key) {
		this.key = key;
		if (StringKit.isEmpty(key)) {
			this.emptyKey = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.scan.apache.AbstractContainApacheViewer#getAllView(com.google.code.smallcrab.scan.apache.ApacheSpliter)
	 */
	@Override
	public String view(ApacheLogPackage spliter) {
		String query = spliter.getQuery();
		String v = query;
		if (!emptyKey) {
			v = UrlKit.extractParameterValue(query, key);
		}
		return v;
	}

}
