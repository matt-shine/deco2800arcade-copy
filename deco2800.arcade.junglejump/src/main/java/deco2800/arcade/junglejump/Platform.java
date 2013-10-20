
package deco2800.arcade.junglejump;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class Platform {
	
	private int width, height, xPos, yPos;
	public boolean climbable = false;
	private Texture platText;
	public boolean visible = true;
	public char platType;
	public String platformType = "";
	public boolean inactive;
	private boolean moveRight;
	private int moveCounter = 250;
	
	/**
	 * Platform constructor
	 * Takes width, height and X and Y position as parameters
	 */
	public Platform(char type, int pX, int pY, int pWidth, int pHeight) {
		this.width = pWidth;
		this.height = pHeight;
		this.xPos = pX;
		this.yPos = pY;
		inactive = false;
		moveRight = false;
		platType = type;
		setTexture(type);
	}
	
	public void setTexture(int type) {
		
		this.width = 40;
		this.height = 40;
		
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
			this.width = 20;
			this.height = 80;
			this.xPos -= 5;
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
		case 'x': // Spike
			platformType = "spike";
			this.width = 20;
			this.height = 20;
			this.yPos -= 20;
			break;
		case '~': // Tunnel floor
			platformType = "floor";
			this.width = 40;
			this.height = 20;
			break;
		case '=': // Tunnel
			platformType = "tunnel";
			this.width = 40;
			this.height = 60;
			this.yPos -= 20;
			break;
		case 'J': // Jim
			platformType = "jimbo";
			this.width = 80;
			this.height = 60;
			break;
		case '_': // Building roof
			platformType = "roof";
			this.height = 40;
			break;
		default:
			platformType = "branch";
			break;
		}
		
		platText = new Texture(Gdx.files.internal("world" + (junglejump.world + 1) + 
				"/" + platformType + ".png"));
		
	}
	
	/**
	 * Gets the width of the platform
	 */
	public int getWidth() {
		return this.width;
	}
	
	public Texture getTexture() {
		// Texture changes depending on world
		return this.platText;
	}
	
	public void refreshTexture() {
		platText = new Texture(Gdx.files.internal("world" + (junglejump.world + 1) + "/" + platformType + ".png"));
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
			URL path = this.getClass().getResource("/");
			try{ 
				String resource = path.toString().replace(".arcade/build/classes/main/", 
						".arcade.junglejump/src/main/").replace("file:", "") + 
						"resources/pickup.wav";
				System.out.println(resource);
				File file = new File(resource);
				new FileHandle(file);
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
			junglejump.monkeyY += 50;
		}
		if(this.platType == 'x') {
			junglejump.killMonkey();
		}
	}
	
	/**
	 * Run a platforms special ability
	 * every frame
	 */
	public void onActive() {
		// If platform is Jim
		if(this.platType == 'J') {
			int moveSpeed = 2;
			
			if(this.xPos > junglejump.SCREENWIDTH) {
				this.inactive = true;
			}
			
			// Move right
			if(moveRight) {
				this.xPos += moveSpeed;
				moveCounter += moveSpeed;
				// stop moving when leaving screen
			} else {
				this.xPos -= moveSpeed;
				moveCounter += moveSpeed;
			}
			
			if(moveCounter > 500) {
				moveCounter = 0;
				moveRight = !moveRight;
				if(moveRight) {
					this.platText = new Texture(Gdx.files.internal(
							"world1/jimboRight.png"));
				} else {
					this.platText = new Texture(Gdx.files.internal(
							"world1/jimbo.png"));
				}
			}
			
			if(inactive) {
				moveCounter = 0;
				this.yPos -= 2;
			}
		}
	}
}
