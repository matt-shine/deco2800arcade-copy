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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.Game;
import deco2800.arcade.model.Player;

public class BlackjackClientTable implements Screen{
	
	//local variables for the Client Table
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture textureBackground;
	private Sprite background;
	public Blackjack myGame;
	private Stage stage;
	JoinTableScreen JoinTableScreen;

	//seats labels
	private Table tableSeat1;
	private Table tableSeat2;
	private Table tableSeat3;
	private Table tableSeat4;
	private Table tableSeat5;
	private LabelStyle Seatlabel;
	
	//placards
	private Table tablePlacard;
	
	//cards
	private Table Tabdealercard1;
	private Table Tabdealercard2;
	private Table Tabdealercard3;
	private Table Tabdealercard4;
	private Table Tabdealercard5;
	private Table player1card1;
	private Table player1card2;
	private Table player1card3;
	private Table player1card4;
	private Table player1card5;
	private Table player2card1;
	private Table player2card2;
	private Table player2card3;
	private Table player2card4;
	private Table player2card5;
	private Table player3card1;
	private Table player3card2;
	private Table player3card3;
	private Table player3card4;
	private Table player3card5;
	private Table player4card1;
	private Table player4card2;
	private Table player4card3;
	private Table player4card4;
	private Table player4card5;
	private Table player5card1;
	private Table player5card2;
	private Table player5card3;
	private Table player5card4;
	private Table player5card5;
	private Texture S2;
	private Texture S3;
	private Texture S4;
	private Texture S5;
	private Texture S6;
	private Texture S7;
	private Texture S8;
	private Texture S9;
	private Texture S10;
	private Texture SJ;
	private Texture SQ;
	private Texture SK;
	private Texture SA;
	private Texture H2;
	private Texture H3;
	private Texture H4;
	private Texture H5;
	private Texture H6;
	private Texture H7;
	private Texture H8;
	private Texture H9;
	private Texture H10;
	private Texture HJ;
	private Texture HQ;
	private Texture HK;
	private Texture HA;
	private Texture D2;
	private Texture D3;
	private Texture D4;
	private Texture D5;
	private Texture D6;
	private Texture D7;
	private Texture D8;
	private Texture D9;
	private Texture D10;
	private Texture DJ;
	private Texture DQ;
	private Texture DK;
	private Texture DA;
	private Texture C2;
	private Texture C3;
	private Texture C4;
	private Texture C5;
	private Texture C6;
	private Texture C7;
	private Texture C8;
	private Texture C9;
	private Texture C10;
	private Texture CJ;
	private Texture CQ;
	private Texture CK;
	private Texture CA;

	// Constructor
	public BlackjackClientTable(Blackjack myGame )
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
        textureBackground = new Texture(Gdx.files.internal("data/Blackjacktable.png"));
        textureBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
 
        background = new Sprite(textureBackground);
		background.setSize(w, h);
		
		//Initialise stage for sprites
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		//placards
        Table tablePlacard = new Table();
        stage.addActor(tablePlacard);
        tablePlacard.setSize(162, 113);
        tablePlacard.setPosition(25, 580);
		
		Texture texturePlacard10250 = new Texture(Gdx.files.internal("data/BetPlac10250.png"));
		Texture texturePlacard1100 = new Texture(Gdx.files.internal("data/BetPlac1100.png"));
		Texture texturePlacard150 = new Texture(Gdx.files.internal("data/BetPlac150.png"));
		Texture texturePlacard25NL = new Texture(Gdx.files.internal("data/BetPlac25NL.png"));
		Texture texturePlacard5100 = new Texture(Gdx.files.internal("data/BetPlac5100.png"));
		
		/* Test code for placing a placard
		TextureRegion imagePlacard = new TextureRegion(texturePlacard10250);
		Image placard = new Image(imagePlacard);
		tablePlacard.add(placard);
		*/
		
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
		
		/*Test code for player card positioning
		TextureRegion TexRegCard = new TextureRegion(SQ);
		
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
		*/
		
		//Seats username labels
		LabelStyle Seatlabel = new LabelStyle(new BitmapFont(), Color.WHITE);
				
		Table tableSeat1 = new Table();
		stage.addActor(tableSeat1);
		tableSeat1.setSize(56, 81);
		tableSeat1.setPosition(100,100);
		Table tableSeat2 = new Table();
		stage.addActor(tableSeat2);
		tableSeat2.setSize(100, 100);
		tableSeat2.setPosition(400,200);
		Table tableSeat3 = new Table();
		stage.addActor(tableSeat3);
		tableSeat3.setSize(100, 100);
		tableSeat3.setPosition(600,200);
		Table tableSeat4 = new Table();
		stage.addActor(tableSeat4);
		tableSeat4.setSize(100, 100);
		tableSeat4.setPosition(800,200);
		Table tableSeat5 = new Table();
		stage.addActor(tableSeat5);
		tableSeat5.setSize(100, 100);
		tableSeat5.setPosition(1000,200);
		
