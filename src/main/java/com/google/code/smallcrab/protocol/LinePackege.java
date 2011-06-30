/**
 * 
 */
package com.google.code.smallcrab.protocol;

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
	String column(int columnIndex);

}
