/**
 * 
 */
package com.google.code.smallcrab.viewer;

import com.google.code.smallcrab.scan.LinePackege;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 * @threadsafe
 */
public interface LineViewer<L extends LinePackege> {

	/**
	 * View segment from line using spliter.
	 * @param spliter
	 * 
	 * @return seg
	 */
	String view(L spliter);
}
