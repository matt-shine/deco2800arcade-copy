package deco2800.arcade.hunter.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.platformergame.model.Entity;
import deco2800.arcade.platformergame.model.EntityCollection;

import java.util.HashMap;
import java.util.Iterator;


public class SpriteLayer extends Map {
    private HashMap<String, TextureRegion> sprites = new HashMap<String, TextureRegion>();
	private EntityCollection clouds = new EntityCollection();
    private EntityCollection trees = new EntityCollection();

    public Vector2 randomScreenCoordinate() {
        int randX, randY;
        randX = (int) Math.round(Math.random() * Hunter.Config.screenWidth);
        randY = (int) Math.round(Math.random() * Hunter.Config.screenHeight);
        return new Vector2(randX, randY);
    }

	public SpriteLayer(float speedModifier) {
		super(speedModifier);

        sprites.put("cloud", new TextureRegion(new Texture(Gdx.files.internal("textures/sprites/cloud.png"))));

        //Create clouds
        for (int c = 0; c < 30; c++) {
            //No need for seeded rng here, doesn't affect gameplay
            clouds.add(new BackgroundSprite(randomScreenCoordinate(),
                    sprites.get("cloud").getRegionWidth(), sprites.get("cloud").getRegionHeight()));
        }
        //Create trees
	}

    /*
     * Clean up by removing objects that have fallen off the left edge of the screen
     */
    public void cleanUp(float cameraX) {
        Iterator<Entity> cl = clouds.iterator();
        while (cl.hasNext()) {
            Entity cloud = cl.next();
            if (cloud.getX() + cloud.getWidth() < cameraX) {
                cl.remove();
            }
        }
    }

	@Override
	public void update(float delta, float gameSpeed) {
		// TODO Auto-generated method stub
        for (Entity c : clouds) {
            c.update(delta * gameSpeed * speedModifier);
        }
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub

	}
}
