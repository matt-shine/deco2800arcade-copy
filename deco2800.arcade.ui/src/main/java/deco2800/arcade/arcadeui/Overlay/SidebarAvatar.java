package deco2800.arcade.arcadeui.Overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class SidebarAvatar extends Widget {

    private NinePatch texture;
    private BitmapFont font;
    @SuppressWarnings("unused")
    private Overlay overlay;

    public SidebarAvatar(Overlay overlay) {

        texture = new NinePatch(new Texture(Gdx.files.internal("iconMagenta.png")), 75, 75, 75, 75);

        font = new BitmapFont(false);
        this.overlay = overlay;

    }

    @Override
    public void act(float d) {
        super.act(d);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {

        texture.draw(batch, getX(), getY() - 20, 180, 180);
        font.setColor(Color.WHITE);
        font.draw(batch, "your picture here", getX(), getY() - 20);
    }

    @Override
    public float getPrefHeight() {
        return 180;
    }

    @Override
    public float getPrefWidth() {
        return 180;
    }




}
