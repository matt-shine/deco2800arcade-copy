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
	public Blackjack myGame;
	private Stage stage;
	JoinTableScreen JoinTableScreen;

	public BlackjackClientTable(
	        Blackjack myGame )
	    {
		this.myGame = myGame;
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
		
		Texture S2 = new Texture(Gdx.files.internal("data/cards/2S.jpg"));
		Texture S3 = new Texture(Gdx.files.internal("data/cards/3S.jpg"));
		Texture S4 = new Texture(Gdx.files.internal("data/cards/4S.jpg"));
		Texture S5 = new Texture(Gdx.files.internal("data/cards/5S.jpg"));
		Texture S6 = new Texture(Gdx.files.internal("data/cards/6S.jpg"));
		Texture S7 = new Texture(Gdx.files.internal("data/cards/7S.jpg"));
		Texture S8 = new Texture(Gdx.files.internal("data/cards/8S.jpg"));
		Texture S9 = new Texture(Gdx.files.internal("data/cards/9S.jpg"));
		Texture S10 = new Texture(Gdx.files.internal("data/cards/10S.jpg"));
		Texture SJ = new Texture(Gdx.files.internal("data/cards/JS.jpg"));
		Texture SQ = new Texture(Gdx.files.internal("data/cards/QS.jpg"));
		Texture SK = new Texture(Gdx.files.internal("data/cards/KS.jpg"));
		Texture SA = new Texture(Gdx.files.internal("data/cards/AS.jpg"));
		
		Texture H2 = new Texture(Gdx.files.internal("data/cards/2H.jpg"));
		Texture H3 = new Texture(Gdx.files.internal("data/cards/3H.jpg"));
		Texture H4 = new Texture(Gdx.files.internal("data/cards/4H.jpg"));
		Texture H5 = new Texture(Gdx.files.internal("data/cards/5H.jpg"));
		Texture H6 = new Texture(Gdx.files.internal("data/cards/6H.jpg"));
		Texture H7 = new Texture(Gdx.files.internal("data/cards/7H.jpg"));
		Texture H8 = new Texture(Gdx.files.internal("data/cards/8H.jpg"));
		Texture H9 = new Texture(Gdx.files.internal("data/cards/9H.jpg"));
		Texture H10 = new Texture(Gdx.files.internal("data/cards/10H.jpg"));
		Texture HJ = new Texture(Gdx.files.internal("data/cards/JH.jpg"));
		Texture HQ = new Texture(Gdx.files.internal("data/cards/QH.jpg"));
		Texture HK = new Texture(Gdx.files.internal("data/cards/KH.jpg"));
		Texture HA = new Texture(Gdx.files.internal("data/cards/AH.jpg"));
		
		Texture C2 = new Texture(Gdx.files.internal("data/cards/2C.jpg"));
		Texture C3 = new Texture(Gdx.files.internal("data/cards/3C.jpg"));
		Texture C4 = new Texture(Gdx.files.internal("data/cards/4C.jpg"));
		Texture C5 = new Texture(Gdx.files.internal("data/cards/5C.jpg"));
		Texture C6 = new Texture(Gdx.files.internal("data/cards/6C.jpg"));
		Texture C7 = new Texture(Gdx.files.internal("data/cards/7C.jpg"));
		Texture C8 = new Texture(Gdx.files.internal("data/cards/8C.jpg"));
		Texture C9 = new Texture(Gdx.files.internal("data/cards/9C.jpg"));
		Texture C10 = new Texture(Gdx.files.internal("data/cards/10C.jpg"));
		Texture CJ = new Texture(Gdx.files.internal("data/cards/JC.jpg"));
		Texture CQ = new Texture(Gdx.files.internal("data/cards/QC.jpg"));
		Texture CK = new Texture(Gdx.files.internal("data/cards/KC.jpg"));
		Texture CA = new Texture(Gdx.files.internal("data/cards/AC.jpg"));
		
		Texture D2 = new Texture(Gdx.files.internal("data/2D.jpg"));
		Texture D3 = new Texture(Gdx.files.internal("data/3D.jpg"));
		Texture D4 = new Texture(Gdx.files.internal("data/4D.jpg"));
		Texture D5 = new Texture(Gdx.files.internal("data/5D.jpg"));
		Texture D6 = new Texture(Gdx.files.internal("data/6D.jpg"));
		Texture D7 = new Texture(Gdx.files.internal("data/7D.jpg"));
		Texture D8 = new Texture(Gdx.files.internal("data/8D.jpg"));
		Texture D9 = new Texture(Gdx.files.internal("data/9D.jpg"));
		Texture D10 = new Texture(Gdx.files.internal("data/10D.jpg"));
		Texture DJ = new Texture(Gdx.files.internal("data/JD.jpg"));
		Texture DQ = new Texture(Gdx.files.internal("data/QD.jpg"));
		Texture DK = new Texture(Gdx.files.internal("data/KD.jpg"));
		Texture DA = new Texture(Gdx.files.internal("data/AD.jpg"));
		
		//dealer card layout
		
		Table Tabdealercard1 = new Table();
		Table Tabdealercard2 = new Table();
		Table Tabdealercard3 = new Table();
		Table Tabdealercard4 = new Table();
		Table Tabdealercard5 = new Table();
		stage.addActor(Tabdealercard1);
		stage.addActor(Tabdealercard2);
		stage.addActor(Tabdealercard3);
		stage.addActor(Tabdealercard4);
		stage.addActor(Tabdealercard5);
		Tabdealercard1.setSize(56, 81);
		Tabdealercard2.setSize(56, 81);
		Tabdealercard3.setSize(56, 81);
		Tabdealercard4.setSize(56, 81);
		Tabdealercard5.setSize(56, 81);
		Tabdealercard1.setPosition(560, 515);
		Tabdealercard2.setPosition(585, 515);
		Tabdealercard3.setPosition(610, 515);
		Tabdealercard4.setPosition(635, 515);
		Tabdealercard5.setPosition(660, 515);
		
		//player 1 card layout
		
		Table player1card1 = new Table();
		Table player1card2 = new Table();
		Table player1card3 = new Table();
		Table player1card4 = new Table();
		Table player1card5 = new Table();
		stage.addActor(player1card1);
		stage.addActor(player1card2);
		stage.addActor(player1card3);
		stage.addActor(player1card4);
		stage.addActor(player1card5);
		player1card1.setSize(56, 81);
		player1card2.setSize(56, 81);
		player1card3.setSize(56, 81);
		player1card4.setSize(56, 81);
		player1card5.setSize(56, 81);
		player1card1.setPosition(95, 385);
		player1card2.setPosition(120, 385);
		player1card3.setPosition(145, 385);
		player1card4.setPosition(170, 385);
		player1card5.setPosition(195, 385);
		
		//player 2 card layout
		
		Table player2card1 = new Table();
		Table player2card2 = new Table();
		Table player2card3 = new Table();
		Table player2card4 = new Table();
		Table player2card5 = new Table();
		stage.addActor(player2card1);
		stage.addActor(player2card2);
		stage.addActor(player2card3);
		stage.addActor(player2card4);
		stage.addActor(player2card5);
		player2card1.setSize(56, 81);
		player2card2.setSize(56, 81);
		player2card3.setSize(56, 81);
		player2card4.setSize(56, 81);
		player2card5.setSize(56, 81);
		player2card1.setPosition(325, 320);
		player2card2.setPosition(350, 320);
		player2card3.setPosition(375, 320);
		player2card4.setPosition(400, 320);
		player2card5.setPosition(425, 320);
		
		//player 3 card layout
		
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
		player3card2.setSize(56, 81);
		player3card3.setSize(56, 81);
		player3card4.setSize(56, 81);
		player3card5.setSize(56, 81);
		player3card1.setPosition(565, 295);
		player3card2.setPosition(590, 295);
		player3card3.setPosition(615, 295);
		player3card4.setPosition(640, 295);
		player3card5.setPosition(665, 295);
		
		//player 4 card layout
		
		Table player4card1 = new Table();
		Table player4card2 = new Table();
		Table player4card3 = new Table();
		Table player4card4 = new Table();
		Table player4card5 = new Table();
		stage.addActor(player4card1);
		stage.addActor(player4card2);
		stage.addActor(player4card3);
		stage.addActor(player4card4);
		stage.addActor(player4card5);
		player4card1.setSize(56, 81);
		player4card2.setSize(56, 81);
		player4card3.setSize(56, 81);
		player4card4.setSize(56, 81);
		player4card5.setSize(56, 81);
		player4card1.setPosition(815, 330);
		player4card2.setPosition(840, 330);
		player4card3.setPosition(865, 330);
		player4card4.setPosition(890, 330);
		player4card5.setPosition(915, 330);
		
		//player 5 card layout
		
		Table player5card1 = new Table();
		Table player5card2 = new Table();
		Table player5card3 = new Table();
		Table player5card4 = new Table();
		Table player5card5 = new Table();
		stage.addActor(player5card1);
		stage.addActor(player5card2);
		stage.addActor(player5card3);
		stage.addActor(player5card4);
		stage.addActor(player5card5);
		player5card1.setSize(56, 81);
		player5card2.setSize(56, 81);
		player5card3.setSize(56, 81);
		player5card4.setSize(56, 81);
		player5card5.setSize(56, 81);
		player5card1.setPosition(1055, 410);
		player5card2.setPosition(1080, 410);
		player5card3.setPosition(1105, 410);
		player5card4.setPosition(1130, 410);
		player5card5.setPosition(1155, 410);
		
		//Apply cards to player
		TextureRegion TexRegCard = new TextureRegion(DQ);
		
		Image Imgdealercard1 = new Image(TexRegCard);
		Image Imgdealercard2 = new Image(TexRegCard);
		Image Imgdealercard3 = new Image(TexRegCard);
		Image Imgdealercard4 = new Image(TexRegCard);
		Image Imgdealercard5 = new Image(TexRegCard);
		
		Tabdealercard1.add(Imgdealercard1);
		Tabdealercard2.add(Imgdealercard2);
		Tabdealercard3.add(Imgdealercard3);
		Tabdealercard4.add(Imgdealercard4);
		Tabdealercard5.add(Imgdealercard5);
		
		//-----------------------------------------------
		//----------------BUTTONS------------------------
		
		Drawable buttontexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/null.png"))));
		Drawable buttontexturegrayed = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/nullgray.png"))));
		TextButtonStyle buttonstyle = new TextButtonStyle(buttontexture, buttontexture, buttontexture);
		buttonstyle.font = new BitmapFont();
		buttonstyle.fontColor = Color.BLACK;
		TextButtonStyle buttonstylegrayed = new TextButtonStyle(buttontexturegrayed, buttontexturegrayed, buttontexturegrayed);
		buttonstylegrayed.fontColor = Color.GRAY;
		buttonstylegrayed.font = new BitmapFont();
		
        TextButton hit = new TextButton("Hit", buttonstylegrayed);
        hit.setSize(200, 20);
        hit.setPosition(20, 10);
        stage.addActor(hit);
        hit.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				return false;
			}
		});
        
        TextButton stay = new TextButton("Stay", buttonstyle);
        stay.setSize(200, 20);
        stay.setPosition(240, 10);
        stage.addActor(stay);
        stay.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				return false;
			}
		});
        
        TextButton split = new TextButton("Split", buttonstyle);
        split.setSize(200, 20);
        split.setPosition(460, 10);
        stage.addActor(split);
        split.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				return false;
			}
		});
        
        TextButton doubledown = new TextButton("Double Down", buttonstyle);
        doubledown.setSize(200, 20);
        doubledown.setPosition(680, 10);
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
