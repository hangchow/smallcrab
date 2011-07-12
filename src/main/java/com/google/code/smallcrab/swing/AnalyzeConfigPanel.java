/**
 * 
 */
package com.google.code.smallcrab.swing;

import java.awt.LayoutManager;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JPanel;

import com.google.code.smallcrab.config.ConfigException;
import com.google.code.smallcrab.matcher.LineMatcher;
import com.google.code.smallcrab.reducer.FileLineAnalyzer;
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

	public boolean isAnalyzeAppend() {
		return false;
	}

	public boolean isAnalyzeCount() {
		return false;
	}

	public boolean isAnalyzeXYSplots() {
		return false;
	}

	/**
	 * @return
	 */
	protected abstract List<lm> prepareMatchers() throws ConfigException;

	/**
	 * @return
	 * @throws ConfigException
	 */
	protected abstract List<lv> prepareViewers() throws ConfigException;

	/**
	 * @param logFile
	 * @throws IOException
	 */
	protected abstract void notifyFileChange(File logFile) throws IOException;

}
