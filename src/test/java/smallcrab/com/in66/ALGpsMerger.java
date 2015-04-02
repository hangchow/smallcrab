package smallcrab.com.in66;
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

public class ALGpsMerger {
	Map<String, String> store = new HashMap<String, String>(320 * 1024);

	public void merge(ALPackage pac) throws UnsupportedEncodingException {
		Map<String, String> param = null;
		String query = pac.getQuery();
		param = UrlKit.getParameterMapFromQuery(query);
		if (param == null) {
			return;
		}
		String privateKey = param.get("_token");
		String gps = param.get("action_gps");
		String longitude = null;
		String latitude = null;
		if (StringKit.isNotEmpty(gps)) {
			String[] gpsArr = StringKit.split(gps, ',');
			if (gpsArr != null && gpsArr.length == 2) {
				longitude = gpsArr[0];
				latitude = gpsArr[1];
				if (StringKit.isNotEmpty(privateKey) && StringKit.isNotEmpty(longitude) && StringKit.isNotEmpty(latitude)) {
					store.put(privateKey, longitude + "," + latitude);
				}
			}
		}
	}

	public void write(Writer writer) throws IOException {
		for (Entry<String, String> entry : store.entrySet()) {
			writer.write(entry.getKey() + "," + entry.getValue() + "\n");
		}
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		ALGpsMerger merge = new ALGpsMerger();
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
				System.out.println("no: " + i);
			}
		}

		reader.close();

		System.out.println("entry size: " + merge.getSize());

		FileWriter fw = new FileWriter(args[1]);
		merge.write(fw);

	}

	private int getSize() {
		return this.store.size();
	}

}
