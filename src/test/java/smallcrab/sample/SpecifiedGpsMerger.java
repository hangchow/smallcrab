package smallcrab.sample;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import smallcrab.protocol.accesslog.ALPackage;
import smallcrab.utils.StringKit;
import smallcrab.utils.UrlKit;

public class SpecifiedGpsMerger {
	Set<String> specifiedTokens = new HashSet<String>();
	Map<String, String> store = new HashMap<String, String>();

	public SpecifiedGpsMerger(String specifiedCsvPath) throws IOException {
		FileReader fileReader = new FileReader(specifiedCsvPath);
		LineNumberReader reader = new LineNumberReader(fileReader);

		String line;
		while ((line = reader.readLine()) != null) {
			if (line != null) {
				specifiedTokens.add(line.trim());
			}
		}

		reader.close();
		
		System.out.println("specified size: " + specifiedTokens.size());
	}

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
					if (specifiedTokens.contains(privateKey)) {
						store.put(privateKey, longitude + "," + latitude);
					}
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
		SpecifiedGpsMerger merge = new SpecifiedGpsMerger(args[0]);
		LineNumberReader reader = null;
		reader = new LineNumberReader(new FileReader(args[1]));

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

		System.out.println("gps size: " + merge.getSize());

		FileWriter fw = new FileWriter(args[2]);
		merge.write(fw);

	}

	private int getSize() {
		return this.store.size();
	}

}
