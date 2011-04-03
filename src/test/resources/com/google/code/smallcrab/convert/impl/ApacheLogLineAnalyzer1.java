package com.google.code.smallcrab.analyze.impl;

import org.apache.commons.lang.StringUtils;

import com.google.code.smallcrab.analyze.LineAnalyzer;

public class ApacheLogLineAnalyzer1 implements LineAnalyzer {

	@Override
	public String[] analyze(String line) {
		StringUtils.split(line, ' ');
		return null;
	}

}
