package deco2800.arcade.arcadeui.Overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Clock extends Widget {

    private Overlay overlay;
    private Skin skin;
    private BitmapFont font;
    private NinePatch texture;
    private SimpleDateFormat simpleDateFormat;
    private Label clock;

    public Clock(Overlay overlay) {
        this.overlay = overlay;

        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        texture = new NinePatch(new Texture(Gdx.files.internal("iconMagenta.png")), 75, 75, 75, 75);

        simpleDateFormat = new SimpleDateFormat("h:mm a");
        font = new BitmapFont(false);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        texture.draw(batch, overlay.getWidth() - 350 + 25, overlay.getHeight() - 150 + 25, 350, 150);
        font.setColor(Color.WHITE);
        font.draw(batch, simpleDateFormat.format(new Date()), overlay.getWidth() - 350 + 25, overlay.getHeight() - 150 + 25);
    }

    @Override
    public float getPrefHeight() {
        return 150;
    }

    @Override
    public float getPrefWidth() {
        return 350;
    }
}
