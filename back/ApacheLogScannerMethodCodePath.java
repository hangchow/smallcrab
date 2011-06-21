package com.google.code.smallcrab.scan.apache;

import com.google.code.smallcrab.analyze.LineScanner;
import com.google.code.smallcrab.utils.ApacheLogHelper;
import com.google.code.smallcrab.utils.UrlKit;

public class ApacheLogScannerMethodCodePath implements LineScanner {

	@Override
	public String[] scan(String line) {
		String[] segs = ApacheLogHelper.defaultSplit(line);
		String method = segs[3];
		String path = UrlKit.extractPath(segs[4]);
		String code = segs[5];
		return new String[] { method, code, path };
	}

}
