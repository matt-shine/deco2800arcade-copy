package deco2800.arcade.wl6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Stores, generates, maintains, and uploads the VBO, prepares the shader
 * @author Simon
 *
 */
public class Renderer {

    private ShaderProgram terrainShader = null;
    private ShaderProgram doodadShader = null;
    private Mesh terrainMesh;
    private Mesh quadMesh;
    private GameModel game = null;
    private WL6 wl6 = null;
    private boolean debugMode = false;
    private HashMap<String, Rectangle> texturePositions = new HashMap<String, Rectangle>();
    private Texture texture = null;
    private int ceilingColor = -1;
    private String level = "";
    
    
    
    public Renderer() {
    }

    public GameModel getGame() {
        return game;
    }

    public void setGame(GameModel game, WL6 wl6) {
        this.wl6 = wl6;
        this.game = game;
    }

    /**
     * zero existing buffer or create one if none exists and
     * fill with generated data
     */
    public void load() {
        if (terrainShader == null) {

            //texture atlas load
            FileHandle imageFileHandle = Gdx.files.internal("wl6Atlas.atlas");
            TextureAtlas atlas = new TextureAtlas(imageFileHandle);

            Array<AtlasRegion> regions = atlas.getRegions();
            for (int i = 0; i < regions.size; i++) {
                texturePositions.put(regions.get(i).name,
                        new Rectangle(regions.get(i).getU(), regions.get(i).getV(),
                                regions.get(i).getU2() - regions.get(i).getU(),
                                regions.get(i).getV2() - regions.get(i).getV())

                );
            }
            texture = (Texture) atlas.getTextures().toArray()[0];



            //gl init
            Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
            Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            

            //load terrain shader
            String vertexShader = loadFile("wl6TerrainVertShader.glsl");
            String fragmentShader = loadFile("wl6TerrainFragShader.glsl");

            terrainShader = new ShaderProgram(vertexShader, fragmentShader);
            VertexAttribute pos = new VertexAttribute(Usage.Position, 3, "a_position");
            VertexAttribute tex = new VertexAttribute(Usage.TextureCoordinates, 2, "a_texpos");
            
            loadTerrainMesh();


            //load doodad shader
            vertexShader = loadFile("wl6DoodadVertShader.glsl");
            fragmentShader = loadFile("wl6DoodadFragShader.glsl");

            doodadShader = new ShaderProgram(vertexShader, fragmentShader);
            pos = new VertexAttribute(Usage.Position, 3, "a_position");
            tex = new VertexAttribute(Usage.TextureCoordinates, 2, "a_texpos");
            float[] posData = toPrimativeArray(generateQuadMesh());
            quadMesh = new Mesh(true, posData.length, 0, pos, tex);
            quadMesh.setVertices(posData);

            String compileErrors = (terrainShader.getLog() + " " + doodadShader.getLog()).trim();
            if (compileErrors.length() != 0) {
                System.out.println("Shader compile errors: " + compileErrors);
            }


        } else {
        	
        	loadTerrainMesh();
        	
        }
    }
    
    
    
    public void loadTerrainMesh() {
    	if (terrainMesh != null) {
    		terrainMesh.dispose();
    		
    	}
    	
    	float[] posData = toPrimativeArray(generateTerrain(game.getMap(), false));
    	VertexAttribute pos = new VertexAttribute(Usage.Position, 3, "a_position");
        VertexAttribute tex = new VertexAttribute(Usage.TextureCoordinates, 2, "a_texpos");
        terrainMesh = new Mesh(true, posData.length, 0, pos, tex);
        terrainMesh.setVertices(posData);

    }
    


    public Matrix4 getProjectionViewMatrix() {

        //projection
        float aspect = Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
        Matrix4 projection = new Matrix4();
        projection.setToProjection(0.01f, 200.0f, 45.0f, aspect);

        //view
        Matrix4 view = debugMode ? getDebugCameraTransform() : getCameraTransform();

        return projection.mul(view);

    }


