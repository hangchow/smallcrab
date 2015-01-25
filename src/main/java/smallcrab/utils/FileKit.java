package smallcrab.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 * @author seanwang xalinx@gmail.com
 *
 */
public class FileKit {

	/**
	 * @param file
	 * @return file total line numbers
	 * @throws IOException
	 */
	public static long getLines(File file) {
		if (file == null || !file.exists()) {
			return 0;
		}
		int lines = 0;
		long fileLength = file.length();
		LineNumberReader lrf = null;
		try {
			lrf = new LineNumberReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			return 0;
		}
		if (lrf != null) {
			try {
				lrf.skip(fileLength);
				lines = lrf.getLineNumber();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			} finally {
				try {
					lrf.close();
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			}
		}
		return lines;
	}
}
