<<<<<<< HEAD
package com.google.code.smallcrab.scan.apache;

import com.google.code.smallcrab.analyze.LineScanner;
import com.google.code.smallcrab.utils.ApacheLogHelper;
import com.google.code.smallcrab.utils.UrlKit;

public class ApacheLogScannerIdentifyAppKey implements LineScanner {

	@Override
	public String[] scan(String line) {
		String[] segs = ApacheLogHelper.defaultSplit(line);
		String path = UrlKit.extractPath(segs[4]);
		if(!"/topcontainer/container/identify".equals(path)) {
			return null;
		}
		String query = UrlKit.extractQuery(segs[4]);
		String appkey = UrlKit.extractParameterValue(query, "app_key");
		return new String[] { appkey};
	}

}
=======
package com.google.code.smallcrab.scan.apache;

import com.google.code.smallcrab.analyze.LineScanner;
import com.google.code.smallcrab.utils.ApacheLogHelper;
import com.google.code.smallcrab.utils.UrlKit;

public class ApacheLogScannerIdentifyAppKey implements LineScanner {

	@Override
	public String[] scan(String line) {
		String[] segs = ApacheLogHelper.defaultSplit(line);
		String path = UrlKit.extractPath(segs[4]);
		if(!"/topcontainer/container/identify".equals(path)) {
			return null;
		}
		String query = UrlKit.extractQuery(segs[4]);
		String appkey = UrlKit.extractParameterValue(query, "app_key");
		return new String[] { appkey};
	}

}
>>>>>>> csvsupport
