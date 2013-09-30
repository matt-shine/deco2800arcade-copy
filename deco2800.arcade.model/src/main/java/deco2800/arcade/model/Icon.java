package deco2800.arcade.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import static org.imgscalr.Scalr.*;

/**
 * Icon is a multipurposed abstracted wrapper designed for simpler use of icons.
 * 
 * @author Leggy (Lachlan Healey: lachlan.j.healey@gmail.com)
 * 
 */
public class Icon {
	private final int THUMBNAIL = 150;
	private final int PAD = 2;

	private BufferedImage icon;

	/*
	 * Notes for testing and implementations:
	 * 
	 * Graphics g = <INSERT CONTAINER HERE>.getGraphics();
	 * 
	 * g.drawImage(<ACHIEVMENT/PLAYER>.getIcon().getImage, Xcoord, Ycoord,
	 * null);
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

	/**
	 * Creates a thumbnail from the Icon, and returns it
	 * 
	 * @return Returns a thumbnail of the Icon as a Buff
	 */
	public BufferedImage getThumbnail() {
		return pad(scale(THUMBNAIL  - 2 * PAD), PAD);
	}

	/**
	 * Scales the icon, to the desired size, centering it on a square if the
	 * icon is not square.
	 * 
	 * @param size
	 *            The desired size of the image.
	 * @return Returns the Icon scaled to fit the desired size.
	 */
	private BufferedImage scale(int size) {
		if (this.icon.getWidth() == this.icon.getHeight()) {
			/*
			 * Image is a square, so we can simple resize. (Note the subtle
			 * cloning in getImage.)
			 */
			return resize(this.getImage(), size);
		} else {
			/*
			 * Image is not square, we need to resize and then reposition image.
			 */

			BufferedImage image = resize(this.getImage(), size);

			/*
			 * Creating a new canvas on which to construct new image.
			 */
			BufferedImage canvas = new BufferedImage(size, size,
					BufferedImage.TYPE_INT_ARGB);
			/*
			 * Creates a new graphics object on the canvas, and draws in the
			 * background.
			 */

			Graphics g = canvas.createGraphics();

			/*
			 * Now we have to center the rectangular image on a square canvas.
			 * To do this we'll need to consider both the portrait and landscape
			 * cases.
			 */
			int x = 0;
			int y = 0;
			if (image.getWidth() > image.getHeight()) {
				
				/*
				 * This image is landscape, so we leave x positioned at 0, and
				 * calculate y.
				 */
				y = (size - image.getHeight()) / 2;
			} else {
				/*
				 * This image is portrait, so we leave y positioned at 0, and
				 * calculate x.
				 */
				x = (size - image.getWidth()) / 2;
			}
			g.drawImage(image, x, y, null);
			return canvas;
		}
	}

}
