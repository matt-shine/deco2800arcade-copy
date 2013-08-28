package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class OverlayWindow extends Group {

	
	private Group windowContent = null;
	private NinePatch texture;
	private Overlay overlay;
	
	public OverlayWindow(Overlay overlay) {
		
		this.overlay = overlay;
		texture = new NinePatch(new Texture(Gdx.files.internal("popupbg.png")), 60, 60, 60, 60);
		
	}
	
	public void setContent(Group g) {
		
		if (this.windowContent != null) {
			this.removeActor(windowContent);
		}
		
		windowContent = g;
		this.addActor(windowContent);
		
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		
		texture.draw(batch, 40, 40, overlay.getWidth() - 100, overlay.getHeight() - 100);
		super.draw(batch, parentAlpha);
		
	}
	

}
