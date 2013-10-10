
package deco2800.arcade.junglejump;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import deco2800.arcade.junglejump.GUI.junglejump;

public class Platform {
	
	private int width, height, xPos, yPos;
	private boolean active;
	public boolean climbable = false;
	private Texture platText;
	public boolean visible = true;
	private boolean inverted;
	private enum world {
		WORLD_ONE, WORLD_TWO, WORLD_THREE
	}
	private world currentWorld;
	public char platType;
	
	/**
	 * Platform constructor
	 * Takes width, height and X and Y position as parameters
	 */
	public Platform(char type, boolean flipped, int pX, int pY, int pWidth, int pHeight) {
		this.width = pWidth;
		this.height = pHeight;
		this.xPos = pX;
		this.yPos = pY;
		this.active = false;
		this.inverted = flipped;
		currentWorld = world.WORLD_ONE; // Placeholder 
		platType = type;
		setTexture(type);
	}
	
	public void setTexture(int type) {
		
		this.width = 40;
		this.height = 40;
		
		String platformType = "";
		switch(type) {
		case '-': 
			platformType = "branch";
			this.height = 20;
			break;
		case '<':
			platformType = "branch_end";
			this.height = 20;
			break;
		case '>':
			platformType = "branch_right";
			this.height = 20;
			break;
		case '^': // Goal Vine
			platformType = "goal";
			this.width = 20;
			this.height = 80;
			break;
		case 'j': // Vine
			platformType = "vine_short";
			this.width = 40;
			this.height = 80;
			this.xPos -= 10;
			break;
		case 't': // short tree
			platformType = "treetop_short";
			this.width = 100;
			this.xPos -= 30;
			break;
		case 'T': // Big tree
			platformType = "treetop";
			this.width = 80;
			this.height = 80;
			break;
		case '|': // long trunk
			platformType = "trunk_long";
			this.height = 80;
			break;
		case 'i':
			platformType = "trunk_short";
			this.height = 40;
			break;
		case 'b':
			platformType = "banana";
			this.width = 30;
			this.height = 30;
			break;
		default:
			platformType = "branch_short";
			break;
		}
		
		platText = new Texture("junglejumpassets/world1/" + platformType + ".png");
		
	}
	
	/**
	 * Gets the width of the platform
	 */
	public int getWidth() {
		return this.width;
	}
	
	public Texture getTexture() {
		// Texture changes depending on world
		/* switch(currentWorld) {
		case WORLD_ONE:
			platText = new Texture("junglejumpassets/branch.png");
			break;
		case WORLD_TWO:
			// World 2 texture
			break;
		case WORLD_THREE:
			// World 3 texture
			break;
		} */
		return this.platText;
	}
	
	/**
	 * Gets the height of the platform
	 */
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * Gets the X Position of platform
	 */
	public int getX() {
		return this.xPos;
	}
	
	/**
	 * Gets the Y position of platform
	 */
	public int getY() {
		return this.yPos;
	}
	
	
	
	/**
	 * Sets the width of the platform
	 */
	public void setWidth(int newWidth) {
		this.width = newWidth;
	}
	
	/**
	 * Sets the height of the platform
	 */
	public void setHeight(int newHeight) {
		this.height = newHeight;
	}
	
	/**
	 * Sets a new X position of platform
	 */
	public void setX(int newX) {
		this.xPos = newX;
	}
	
	/**
	 * Sets a new Y position of platform
	 */
	public void setY(int newY) {
		this.yPos = newY;
	}
	
	/**
	 * Sets the platform to be active.
	 * 
	 * Platforms are activated when stood on
	 * and deactivated when jumped off.
	 * 
	 * This is useful for dynamic platforms that
	 * move or change when stood on.
	 */
	public void setActive() {
		if(this.platType == '^') {
			// Play banana sound
			try{ 
				File file = new File("junglejumpassets/pickup.wav");
				FileHandle fileh = new FileHandle(file);
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			} catch (Exception e) {
				Gdx.app.log(junglejump.messages,
						"Audio File for Banana Music Not Found");
			}
			LevelContainer.nextLevel();
		}
		if(this.platType == 'j') {
			junglejump.monkeyY += 5;
		}
		this.active = true;
	}
	
	/**
	 * Sets the platform to no longer be active
	 */
	public void setInactive() {
		this.active = false;
	}
	
	/**
	 * What happens each frame when platform is active.
	 * Does nothing on standard platform
	 */
	public void onActive() {
		return;
	}
	
}
