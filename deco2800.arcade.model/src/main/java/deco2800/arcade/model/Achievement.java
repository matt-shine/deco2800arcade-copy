package deco2800.arcade.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Achievement {

	// TODO shared between server & client?

	/*
	 * Notes for testing and implementations:
	 * 
	 * Graphics g = <INSERT COONTAINER HERE>.getGraphics();
	 * 
	 * g.drawImage(Achievement.getIcon(), Xcoord, Ycoord, null);
	 */

	private String description;

	private final BufferedImage icon;

	/**
	 * Creates a new Achievement given the description.
	 * 
	 * @param description
	 *            The Achievement's description.
	 */
	public Achievement(String description) {
		this.description = description;
		this.icon = null;
	}

	/**
	 * Creates a new Achievement given the description and icon.
	 * 
	 * @param description
	 *            The Achievement's description.
	 * @param filepath
	 *            The filepath to the Achievement's icon image file.
	 * @throws IOException
	 *             Throws exception when the image cannot be found at the
	 *             designated filepath.
	 */
	public Achievement(String description, String filepath) throws IOException {
		/*
		 * Note that exception handling could be done in-method, however if it
		 * cannot be loaded there is no way (other than changing the return type
		 * to boolean/int and specifying error range) to communicate this.
		 */
		this.description = description;
		icon = ImageIO.read(new File(filepath));

	}

	/**
	 * Creates an achievement identical to another.
	 * 
	 * @param achievement
	 *            The original Achievement to be copied.
	 */
	public Achievement(Achievement achievement) {
		/*
		 * Preserving immutability.
		 */
		this.description = achievement.getDescription();
		this.icon = achievement.getIcon();

	}

	/**
	 * Access method for Achievement's description.
	 * 
	 * @return The Achievement's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Access method for the Achievement's icon.
	 * 
	 * @return The Achievement's icon.
	 */
	public BufferedImage getIcon() {
		/*
		 * This method clones the icon and then returns it, maintaining
		 * immutability.
		 * 
		 * Creates a new empty BufferedImage with the same dimensions of the
		 * Icon.
		 */
		BufferedImage clone = new BufferedImage(icon.getWidth(),
				icon.getHeight(), BufferedImage.TYPE_INT_ARGB);
		/*
		 * Creates a new graphics object on the new clone, and draws the icon
		 * onto the clone, hence cloning it. Finally returns the clone.
		 */

		Graphics g = clone.createGraphics();
		g.drawImage(icon, 0, 0, null);
		return clone;
	}

}
