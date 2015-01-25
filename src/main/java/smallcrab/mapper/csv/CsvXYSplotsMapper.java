/**
 * 
 */
package smallcrab.mapper.csv;

import java.util.ArrayList;
import java.util.List;

import smallcrab.config.matcher.csv.CsvLineMatcher;
import smallcrab.config.viewer.csv.CsvLineViewer;
import smallcrab.mapper.Mapper;
import smallcrab.protocol.csv.CsvPackage;


/**
 * @author seanlinwang at gmail dot com
 * @date 2011-6-20
 */
public class CsvXYSplotsMapper implements Mapper {
	private List<CsvLineMatcher> lineMatchers;

	private List<CsvLineViewer> lineViewers;

	private CsvLineViewer xAxisLineViewr;

	private List<CsvLineViewer> yAxislineViewers;

	public List<CsvLineMatcher> getLineMatchers() {
		return lineMatchers;
	}

	public void setLineMatchers(List<CsvLineMatcher> lineMatchers) {
		this.lineMatchers = lineMatchers;
	}

	public List<CsvLineViewer> getLineViewers() {
		return lineViewers;
	}

	public void setLineViewers(List<CsvLineViewer> lineViewers) {
		this.lineViewers = lineViewers;
		this.yAxislineViewers = new ArrayList<CsvLineViewer>();
		for (CsvLineViewer lineViewer : lineViewers) {
			if (lineViewer.isXAxis()) {
				this.xAxisLineViewr = lineViewer;
			} else if (lineViewer.isYAxis()) {
				this.yAxislineViewers.add(lineViewer);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.code.smallcrab.analyze.LineScanner#scan(java.lang.String)
	 */
	@Override
	public String[] map(String line) {
		CsvPackage pac = new CsvPackage();
		pac.split(line);
		String[] result = new String[yAxislineViewers.size() + 1];
		int index = 1;
		for (CsvLineViewer lineViewer : yAxislineViewers) {
			result[index++] = lineViewer.view(pac);
		}
		result[0] = xAxisLineViewr.view(pac);
		return result;
	}

}
