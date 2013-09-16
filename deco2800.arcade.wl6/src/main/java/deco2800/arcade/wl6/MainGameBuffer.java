package deco2800.arcade.wl6;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Stores, generates, maintains, and uploads the VBO, prepares the shader
 * @author Simon
 *
 */
public class MainGameBuffer {

	private FloatBuffer data = FloatBuffer.allocate(10000000);
	
	private int bufferObject = 0;
	private int programObject = 0;
	private int vertexShaderObject = 0;
	private int fragmentShaderObject = 0;;
	
	public MainGameBuffer() {
	}
	
	/**
	 * zero existing buffer or create one if none exists and
	 * fill with generated data
	 */
	public void load() {
		if (bufferObject == 0) {
			
			//load shaders
			//opengl is not fun
			vertexShaderObject = Gdx.gl20.glCreateShader(GL20.GL_VERTEX_SHADER);
			fragmentShaderObject = Gdx.gl20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
			Gdx.gl20.glShaderSource(vertexShaderObject, this.getVertShaderSource());
			Gdx.gl20.glShaderSource(fragmentShaderObject, this.getFragShaderSource());
			Gdx.gl20.glCompileShader(vertexShaderObject);
			//TODO check compilation success
			Gdx.gl20.glCompileShader(fragmentShaderObject);
			//TODO check compilation success
			programObject = Gdx.gl20.glCreateProgram();
			Gdx.gl20.glAttachShader(programObject, vertexShaderObject);
			Gdx.gl20.glAttachShader(programObject, fragmentShaderObject);
			Gdx.gl20.glLinkProgram(programObject);
			//TODO check link success
			System.out.println(vertexShaderObject + " " + fragmentShaderObject + " " + programObject);
			
			//buffers
			IntBuffer bufferid = IntBuffer.allocate(1);
			Gdx.gl20.glGenBuffers(1 , bufferid);
			Gdx.gl20.glBindBuffer(GL20.GL_ARRAY_BUFFER, bufferid.get(0));
			Gdx.gl20.glBufferData(GL20.GL_ARRAY_BUFFER, data.capacity(), data, GL20.GL_DYNAMIC_DRAW);
			
		} else {
			//empty buffer
		}
	}
	
	public void generateTerrain(Level map, boolean debug) {
		data.put(new float[]{0, 0, 1});
		data.put(new float[]{0, 100, 1});
		data.put(new float[]{100, 100, 1});
	    /*for (int i = 0; i < WL6.MAP_DIM; i++) {
		    for (int j = 0; j < WL6.MAP_DIM; j++) {
		    	
		    }
	    }*/
	    
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
	
}
