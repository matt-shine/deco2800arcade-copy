package deco2800.arcade.hunter.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import deco2800.arcade.hunter.Hunter.Config;
import deco2800.arcade.hunter.model.MapPane.MapType;

public class MapPaneRenderer {
	private static Pixmap tilemap = new Pixmap(Gdx.files.internal("textures/tilemap.png"));
	
	/**
	 * Render the given map pane to a TextureRegion for drawing to the screen
	 * @param pane map pane to be drawn
	 * @param type current map type
	 * @return rendered texture version of the map
	 */
	public static TextureRegion renderPane(MapPane pane, MapType type) {
		int tilemapRow = type.ordinal() * Config.TILE_SIZE; //Tilemap row to draw from
		int[][] data = pane.getData();
		
		//MapPane sized texture
		TextureRegion tr = new TextureRegion(new Texture(Config.PANE_SIZE * Config.TILE_SIZE, Config.PANE_SIZE * Config.TILE_SIZE, Format.RGBA8888));
		
		Pixmap tile; //Temporary pixmap to hold the image of one tile
		
		for (int row = 0; row < data.length; row++) {
			for (int col = 0; col < data[row].length; col++) {
				tile = new Pixmap(Config.TILE_SIZE, Config.TILE_SIZE, Format.RGBA8888); //probably shouldn't update this for every tile
				//Draw the tile image to the temporary pixmap
				tile.drawPixmap(tilemap, 0, 0, data[row][col] * Config.TILE_SIZE, tilemapRow, Config.TILE_SIZE, Config.TILE_SIZE);
				//Draw the tile to the main MapPane render
				tr.getTexture().draw(tile, col * Config.TILE_SIZE, row * Config.TILE_SIZE);
			}
		}
		
		return tr;
	}
}
