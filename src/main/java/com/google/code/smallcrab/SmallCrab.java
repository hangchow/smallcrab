package com.google.code.smallcrab;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import com.google.code.smallcrab.swing.ControlPanel;
import com.google.code.smallcrab.swing.OutputPanel;
import com.google.code.smallcrab.utils.CrabKit;

/**
 * @author seanlinwang at gmail dot com
 * @date Apr 5, 2011
 *
 */
public class SmallCrab extends JPanel {
	private static final long serialVersionUID = -7585970691360623305L;

	private static JFrame frame;

	public SmallCrab() {
		super(new BorderLayout());
		OutputPanel leftPanel = new OutputPanel();
		JPanel rightPanel = new ControlPanel(frame, leftPanel.getTaskOutput(), leftPanel.getChartPanel(), leftPanel);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setOneTouchExpandable(true);
		add(splitPane, BorderLayout.CENTER);
		setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
	}

	protected JPanel createVerticalBoxPanel() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		return p;
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		frame = new JFrame("SmallCrab");
		frame.setIconImage(CrabKit.createImage("logo/crab-icon-16.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		// Create and set up the content pane.
		JComponent contentPane = new SmallCrab();
		contentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(contentPane);

		// Display the window.
		frame.pack();
		frame.setLocationRelativeTo(null);// center of the screen
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}
}
