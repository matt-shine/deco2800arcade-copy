package deco2800.arcade.wl6;

import com.google.gson.Gson;

public class Level {

    private int[][] terrain = null;
    private int[][] doodads = null;


    /**
     * Initialise the level data from a JSON array of 2x64x64 integers <= 255
     * @param jsonArray
     */
    public Level(String jsonArray) {
        Gson g = new Gson();
        int[][][] data = g.fromJson(jsonArray, int[][][].class);

        terrain = transpose(data[0]);
        doodads = transpose(data[1]);

        if (terrain.length != 64 || doodads.length != 64) {
            throw new IllegalArgumentException("wrong sized level");
        }
    }




    /**
     * Returns the tile id at (x, y) on the terrain layer
     * @param x
     * @param y
     * @return
     */
    public int getTerrainAt(int x, int y) {
        if (x >= 64 || x < 0 || y >= 64 || y < 0) return 0;
        return terrain[x][y];
    }




    /**
     * Returns the tile id of (x, y) on the doodads layer
     * @param x
     * @param y
     * @return
     */
    public int getDoodadAt(int x, int y) {
        if (x >= 64 || x < 0 || y >= 64 || y < 0) return 0;
        return doodads[x][y];
    }




    /**
     * Transposes an array. Taken from
     * http://stackoverflow.com/questions/8422374/java-multi-dimensional-array-transposing
     * @param array
     * @return
     */
    public int[][] transpose (int[][] array) {
        if (array == null || array.length == 0) {
            return array;
        }

        int width = array.length;
        int height = array[0].length;

        int[][] array_new = new int[height][width];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                array_new[y][x] = array[x][y];
            }
        }
        return array_new;
    }


}
