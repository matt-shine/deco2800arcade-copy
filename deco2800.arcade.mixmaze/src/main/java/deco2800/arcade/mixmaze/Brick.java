package deco2800.arcade.mixmaze;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;




public class Brick extends Actor {

	private Texture texture;
	private TextureRegion region;
	long lastDropTime;
	
	Brick() {		
		texture = new Texture(Gdx.files.internal("brick.png"));
		region = new TextureRegion(texture, 0, 0, 512, 512);
		setPixelLocation(0,0);		
	}

	@Override
	public void act(float delta) {
		  if(TimeUtils.millis() - lastDropTime >5000) spawnBrick();
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.draw(region, getX(), getY(), 128, 128);
	}
	
	private void spawnBrick() {	
		setPixelLocation(MathUtils.random(0, 4),MathUtils.random(0, 4));
		lastDropTime = TimeUtils.millis();
		
	}
	
	private void setPixelLocation(int row,int col){
		setX(128f * col);
		setY(128f * row);
	}
}
