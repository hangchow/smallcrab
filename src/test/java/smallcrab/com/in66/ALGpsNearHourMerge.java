package smallcrab.com.in66;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import smallcrab.protocol.accesslog.ALPackage;
import smallcrab.utils.GpsKit;
import smallcrab.utils.StringKit;
import smallcrab.utils.UrlKit;

public class ALGpsNearHourMerge {
	Map<String, String> store = new LinkedHashMap<String, String>(320 * 1024);

	DateFormat df = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss", Locale.US);
	Calendar calendar = GregorianCalendar.getInstance();

	public void merge(ALPackage pac, double destLong, double destLat, double distance) throws UnsupportedEncodingException, ParseException {
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
					if (GpsKit.getDistance(destLat, destLong, Double.valueOf(latitude), Double.valueOf(longitude)) <= distance) {
						Date date = df.parse(pac.getTime());
						calendar.setTime(date);
						int hour = calendar.get(Calendar.HOUR_OF_DAY);
						if (hour > 6 && hour < 8) {
							store.put(hour + ":" + privateKey, privateKey + " " + pac.getTime() + " " + longitude + " " + latitude);
						}
					}
				}
			}
		}
	}

	public void write(Writer writer) throws IOException {
		for (Entry<String, String> entry : store.entrySet()) {
			writer.write(entry.getValue() + "\n");
		}
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		ALGpsNearHourMerge near = new ALGpsNearHourMerge();
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
				near.merge(alp, longitude, latitude, distance);
			} catch (Exception e) {
				System.out.println(line);
			}
			i++;
			if (i % 100000 == 0) {
				System.out.println("no: " + i);
			}
		}

		reader.close();

		System.out.println("entry size: " + near.getSize());

		FileWriter fw = new FileWriter(args[1]);
		near.write(fw);

	}

	private int getSize() {
		return this.store.size();
	}

}
