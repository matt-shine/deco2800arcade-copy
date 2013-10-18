package deco2800.arcade.burningskies.screen;

import java.util.Map;
import java.util.TreeMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import deco2800.arcade.burningskies.BurningSkies;
import deco2800.arcade.burningskies.Configuration;
import deco2800.arcade.client.ArcadeInputMux;

public class ScoreScreen implements Screen {
	private BurningSkies game;
    private Stage stage;
    private BitmapFont black;
    private BitmapFont white;
    private Skin skin;
    private SpriteBatch batch;
    private TextButton backButton;
    private Image background;
	private MenuInputProcessor processor;
	private Image scoreTableImage;
	private Label nameLabelOne;
	private Label nameLabelTwo;
	private Label scoreLabelOne;
	private Label scoreLabelTwo;
	private Label localLabel;
	private Label globalLabel;
	private Label[] localNameLabelArray = new Label[5];
	private Label[] localScoreLabelArray = new Label[5];
	private Map<Long, String> localScoresMap = new TreeMap<Long, String>();
    
    int width = BurningSkies.SCREENWIDTH;
    int height = BurningSkies.SCREENHEIGHT;
	
	public ScoreScreen(BurningSkies game) {
		this.game = game;
	}

	@Override
	public void dispose() {
		batch.dispose();
        skin.dispose();
        white.dispose();
        black.dispose();
        stage.dispose();
	}

	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
		ArcadeInputMux.getInstance().removeProcessor(processor);
		this.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
    		game.setScreen(game.menuScreen);
    	}
        
        stage.act(delta);

        batch.begin();
        stage.draw();
        batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("images/menu/uiskin32.json"));
        white = new BitmapFont(Gdx.files.internal("images/menu/whitefont.fnt"), false);
        black = new BitmapFont(Gdx.files.internal("images/menu/font.fnt"), false);
        background = new Image(new Texture(Gdx.files.internal("images/menu/menu_background.png")));
        scoreTableImage = new Image(new Texture(Gdx.files.internal("images/menu/dual_score_table.png")));

        stage = new Stage(width, height, true);
	
        ArcadeInputMux.getInstance().addProcessor(stage);
        
        processor = new MenuInputProcessor(game);
    	ArcadeInputMux.getInstance().addProcessor(processor);

    	Configuration.readLocalHighScores();
    	localScoresMap = Configuration.getScores();
    	addLabels();
    	
	    backButton = new TextButton("Back", skin);
	    backButton.setWidth(200);
	    backButton.setHeight(50);
	    backButton.setX((float)(width*0.02));
	    backButton.setY((float)(height*0.02));
	    
	    backButton.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    game.setScreen(game.menuScreen);
            }
	    });
	    
	    stage.addActor(background);
	    stage.addActor(scoreTableImage);
	    stage.addActor(backButton);
	    stage.addActor(nameLabelOne);
	    stage.addActor(nameLabelTwo);
	    stage.addActor(scoreLabelOne);
	    stage.addActor(scoreLabelTwo);
	    stage.addActor(localLabel);
	    stage.addActor(globalLabel);
	    
	    int scoreLimit = localScoresMap.size();
	    
	    for (int i = 0; i < scoreLimit; i++) {	    
	    	stage.addActor(localNameLabelArray[i]);
	    	stage.addActor(localScoreLabelArray[i]);
	    }
	    
	    background.toBack();    
	}
	
	private void addLabels() {
		LabelStyle ls = new LabelStyle(white, Color.WHITE);
		
		nameLabelOne = new Label("NAME", ls);
		nameLabelOne.setX(65);
		nameLabelOne.setY(720 - 225);
		nameLabelOne.setWidth(115);
		
		nameLabelTwo = new Label("NAME", ls);
		nameLabelTwo.setX(620 + 65);
		nameLabelTwo.setY(720 - 225);
		nameLabelTwo.setWidth(115);
		
		scoreLabelOne = new Label("SCORE", ls);
		scoreLabelOne.setX(225);
		scoreLabelOne.setY(720 - 225);
		scoreLabelOne.setWidth(115);
		
		scoreLabelTwo = new Label("SCORE", ls);
		scoreLabelTwo.setX(225 + 620);
		scoreLabelTwo.setY(720 - 225);
		scoreLabelTwo.setWidth(115);
		 
		localLabel = new Label("Local Scores", ls);
		localLabel.setX(width/4 - localLabel.getWidth()/2 + 15);
		localLabel.setY(720 - 175);
		localLabel.setWidth(115);
		
		globalLabel = new Label("Global Scores", ls);
		globalLabel.setX((3*width)/4 - globalLabel.getWidth()/2 - 10);
		globalLabel.setY(720 - 175);
		globalLabel.setWidth(115);
		
		int i = 0;
		for (Map.Entry<Long, String> entry : localScoresMap.entrySet()) {
			localNameLabelArray[i] = new Label(entry.getValue(), ls);
			localNameLabelArray[i].setX(65); //X will always be the same
			localNameLabelArray[i].setY(720 - 225 - 100 - 100*i); //Y will be some multiple of i
			localNameLabelArray[i].setWidth(115);
			
			localScoreLabelArray[i] = new Label(String.valueOf(-1 * entry.getKey()), ls);
			localScoreLabelArray[i].setX(225);
			localScoreLabelArray[i].setY(720 - 225 - 80 - 100*i);
			localScoreLabelArray[i].setWidth(115);
			
			i++;
		}
	}
}
