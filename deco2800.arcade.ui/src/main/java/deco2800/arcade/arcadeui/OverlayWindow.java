package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import deco2800.arcade.client.ArcadeInputMux;

public class OverlayWindow extends Group {
	
	
	private NinePatch texture;
	private Overlay overlay;
	private Stage contentStage = new Stage();
	private Group contentGroup = new Group();
	private Group windowContent = null;
	private float delta = 0;
	
	public OverlayWindow(Overlay overlay) {
		
		this.overlay = overlay;
		texture = new NinePatch(new Texture(Gdx.files.internal("popupbg.png")), 30, 30, 30, 30);
		contentStage.addActor(contentGroup);
		ArcadeInputMux.getInstance().addProcessor(contentStage);
	}
	
	public void setContent(Group g) {
		
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
	

}
