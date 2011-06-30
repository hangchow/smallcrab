package com.google.code.smallcrab.scan.apache;

import com.google.code.smallcrab.analyze.LineScanner;
import com.google.code.smallcrab.utils.ApacheLogHelper;
import com.google.code.smallcrab.utils.StringKit;

public class ApacheLogScannerAgentCode implements LineScanner {

	@Override
	public String[] scan(String line) {
		String[] segs = ApacheLogHelper.defaultSplit(line);
		String code = segs[5];
		String agent = segs[8];
		
		if(agent.startsWith("Mozilla/")) {
			agent = StringKit.split(agent, ' ')[0];
		} else if(agent.startsWith("Java/")) {
			agent =  StringKit.split(agent, '_')[0];
		}
		return new String[] { agent, code };
	}

}
