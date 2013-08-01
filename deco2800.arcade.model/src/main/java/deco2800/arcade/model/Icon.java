package deco2800.arcade.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Icon is a multipurposed abstracted wrapper designed for simpler use of icons.
 * 
 * @author Leggy
 * 
 */
public class Icon {

	private BufferedImage icon;
	
	/*
	 * Notes for testing and implementations:
	 * 
	 * Graphics g = <INSERT CONTAINER HERE>.getGraphics();
	 * 
	 * g.drawImage(<ACHIEVMENT/PLAYER>.getIcon().getImage, Xcoord, Ycoord, null);
	 */

	/**
	 * Creates a new Icon
	 * 
	 * @param filepath
	 *            The filepath of the icon image file.
	 * @throws IOException
	 *             Throws IOException if the filepath cannot be resolved to an
	 *             image.
	 */
	public Icon(String filepath) throws IOException {
		/*
		 * Note that exception handling could be done in-method, however if it
		 * cannot be loaded there is no way (other than changing the return type
		 * to boolean/int and specifying error range) to communicate this.
		 */
		icon = ImageIO.read(new File(filepath));
	}

	/**
	 * Creates a new Icon given a BufferedImage icon.
	 * 
	 * @param image
	 *            The icon's image
	 */
	private Icon(BufferedImage image) {
		// TODO @require
		this.icon = image;
	}

	/**
	 * Clones an icon to preserve immutability.
	 * 
	 * @return Returns a clone of the Icon.
	 */
	public Icon clone() {
		return new Icon(cloneImage(this.icon));
	}

	/**
	 * Clones a BufferedImage. Helper method for clone and getImage() methods.
	 * 
	 * @param image
	 *            The image to be cloned
	 * @return Returns a clone of the given image
	 */
	private BufferedImage cloneImage(BufferedImage image) {
		/*
		 * Creates a new empty BufferedImage with the same dimensions of the
		 * image.
		 */
		BufferedImage clone = new BufferedImage(icon.getWidth(),
				icon.getHeight(), BufferedImage.TYPE_INT_ARGB);
		/*
		 * Creates a new graphics object on the new clone, and draws the icon
		 * onto the clone. Finally initialises a new Icon and returns it.
		 */

		Graphics g = clone.createGraphics();
		g.drawImage(icon, 0, 0, null);
		return clone;
	}

	/**
	 * Access method for the Icon's BufferedImage so that it can be displayed.
	 * 
	 * @return Returns the Icon's BufferedImage image
	 */
	public BufferedImage getImage() {
		return cloneImage(this.icon);
	}

}
