/**
 * 
 */
package com.google.code.smallcrab.scan;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 * @threadsafe
 */
public interface LineViewer<L extends LineSpliter> {

	/**
	 * View segment from line using spliter.
	 * @param spliter
	 * 
	 * @return seg
	 */
	String view(L spliter);
}
