package deco2800.arcade.hunter.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import deco2800.arcade.hunter.Hunter.Config;

public class MapPaneRenderer {
    private static final Pixmap tileMap = new Pixmap(Gdx.files.internal("textures/tilemap.png"));
    private static final int tileMapWidthTiles = tileMap.getWidth() / Config.TILE_SIZE;

    /**
     * Render the given map pane to a TextureRegion for drawing to the screen
     *
     * @param paneData map pane data to be drawn
     * @return rendered texture version of the map
     */
    public static TextureRegion renderPane(int[][] paneData) {
        //MapPane sized texture
        TextureRegion tr = new TextureRegion(new Texture(Config.PANE_SIZE_PX, Config.PANE_SIZE_PX, Format.RGBA8888));

        //Temporary pixmap to hold the image of one tile
        Pixmap tile = new Pixmap(Config.TILE_SIZE, Config.TILE_SIZE, Format.RGBA8888);
        tile.setColor(Color.CLEAR);

        for (int row = 0; row < Config.PANE_SIZE; row++) {
            for (int col = 0; col < Config.PANE_SIZE; col++) {
                int tileX = (paneData[row][col] % tileMapWidthTiles) * Config.TILE_SIZE;
                int tileY = paneData[row][col] / tileMapWidthTiles * Config.TILE_SIZE;

                tile.fill();
                //Draw the tile image to the temporary pixmap
                tile.drawPixmap(tileMap, 0, 0, tileX, tileY, Config.TILE_SIZE, Config.TILE_SIZE);
                //Draw the tile to the main MapPane render
                tr.getTexture().draw(tile, col * Config.TILE_SIZE, row * Config.TILE_SIZE);
            }
        }
        tile.dispose();
        return tr;
    }

    /**
     * To be called when the GameScreen is disposed
     */
    public static void dispose() {
        tileMap.dispose();
    }
}
