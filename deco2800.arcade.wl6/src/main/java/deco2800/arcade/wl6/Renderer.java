package deco2800.arcade.wl6;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

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
	private ArrayList<Float> terrainScratch = new ArrayList<Float>();
	private GameModel game = null;
	private boolean debugMode = false;
	private Texture terrainAtlas = null;
	private Texture doodadsAtlas = null;
	
	
	public Renderer() {
	}
	
	public GameModel getGame() {
		return game;
	}

	public void setGame(GameModel game) {
		this.game = game;
	}

	/**
	 * zero existing buffer or create one if none exists and
	 * fill with generated data
	 */
	public void load() {
		if (terrainShader == null) {

			FileHandle imageFileHandle = Gdx.files.internal("wl6WallsAtlas.png"); 
	        terrainAtlas = new Texture(imageFileHandle);

			imageFileHandle = Gdx.files.internal("wl6DoodadsAtlas.png"); 
	        doodadsAtlas = new Texture(imageFileHandle);
			
	        
			Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST);
			
			String vertexShader = loadFile("wl6TerrainVertShader.glsl");
			String fragmentShader = loadFile("wl6TerrainFragShader.glsl");

			terrainShader = new ShaderProgram(vertexShader, fragmentShader);
			VertexAttribute pos = new VertexAttribute(Usage.Position, 3, "a_position");
			VertexAttribute tex = new VertexAttribute(Usage.TextureCoordinates, 2, "a_texpos");
			float[] posData = toPrimativeArray(terrainScratch);
			terrainMesh = new Mesh(true, posData.length, 0, pos, tex);
			terrainMesh.setVertices(posData);
			
			
			
			
			
			vertexShader = loadFile("wl6DoodadVertShader.glsl");
			fragmentShader = loadFile("wl6DoodadFragShader.glsl");

			doodadShader = new ShaderProgram(vertexShader, fragmentShader);
			pos = new VertexAttribute(Usage.Position, 3, "a_position");
			tex = new VertexAttribute(Usage.TextureCoordinates, 2, "a_texpos");
			posData = toPrimativeArray(generateQuadMesh());
			quadMesh = new Mesh(true, posData.length, 0, pos, tex);
			quadMesh.setVertices(posData);
			
			System.out.println("Shader compile errors: " + terrainShader.getLog() + " " + doodadShader.getLog());
			
			
		} else {
			//empty buffer
		}
	}
	
	
	public Matrix4 getProjectionViewMatrix() {
		
		//projection
		float aspect = Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
		Matrix4 projection = new Matrix4();
		projection.setToProjection(0.01f, 200.0f, 60.0f, aspect);
		
		//view
		Matrix4 view = debugMode ? getDebugCameraTransform() : getCameraTransform();

		return projection.mul(view);
		
	}
	

	public void draw(boolean debug) {
		debugMode = debug;
		
		//prepare
		Gdx.graphics.getGL20().glEnable(GL20.GL_TEXTURE_2D);
		terrainAtlas.bind();
		
		
		//draw terrain
		terrainShader.begin();
		
		Matrix4 pv = getProjectionViewMatrix();
		
		//the terrain's model transform is the identity, so we don't bother
		terrainShader.setUniformMatrix("uMVPMatrix", pv);
		
		//draw
		terrainMesh.render(terrainShader, GL20.GL_TRIANGLES);
		terrainShader.end();
		
		
		doodadsAtlas.bind();
		
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
		
		//set texture offset
		doodadShader.setUniform2fv("u_texoffset", new float[]{0, 0}, 0, 2);
		
		//draw
		quadMesh.render(doodadShader, GL20.GL_TRIANGLES);
		
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
		
		float texX = 0;
		float texY = 0;
		float texS = 1;
		
		CubeGen.genQuad(texX, texY, texS, quadMesh);
		
		return quadMesh;
		
	}
	
	
	/**
	 * Generates the terrain for the current level
	 * @param map
	 * @param debug
	 */
	public void generateTerrain(Level map, boolean debug) {
		for (int i = 0; i < WL6.MAP_DIM; i++) {
		    for (int j = 0; j < WL6.MAP_DIM; j++) {
		    	
		    	//we want to generate all terrain blocks that have a texture
		    	if (WL6Meta.block(map.getTerrainAt(i, j)).texture != null) {
		    		
		    		
		    		if (map.getDoodadAt(i, j) == WL6Meta.SECRET_DOOR ||
		    				isSurrounded(i, j, map)) {
		    			continue;
		    		}
		    		

		    		float texX = 0;
		    		float texY = 0;
		    		float texS = 1;
		    		
		    		CubeGen.getCube(i, j, texX, texY, texS, terrainScratch);
		    		

		    	}
		    }
	    }
	}
	
	
	public boolean isSurrounded(int x, int y, Level map) {
		int surrounded = 0;
		//check if we're surrounded
		if (x == 0 || (WL6Meta.block(map.getTerrainAt(x - 1, y)).texture != null) &&
				map.getDoodadAt(x - 1, y) != WL6Meta.SECRET_DOOR) {
			surrounded++;
		}
		if (x == 63 || (WL6Meta.block(map.getTerrainAt(x + 1, y)).texture != null) &&
				map.getDoodadAt(x + 1, y) != WL6Meta.SECRET_DOOR) {
			surrounded++;
		}
		if (y == 0 || (WL6Meta.block(map.getTerrainAt(x, y - 1)).texture != null) &&
				map.getDoodadAt(x, y - 1) != WL6Meta.SECRET_DOOR) {
			surrounded++;
		}
		if (y == 63 || (WL6Meta.block(map.getTerrainAt(x, y + 1)).texture != null) &&
				map.getDoodadAt(x, y + 1) != WL6Meta.SECRET_DOOR) {
			surrounded++;
		}
		if (surrounded == 4) {
			return true;
		}
		
		return false;
		
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
		float scale = 0.08f;
		return new Matrix4().idt().trn(-(32 * scale), (32 * scale), -5.0f).scale(scale, scale, scale).rotate(1, 0, 0, 180);
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
		
		//we're upsidedown...
		//m.rotate(1, 0, 0, 90);
				
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
