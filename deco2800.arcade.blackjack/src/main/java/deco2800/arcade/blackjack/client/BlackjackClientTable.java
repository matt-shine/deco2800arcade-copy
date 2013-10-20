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
		
		Texture CardTwoSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardThreeSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardFourSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardFiveSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardSixSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardSevenSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardEightSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardNineSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardTenSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardJackSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardQueenSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardKingSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardAceSpade = new Texture(Gdx.files.internal("data/QS.jpg"));
		
		Texture CardTwoHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardThreeHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardFourHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardFiveHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardSixHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardSevenHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardEightHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardNineHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardTenHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardJackHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardQueenHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardKingHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardAceHeart = new Texture(Gdx.files.internal("data/QS.jpg"));
		
		Texture CardTwoClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardThreeClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardFourClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardFiveClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardSixClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardSevenClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardEightClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardNineClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardTenClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardJackClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardQueenClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardKingClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardAceClub = new Texture(Gdx.files.internal("data/QS.jpg"));
		
		Texture CardTwoDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardThreeDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardFourDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardFiveDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardSixDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardSevenDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardEightDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardNineDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardTenDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardJackDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardQueenDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardKingDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		Texture CardAceDiamond = new Texture(Gdx.files.internal("data/QS.jpg"));
		
		//dealer card layout
		
		Table dealercard1 = new Table();
		Table dealercard2 = new Table();
		Table dealercard3 = new Table();
		Table dealercard4 = new Table();
		Table dealercard5 = new Table();
		stage.addActor(dealercard1);
		stage.addActor(dealercard2);
		stage.addActor(dealercard3);
		stage.addActor(dealercard4);
		stage.addActor(dealercard5);
		dealercard1.setSize(56, 81);
		dealercard2.setSize(56, 81);
		dealercard3.setSize(56, 81);
		dealercard4.setSize(56, 81);
		dealercard5.setSize(56, 81);
		dealercard1.setPosition(560, 515);
		dealercard2.setPosition(585, 515);
		dealercard3.setPosition(610, 515);
		dealercard4.setPosition(635, 515);
		dealercard5.setPosition(660, 515);
		
		TextureRegion dealercardsTexReg = new TextureRegion(CardQueenClub);
		Image dealercardfinal = new Image(dealercardsTexReg);
		Image dealercardfinal2 = new Image(dealercardsTexReg);
		Image dealercardfinal3 = new Image(dealercardsTexReg);
		Image dealercardfinal4 = new Image(dealercardsTexReg);
		Image dealercardfinal5 = new Image(dealercardsTexReg);
		dealercard1.add(dealercardfinal);
		dealercard2.add(dealercardfinal2);
		dealercard3.add(dealercardfinal3);
		dealercard4.add(dealercardfinal4);
		dealercard5.add(dealercardfinal5);
		
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
		
		TextureRegion player1cardsTexReg = new TextureRegion(CardQueenClub);
		Image player1cardfinal = new Image(player1cardsTexReg);
		Image player1cardfinal2 = new Image(player1cardsTexReg);
		Image player1cardfinal3 = new Image(player1cardsTexReg);
		Image player1cardfinal4 = new Image(player1cardsTexReg);
		Image player1cardfinal5 = new Image(player1cardsTexReg);
		player1card1.add(player1cardfinal);
		player1card2.add(player1cardfinal2);
		player1card3.add(player1cardfinal3);
		player1card4.add(player1cardfinal4);
		player1card5.add(player1cardfinal5);
		
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
		
		TextureRegion player2cardsTexReg = new TextureRegion(CardQueenClub);
		Image player2cardfinal = new Image(player2cardsTexReg);
		Image player2cardfinal2 = new Image(player2cardsTexReg);
		Image player2cardfinal3 = new Image(player2cardsTexReg);
		Image player2cardfinal4 = new Image(player2cardsTexReg);
		Image player2cardfinal5 = new Image(player2cardsTexReg);
		player2card1.add(player2cardfinal);
		player2card2.add(player2cardfinal2);
		player2card3.add(player2cardfinal3);
		player2card4.add(player2cardfinal4);
		player2card5.add(player2cardfinal5);
		
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
		
		TextureRegion player3cardsTexReg = new TextureRegion(CardQueenClub);
		Image player3cardfinal = new Image(player3cardsTexReg);
		Image player3cardfinal2 = new Image(player3cardsTexReg);
		Image player3cardfinal3 = new Image(player3cardsTexReg);
		Image player3cardfinal4 = new Image(player3cardsTexReg);
		Image player3cardfinal5 = new Image(player3cardsTexReg);
		player3card1.add(player3cardfinal);
		player3card2.add(player3cardfinal2);
		player3card3.add(player3cardfinal3);
		player3card4.add(player3cardfinal4);
		player3card5.add(player3cardfinal5);
		
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
		
		TextureRegion player4cardsTexReg = new TextureRegion(CardQueenClub);
		Image player4cardfinal = new Image(player4cardsTexReg);
		Image player4cardfinal2 = new Image(player4cardsTexReg);
		Image player4cardfinal3 = new Image(player4cardsTexReg);
		Image player4cardfinal4 = new Image(player4cardsTexReg);
		Image player4cardfinal5 = new Image(player4cardsTexReg);
		player4card1.add(player4cardfinal);
		player4card2.add(player4cardfinal2);
		player4card3.add(player4cardfinal3);
		player4card4.add(player4cardfinal4);
		player4card5.add(player4cardfinal5);
		
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
		
		TextureRegion player5cardsTexReg = new TextureRegion(CardQueenClub);
		Image player5cardfinal = new Image(player5cardsTexReg);
		Image player5cardfinal2 = new Image(player5cardsTexReg);
		Image player5cardfinal3 = new Image(player5cardsTexReg);
		Image player5cardfinal4 = new Image(player5cardsTexReg);
		Image player5cardfinal5 = new Image(player5cardsTexReg);
		player5card1.add(player5cardfinal);
		player5card2.add(player5cardfinal2);
		player5card3.add(player5cardfinal3);
		player5card4.add(player5cardfinal4);
		player5card5.add(player5cardfinal5);
		
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
