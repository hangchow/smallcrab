package smallcrab.sample;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class GetAliyunAddressMapper {
	public static final String aliyun_interface = "http://gc.ditu.aliyun.com/regeocoding?l=";

	public static String getHttpResult(String path) throws InterruptedException {
		URL url;
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		try {
			String res = "";
			url = new URL(path);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(1 * 1000);
			// InputStream inStream = conn.getInputStream();
			Thread.sleep(60);
			java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				res += line;
			}
			conn.disconnect();
			return res;
		} catch (IOException e) {
			System.out.println(date + "aliyun error: " + path);
			Thread.sleep(90);
			e.printStackTrace();
		}
		return "error";
	}

	public static String[] getAddress(String lon, String lat) throws IOException, InterruptedException {
		String path = aliyun_interface + lat + ',' + lon;
		String res = getHttpResult(path);
		int j = 0;
		while (res.equals("error") & j < 5) {
			res = getHttpResult(path);
			j++;
		}
		String[] address = null;
		try {
			address = getAddress(res);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return address;
	}

	public static String[] getAddress(String res) throws JSONException {
		JSONObject dataJson;
		String[] locaList;
		dataJson = new JSONObject(res);
		JSONArray datalist = dataJson.getJSONArray("addrList");
		JSONObject data = datalist.getJSONObject(1);
		String admName = data.get("admName").toString();
		locaList = admName.split(",");
		String[] result = new String[3];
		if (locaList.length == 2) {
			result[0] = locaList[0];
			result[1] = locaList[1];
			result[2] = locaList[1];
		} else if (locaList.length == 1) {
			result[0] = locaList[0];
			result[1] = locaList[0];
			result[2] = locaList[0];
		} else if (locaList.length >= 3) {
			result[0] = locaList[0];
			result[1] = locaList[1];
			result[2] = locaList[2];
		}
		return result;

	}
}
