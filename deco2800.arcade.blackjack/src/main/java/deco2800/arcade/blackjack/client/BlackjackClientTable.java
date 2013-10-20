package deco2800.arcade.blackjack.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.Game;

public class BlackjackClientTable implements Screen{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture textureBackground;
	private Sprite background;
	public Blackjack_UI myGame;
	private Stage stage;
	JoinTableScreen JoinTableScreen;

	public BlackjackClientTable(
	        Blackjack_UI game )
	    {
		myGame = game;
	    }
	@Override
    public void show()
    {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		Texture.setEnforcePotImages(false);
		
		camera = new OrthographicCamera();
	    camera.viewportHeight = h;
	    camera.viewportWidth = w;

	    camera.position.set(camera.viewportWidth * .5f,
	    camera.viewportHeight * .5f, 0f);
	    camera.update();
		
		batch = new SpriteBatch();
 
        // load the image and create the texture region
        textureBackground = new Texture(Gdx.files.internal("data/Blackjacktable.png"));
 
        // we set the linear texture filter to improve the stretching
        textureBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
 
        background = new Sprite(textureBackground);
		background.setSize(w, h);
		
		//buttons
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		//placards
		
        Table table = new Table();
        stage.addActor(table);
        table.setSize(162, 113);
		table.setPosition(25, 580);
		
		Texture texturePlacard10250 = new Texture(Gdx.files.internal("data/BetPlac10250.png"));
		Texture texturePlacard1100 = new Texture(Gdx.files.internal("data/BetPlac1100.png"));
		Texture texturePlacard150 = new Texture(Gdx.files.internal("data/BetPlac150.png"));
		Texture texturePlacard25NL = new Texture(Gdx.files.internal("data/BetPlac25NL.png"));
		Texture texturePlacard5100 = new Texture(Gdx.files.internal("data/BetPlac5100.png"));
		
		TextureRegion imagePlacard = new TextureRegion(texturePlacard10250);
		
		
		Image placard = new Image(imagePlacard);
		table.add(placard);
		
		//cards
		
		Table player3card1 = new Table();
		Table player3card2 = new Table();
		Table player3card3 = new Table();
		Table player3card4 = new Table();
		Table player3card5 = new Table();
		stage.addActor(player3card1);
		stage.addActor(player3card2);
		stage.addActor(player3card3);
		stage.addActor(player3card4);
		stage.addActor(player3card5);
		player3card1.setSize(56, 81);
		player3card1.setSize(56, 81);
		player3card2.setSize(56, 81);
		player3card3.setSize(56, 81);
		player3card4.setSize(56, 81);
		player3card5.setSize(56, 81);
		player3card1.setPosition(565, 295);
		player3card2.setPosition(590, 295);
		player3card3.setPosition(615, 295);
		player3card4.setPosition(640, 295);
		player3card5.setPosition(665, 295);
		
		Texture CardQueenSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		
		TextureRegion player1cardsTexReg = new TextureRegion(CardQueenSpade);
		Image player1cardfinal = new Image(player1cardsTexReg);
		Image player1cardfinal2 = new Image(player1cardsTexReg);
		Image player1cardfinal3 = new Image(player1cardsTexReg);
		Image player1cardfinal4 = new Image(player1cardsTexReg);
		Image player1cardfinal5 = new Image(player1cardsTexReg);
		player3card1.add(player1cardfinal);
		player3card2.add(player1cardfinal2);
		player3card3.add(player1cardfinal3);
		player3card4.add(player1cardfinal4);
		player3card5.add(player1cardfinal5);
		
		//-----------------------------------------------
		//----------------BUTTONS------------------------
		
		Drawable buttontexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/null.png"))));
		TextButtonStyle buttonstyle = new TextButtonStyle(buttontexture, buttontexture, buttontexture);
		buttonstyle.font = new BitmapFont();
		buttonstyle.fontColor = Color.BLACK;
		
        TextButton hit = new TextButton("Hit", buttonstyle);
        hit.setSize(200, 20);
        hit.setPosition(20, 20);
        stage.addActor(hit);
        hit.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				return false;
			}
		});
        
        TextButton stay = new TextButton("Stay", buttonstyle);
        stay.setSize(200, 20);
        stay.setPosition(240, 20);
        stage.addActor(stay);
        stay.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				return false;
			}
		});
        
        TextButton split = new TextButton("Split", buttonstyle);
        split.setSize(200, 20);
        split.setPosition(460, 20);
        stage.addActor(split);
        split.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				return false;
			}
		});
        
        TextButton doubledown = new TextButton("Double Down", buttonstyle);
        doubledown.setSize(200, 20);
        doubledown.setPosition(680, 20);
        stage.addActor(doubledown);
        doubledown.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				return false;
			}
		});
		
    }
 
    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		background.draw(batch);
		batch.end();
		stage.draw();
    }
 
    @Override
    public void dispose()
    {
		batch.dispose();
		stage.dispose();
		textureBackground.dispose();
    }
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
