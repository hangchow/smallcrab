package com.google.code.smallcrab.analyze.apache;

import java.util.List;

import com.google.code.smallcrab.analyze.LineScanner;
import com.google.code.smallcrab.matcher.LineMatcher;
import com.google.code.smallcrab.matcher.apache.ApacheLogLineMatcher;
import com.google.code.smallcrab.protocol.apache.ApacheLogPackage;
import com.google.code.smallcrab.viewer.LineViewer;
import com.google.code.smallcrab.viewer.apache.ApacheLogLineViewer;

/**
 * Default apache log scanner, contain match rules.
 * 
 * @author xalinx at gmail dot com
 * @date Dec 29, 2010
 * 
 */
public class ApacheCountScanner implements LineScanner {
	/**
	 * log line matcher
	 */
	private List<ApacheLogLineMatcher> lineMatchers;

	public List<ApacheLogLineMatcher> getLineMatchers() {
		return lineMatchers;
	}

	public void setLineMatchers(List<ApacheLogLineMatcher> lineMatchers) {
		this.lineMatchers = lineMatchers;
	}

	private List<ApacheLogLineViewer> lineViewers;

	public List<ApacheLogLineViewer> getLineViewers() {
		return lineViewers;
	}

	public void setLineViewers(List<ApacheLogLineViewer> lineViewers) {
		this.lineViewers = lineViewers;
	}

	/*
	 * 
	 * @threadsafe
	 * 
	 * @see com.google.code.smallcrab.scan.LineScanner#scan(java.lang.String)
	 */
	@Override
	public String[] scan(String line) {
		ApacheLogPackage spliter = new ApacheLogPackage();
		spliter.split(line);// split and store segments
		boolean allMatched = true;
		if (lineMatchers != null && lineMatchers.size() != 0)
			for (LineMatcher<ApacheLogPackage> lineMatcher : lineMatchers) {
				if (!lineMatcher.match(spliter)) { // one spliter can be matched any times
					allMatched = false; // any rule doesn't matched, break matching
					break;
				}
			}
		if (!allMatched) { // any rule doesn't matched, return null
			return null;
		}
		String[] result = new String[lineViewers.size()];
		int index = 0;
		for (LineViewer<ApacheLogPackage> lineViewer : lineViewers) {
			result[index++] = lineViewer.view(spliter); // one spliter can be matched or viewed
		}
		return result;
	}


}
