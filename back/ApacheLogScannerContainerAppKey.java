<<<<<<< HEAD
package com.google.code.smallcrab.scan.apache;

import com.google.code.smallcrab.analyze.LineScanner;
import com.google.code.smallcrab.utils.ApacheLogHelper;
import com.google.code.smallcrab.utils.UrlKit;

public class ApacheLogScannerContainerAppKey implements LineScanner {

	@Override
	public String[] scan(String line) {
		String[] segs = ApacheLogHelper.defaultSplit(line);
		String url = segs[4];
		String path = UrlKit.extractPath(url);
		if(path == null || !path.equals("/topcontainer/container")) {
			return null;
		}
		String query = UrlKit.extractQuery(url);
		String appkey = UrlKit.extractParameterValue(query, "appkey");
		return new String[] { appkey };
	}

}
=======
package com.google.code.smallcrab.scan.apache;

import com.google.code.smallcrab.analyze.LineScanner;
import com.google.code.smallcrab.utils.ApacheLogHelper;
import com.google.code.smallcrab.utils.UrlKit;

public class ApacheLogScannerContainerAppKey implements LineScanner {

	@Override
	public String[] scan(String line) {
		String[] segs = ApacheLogHelper.defaultSplit(line);
		String url = segs[4];
		String path = UrlKit.extractPath(url);
		if(path == null || !path.equals("/topcontainer/container")) {
			return null;
		}
		String query = UrlKit.extractQuery(url);
		String appkey = UrlKit.extractParameterValue(query, "appkey");
		return new String[] { appkey };
	}

}
>>>>>>> csvsupport
