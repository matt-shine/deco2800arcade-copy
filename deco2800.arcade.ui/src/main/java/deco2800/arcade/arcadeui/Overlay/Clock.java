package deco2800.arcade.arcadeui.Overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
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

    public void destroy() {
        ArcadeInputMux.getInstance().removeProcessor(contentStage);
    }
}