    public void draw(boolean debug) {
        debugMode = debug;

        
        //reload the level if we've changed levels
        if (!game.getLevel().equals(level)) {
        	this.load();
        }
        

        //prepare
        Gdx.gl.glClearColor(0.48f, 0.48f, 0.48f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.graphics.getGL20().glEnable(GL20.GL_TEXTURE_2D);
        texture.bind();

        IngameUI.drawCeiling(getCeilingColor(), wl6.getWidth(), wl6.getHeight());
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        

        //draw terrain
        terrainShader.begin();

        Matrix4 pv = getProjectionViewMatrix();

        //the terrain's model transform is the identity, so we don't bother
        terrainShader.setUniformMatrix("uMVPMatrix", pv);

        //draw
        terrainMesh.render(terrainShader, GL20.GL_TRIANGLES);
        terrainShader.end();





        //draw doodads
        doodadShader.begin();

        Iterator<Doodad> itr = game.getDoodadIterator();
        while (itr.hasNext()) {
            Doodad d = itr.next();
            d.draw(this);
        }

        doodadShader.end();



    }


    /**
     * Draws a sprite. This assumes that the doodadShader is active
     * @param s
     */
    public void drawBasicSprite(String s, float x, float y, float angle) {

        //camera
        Matrix4 pv = getProjectionViewMatrix();

        //model transform
        Matrix4 m = new Matrix4().idt();
        m.translate(x, y, 0.5f);
        if (this.debugMode) {
            //if debug mode, face up, and face the player
            m.rotate(0, 0, 1, angle + 180);
            m.rotate(1, 0, 0, 270);
        } else {
            //if not debug mode, rotate to face the player
            m.rotate(0, 0, 1, angle);
        }


        //combine matrixes
        Matrix4 combined = pv.mul(m);
        doodadShader.setUniformMatrix("uMVPMatrix", combined);

        //get texture
        Rectangle r = this.texturePositions.get(s);
        if (r == null) {
            r = this.texturePositions.get("unknown");
        }
        Vector2 texPos = new Vector2(r.x, r.y);


        //set texture offset
        doodadShader.setUniform2fv("u_texoffset", new float[]{texPos.x, texPos.y}, 0, 2);

        //draw
        quadMesh.render(doodadShader, GL20.GL_TRIANGLES);

    }



    private int getCeilingColor() {
        if (ceilingColor == -1) {
            //TODO work out ceiling color
        }

        return 0x555555;

    }



    /**
     * Takes an ArrayList of Floats and returns an equivalent float[]
     * @param data
     * @return
     */
    public static float[] toPrimativeArray(List<Float> data) {
        float[] ret = new float[data.size()];
        for (int i=0; i < ret.length; i++) {
            ret[i] = data.get(i).floatValue();
        }
        return ret;
    }


    /**
     * Returns the vertices for a square.
     * @return
     */
    public ArrayList<Float> generateQuadMesh() {

        ArrayList<Float> quadMesh = new ArrayList<Float>();

        Rectangle r = this.texturePositions.get("unknown");
        float texS = r.width;

        CubeGen.genQuad(texS, quadMesh);

        return quadMesh;

    }


    /**
     * Generates the terrain for the current level
     * @param map
     * @param debug
     */
    public ArrayList<Float> generateTerrain(Level map, boolean debug) {

        ArrayList<Float> terrainScratch = new ArrayList<Float>();

        for (int i = 0; i < WL6.MAP_DIM; i++) {
            for (int j = 0; j < WL6.MAP_DIM; j++) {

                //we want to generate all terrain blocks that have a texture
                if (WL6Meta.block(map.getTerrainAt(i, j)).texture != null) {


                    if (map.getDoodadAt(i, j) == WL6Meta.SECRET_DOOR ||
                            WL6Meta.isSurrounded(i, j, map)) {
                        continue;
                    }


                    Rectangle r = this.texturePositions.get(
                            WL6Meta.block(map.getTerrainAt(i, j)).texture);
                    if (r == null) {
                        r = this.texturePositions.get("unknown");
                    }
                    Vector2 texPos = new Vector2(r.x, r.y);
                    float texS = r.width;

                    CubeGen.getCube(i, j,
                            texPos.x, texPos.y, texS,
                            i != 0 && !WL6Meta.hasObscuringBlockAt(i - 1, j, map),
                            i != 63 && !WL6Meta.hasObscuringBlockAt(i + 1, j, map),
                            j != 0 && !WL6Meta.hasObscuringBlockAt(i, j - 1, map),
                            j != 63 && !WL6Meta.hasObscuringBlockAt(i, j + 1, map),
                            true,
                            terrainScratch
                    );


                }
            }
        }
        return terrainScratch;
    }


    /**
     * TODO dispose wl6 renderer
     */
    public void dispose() {
        Gdx.gl20.glDisable(GL20.GL_DEPTH_TEST);
        this.doodadShader.dispose();
        this.terrainShader.dispose();
        this.quadMesh.dispose();
        this.terrainMesh.dispose();
    }



    /**
     * Loads a text file
     * @param name
     * @return
     */
    private String loadFile(String name) {
        return Gdx.files.internal(name).readString();
    }



    /**
     * return a top down camera transform
     * @return
     */
    public Matrix4 getDebugCameraTransform() {
        return new Matrix4().idt().trn(-32, 32, -80.0f).rotate(1, 0, 0, 180);
    }

    /**
     * return the player's first person camera transform
     * @return
     */
    public Matrix4 getCameraTransform() {
        float x = game.getPlayer().getPos().x;
        float y = game.getPlayer().getPos().y;
        float r = game.getPlayer().getAngle();

        Matrix4 m = new Matrix4().idt();

        //we're looking down at the moment, we need to look at the horizon
        m.rotate(1, 0, 0, 90);

        //now look the same way the player is facing
        m.rotate(0, 0, 1, r);

        //now translate
        m.translate(-x, -y, -0.5f);

        return m;
    }


    /**
     * True if the render is in debug
     * @return
     */
    public boolean isDebugMode() {
        return this.debugMode;
    }

}
