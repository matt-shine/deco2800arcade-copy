package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class OverlayWindow extends Group {
	
	
	private WidgetGroup windowContent = null;
	private NinePatch texture;
	private Overlay overlay;
	
	public OverlayWindow(Overlay overlay) {
		
		this.overlay = overlay;
		texture = new NinePatch(new Texture(Gdx.files.internal("popupbg.png")), 30, 30, 30, 30);
		
	}
	
	public void setContent(WidgetGroup g) {
		
		if (this.windowContent != null) {
			this.removeActor(windowContent);
		}
		
		windowContent = g;
		this.addActor(windowContent);
		windowContent.setBounds(160, 100, overlay.getWidth() - 320, overlay.getHeight() - 200);
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		
		texture.draw(batch, 160, 100, overlay.getWidth() - 320, overlay.getHeight() - 200);
		super.draw(batch, parentAlpha);
		
	}
	
	@Override
	public void act(float d) {
		super.act(d);
		
		if (windowContent != null) {
			windowContent.act(d);
		}
		
	}
	

}
