package deco2800.arcade.hunter.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.platformergame.model.Entity;
import deco2800.arcade.platformergame.model.EntityCollection;

import java.util.HashMap;
import java.util.Iterator;


public class SpriteLayer extends Map {
    private HashMap<String, TextureRegion> sprites = new HashMap<String, TextureRegion>();
	private EntityCollection clouds = new EntityCollection();
    private EntityCollection trees = new EntityCollection();

    private float CHANCE_OF_CLOUDS = 0.05f;
    private float CHANCE_OF_TREES = 0.1f;

    public Vector2 randomScreenCoordinate() {
        int randX, randY;
        randX = (int) Math.round(Math.random() * Hunter.Config.screenWidth);
        randY = (int) Math.round(Math.random() * Hunter.Config.screenHeight * 3) - Hunter.Config.screenHeight;
        return new Vector2(randX, randY);
    }

	public SpriteLayer(float speedModifier) {
		super(speedModifier);

        sprites.put("cloud", new TextureRegion(new Texture(Gdx.files.internal("textures/sprites/cloud.png"))));

        //Create clouds
        for (int c = 0; c < 10; c++) {
            //No need for seeded rng here, doesn't affect gameplay
            clouds.add(new BackgroundSprite(randomScreenCoordinate(),
                    sprites.get("cloud").getRegionWidth(), sprites.get("cloud").getRegionHeight()));
        }
        //Create trees
    }

	public void update(float delta, Vector3 cameraPos) {
		// TODO Auto-generated method stub
        Iterator<Entity> cl = clouds.iterator();
        while (cl.hasNext()) {
            Entity cloud = cl.next();
            cloud.update(delta * speedModifier);
            if (cloud.getX() + cloud.getWidth() < cameraPos.x * speedModifier - Hunter.Config.screenWidth) {
                cl.remove();
            }
        }
        Vector2 cloudLocation;
        if (Math.random() < CHANCE_OF_CLOUDS) {
            cloudLocation = randomScreenCoordinate();
            cloudLocation.set(cameraPos.x + Hunter.Config.screenWidth / 2, cloudLocation.y + cameraPos.y);
            clouds.add(new BackgroundSprite(cloudLocation,
                    sprites.get("cloud").getRegionWidth(), sprites.get("cloud").getRegionHeight()));
        }
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
        for (Entity c : clouds) {
            batch.draw(sprites.get("cloud"), c.getX(), c.getY());
        }
	}
}
