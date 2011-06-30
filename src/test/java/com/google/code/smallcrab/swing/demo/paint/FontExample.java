package com.google.code.smallcrab.swing.demo.paint;

import java.awt.*;

/**
 * An example of using local fonts with Java2D in Java 1.2.
 * 
 * From tutorial on learning Java2D at http://www.apl.jhu.edu/~hall/java/Java2D-Tutorial.html
 * 
 * 1998 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class FontExample extends GradientPaintExample {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3572345730448140969L;

	public FontExample() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		env.getAvailableFontFamilyNames();
		setFont(new Font("Goudy Handtooled BT", Font.PLAIN, 100));
	}

	protected void drawBigString(Graphics2D g2d) {
		g2d.setPaint(Color.black);
		g2d.drawString("Java 2D", 25, 215);
	}

	public void paintComponent(Graphics g) {
		clear(g);
		Graphics2D g2d = (Graphics2D) g;
		drawGradientCircle(g2d);
		drawBigString(g2d);
	}

	public static void main(String[] args) {
		WindowUtilities.openInJFrame(new FontExample(), 380, 400);
	}
}
