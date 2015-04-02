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

public class ALGpsNear {

	private static double EARTH_RADIUS = 6378.137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s;
	}

	Map<String, String> store = new HashMap<String, String>(320 * 1024);

	public void merge(ALPackage pac, double destLong, double destLat, double distance) throws UnsupportedEncodingException {
		Map<String, String> param = null;
		String query = pac.getQuery();
		param = UrlKit.getParameterMapFromQuery(query);
		if (param == null) {
			return;
		}
		String privateKey = param.get("_token");
		String gps = param.get("action_gps");
		String time = pac.getTime();
		String longitude = null;
		String latitude = null;
		if (StringKit.isNotEmpty(gps)) {
			String[] gpsArr = StringKit.split(gps, ',');
			if (gpsArr != null && gpsArr.length == 2) {
				longitude = gpsArr[0];
				latitude = gpsArr[1];
				if (StringKit.isNotEmpty(privateKey) && StringKit.isNotEmpty(longitude) && StringKit.isNotEmpty(latitude)) {
					if (getDistance(destLat, destLong, Double.valueOf(latitude), Double.valueOf(longitude)) <= distance) {
						store.put(privateKey, longitude + "," + latitude + "," + time);
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
		ALGpsNear merge = new ALGpsNear();
		LineNumberReader reader = null;
		reader = new LineNumberReader(new FileReader(args[0]));
		
		double longitude = Double.valueOf(args[2]);
		double latitude = Double.valueOf(args[3]);
		double distance = Double.valueOf(args[4]);

		String line;
		int i = 0;
		while ((line = reader.readLine()) != null) {
			try {
				ALPackage alp = new ALPackage();
				alp.split(line);
				merge.merge(alp, longitude, latitude, distance);
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
