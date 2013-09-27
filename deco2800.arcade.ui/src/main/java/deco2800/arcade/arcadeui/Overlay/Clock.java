package deco2800.arcade.arcadeui.Overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import deco2800.arcade.client.ArcadeInputMux;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Clock extends Group {

    private class ClockStage extends Stage {}

    private Overlay overlay;
    private ClockStage contentStage = new ClockStage();
    private Group contentGroup = new Group();
    private Skin skin;
    private NinePatch texture;
    private int width;
    private int height;
    private SimpleDateFormat simpleDateFormat;
    private Label clock;

    public Clock(Overlay overlay) {
        this.overlay = overlay;
        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        texture = new NinePatch(new Texture(Gdx.files.internal("iconMagenta.png")), 75, 75, 75, 75);

        simpleDateFormat = new SimpleDateFormat("h:mm a");
        System.out.println(simpleDateFormat.format(new Date()));
        clock = new Label("", skin);
        clock.setText(simpleDateFormat.format(new Date()));

        contentStage.addActor(contentGroup);
        ArcadeInputMux.getInstance().addProcessor(contentStage);
        width = 350;
        height = 150;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        texture.draw(batch, overlay.getWidth() - width + 25, overlay.getHeight() - height + 25, width, height);
        clock.setPosition(overlay.getWidth() - width + 25, overlay.getHeight() - height + 25);
        super.draw(batch, parentAlpha);
    }

    public void destroy() {
        ArcadeInputMux.getInstance().removeProcessor(contentStage);
    }
}
