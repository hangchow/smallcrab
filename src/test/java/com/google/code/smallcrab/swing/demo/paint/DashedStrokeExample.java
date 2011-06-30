package com.google.code.smallcrab.swing.demo.paint;

import java.awt.*;

/** An example of dashed lines with Java2D in Java 1.2.
 *
 *  From tutorial on learning Java2D at
 *  http://www.apl.jhu.edu/~hall/java/Java2D-Tutorial.html
 *
 *  1998 Marty Hall, http://www.apl.jhu.edu/~hall/java/
 */

public class DashedStrokeExample extends FontExample {
  /**
	 * 
	 */
	private static final long serialVersionUID = -5117035204270191175L;

public void paintComponent(Graphics g) {
    clear(g);
    Graphics2D g2d = (Graphics2D)g;
    drawGradientCircle(g2d);
    drawBigString(g2d);
    drawDashedCircleOutline(g2d);
  }

  protected void drawDashedCircleOutline(Graphics2D g2d) {
    g2d.setPaint(Color.blue);
    // 30 pixel line, 10 pixel gap, 10 pixel line, 10 pixel gap
    float[] dashPattern = { 30, 10, 10, 10 };
    g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_BUTT,
                                  BasicStroke.JOIN_MITER, 10,
                                  dashPattern, 0));
    g2d.draw(getCircle());
  }

  public static void main(String[] args) {
    WindowUtilities.openInJFrame(new DashedStrokeExample(),
                                 380, 400);
  }
}