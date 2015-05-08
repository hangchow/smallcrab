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

public class SpecifiedQps {
	Set<String> specifiedPaths = new HashSet<String>();
	Map<String, Map<String, Integer>> pathQps = new HashMap<String, Map<String, Integer>>();
	

	public SpecifiedQps(String specifiedCsvPath) throws IOException {
		FileReader fileReader = new FileReader(specifiedCsvPath);
		LineNumberReader reader = new LineNumberReader(fileReader);

		String line;
		while ((line = reader.readLine()) != null) {
			if (line != null) {
				specifiedPaths.add(line.trim());
			}
		}

		reader.close();

		System.out.println("specified size: " + specifiedPaths.size());
	}

	public void merge(ALPackage pac) throws UnsupportedEncodingException {
		String path = pac.getPath();
		if (specifiedPaths.contains(path)) {
			Map<String, Integer> qps = pathQps.get(path);
			if (qps == null) {
				qps = new HashMap<String, Integer>();
				pathQps.put(path, qps);
			}
			String time = pac.getTime();
			Integer count = qps.get(time);
			if (count == null) {
				count = 1;
			} else {
				count++;
			}
			qps.put(time, count);
		}
	}

	public void write(Writer writer) throws IOException {
		for (Entry<String, Map<String, Integer>> entry : pathQps.entrySet()) {
			String path = entry.getKey();
			Map<String, Integer> qps = entry.getValue();
			int count = 0;
			String time = null;
			for (Entry<String, Integer> qpsEntry: qps.entrySet()) {
				 Integer nowCount = qpsEntry.getValue();
				 String nowTime = qpsEntry.getKey();
				 if (nowCount > count) {
					 count = nowCount;
					 time = nowTime;
				 }
			}
			writer.write(path + "," + count + "," + time + "\n");
		}
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		SpecifiedQps merge = new SpecifiedQps(args[0]);
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

		FileWriter fw = new FileWriter(args[2]);
		merge.write(fw);

	}

}
