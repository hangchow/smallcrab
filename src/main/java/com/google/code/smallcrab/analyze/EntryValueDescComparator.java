/**
 * 
 */
package com.google.code.smallcrab.analyze;

import java.util.Comparator;
import java.util.Map.Entry;

/**
 * @author lin.wangl
 * 
 */
public class EntryValueDescComparator implements
		Comparator<Entry<String, Integer>> {

	@Override
	public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
		return o2.getValue() - o1.getValue();
	}
}
