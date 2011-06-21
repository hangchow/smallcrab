/**
 * 
 */
package com.google.code.smallcrab.matcher;

import com.google.code.smallcrab.scan.LinePackege;

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
