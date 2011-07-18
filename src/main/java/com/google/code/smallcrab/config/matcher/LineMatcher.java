/**
 * 
 */
package com.google.code.smallcrab.config.matcher;

import com.google.code.smallcrab.protocol.LinePackege;

/**
 * @author xalinx at gmail dot com
 * @date Dec 29, 2010
 */
public interface LineMatcher<L extends LinePackege> {

	/**
	 * @param spliter
	 * @param segs
	 * @return
	 */
	boolean match(L spliter);

}
