/**
 * 
 */
package smallcrab.swing.accesslog;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/**
 * @author xalinx at gmail dot com
 * @date Dec 30, 2010
 */
public abstract class ALConfigTableModel extends AbstractTableModel implements TableModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 62142799192158660L;
	
	/**
	 * 
	 */
	public static final String HOST = "host";
	
	/**
	 * 
	 */
	public static final String QUERY = "query";

	/**
	 * 
	 */
	public static final String PATH = "path";

	/**
	 * 
	 */
	public static final String REFERER = "referer";

	/**
	 * 
	 */
	public static final String STATUS = "status";

	/**
	 * 
	 */
	public static final String METHOD = "method";

	/**
	 * 
	 */
	public static final String AGENT = "agent";

	/**
	 * 
	 */
	public static final String IP = "ip";

	/**
	 * one log line.
	 */
	public static final String ALL = "all";

}
