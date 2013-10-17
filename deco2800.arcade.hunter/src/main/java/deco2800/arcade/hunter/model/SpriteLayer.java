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

import java.util.ArrayList;
import java.util.Iterator;


public class SpriteLayer extends Map {
    private ArrayList<TextureRegion> treeSprites = new ArrayList<TextureRegion>();
    private ArrayList<TextureRegion> cloudSprites = new ArrayList<TextureRegion>();
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

        for (int c = 1; c <= 5; c++) {
            cloudSprites.add(new TextureRegion(new Texture(Gdx.files.internal("textures/sprites/cloud" + c + ".png"))));
        }
        for (int t = 1; t <= 5; t++) {
            treeSprites.add(new TextureRegion(new Texture(Gdx.files.internal("textures/sprites/tree" + t + ".png"))));
        }

        //Create clouds
        for (int c = 0; c < 10; c++) {
            //No need for seeded rng here, doesn't affect gameplay
            int id = clouds.add(new BackgroundSprite(randomScreenCoordinate(), 0, 0));
            TextureRegion sprite = cloudSprites.get(id % cloudSprites.size());
            clouds.getById(id).setWidth(sprite.getRegionWidth());
            clouds.getById(id).setHeight(sprite.getRegionHeight());
        }

        //Create trees
        for (int t = 0; t < 2; t++) {
            float tX = randomScreenCoordinate().x;
            float tY = gameScreen.getForeground().getColumnTop(tX);

            int id = trees.add(new BackgroundSprite(new Vector2(tX, 0), 0, 0));

            TextureRegion sprite = treeSprites.get(id % treeSprites.size());
            trees.getById(id).setWidth(sprite.getRegionWidth());
            trees.getById(id).setHeight(sprite.getRegionHeight());
            trees.getById(id).setY(gameScreen.getForeground().getColumnTop(tX + sprite.getRegionWidth() / 2) - Hunter.Config.TILE_SIZE);
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

            int id = clouds.add(new BackgroundSprite(cloudLocation, 0, 0));
            TextureRegion sprite = cloudSprites.get(id % cloudSprites.size());
            clouds.getById(id).setWidth(sprite.getRegionWidth());
            clouds.getById(id).setHeight(sprite.getRegionHeight());
        }

        Vector2 treeLocation = new Vector2();
        if (Math.random() < CHANCE_OF_TREES) {
            treeLocation.x = cameraPos.x + Hunter.State.screenWidth / 2;

            int id = trees.add(new BackgroundSprite(treeLocation, 0, 0));
            TextureRegion sprite = treeSprites.get(id % treeSprites.size());
            trees.getById(id).setWidth(sprite.getRegionWidth());
            trees.getById(id).setHeight(sprite.getRegionHeight());
            trees.getById(id).setY(gameScreen.getForeground().getColumnTop(treeLocation.x + sprite.getRegionWidth() / 2) - Hunter.Config.TILE_SIZE);
        }
	}

	@Override
	public void draw(SpriteBatch batch) {
		// TODO Auto-generated method stub
        for (int c : clouds.idSet()) {
            batch.draw(cloudSprites.get(c % cloudSprites.size()), clouds.getById(c).getX(), clouds.getById(c).getY());
        }

        for (int t : trees.idSet()) {
            batch.draw(treeSprites.get(t % treeSprites.size()), trees.getById(t).getX(), trees.getById(t).getY());
        }
	}
}
