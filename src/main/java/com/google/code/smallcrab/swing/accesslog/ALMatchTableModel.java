/**
 * 
 */
package com.google.code.smallcrab.swing.accesslog;

/**
 * @author xalinx at gmail dot com
 * @date Dec 31, 2010
 */
public class ALMatchTableModel extends ALViewTableModel {
	private final Object[][] data = { { DOMAIN, "", false }, { QUERY, "", false }, { PATH, "", false }, { REFERRER, "", false }, { CODE, "", false }, { METHOD, "", false }, { AGENT, "", false }, { IP, "", false } };

	/**
	 * 
	 */
	private static final long serialVersionUID = -6014648934110785320L;

	@Override
	protected Object[][] getData() {
		return data;
	}

}
