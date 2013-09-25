package deco2800.arcade.arcadeui.Overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import deco2800.arcade.client.ArcadeInputMux;


public class Clock extends Group {

    private class ClockStage extends Stage {}

    private Overlay overlay;
    private ClockStage contentStage = new ClockStage();
    private Group contentGroup = new Group();
    private NinePatch texture;

    public Clock(Overlay overlay) {
        this.overlay = overlay;
        texture = new NinePatch(new Texture(Gdx.files.internal("iconGreen.png")), 100, 100, 100, 100);
        contentStage.addActor(contentGroup);
        ArcadeInputMux.getInstance().addProcessor(contentStage);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        texture.draw(batch, 160, 100, overlay.getWidth() - 320, overlay.getHeight() - 200);
        super.draw(batch, parentAlpha);
    }

    public void destroy() {
        ArcadeInputMux.getInstance().removeProcessor(contentStage);
    }
}
