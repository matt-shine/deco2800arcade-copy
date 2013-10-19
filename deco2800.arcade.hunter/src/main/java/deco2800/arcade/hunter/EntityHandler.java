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
	private EntityCollection entities;
	private HashMap<String,Animation> animalAnims; //Animation HashMap for animals
	private HashMap<String,Texture> itemTextures; //Texture HashMap for items
	private HashMap<String,Texture> mapEntityTextures; //Texture HashMap for MapEntities
	
	
	public EntityHandler(EntityCollection entities){
		this.entities = entities;
		animalAnims = new HashMap<String,Animation>();
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
		String[] anims= {"hippo","lion","zebra"};
		for (String x: anims){
			Texture text = new Texture("textures/Animals/"+ x + ".png");
			TextureRegion[][] tmp = TextureRegion.split(text, text.getWidth() / 2,
					text.getHeight());
			TextureRegion[] animFrames = new TextureRegion[2];
			int index = 0;
			for (int i = 0; i < 2; i++) {
				animFrames[index++] = tmp[0][i];
			}
			animalAnims.put(x, new Animation(0.5f,animFrames));
			
			Texture text2 = new Texture("textures/Animals/" + x + "Dead.png");
			TextureRegion[][] tmp2 = TextureRegion.split(text2, text2.getWidth(), text2.getHeight());
			TextureRegion[] animFrames2 = new TextureRegion[1];
			animFrames2[0] = tmp2[0][0]; 
			animalAnims.put(x + "DEAD", new Animation(0.5f,animFrames2));
		}
		
	}
	
	
	/**
	 * Loads all the item textures 
	 */
	private void loadItems(){
		String[] textures = {"DoublePoints", "ExtraLife", "Invulnerability", "Coin","Bow","Spear","Trident"};
		for (String x:textures){
			Texture texture = new Texture("textures/Items/" + x + ".png");
			itemTextures.put(x, texture);
		}
	}
	
	/**
	 * Loads all the map entity textures
	 */
	private void loadMapEntities(){
		String[] textures = {"arrow", "spike trap","net","bomb","deathShroom"};
		for (String x: textures){
			Texture texture = new Texture("textures/MapEntities/" + x + ".png");
			mapEntityTextures.put(x, texture);
		}
	}
	
	/**
	 * Returns an animal animation
	 * @param animal - The animal animation required
	 * @return Animation of the animal
	 */
	public Animation getAnimalAnimation(String animal){
		return animalAnims.get(animal);
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
}
