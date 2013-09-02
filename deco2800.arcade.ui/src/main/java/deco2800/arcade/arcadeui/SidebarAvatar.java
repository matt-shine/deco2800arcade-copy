package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class SidebarAvatar extends Widget {

	private NinePatch texture;
	private BitmapFont font;
	private Overlay overlay;

	public SidebarAvatar(Overlay overlay) {
		
		texture = new NinePatch(new Texture(Gdx.files.internal("popupbg.png")), 60, 60, 60, 60);

		font = new BitmapFont(false);
		this.overlay = overlay;
		
	}
	
	@Override
	public void act(float d) {
		super.act(d);
		

		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		texture.draw(batch, getX(), getY(), 200, 200);
		font.setColor(Color.WHITE);
		TextBounds b = font.getBounds("text");
		font.draw(batch, "text", 0, 0);
	}

	@Override
	public float getPrefHeight() {
		return 200;
	}

	@Override
	public float getPrefWidth() {
		return 200;
	}
	
	
	
	
}
