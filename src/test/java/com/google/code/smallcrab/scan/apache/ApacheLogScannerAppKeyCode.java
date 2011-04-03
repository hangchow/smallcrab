package com.google.code.smallcrab.scan.apache;

import com.google.code.smallcrab.scan.LineScanner;
import com.google.code.smallcrab.utils.ApacheLogHelper;
import com.google.code.smallcrab.utils.UrlKit;

public class ApacheLogScannerAppKeyCode implements LineScanner {

	@Override
	public String[] scan(String line) {
		String[] segs = ApacheLogHelper.defaultSplit(line);
		String code = segs[5];
		String query = UrlKit.extractQuery(segs[4]);
		String appkey = UrlKit.extractParameterValue(query, "app_key");
		return new String[] { appkey, code};
	}

}
