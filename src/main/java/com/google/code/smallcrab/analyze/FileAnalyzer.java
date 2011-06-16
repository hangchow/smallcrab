/**
 * 
 */
package com.google.code.smallcrab.analyze;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author lin.wangl
 * 
 */
public interface FileAnalyzer {

	/**
	 * @param source
	 *            the file for analyzing s * @param callback analyze callback
	 * @param result
	 *            analyzed result
	 * @throws IOException
	 */
	void analyze(File source, Map<String, Integer> result, AnalyzeCallback callback) throws IOException;

	/**
	 * @return a flag indicate analyze task running status : true/finished or false/running
	 */
	boolean isFinished();

	/**
	 * @return
	 */
	boolean isPaused();

}
