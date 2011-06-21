package com.google.code.smallcrab.scan.apache;

import com.google.code.smallcrab.analyze.LineScanner;
import com.google.code.smallcrab.utils.ApacheLogHelper;
import com.google.code.smallcrab.utils.UrlKit;

public class ApacheLogScannerContainerLoginSuccess implements LineScanner {

	@Override
	public String[] scan(String line) {
		String[] segs = ApacheLogHelper.defaultSplit(line);
		String path = UrlKit.extractPath(segs[4]);
		if(!"/topcontainer/container".equals(path)) {
			return null;
		}
		String reffer = segs[7];
		if (reffer == null) {
			
		} else if (!reffer.startsWith("http://login.taobao.com")) {
			reffer = "others";
		} else {
			reffer = UrlKit.extractPath(reffer);
		}
		return new String[] { reffer};
	}

}
