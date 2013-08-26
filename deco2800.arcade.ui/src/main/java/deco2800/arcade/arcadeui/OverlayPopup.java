package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sun.jmx.remote.internal.ArrayQueue;

import deco2800.arcade.client.UIOverlay.PopupMessage;

public class OverlayPopup extends Actor {

	private ArrayQueue<PopupMessage> msgs = new ArrayQueue<PopupMessage>(100);
	
	private PopupMessage current = null;
	
	private int state = 0;
	
	private static float YPOS_GOAL = 100;
	private static float YPOS_START = -100;
	private static float EXPAND_GOAL = 300;
	private static float EXPAND_START = 64;
	
	private float ypos = 0;
	private float yvel = 0;
	private float expandedTime = 0;
	private float expandedAmt = 0;
	
	private NinePatch texture;
	private BitmapFont font;
	private Overlay overlay;

	public OverlayPopup(Overlay overlay) {
		super();
		
		font = new BitmapFont(false);
		this.overlay = overlay;
		
		texture = new NinePatch(new Texture(Gdx.files.internal("pacman.png")), 60, 60, 60, 60);
		
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
				state++;
			}
			
		} else if (state == 1) {
			
			if (ypos > YPOS_GOAL) {
				yvel = 0;
				state++;
			} else {
				yvel += 0.4;
			}
			
		} else if (state == 2) {
			
			expandedAmt += 12;
			if (expandedAmt > EXPAND_GOAL) {
				
				expandedAmt = EXPAND_GOAL;
				state++;
				
			}
			
		} else if (state == 3) {
			
			expandedTime += d;
			if (expandedTime > 0.8) {
				expandedTime = 0;
				state++;
			}
			
		} else if (state == 4) {
			
			expandedAmt -= 12;
			if (expandedAmt < EXPAND_START) {
				
				expandedAmt = EXPAND_START;
				
				if (msgs.size() == 0) {
					state++;
				} else {
					state = 2;
					current = msgs.remove(0);
				}
				
			}
			
		} else if (state == 5) {
			
			if (ypos < YPOS_START) {
				yvel = 0;
				state = 0;
			} else {
				yvel -= 0.4;
			}
			
		}
		
		ypos += yvel;
		this.setX(overlay.getWidth() / 2 - expandedAmt / 2);
		this.setY(ypos);
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		if (state != 0) {
			texture.draw(batch, getX(), getY(), expandedAmt, 64);
			font.setColor(Color.WHITE);
			if (current != null && expandedAmt == EXPAND_GOAL) {
				TextBounds b = font.getBounds(current.getMessage());
				font.draw(batch, current.getMessage(), getX() + expandedAmt / 2 - b.width / 2, getY() + 32 + b.height / 2);
				
			}
		}
		
	}
	
	
	
	
}
