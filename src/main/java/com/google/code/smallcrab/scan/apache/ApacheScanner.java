package com.google.code.smallcrab.scan.apache;

import com.google.code.smallcrab.scan.LineMatcher;
import com.google.code.smallcrab.scan.LineScanner;
import com.google.code.smallcrab.scan.LineViewer;

/**
 * Default apache log scanner, contain match rules.
 * 
 * @author xalinx at gmail dot com
 * @date Dec 29, 2010
 * 
 */
public class ApacheScanner implements LineScanner {
	/**
	 * log line matcher
	 */
	private LineMatcher<ApacheSpliter>[] lineMatchers;

	public LineMatcher<ApacheSpliter>[] getLineMatchers() {
		return lineMatchers;
	}

	public void setLineMatchers(LineMatcher<ApacheSpliter>[] lineMatchers) {
		this.lineMatchers = lineMatchers;
	}

	private LineViewer<ApacheSpliter>[] lineViewers;

	public LineViewer<ApacheSpliter>[] getLineViewers() {
		return lineViewers;
	}

	public void setLineViewers(LineViewer<ApacheSpliter>[] lineViewers) {
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
		ApacheSpliter spliter = new ApacheSpliter();
		spliter.split(line);// split and store segments
		boolean allMatched = true;
		if (lineMatchers != null) { // if lineMatchers is null, it means this line matched
			for (int i = 0; i < lineMatchers.length; i++) {
				LineMatcher<ApacheSpliter> matcher = lineMatchers[i];
				if (!matcher.match(spliter)) { // one spliter can be matched any times
					allMatched = false; // any rule doesn't matched, break matching
					break;
				}
			}
		}
		if (!allMatched) { // any rule doesn't matched, return null
			return null;
		}
		String[] result = new String[lineViewers.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = lineViewers[i].view(spliter); // one spliter can be matched or viewed
		}
		return result;
	}

}
