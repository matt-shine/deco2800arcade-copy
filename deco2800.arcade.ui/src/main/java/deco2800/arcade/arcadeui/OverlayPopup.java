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
	private static float YPOS_GOAL = 100;
	private static float YPOS_START = -200;
	private float ypos = 0;
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

		if (msgs.size() != 0) {
			
			if (ypos < YPOS_GOAL) {
				ypos += 5;
			} else {
				expandedTime += d;
				
				if (expandedTime > 2) {
					msgs.remove(0);
					ypos = YPOS_START;
					expandedTime = 0;
				}
				
			}
			
			
			
			
		}
		
		this.setX(overlay.getWidth() / 2);
		this.setY(ypos);
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		batch.draw(region, getX(), getY(), 64, 64);
		font.setColor(Color.WHITE);
		if (msgs.size() != 0) {
			font.draw(batch, msgs.get(0).getMessage(), getX(), getY());
		}
	}
	
	
	
	
}
