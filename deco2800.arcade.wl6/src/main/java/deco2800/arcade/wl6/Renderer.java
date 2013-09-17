package deco2800.arcade.wl6;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
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

	//private FloatBuffer data = ByteBuffer.allocateDirect(10000000).asFloatBuffer();
	private ShaderProgram shader = null;
	private Mesh mesh;
	private ArrayList<Float> terrainScratch = new ArrayList<Float>();
	private Matrix4 projection = new Matrix4();
	private Matrix4 view = new Matrix4();
	private Matrix4 model = new Matrix4();
	private Matrix4 combined = new Matrix4();
	private GameModel game = null;

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
		if (shader == null) {
			
			String vertexShader = this.getVertShaderSource();
			String fragmentShader = this.getFragShaderSource();

			shader = new ShaderProgram(vertexShader, fragmentShader);
			VertexAttribute pos = new VertexAttribute(Usage.Position, 3, "a_position");
			float[] posData = toPrimativeArray(terrainScratch);
			mesh = new Mesh(true, posData.length, 0, pos);
			mesh.setVertices(posData);
			
		} else {
			//empty buffer
		}
	}
	

	public void draw(boolean debug) {
		
		shader.begin();
		
		//projection
		float aspect = Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
		projection.setToProjection(0.001f, 200.0f, 60.0f, aspect);
		//projection.setToOrtho(0, aspect, aspect, 0, 0, 200);
		
		//view
		view = debug ? getDebugCameraTransform() : getCameraTransform();
		
		//terrain transform
		model.idt();//.setToRotation(axis, angle);
		
		//combine
		combined.set(projection).mul(view).mul(model);
		shader.setUniformMatrix("uMVPMatrix", combined);
		
		//draw
		mesh.render(shader, GL20.GL_TRIANGLES);
		shader.end();
	}
	
	public static float[] toPrimativeArray(List<Float> data) {
		float[] ret = new float[data.size()];
	    for (int i=0; i < ret.length; i++) {
	        ret[i] = data.get(i).floatValue();
	    }
	    return ret;
	}

	
	public void generateTerrain(Level map, boolean debug) {
		for (int i = 0; i < WL6.MAP_DIM; i++) {
		    for (int j = 0; j < WL6.MAP_DIM; j++) {
		    	if (WL6Meta.block(map.getTerrainAt(i, j)).solid) {
		    		
		    		//triangle 1
		    		//bottom left
		    		terrainScratch.add((float) i);
		    		terrainScratch.add((float) j);
		    		terrainScratch.add((float) 0);
		    		
		    		//bottom right
		    		terrainScratch.add((float) i + 1);
		    		terrainScratch.add((float) j);
		    		terrainScratch.add((float) 0);
		    		
		    		//top left
		    		terrainScratch.add((float) i);
		    		terrainScratch.add((float) j + 1);
		    		terrainScratch.add((float) 0);
		    		
		    		//triangle 2
		    		//top left
		    		terrainScratch.add((float) i);
		    		terrainScratch.add((float) j + 1);
		    		terrainScratch.add((float) 0);
		    		
		    		//bottom right
		    		terrainScratch.add((float) i + 1);
		    		terrainScratch.add((float) j);
		    		terrainScratch.add((float) 0);
		    		
		    		//top right
		    		terrainScratch.add((float) i + 1);
		    		terrainScratch.add((float) j + 1);
		    		terrainScratch.add((float) 0);
		    		
		    	}
		    }
	    }
		

	}
	
	
	public void generateDoodads(Level map, boolean debug) {
		//make the doodads
		
	}
	
	public void dispose() {
		
	}
	
	private String loadFile(String name) {
		return Gdx.files.internal(name).readString();
	}
	
	private String vertShaderSource = "";
	public String getVertShaderSource() {
		if (vertShaderSource.length() == 0) {
			vertShaderSource = loadFile("wl6vertShader.glsl");
		}
		return vertShaderSource;
	}
	
	
	private String fragShaderSource = "";
	public String getFragShaderSource() {
		if (fragShaderSource.length() == 0) {
			fragShaderSource = loadFile("wl6fragShader.glsl");
		}
		return fragShaderSource;
	}
	
	public Matrix4 getDebugCameraTransform() {
		float scale = 0.1f;
		return new Matrix4().idt().trn(-(32 * scale), -(32 * scale), -5.0f).scale(scale, scale, scale);
	}
	
	public Matrix4 getCameraTransform() {
		float x = game.getPlayer().getX();
		float y = game.getPlayer().getY();
		float r = game.getPlayer().getAngle();
		System.out.println("player: " + x + " " + y + " " + r);
		
		Matrix4 m = new Matrix4().idt();
		//we're looking down at the moment, we need to look at the horizon
		m.rotate(1, 0, 0, 90);
		//now look the same way the player is facing
		m.rotate(0, 0, 1, r);
		//now translate
		m.translate(-x, -y, 0.5f);
		return m;
	}
	
}
