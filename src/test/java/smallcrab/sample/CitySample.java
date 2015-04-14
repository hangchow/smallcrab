package smallcrab.sample;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

import smallcrab.utils.StringKit;

public class CitySample {

	public static void main(String[] args) throws InterruptedException, IOException {
		FileReader fileReader = new FileReader(args[0]);
		LineNumberReader reader = new LineNumberReader(fileReader);

		FileWriter fileWriter = new FileWriter(args[1]);

		String line;
		int i = 0;
		while ((line = reader.readLine()) != null) {
			System.out.println(++i);
			line = line.trim();
			if (line != null) {
				String[] segs = StringKit.split(line, ',');
				String[] names;
				while(true) {
				try {
					names = GetAliyunAddressMapper.getAddress(segs[1], segs[2]);
					break;
				} catch (IOException e) {
					e.printStackTrace();
				}
				}
				String cityName = names[1];
				if (StringKit.isNotEmpty(cityName)) {
					fileWriter.write(segs[0] + "," + cityName +"\n");
				}
			}
		}

		fileReader.close();
		reader.close();
		fileWriter.close();
	}

}
