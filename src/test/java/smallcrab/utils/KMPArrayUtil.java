package smallcrab.utils;

import java.io.UnsupportedEncodingException;

import smallcrab.utils.ArrayKit;

/**
 * ArrayUtil
 * <p/>
 * Author By: junshan Created Date: 2010-12-27 16:17:23
 */
public class KMPArrayUtil {

	public static byte[] arrayReplace(byte[] org, byte[] search, byte[] replace, int startIndex) throws UnsupportedEncodingException {
		int index = indexOf(org, search, startIndex);
		if (index != -1) {
			int newLength = org.length + replace.length - search.length;
			byte[] newByte = new byte[newLength];
			System.arraycopy(org, 0, newByte, 0, index);
			System.arraycopy(replace, 0, newByte, index, replace.length);
			System.arraycopy(org, index + search.length, newByte, index + replace.length, org.length - index - search.length);
			int newStart = index + replace.length;
			// String newstr = new String(newByte, "GBK");
			// System.out.println(newstr);
			if ((newByte.length - newStart) > replace.length) {
				return arrayReplace(newByte, search, replace, newStart);
			}
			return newByte;
		} else {
			return org;
		}
	}

	public static int indexOf(byte[] org, byte[] search) {
		return indexOf(org, search, 0);
	}

	public static int indexOf(byte[] org, byte[] search, int startIndex) {
		KMPMatcher kmpMatcher = new KMPMatcher();
		kmpMatcher.computeFailure4Byte(search);
		return kmpMatcher.indexOf(org, startIndex);
		// return com.alibaba.common.lang.ArrayUtil.indexOf(org, search);
	}

	public static int lastIndexOf(byte[] org, byte[] search) {
		return lastIndexOf(org, search, 0);
	}

	public static int lastIndexOf(byte[] org, byte[] search, int fromIndex) {
		KMPMatcher kmpMatcher = new KMPMatcher();
		kmpMatcher.computeFailure4Byte(search);
		return kmpMatcher.lastIndexOf(org, fromIndex);
	}

	static class KMPMatcher {
		private int[] failure;
		private int matchPoint;
		private byte[] bytePattern;

		public int indexOf(byte[] text, int startIndex) {
			int j = 0;
			if (text.length == 0 || startIndex > text.length)
				return -1;

			for (int i = startIndex; i < text.length; i++) {
				while (j > 0 && bytePattern[j] != text[i]) {
					j = failure[j - 1];
				}
				if (bytePattern[j] == text[i]) {
					j++;
				}
				if (j == bytePattern.length) {
					matchPoint = i - bytePattern.length + 1;
					return matchPoint;
				}
			}
			return -1;
		}

		public int lastIndexOf(byte[] text, int startIndex) {
			matchPoint = -1;
			int j = 0;
			if (text.length == 0 || startIndex > text.length)
				return -1;

			for (int i = startIndex; i < text.length; i++) {
				while (j > 0 && bytePattern[j] != text[i]) {
					j = failure[j - 1];
				}
				if (bytePattern[j] == text[i]) {
					j++;
				}
				if (j == bytePattern.length) {
					matchPoint = i - bytePattern.length + 1;
					if ((text.length - i) > bytePattern.length) {
						j = 0;
						continue;
					}
					return matchPoint;
				}
			}
			return matchPoint;
		}

		public void computeFailure4Byte(byte[] patternStr) {
			bytePattern = patternStr;
			int j = 0;
			int len = bytePattern.length;
			failure = new int[len];
			for (int i = 1; i < len; i++) {
				while (j > 0 && bytePattern[j] != bytePattern[i]) {
					j = failure[j - 1];
				}
				if (bytePattern[j] == bytePattern[i]) {
					j++;
				}
				failure[i] = j;
			}
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] org = "deabcdefdegh".getBytes("GBK");
		byte[] search = "de".getBytes("GBK");
		for (int t = 0; t < 10; t++) {
			long t1 = 0;
			long t2 = 0;
			long times = 100000;
			for (int i = 0; i < times; i++) {
				long s1 = System.nanoTime();
				lastIndexOf(org, search);
				long s2 = System.nanoTime();
				ArrayKit.lastIndexOf(org, search);
				long s3 = System.nanoTime();
				t1 = t1 + (s2 - s1);
				t2 = t2 + (s3 - s2);
			}
			System.out.println("kmp=" + t1 / times + ",ali=" + t2 / times);
		}
	}
}
