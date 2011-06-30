/**
 * 
 */
package com.google.code.smallcrab.analyze;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author seanlinwang at gmail dot com
 * @date 2011-6-20
 */
public interface FileAnalyzer {

	/**
	 * @param source
	 *            the file for analyzing
	 * @param callback
	 *            analyze callback
	 * @param result
	 *            analyzed result
	 * @throws IOException
	 */
	void analyzeAppend(final File source, final Map<String, Set<String>> result, final AnalyzeCallback callback) throws IOException;

	/**
	 * @param source
	 *            the file for analyzing
	 * @param callback
	 *            analyze callback
	 * @param result
	 *            analyzed result
	 * @throws IOException
	 */
	void analyzeCount(final File source, final Map<String, Integer> result, final AnalyzeCallback callback) throws IOException;

	/**
	 * @param source
	 * @param result
	 * @param callback
	 * @throws IOException
	 */
	void analyzeXYSplots(final File source, final List<List<Double>> result, final Map<Double, Integer> xCount, final AnalyzeCallback callback) throws IOException;

	/**
	 * @return a flag indicate analyzing finished or not
	 */
	boolean isFinished();

	/**
	 * @return a flag indicate analyzing paused or not
	 */
	boolean isPaused();
}
