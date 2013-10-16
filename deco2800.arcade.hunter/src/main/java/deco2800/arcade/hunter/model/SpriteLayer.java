package deco2800.arcade.hunter.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import deco2800.arcade.hunter.Hunter;
import deco2800.arcade.hunter.platformergame.Entity;
import deco2800.arcade.hunter.platformergame.EntityCollection;
import deco2800.arcade.hunter.screens.GameScreen;

import java.util.HashMap;
import java.util.Iterator;


public class SpriteLayer extends Map {
    private HashMap<String, TextureRegion> treeSprites = new HashMap<String, TextureRegion>();
    private HashMap<String, TextureRegion> cloudSprites = new HashMap<String, TextureRegion>();
	private EntityCollection clouds = new EntityCollection();
    private EntityCollection trees = new EntityCollection();

    private float CHANCE_OF_CLOUDS = 0.05f;
    private float CHANCE_OF_TREES = 0.021f;
    private GameScreen gameScreen;

    public Vector2 randomScreenCoordinate() {
        int randX, randY;
        randX = (int) Math.round(Math.random() * Hunter.State.screenWidth);
        randY = (int) Math.round(Math.random() * Hunter.State.screenHeight * 3) - Hunter.State.screenHeight;
        return new Vector2(randX, randY);
    }

	public SpriteLayer(float speedModifier, GameScreen gameScreen) {
		super(speedModifier);
		this.gameScreen = gameScreen;

        cloudSprites.put("cloud", new TextureRegion(new Texture(Gdx.files.internal("textures/sprites/cloud.png"))));
        treeSprites.put("tree", new TextureRegion(new Texture(Gdx.files.internal("textures/sprites/tree.png"))));
        treeSprites.put("tree2", new TextureRegion(new Texture(Gdx.files.internal("textures/sprites/tree2.png"))));

        //Create clouds
        for (int c = 0; c < 10; c++) {
            //No need for seeded rng here, doesn't affect gameplay
            clouds.add(new BackgroundSprite(randomScreenCoordinate(),
                    cloudSprites.get("cloud").getRegionWidth(), cloudSprites.get("cloud").getRegionHeight()));
        }
        //Create trees
        for (int t = 0; t < 2; t++) {
            float tX = randomScreenCoordinate().x;
            float tY = gameScreen.getForeground().getColumnTop(tX);
            trees.add(new BackgroundSprite(new Vector2(tX, tY), treeSprites.get("tree").getRegionWidth(), treeSprites.get("tree").getRegionHeight()));
        }
    }

	public void update(float delta, Vector3 cameraPos) {
		// TODO Auto-generated method stub
        Iterator<Entity> cl = clouds.iterator();
        while (cl.hasNext()) {
            Entity cloud = cl.next();
            cloud.update(delta * speedModifier);
            if (cloud.getX() + cloud.getWidth() < cameraPos.x * speedModifier - Hunter.State.screenWidth) {
                cl.remove();
            }
        }
        Vector2 cloudLocation;
        if (Math.random() < CHANCE_OF_CLOUDS) {
            cloudLocation = randomScreenCoordinate();
            cloudLocation.set(cameraPos.x + Hunter.State.screenWidth / 2, cloudLocation.y + cameraPos.y);
            clouds.add(new BackgroundSprite(cloudLocation,
                    cloudSprites.get("cloud").getRegionWidth(), cloudSprites.get("cloud").getRegionHeight()));
        }

        Vector2 treeLocation = new Vector2();
        if (Math.random() < CHANCE_OF_TREES) {
            treeLocation.x = cameraPos.x + Hunter.State.screenWidth / 2;
            treeLocation.y = gameScreen.getForeground().getColumnTop(treeLocation.x + treeSprites.get("tree").getRegionWidth() / 2 - Hunter.Config.TILE_SIZE);

            trees.add(new BackgroundSprite(treeLocation, treeSprites.get("tree").getRegionWidth(), treeSprites.get("tree").getRegionHeight()));
        }
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
        for (Entity c : clouds) {
            batch.draw(cloudSprites.get("cloud"), c.getX(), c.getY());
        }

        for (Entity t : trees) {
            batch.draw(treeSprites.get("tree"), t.getX(), t.getY());
        }
	}
}
