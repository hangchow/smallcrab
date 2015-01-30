/**
 * 
 */
package smallcrab.protocol.accesslog;

import java.util.HashMap;
import java.util.Map;

import smallcrab.utils.UrlKit;


/**
 * @author lin.wangl
 * 
 */
public class ALHelper {
	private static final char SEPARATOR_CHAR = ' ';

	public static Map<String, String> defaultSplit(String line) {
		Map<String, String> segs = new HashMap<String, String>();
		split(line, segs);
		return segs;
	}

	private static void split(String line, Map<String, String> segs) {
		int start = 0;
		int end = line.indexOf(SEPARATOR_CHAR);
		buildSplits(line, segs, "ip", start, end);
		
		start = end + 1;
		end = line.indexOf(SEPARATOR_CHAR, start);
		
	     // forward
        start = end + 1;
        end = line.indexOf('[', start);
        buildSplits(line, segs, "forward", start, end);

		// time
		start = end + 1;// ' ['
		end = line.indexOf(']', start);
		buildSplits(line, segs, "time", start, end);
		
		// method
		start = end + 3; // '] "'
		end = line.indexOf(SEPARATOR_CHAR, start);
		buildSplits(line, segs, "method", start, end);
		
		// request
		start = end + 1;// ' '
		end = line.indexOf(SEPARATOR_CHAR, start);
		buildSplits(line, segs, "request", start, end);
		
		// protocol
		start = end + 1; 
		end = UrlKit.findQut(start, line);
		buildSplits(line, segs, "protocol", start, end);
		
		// code
		start = end + 2;
		end = line.indexOf(SEPARATOR_CHAR, start);
		buildSplits(line, segs, "status", start, end);
		
		// size
		start = end + 1;
		end = line.indexOf(SEPARATOR_CHAR, start);
		buildSplits(line, segs, "size", start, end);
		
		// referer
		start = end + 2; // ' "'
		end = UrlKit.findQut(start, line);
		buildSplits(line, segs, "referer", start, end);
		
		// agent
		start = end + 3;// '" "
		end = UrlKit.findQut(start, line);
		buildSplits(line, segs, "agent", start, end);
		
		// host
		start = end + 2; // ' "'
		end = line.indexOf(SEPARATOR_CHAR, start);
		buildSplits(line, segs, "host", start, end);
		
		start = end + 1; // ' "'
		end = line.indexOf(SEPARATOR_CHAR, start);
		
		start = end + 1; // ' "'
		end = line.indexOf(SEPARATOR_CHAR, start);
		
		start = end + 1; // ' "'
		end = line.indexOf(SEPARATOR_CHAR, start);
		
		start = end + 1; // ' "'
		end = line.indexOf(SEPARATOR_CHAR, start);
		
		start = end + 1; // ' "'
		end = line.indexOf(SEPARATOR_CHAR, start);
		
		start = end + 1; // ' "'
		end = line.indexOf(SEPARATOR_CHAR, start);
		
		start = end + 1;
		end = line.length();
		buildSplits(line, segs, "period", start, end);
	}

	private static void buildSplits(String line, Map<String, String> segs, String key, int start, int end) {
		segs.put(key, line.substring(start, end));
	}

}
