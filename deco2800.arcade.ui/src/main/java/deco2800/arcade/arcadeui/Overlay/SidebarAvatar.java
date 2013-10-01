package deco2800.arcade.arcadeui.Overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class SidebarAvatar extends Widget {

    @SuppressWarnings("unused")
    private Overlay overlay;
    private BitmapFont font;
    private NinePatch texture;


    public SidebarAvatar(Overlay overlay) {
        this.overlay = overlay;

        font = new BitmapFont(false);
        texture = new NinePatch(new Texture(Gdx.files.internal("iconMagenta.png")), 75, 75, 75, 75);
    }

    @Override
    public void act(float d) {
        super.act(d);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        texture.draw(batch, getX(), getY(), 180, 180);
        font.setColor(Color.WHITE);
        font.draw(batch, "your picture", getX() + 50, getY() + 105);
        font.draw(batch, "here", getX() + 50, getY() + 85);
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