		/*Test code for username labels

		Label seat2label = new Label("User2", Seatlabel);
		Label seat2label2 = new Label("$2000", Seatlabel);
		tableSeat2.add(seat2label);
		tableSeat2.row();
		tableSeat2.add(seat2label2);
		Label seat3label = new Label("User3", Seatlabel);
		Label seat3label2 = new Label("$2000", Seatlabel);
		tableSeat3.add(seat3label);
		tableSeat3.row();
		tableSeat3.add(seat3label2);
		Label seat4label = new Label("User4", Seatlabel);
		Label seat4label2 = new Label("$2000", Seatlabel);
		tableSeat4.add(seat4label);
		tableSeat4.row();
		tableSeat4.add(seat4label2);
		Label seat5label = new Label("User5", Seatlabel);
		Label seat5label2 = new Label("$2000", Seatlabel);
		tableSeat5.add(seat5label);
		tableSeat5.row();
		tableSeat5.add(seat5label2);
		*/
		
		// Buttons for controls
		//button style for clickable button
		Drawable buttontexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/null.png"))));
		TextButtonStyle buttonstyle = new TextButtonStyle(buttontexture, buttontexture, buttontexture);
		buttonstyle.fontColor = Color.BLACK;
		buttonstyle.font = new BitmapFont();
		
		//button style for unclickable button
		Drawable buttontexturegrayed = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("data/nullgray.png"))));
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
        
        TextButton stay = new TextButton("Stay", buttonstylegrayed);
        stay.setSize(200, 20);
        stay.setPosition(240, 10);
        stage.addActor(stay);
        stay.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return false;
			}
		});
        
        TextButton split = new TextButton("Split", buttonstylegrayed);
        split.setSize(200, 20);
        split.setPosition(460, 10);
        stage.addActor(split);
        split.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return false;
			}
		});
        
        TextButton doubledown = new TextButton("Double Down", buttonstylegrayed);
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
	
	//Message handler script when it receives messages from Server
	public void parseMessage(String msg){
	String[] stringArray = msg.split("|");
	String function = stringArray[0];
	    if (function == "takeseat"){
	        	//code for adding a user to a seat, takes format:
	        	//takeseat|player|seatno
	    }
	    if (function == "startbettingphase"){
	        	//code for notifying the betting phase has started, takes format:
	        	//startbettingphase
	    }
	    if (function == "endbettingphase"){
	        	//code for notifying the betting phase has ended, takes format:
	        	//endbettingphase
	    }
	    if (function == "turn"){
	        	//code for notifying that a players turn has begun, takes format:
	        	//turn|player
	    }
	    if (function == "cardpull"){
	        	//code for notifying that card in a hand has been updated, takes format:
	        	//cardpull|player|handposition|card
	    }
	    if (function == "addchips"){
	        	//code for notifying that a chip pile has been updated, takes format:
	        	//addchips|player|chippile total
	    }
	}
	// Code for adding a player to a seat
	private void fillSeat(Player player, int SeatNo, int Chipcount){
		String username = player.getUsername();
		if (SeatNo == 1){
			Label namelabel = new Label(username, Seatlabel);
			Label moneylabel = new Label(Integer.toString(Chipcount), Seatlabel);
			tableSeat1.add(namelabel);
			tableSeat1.row();
			tableSeat1.add(moneylabel);
		}
		if (SeatNo == 2){
			Label namelabel = new Label(username, Seatlabel);
			Label moneylabel = new Label(Integer.toString(Chipcount), Seatlabel);
			tableSeat2.add(namelabel);
			tableSeat2.row();
			tableSeat2.add(moneylabel);
		}
		if (SeatNo == 3){
			Label namelabel = new Label(username, Seatlabel);
			Label moneylabel = new Label(Integer.toString(Chipcount), Seatlabel);
			tableSeat3.add(namelabel);
			tableSeat3.row();
			tableSeat3.add(moneylabel);
		}
		if (SeatNo == 4){
			Label namelabel = new Label(username, Seatlabel);
			Label moneylabel = new Label(Integer.toString(Chipcount), Seatlabel);
			tableSeat4.add(namelabel);
			tableSeat4.row();
			tableSeat4.add(moneylabel);
		}
		if (SeatNo == 5){
			Label namelabel = new Label(username, Seatlabel);
			Label moneylabel = new Label(Integer.toString(Chipcount), Seatlabel);
			tableSeat5.add(namelabel);
			tableSeat5.row();
			tableSeat5.add(moneylabel);
		}
	}
	//Code for updating a specific card in a specific hand
	private void updateCard(int playerNo, int cardPos, String card){
		//link card to texture
		//set texture region to card
		//update card position in player hand with texture region
	}
}
