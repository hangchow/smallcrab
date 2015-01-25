/**
 * 
 */
package smallcrab.mapper.java;

import smallcrab.mapper.Mapper;
import smallcrab.protocol.csv.CsvPackage;

/**
 * @author seanlinwang at gmail dot com
 * @date 2011-6-20
 */
public class CsvJavaMapper implements Mapper {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.analyze.LineScanner#scan(java.lang.String)
	 */
	@Override
	public String[] map(String line) {
		CsvPackage pac = new CsvPackage();
		pac.split(line);
		return pac.getColumns();
	}

}
