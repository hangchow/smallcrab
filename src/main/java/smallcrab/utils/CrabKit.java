package smallcrab.utils;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

public class CrabKit {
	/** Returns an ImageIcon, or null if the path was invalid. */
	public static ImageIcon createImageIcon(String path) {
		path = "/images/" + path;
		java.net.URL imgURL = CrabKit.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	public static Image createImage(String path) {
		path = "/images/" + path;
		java.net.URL imgURL = CrabKit.class.getResource(path);
		if (imgURL != null) {
			return Toolkit.getDefaultToolkit().getImage(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
