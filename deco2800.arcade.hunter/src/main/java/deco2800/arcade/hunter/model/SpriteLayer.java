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
    /**
     * Lists of textures for all the trees and clouds
     */
    private ArrayList<TextureRegion> treeSprites = new ArrayList<TextureRegion>();
    private ArrayList<TextureRegion> cloudSprites = new ArrayList<TextureRegion>();

    /**
     * Collection of trees and clouds currently spawned into the world
     */
    private EntityCollection clouds = new EntityCollection();
    private EntityCollection trees = new EntityCollection();

    private GameScreen gameScreen;

    /**
     * Get a random coordinate within the area of the width of the screen, and
     * one screenHeight above and below the current camera position.
     * @return a random screen coordinate within the current screen space or
     *         one screen space above or below the current screen.
     */
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
            int id = clouds.add(new BackgroundSprite(randomScreenCoordinate(), 0, 0, randomCloudSpeed()));
            TextureRegion sprite = cloudSprites.get(id % cloudSprites.size());
            clouds.getById(id).setWidth(sprite.getRegionWidth());
            clouds.getById(id).setHeight(sprite.getRegionHeight());
        }

        //Create trees
        for (int t = 0; t < 2; t++) {
            float tX = randomScreenCoordinate().x;

            int id = trees.add(new BackgroundSprite(new Vector2(tX, 0), 0, 0, randomTreeSpeed()));

            TextureRegion sprite = treeSprites.get(id % treeSprites.size());
            trees.getById(id).setWidth(sprite.getRegionWidth());
            trees.getById(id).setHeight(sprite.getRegionHeight());
            trees.getById(id).setY(gameScreen.getForeground().getColumnTop(tX + sprite.getRegionWidth() / 2) - Hunter.Config.TILE_SIZE);
        }
    }

    /**
     * @return a random tree speed between the config values of TREE_MIN_SPEED
     *         and TREE_MAX_SPEED
     */
    private float randomTreeSpeed() {
        return (float) (Hunter.Config.TREE_MIN_SPEED + (Math.random() *
                ((Hunter.Config.TREE_MAX_SPEED -
                        Hunter.Config.TREE_MIN_SPEED) + 1)));
    }

    /**
     * @return a random cloud speed between the config values of CLOUD_MIN_SPEED
     *         and CLOUD_MAX_SPEED
     */
    private float randomCloudSpeed() {
        return (float) (Hunter.Config.CLOUD_MIN_SPEED + (Math.random() *
                ((Hunter.Config.CLOUD_MAX_SPEED -
                        Hunter.Config.CLOUD_MIN_SPEED) + 1)));
    }

    /**
     * Update the state of the sprites in the sprite layer
     * @param delta delta time since the last update
     * @param cameraPos current camera position
     */
    public void update(float delta, Vector3 cameraPos) {
        updateClouds(delta, cameraPos);
        updateTrees(delta, cameraPos);
        addClouds(cameraPos);
        addTrees(cameraPos);
    }

    /**
     * Add new trees to the screen
     * @param cameraPos current camera position
     */
    private void addTrees(Vector3 cameraPos) {
        //Add new trees randomly
        Vector2 treeLocation = new Vector2();
        if (Math.random() < Hunter.Config.CHANCE_OF_TREES) {
            treeLocation.x = cameraPos.x + Hunter.State.screenWidth / 2;

            int id = trees.add(new BackgroundSprite(treeLocation, 0, 0, randomTreeSpeed()));
            TextureRegion sprite = treeSprites.get(id % treeSprites.size());
            trees.getById(id).setWidth(sprite.getRegionWidth());
            trees.getById(id).setHeight(sprite.getRegionHeight());
            trees.getById(id).setY(gameScreen.getForeground().getColumnTop(treeLocation.x) - Hunter.Config.TILE_SIZE * 3);
        }
    }

    /**
     * Add new clouds to the screen
     * @param cameraPos current camera position
     */
    private void addClouds(Vector3 cameraPos) {
        //Add new clouds randomly
        Vector2 cloudLocation;
        if (Math.random() < Hunter.Config.CHANCE_OF_CLOUDS) {
            cloudLocation = randomScreenCoordinate();
            cloudLocation.set(cameraPos.x + Hunter.State.screenWidth / 2, cloudLocation.y + cameraPos.y);

            int id = clouds.add(new BackgroundSprite(cloudLocation, 0, 0, randomCloudSpeed()));
            TextureRegion sprite = cloudSprites.get(id % cloudSprites.size());
            clouds.getById(id).setWidth(sprite.getRegionWidth());
            clouds.getById(id).setHeight(sprite.getRegionHeight());
        }
    }

    /**
     * Update the state of the existing trees, remove them if they are
     * off the left edge of the screen.
     * @param delta delta time since the last update
     * @param cameraPos current camera position
     */
    private void updateTrees(float delta, Vector3 cameraPos) {
        //Remove trees that are off the left edge of the screen, update the position of the tree
        Iterator<Entity> tr = trees.iterator();
        while (tr.hasNext()) {
            Entity tree = tr.next();
            tree.update(delta * speedModifier);
            if (tree.getX() + tree.getWidth() < cameraPos.x * speedModifier -
                    Hunter.State.screenWidth) {
                tr.remove();
            }
        }
    }

    /**
     * Update the state of the existing clouds, remove them if they are
     * off the left edge of the screen.
     * @param delta delta time since the last update
     * @param cameraPos current camera position
     */
    private void updateClouds(float delta, Vector3 cameraPos) {
        //Remove clouds that are off the left edge of the screen, update the position of the cloud
        Iterator<Entity> cl = clouds.iterator();
        while (cl.hasNext()) {
            Entity cloud = cl.next();
            cloud.update(delta * speedModifier);
            if (cloud.getX() + cloud.getWidth() < cameraPos.x * speedModifier -
                    Hunter.State.screenWidth) {
                cl.remove();
            }
        }
    }

    /**
     * Draw all the sprites to the given sprite batch.
     * @param batch sprite batch to draw all the sprites to.
     */
    @Override
    public void draw(SpriteBatch batch) {
        // TODO Auto-generated method stub
        for (int c : clouds.idSet()) {
            batch.draw(cloudSprites.get(c % cloudSprites.size()),
                    clouds.getById(c).getX(), clouds.getById(c).getY());
        }

        for (int t : trees.idSet()) {
            TextureRegion treeSprite = treeSprites.get(t % treeSprites.size());
            Entity tree = trees.getById(t);
            batch.draw(treeSprite, tree.getX(), tree.getY(),
                    treeSprite.getRegionWidth() *
                            ((BackgroundSprite) tree).speedModifier,
                    treeSprite.getRegionHeight() *
                            ((BackgroundSprite) tree).speedModifier);
        }
    }

    /**
     * Clean up all the resources, to be called when the GameScreen is disposed
     */
    public void dispose() {
        for (TextureRegion c : cloudSprites) {
            c.getTexture().dispose();
        }

        for (TextureRegion t : treeSprites) {
            t.getTexture().dispose();
        }
    }
}
