package com.google.code.smallcrab.mapper.accesslog;

import java.util.List;

import com.google.code.smallcrab.config.matcher.LineMatcher;
import com.google.code.smallcrab.config.matcher.accesslog.ALLineMatcher;
import com.google.code.smallcrab.config.viewer.accesslog.ALLineViewer;
import com.google.code.smallcrab.mapper.Mapper;
import com.google.code.smallcrab.protocol.accesslog.ALPackage;

/**
 * Default apache log scanner, contain match rules.
 * 
 * @author xalinx at gmail dot com
 * @date Dec 29, 2010
 * 
 */
public class ALCountMapper implements Mapper {
	/**
	 * log line matcher
	 */
	private List<ALLineMatcher> lineMatchers;

	public List<ALLineMatcher> getLineMatchers() {
		return lineMatchers;
	}

	public void setLineMatchers(List<ALLineMatcher> lineMatchers) {
		this.lineMatchers = lineMatchers;
	}

	private List<ALLineViewer> lineViewers;

	public List<ALLineViewer> getLineViewers() {
		return lineViewers;
	}

	public void setLineViewers(List<ALLineViewer> lineViewers) {
		this.lineViewers = lineViewers;
	}

	/*
	 * 
	 * @threadsafe
	 * 
	 * @see com.google.code.smallcrab.scan.LineScanner#scan(java.lang.String)
	 */
	@Override
	public String[] map(String line) {
		ALPackage pac = new ALPackage();
		pac.split(line);// split and store segments
		boolean allMatched = true;
		if (lineMatchers != null && lineMatchers.size() != 0)
			for (LineMatcher<ALPackage> lineMatcher : lineMatchers) {
				if (!lineMatcher.match(pac)) { // one pac can be matched any times
					allMatched = false; // any rule doesn't matched, break matching
					break;
				}
			}
		if (!allMatched) { // any rule doesn't matched, return null
			return null;
		}
		String[] result = new String[lineViewers.size()];
		int index = 0;
		for (ALLineViewer lineViewer : lineViewers) {
			result[index++] = lineViewer.view(pac); // one pac can be matched or viewed
		}
		return result;
	}


}
