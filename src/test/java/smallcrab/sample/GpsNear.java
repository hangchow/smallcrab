package smallcrab.sample;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import smallcrab.protocol.accesslog.ALPackage;
import smallcrab.utils.StringKit;
import smallcrab.utils.UrlKit;

/**
 * 附近最后出现的
 * 
 * @FIXME 最后的没做好
 * 
 * @author seanwang xalinx@gmail.com
 * 
 */
public class GpsNear {
	private static class Loc {
		String file;
		double longitude;
		double latitude;
	}

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

	List<String> store = new ArrayList<String>();
	
	
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
	public static Map<String,FileWriter> fileMap = new HashMap<String, FileWriter>();
	
	public void merge(ALPackage pac, List<Loc> locations, double distance) throws UnsupportedEncodingException, ParseException {
		Map<String, String> param = null;
		String query = pac.getQuery();
		param = UrlKit.getParameterMapFromQuery(query);
		if (param == null) {
			return;
		}
		String privateKey = param.get("_token");
		String gps = param.get("action_gps");
		long time = simpleDateFormat.parse(pac.getTime()).getTime();
		String longitude = null;
		String latitude = null;
		if (StringKit.isNotEmpty(gps)) {
			String[] gpsArr = StringKit.split(gps, ',');
			if (gpsArr != null && gpsArr.length == 2) {
				longitude = gpsArr[0];
				latitude = gpsArr[1];
				if (StringKit.isNotEmpty(privateKey) && StringKit.isNotEmpty(longitude) && StringKit.isNotEmpty(latitude)) {
					for (Loc loc : locations) {
						double destLong = loc.longitude;
						double destLat = loc.latitude;
						if (getDistance(destLat, destLong, Double.valueOf(latitude), Double.valueOf(longitude)) <= distance) {
							store.add(privateKey + "," + longitude + "," + latitude + "," + time + "," + pac.getPath());
							try {
								fileMap.get(loc.file).write(privateKey + "," + longitude + "," + latitude + "," + time + "," + pac.getPath()+"\n");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	public void write(Writer writer) throws IOException {
		for (String entry : store) {
			writer.write(entry + "\n");
		}
		writer.close();
	}

	/**
	 * @param args
	 *            log file,target gps file, distance
	 * 
	 *            gps file:
	 * 
	 *            <code>
	 * 				120.112779,30.305883,file_out
	 *              120.112779,30.305883,file_out
	 *            </code>
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		GpsNear merge = new GpsNear();
		LineNumberReader reader = null;
		reader = new LineNumberReader(new FileReader(args[0]));
		
		LineNumberReader configReader = null;
		configReader = new LineNumberReader(new FileReader(args[1]));
		
		List<Loc> locs = new ArrayList<GpsNear.Loc>();
		String configLine = null;
		while ((configLine = configReader.readLine()) != null) {
			String[] lineString = configLine.split(",");
			Loc loc = new Loc();
			loc.longitude = Double.valueOf(lineString[0]);
			loc.latitude = Double.valueOf(lineString[1]);
			loc.file = lineString[2];
			System.out.println(loc.latitude+":"+loc.longitude);
			fileMap.put(loc.file, new FileWriter(loc.file));
			locs.add(loc);
		}
		
//		double longitude = Double.valueOf(args[2]);
//		double latitude = Double.valueOf(args[3]);
		double distance = Double.valueOf(args[2]);

		String line;
		int i = 0;
		while ((line = reader.readLine()) != null) {
			try {
				ALPackage alp = new ALPackage();
				alp.split(line);
				merge.merge(alp, locs, distance);
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
			
		for (int j = 0; j < locs.size(); j++) {
			fileMap.get(locs.get(j).file).close();
		}
		System.out.println("complete");
//		FileWriter fw = new FileWriter(args[1]);
//		merge.write(fw,locs.get(j).file);
//		System.out.println("entry size: " + merge.getSize());

		

	}

	private int getSize() {
		return this.store.size();
	}

}
