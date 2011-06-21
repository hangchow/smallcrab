/**
 * 
 */
package com.google.code.smallcrab.scan;

/**
 * @author xalinx at gmail dot com
 * @date Dec 29, 2010
 */
public interface LinePackege {

	/**
	 * @param line
	 */
	void split(String line);
	
	/**
	 * @param columnIndex
	 * @return
	 */
	String c(int columnIndex);

}
