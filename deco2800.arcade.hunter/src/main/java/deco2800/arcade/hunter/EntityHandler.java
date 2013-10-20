package deco2800.arcade.hunter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.arcade.hunter.platformergame.EntityCollection;

import java.util.HashMap;

public class EntityHandler {
	/**
	 * Creates Entities
	 */
	private final EntityCollection entities;
	private final HashMap<String,Animation> animalAnimations; //Animation HashMap for animals
	private final HashMap<String,Texture> itemTextures; //Texture HashMap for items
	private final HashMap<String,Texture> mapEntityTextures; //Texture HashMap for MapEntities
	
	
	public EntityHandler(EntityCollection entities){
		this.entities = entities;
		animalAnimations = new HashMap<String,Animation>();
		itemTextures = new HashMap<String,Texture>();
		mapEntityTextures = new HashMap<String,Texture>();
		loadAnimals();
		loadItems();
		loadMapEntities();
	}
	
	/**
	 * Loads all the animal animations into the game
	 */
	private void loadAnimals(){
		String[] animations = {"hippo","lion","zebra"};
		for (String x: animations){
			Texture text = new Texture("textures/Animals/"+ x + ".png");
			TextureRegion[][] tmp = TextureRegion.split(text, text.getWidth() / 2,
					text.getHeight());
			TextureRegion[] animationFrames = new TextureRegion[2];
			int index = 0;
			for (int i = 0; i < 2; i++) {
				animationFrames[index++] = tmp[0][i];
			}
			animalAnimations.put(x, new Animation(0.5f, animationFrames));
			
			Texture text2 = new Texture("textures/Animals/" + x + "Dead.png");
			TextureRegion[][] tmp2 = TextureRegion.split(text2, text2.getWidth(), text2.getHeight());
			TextureRegion[] animationFrames2 = new TextureRegion[1];
			animationFrames2[0] = tmp2[0][0];
			animalAnimations.put(x + "DEAD", new Animation(0.5f, animationFrames2));
		}
		
	}
	
	
	/**
	 * Loads all the item textures 
	 */
	private void loadItems(){
		String[] textures = {"DoublePoints", "ExtraLife", "Invulnerability", "Coin","Bow","Spear","Trident"};
        for (int t = 0, tl = textures.length; t < tl; t++) {
			Texture texture = new Texture("textures/Items/" + textures[t] + ".png");
			itemTextures.put(textures[t], texture);
		}
	}
	
	/**
	 * Loads all the map entity textures
	 */
	private void loadMapEntities(){
		String[] textures = {"arrow", "spike trap", "net","bomb","deathShroom","explosion"};
        for (int t = 0, tl = textures.length; t < tl; t++) {
			Texture texture = new Texture("textures/MapEntities/" + textures[t] + ".png");
			mapEntityTextures.put(textures[t], texture);
		}
	}
	
	/**
	 * Returns an animal animation
	 * @param animal - The animal animation required
	 * @return Animation of the animal
	 */
	public Animation getAnimalAnimation(String animal){
		return animalAnimations.get(animal);
	}
	 /**
	  * Returns an item texture
	  * @param item - The item texture that is specified
	  * @return Texture of the item
	  */
	public Texture getItemTexture(String item){
		return itemTextures.get(item);
	}
	
	/**
	 * Returns a Map Entity texture
	 * @param map - The  map entity texture required
	 * @return Texture of the map entity
	 */
	public Texture getMapEntity(String map){
		return mapEntityTextures.get(map);
	}

    public void dispose() {
        for (Texture me : mapEntityTextures.values()) {
            me.dispose();
        }

        for (Texture i : itemTextures.values()) {
            i.dispose();
        }
    }
}
