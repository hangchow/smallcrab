/**
 * 
 */
package smallcrab.swing;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import smallcrab.utils.CrabKit;


/**
 * Tool bar. 
 * 
 * @author seanlinwang at gmail dot com
 * @date Jun 16, 2011
 * 
 */
public class ToolBarPanel extends JPanel {
	public static final String CMD_PAUSE = "cmd_pause";
	public static final String CMD_STOP = "cmd_stop";
	public static final String CMD_START = "cmd_start";
	public static final String CMD_SOURCE_CHOOSE = "cmd_source_choose";

	private JButton fileChooseButton, startButton, pauseButton, stopButton;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2469920128184366163L;

	public ToolBarPanel(ActionListener actionListener) {
		super();
		ImageIcon fileChooseButtonIcon = CrabKit.createImageIcon("24/EjectHot.png");
		ImageIcon leftButtonIcon = CrabKit.createImageIcon("24/PlayHot.png");
		ImageIcon middleButtonIcon = CrabKit.createImageIcon("24/PauseHot.png");
		ImageIcon rightButtonIcon = CrabKit.createImageIcon("24/StopHot.png");

		fileChooseButton = new JButton(fileChooseButtonIcon);
		fileChooseButton.setMnemonic(KeyEvent.VK_F);
		fileChooseButton.setActionCommand(CMD_SOURCE_CHOOSE);

		startButton = new JButton(leftButtonIcon);
		startButton.setMnemonic(KeyEvent.VK_S);
		startButton.setActionCommand(CMD_START);

		pauseButton = new JButton(middleButtonIcon);
		pauseButton.setMnemonic(KeyEvent.VK_P);
		pauseButton.setActionCommand(CMD_PAUSE);

		stopButton = new JButton(rightButtonIcon);
		stopButton.setMnemonic(KeyEvent.VK_T);
		stopButton.setActionCommand(CMD_STOP);

		// Listen for actions on buttons.
		fileChooseButton.addActionListener(actionListener);
		startButton.addActionListener(actionListener);
		pauseButton.addActionListener(actionListener);
		stopButton.addActionListener(actionListener);
		initButtons();

		fileChooseButton.setToolTipText("Choose analyzed file.");
		startButton.setToolTipText("Start task.");
		pauseButton.setToolTipText("Pause task.");
		stopButton.setToolTipText("Stop task.");

		add(fileChooseButton);
		add(startButton);
		add(pauseButton);
		add(stopButton);
	}

	public void clickStartButton() {
		startButton.setEnabled(false);
		pauseButton.setEnabled(true);
		stopButton.setEnabled(true);
		fileChooseButton.setEnabled(false);
	}

	public void clickPauseButton() {
		startButton.setEnabled(true);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(true);
		fileChooseButton.setEnabled(false);
	}

	public void clickStopButton() {
		startButton.setEnabled(true);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
		fileChooseButton.setEnabled(true);
	}

	public void initButtons() {
		startButton.setEnabled(false);
		pauseButton.setEnabled(false);
		stopButton.setEnabled(false);
	}

}
