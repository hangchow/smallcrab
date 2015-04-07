package smallcrab.com.in66;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import smallcrab.protocol.accesslog.ALPackage;
import smallcrab.utils.StringKit;
import smallcrab.utils.UrlKit;

public class ALGpsSingle {
	List<String> store = new ArrayList<String>();

	public void merge(ALPackage pac, String token) throws UnsupportedEncodingException {
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
				if (StringKit.isNotEmpty(privateKey) && privateKey.equals(token)) {
					if (StringKit.isNotEmpty(longitude) && StringKit.isNotEmpty(latitude)) {
						store.add(token + " " + pac.getTime()+ " " + longitude + " " + latitude);
					}
				}
			}
		}
	}

	public void write(Writer writer) throws IOException {
		for (String entry : store) {
			writer.write(entry + "\n");
		}
	}

	public static void main(String[] args) throws IOException {
		ALGpsSingle single = new ALGpsSingle();
		LineNumberReader reader = null;
		String readFilename = args[0];
		String writeFilename = args[1];
		String token = args[2];
		reader = new LineNumberReader(new FileReader(readFilename));

		String line;
		int i = 0;
		while ((line = reader.readLine()) != null) {
			try {
				ALPackage alp = new ALPackage();
				alp.split(line);
				single.merge(alp, token);
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

		FileWriter fw = new FileWriter(writeFilename);
		single.write(fw);
		fw.close();
	}

}
