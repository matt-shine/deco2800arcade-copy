package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sun.jmx.remote.internal.ArrayQueue;

import deco2800.arcade.client.UIOverlay.PopupMessage;

public class OverlayPopup extends Actor {

	private ArrayQueue<PopupMessage> msgs = new ArrayQueue<PopupMessage>(100);
	
	private PopupMessage current = null;
	
	private int state = 0;
	
	private static float YPOS_GOAL = 100;
	private static float YPOS_START = -100;
	
	private float ypos = 0;
	private float yvel = 0;
	
	private float expandedTime = 0;
	private Texture texture;
	private TextureRegion region;
	private BitmapFont font;
	private Overlay overlay;

	public OverlayPopup(Overlay overlay) {
		super();
		
		font = new BitmapFont(false);
		this.overlay = overlay;
		
		texture = new Texture(Gdx.files.internal("pacman.png"));
		region = new TextureRegion(texture, 0, 0, 512, 512);
		
		ypos = YPOS_START;
		
	}
	
	public void addMessageToQueue(PopupMessage p) {
		msgs.add(p);
	}
	
	
	
	@Override
	public void act(float d) {
		super.act(d);

		if (state == 0) {
			
			yvel = 0;
			ypos = YPOS_START;
			if (msgs.size() != 0) {
				current = msgs.remove(0);
				state = 1;
			}
			
		} else if (state == 1) {
			
			if (ypos > YPOS_GOAL) {
				yvel = 0;
				state = 2;
			} else {
				yvel += 0.4;
			}
			
		} else if (state == 2) {
			
			expandedTime += d;
			if (expandedTime > 1.2) {
				expandedTime = 0;
				state = 3;
			}
			
		} else if (state == 3) {
			
			if (ypos < YPOS_START) {
				yvel = 0;
				state = 0;
			} else {
				yvel -= 0.4;
			}
			
		}
		
		ypos += yvel;
		this.setX(overlay.getWidth() / 2 - 32);
		this.setY(ypos);
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		if (state != 0) {
			batch.draw(region, getX(), getY(), 64, 64);
			font.setColor(Color.WHITE);
			if (current != null) {
				font.draw(batch, current.getMessage(), getX() + 32, getY() + 32);
			}
		}
		
	}
	
	
	
	
}
