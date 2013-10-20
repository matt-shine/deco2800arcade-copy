package deco2800.arcade.cyra.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class ParallaxLayer {
	private Array<Texture> bg;
	private Array<Vector2> offsetPositions;
	private float repeatWidth;
	private Vector2 basePos;
	private int repeatCount;
	
	
	public ParallaxLayer(Array<Texture> backgroundTextures, Array<Vector2> offsetPositions, float repeatWidth, int repeatCount, Vector2 basePosition) {
		bg = backgroundTextures;
		this.offsetPositions = offsetPositions;
		this.repeatWidth = repeatWidth;
		this.repeatCount = repeatCount;
		basePos = basePosition;
		
	}
	
	public int getElementsSize() {
		return bg.size;
	}
	
	public Vector2 getBasePosition() {
		return basePos;
	}
	
	public Array<Texture> getAllDrawTextures() {
		Array<Texture> output = new Array<Texture>();
		for (int i=0; i <repeatCount; i++) {
			for (int j=0; j < bg.size; j++) {
				output.add(bg.get(j));
			}
		}
		return output;
	}
		
	public Array<Vector2> getAllDrawPositions() {
		Array<Vector2> output = new Array<Vector2>();
		for (int i=0; i <repeatCount; i++) {
			for (int j=0; j < offsetPositions.size; j++) {
				output.add(new Vector2(basePos.x + i*repeatWidth + offsetPositions.get(j).x, basePos.y+offsetPositions.get(j).y));
			}
		}
		return output;
	}

}

