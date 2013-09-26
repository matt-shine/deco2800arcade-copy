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
    private int width;
    private int height;

    public Clock(Overlay overlay) {
        this.overlay = overlay;
        texture = new NinePatch(new Texture(Gdx.files.internal("iconMagenta.png")), 75, 75, 75, 75);
        contentStage.addActor(contentGroup);
        ArcadeInputMux.getInstance().addProcessor(contentStage);
        //width = overlay.getWidth() - 320;
        //height = overlay.getHeight() - 200;
        width = 400;
        height = 200;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        texture.draw(batch, overlay.getWidth() - width + 25, overlay.getHeight() - height + 25, width, height);
        super.draw(batch, parentAlpha);
    }

    public void destroy() {
        ArcadeInputMux.getInstance().removeProcessor(contentStage);
    }
}
