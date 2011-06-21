/**
 * 
 */
package com.google.code.smallcrab.swing;

import java.awt.LayoutManager;
import java.util.List;

import javax.swing.JPanel;

import com.google.code.smallcrab.analyze.FileLineAnalyzer;
import com.google.code.smallcrab.config.ConfigException;
import com.google.code.smallcrab.matcher.LineMatcher;
import com.google.code.smallcrab.viewer.LineViewer;

/**
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public abstract class AnalyzeConfigPanel<lv extends LineViewer<?>, lm extends LineMatcher<?>> extends JPanel {
	private static final long serialVersionUID = -4714616501704128141L;

	public AnalyzeConfigPanel(LayoutManager layout) {
		super(layout);
	}

	public abstract void resetConfigOutput();

	/**
	 * Create file analyzer
	 * 
	 * @return
	 */
	public abstract FileLineAnalyzer createFileLineAnalyzer();

	/**
	 * @return
	 * @throws ConfigException
	 */
	public abstract boolean isPrepared() throws ConfigException;

	public abstract boolean isAnalyzeAppend();

	public abstract boolean isAnalyzeCount();

	/**
	 * @return
	 */
	protected abstract List<lm> prepareMatchers() throws ConfigException;

	/**
	 * @return
	 * @throws ConfigException
	 */
	protected abstract List<lv> prepareViewers() throws ConfigException;

}
