/**
 * 
 */
package com.google.code.smallcrab.config;

/**
 * @author seanlinwang at gmail dot com
 * @date Apr 24, 2011
 *
 */
public class ConfigException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1683130874960721263L;

	/**
	 * 
	 */
	public ConfigException() {
	}

	/**
	 * @param message
	 */
	public ConfigException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ConfigException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}

}
