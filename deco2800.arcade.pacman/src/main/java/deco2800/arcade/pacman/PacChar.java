package deco2800.arcade.pacman;

import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.FilledRectangle;
import static com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Rectangle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PacChar extends Actor{
	private static final int FRAME_COLS = 2;
	private static final int FRAME_ROWS = 4;
	@SuppressWarnings("unused")
	private int facing = 1; // 1: Right, 3: Left
							// 3: Up, 4: Down

	Sprite sprite;
	private Stage stage;
	@SuppressWarnings("unused")
	private Texture texture;
	private TextureRegion region;
	Animation walkAnimation;
	Texture walkSheet;
	TextureRegion[] walkFrames;
	SpriteBatch spriteBatch;
	TextureRegion currentFrame;

	float stateTime;

	public void create() {
		
		
		// Create a stage, create Pacman actor. 
		stage = new Stage(800, 800, false);
        Gdx.input.setInputProcessor(stage);
        //stage.addActor(actor);
       
        // Create textures
        texture = new Texture(Gdx.files.internal("data/libgdx.png"));
        TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);    
       

		
//		shapeRenderer = new ShapeRenderer();
		
		// TODO: Need to modify this to work with our current sprites
		sprite = new Sprite(new Texture(Gdx.files.internal("pacmove.png")));

		walkSheet = new Texture(Gdx.files.internal("pacmove.png"));
		TextureRegion[][] tmp = TextureRegion.split(walkSheet,
				walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight()
						/ FRAME_ROWS);
		walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFrames[index++] = tmp[i][j];
			}
		}
		
		
		walkAnimation = new Animation(0.025f, walkFrames);
		spriteBatch = new SpriteBatch();
		stateTime = 0f;
	}

	public void render() {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		spriteBatch.begin();
		spriteBatch.draw(currentFrame, 50, 50);
		spriteBatch.end();
	}


}
