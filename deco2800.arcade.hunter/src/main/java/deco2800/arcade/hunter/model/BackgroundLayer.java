package deco2800.arcade.hunter.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import deco2800.arcade.hunter.Hunter;

public class BackgroundLayer extends Map {
    //Image which makes up the background
    private final TextureRegion background = new TextureRegion(new Texture("textures/background.png"));

    public BackgroundLayer(float speedModifier) {
        super(speedModifier);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(background, 0, 0, Hunter.Config.SCREEN_WIDTH, Hunter.Config.SCREEN_HEIGHT);
    }

    @Override
    public void update(float delta, Vector3 cameraPos) {
        // Nothing to do here, background image is static
    }

    public void dispose() {
        background.getTexture().dispose();
    }
}
