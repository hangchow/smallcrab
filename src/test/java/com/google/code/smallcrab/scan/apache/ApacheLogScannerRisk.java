package com.google.code.smallcrab.scan.apache;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.code.smallcrab.analyze.LineScanner;
import com.google.code.smallcrab.utils.ApacheLogHelper;
import com.google.code.smallcrab.utils.UrlKit;

public class ApacheLogScannerRisk implements LineScanner {

	@Override
	public String[] scan(String line) {
		String[] segs = ApacheLogHelper.defaultSplit(line);
		String url = segs[4];
		String path = UrlKit.extractPath(url);
		if(path == null || !path.equals("/topcontainer/container")) {
			return null;
		}
		String query = UrlKit.extractQuery(url);
		String name = null;
		Map<String, String> map = UrlKit.getParameterMapFromQuery(query);
		if(map == null || map.size() == 0) {
			return null;
		}
		for (Iterator<Entry<String, String>> itt = map.entrySet().iterator();itt.hasNext();){
			Entry<String, String> now = itt.next();
			if(now.getValue().startsWith("http://")) {
				name = now.getKey();
				break;
			}
		}
		if(name == null) {
			return null;
		}
		String appKey = map.get("appkey");
		if(appKey == null) {
			return null;
		}
		return new String[] { appKey, name };
	}

}
