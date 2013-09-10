package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import deco2800.arcade.client.ArcadeInputMux;

public class OverlayWindow extends Group {
	
	private class OverlayWindowStage extends Stage {}

	private NinePatch texture;
	private Overlay overlay;
	private OverlayWindowStage contentStage = new OverlayWindowStage();
	private Group contentGroup = new Group();
	private OverlayWindowContent windowContent = null;
	private float delta = 0;
	private int height = 0;
	private int width = 0;
	
	
	public OverlayWindow(Overlay overlay) {
		
		this.overlay = overlay;
		texture = new NinePatch(new Texture(Gdx.files.internal("popupbg.png")), 30, 30, 30, 30);
		contentStage.addActor(contentGroup);
		ArcadeInputMux.getInstance().addProcessor(contentStage);
	}
	
	public void setContent(OverlayWindowContent g) {
		
		if (this.windowContent != null) {
			contentGroup.removeActor(windowContent);
		}
		
		windowContent = g;
		contentGroup.addActor(windowContent);
		windowContent.setBounds(160, 100, overlay.getWidth() - 320, overlay.getHeight() - 200);
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		//normal stuff
		texture.draw(batch, 160, 100, overlay.getWidth() - 320, overlay.getHeight() - 200);
		super.draw(batch, parentAlpha);
		
		//weird stuff
		if (overlay.getWidth() != width || overlay.getHeight() != height) {
			width = overlay.getWidth();
			height = overlay.getHeight();
			if (windowContent != null) {
				windowContent.resize(width, height);
				windowContent.setBounds(160, 100, overlay.getWidth() - 320, overlay.getHeight() - 200);
			}
			contentStage.setViewport(width, height, true);
			contentStage.getCamera().position.set(width / 2, height / 2, 0);
		}
		batch.end();
		contentStage.act(delta);
		contentStage.draw();
		batch.begin();
	}
	
	@Override
	public void act(float d) {
		delta = d;
		//do not call super.act(d);
	}
	

	public void destroy() {
		ArcadeInputMux.getInstance().removeProcessor(contentStage);
	}
	
}
