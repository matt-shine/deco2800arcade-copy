package deco2800.arcade.arcadeui.Overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Topbar extends Group {

    private Overlay overlay;
    private Skin skin;
    private NinePatch texture;
    private SimpleDateFormat simpleDateFormat;
    private Label username;
    private Label clock;

    public Topbar(Overlay overlay) {
        this.overlay = overlay;
        texture = new NinePatch(new Texture(Gdx.files.internal("iconMagenta.png")), 75, 75, 75, 75);
        skin = new Skin(Gdx.files.internal("loginSkin.json"));

        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        username = new Label("USERNAME", skin, "default-large");
        clock = new Label("CLOCK", skin, "default-large");

    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        int width = overlay.getWidth();
        int height = overlay.getHeight();
        texture.draw(batch, width - 675, height - 125, 700, 150);
        username.setPosition(width - 620, height - 70);
        username.setText(overlay.getPlayer().getUsername());
        clock.setPosition(width - 170, height - 70);
        clock.setText(simpleDateFormat.format(new Date()));
        this.addActor(username);
        this.addActor(clock);
        super.draw(batch, parentAlpha);
    }
}
