package smallcrab.sample;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import smallcrab.protocol.accesslog.ALPackage;
import smallcrab.utils.StringKit;
import smallcrab.utils.UrlKit;

public class Gps {
	Set<String> specifiedTokens = new HashSet<String>();

	public Gps(String specifiedCsvPath) throws IOException {
		System.out.println("tokens from:" + specifiedCsvPath);
		LineNumberReader reader = new LineNumberReader(new FileReader(specifiedCsvPath));

		String line;
		while ((line = reader.readLine()) != null) {
			if (line != null) {
				specifiedTokens.add(line.trim());
			}
		}

		reader.close();

		System.out.println("tokens size:" + specifiedTokens.size());
	}
	
	private static final SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

	public void merge(Writer writer, ALPackage pac) throws IOException {
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
						long milli = 0;
						try {
							milli = format.parse(pac.getTime()).getTime();
						} catch (ParseException e) {
							e.printStackTrace();
						}
						writer.write(privateKey + "," + longitude + "," + latitude + "," + milli + "," + pac.getPath() + "\n");
					}
				}
			}
		}
	}

	/**
	 * @param args
	 *            tokens csv, log file, out csv
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Gps worker = new Gps(args[0]);
		LineNumberReader reader = new LineNumberReader(new FileReader(args[1]));
		FileWriter writer = new FileWriter(args[2]);

		String line;
		int i = 0;
		while ((line = reader.readLine()) != null) {
			try {
				ALPackage alp = new ALPackage();
				alp.split(line);
				worker.merge(writer, alp);
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
		writer.close();
	}


}
