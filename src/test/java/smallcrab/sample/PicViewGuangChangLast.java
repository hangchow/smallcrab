package smallcrab.sample;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import smallcrab.protocol.accesslog.ALPackage;
import smallcrab.utils.StringKit;
import smallcrab.utils.UrlKit;

/**
 * 广场页曝光图片次数
 * 
 * @FIXME 最后的没做好
 * 
 * @author seanwang xalinx@gmail.com
 * 
 */
public class PicViewGuangChangLast {
	private final String REQUEST_EXPOSURE = "/onepiece/exposure";

	private Map<String, Integer> store = new HashMap<String, Integer>(32 * 1024);

	public String[] dealWithIds(String request, String ids) {
		if (StringKit.isEmpty(ids)) {
			return null;
		}
		String[] result = StringKit.split(ids, ',');
		for (int i = 0; i < result.length; i++) {
			String seg = result[i];
			int under_line_index = seg.indexOf('_');
			if (under_line_index >= 0) {
				result[i] = seg.substring(0, under_line_index);
			} else {
				result[i] = seg;
			}
		}
		return result;
	}

	public void merge(ALPackage pac) throws UnsupportedEncodingException {
		String request = pac.getRequest();
		if (!request.startsWith(REQUEST_EXPOSURE)) { // discover, realtime
			return;
		}
		Map<String, String> param = null;
		try {
			param = UrlKit.getParameterMapFromQuery(pac.getQuery());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		String actionName = param.get("action");
		if (!"discover".equalsIgnoreCase(actionName)) {
			return;
		}
		String ids = param.get("ids");
		if (ids != null && ids.length() > 0) {
			String[] picIds = dealWithIds(request, ids);
			if (picIds != null) {
				for (String key : picIds) {
					if (store.containsKey(key)) {
						int value = store.get(key);
						store.put(key, value + 1);
					} else {
						store.put(key, 1);
					}
				}
			}
		}
	}

	public void write(Writer writer) throws IOException {
		for (Entry<String, Integer> entry : store.entrySet()) {
			writer.write(entry.getKey() + "," + entry.getValue() + "\n");
		}
		writer.close();
	}

	private int getSize() {
		return this.store.size();
	}
	
	public static void main(String[] args) throws IOException {
		PicViewGuangChangLast merge = new PicViewGuangChangLast();
		LineNumberReader reader = null;
		reader = new LineNumberReader(new FileReader(args[0]));

		String line;
		int i = 0;
		while ((line = reader.readLine()) != null) {
			try {
				ALPackage alp = new ALPackage();
				alp.split(line);
				merge.merge(alp);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(line);
			}
			i++;
			if (i % 100000 == 0) {
				System.out.println("no:" + i + " size:" + merge.getSize());
			}
		}

		reader.close();

		System.out.println("entry size: " + merge.getSize());

		FileWriter fw = new FileWriter(args[1]);
		merge.write(fw);

	}


}
