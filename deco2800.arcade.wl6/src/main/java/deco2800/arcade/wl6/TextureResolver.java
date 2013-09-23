package deco2800.arcade.wl6;

import com.badlogic.gdx.math.Vector2;

public class TextureResolver {

	public static Vector2 getTextureLocation(String s) {
		
		//temporary solution -- should make this more elegant
		
		float size = 3;
		
		//bricks
		if (s == "wood") {
			return new Vector2(0 / size, 1 / size);
		}
		if (s == "greybrick") {
			return new Vector2(1 / size, 0 / size);
		}
		if (s == "redbrick") {
			return new Vector2(1 / size, 1 / size);
		}
		if (s == "bluebrick") {
			return new Vector2(2 / size, 0 / size);
		}
		if (s == "steel") {
			return new Vector2(2 / size, 1 / size);
		}
		if (s == "door") {
			return new Vector2(1 / size, 2 / size);
		}
		if (s == "doodad") {
			return new Vector2(0 / size, 2 / size);
		}
		
		
		//unknown
		return new Vector2(0 / 2048, 0 / 2048);
		
	}
	
	
	
}
