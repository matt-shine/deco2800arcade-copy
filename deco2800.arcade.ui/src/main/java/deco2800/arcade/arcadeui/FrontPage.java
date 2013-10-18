package deco2800.arcade.arcadeui;

import java.util.Random;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

import deco2800.arcade.arcadeui.store.StoreHome;
import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.GameClient;
import deco2800.arcade.model.Player;

//adee added
import deco2800.arcade.arcadeui.Overlay.*;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.arcadeui.store.StoreScreen;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.gamelibrary.PlayButtonActionHandler;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Game.InternalGame;

@ArcadeGame(id = "frontpage")
public class FrontPage implements Screen {

	private class FrontPageStage extends Stage {
	}

	public static Skin skin;
	private static FrontPageStage stage;
	
	private float funds;
	private int tokens;
    
    //need to get values for

    private static int creditVal = 0;
    private static String[] onlineFriends = {"adeleen", "alina", "moji", "will"};
    private static int nFriends = onlineFriends.length;
    private static String uName = "Adeleen Pavia";
    private static Set<String> games = ArcadeSystem.getGamesList();
   /* private String[] games = {"Pong", "Tower Defence", "Connect 4", "Tic Tac Toe", "Chess", "Jungle Jump",
    		"Sound Board", "Pacman", "Raiden", "Wolfenstein 3D", "Burning Skies", "Snake and Ladders", "Deer Forest",
    		"Mix Maze", "Land Invaders", "Checkers","Breakout"};*/
   
    
    public static String pName = "<USERNAME>";
    
    private boolean bclicked;
    private ArcadeUI arcadeUI;
   // private TextButton recentGame;
    
    Texture bg;
    Texture mB;
    Texture rP;
    Sprite bgSprite;
    Sprite mbSprite;
    Sprite rpSprite;
    
    SpriteBatch batch;
    
    public FrontPage(ArcadeUI ui) {
    	//FIXME big method
        arcadeUI = ui;
        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        skin.add("background", new Texture("homescreen_bg.png"));
        skin.add("menuBar", new Texture("menuBar.png"));
        skin.add("recentBar", new Texture("recent.png"));
        skin.add("chatOverlay", new Texture("overlayPopUp.png"));
        stage = new FrontPageStage();
        
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("background"));
        stage.addActor(table);
        
        bg = new Texture("homescreen_bg.png");
        bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bgSprite = new Sprite(bg);
        batch = new SpriteBatch();
        
        mB = new Texture("menuBar.png");
        mB.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mbSprite = new Sprite(mB);
        
        rP = new Texture("recent.png");
        rP.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        rpSprite = new Sprite(rP);
        
       
        
        
        //Text Buttons
        final TextButton storeButton = new TextButton("Store", skin, "green");
        
        //skin.add("Store", new Texture("homescreen_bg.png"));
        final TextButton chatButton = new TextButton("Chat", skin);
        final TextButton libraryButton = new TextButton("Library", skin, "magenta");
        final TextButton recentButton = new TextButton("Recently Played", skin, "blue");
       
       
        
        final Table recentTable = new Table();
        //recentTable.setFillParent(true);
        recentTable.setBackground(skin.getDrawable("recentBar"));
        recentTable.setSize(350, 800);
    	recentTable.setPosition(125, 0);
    	recentTable.setName("recenticon");
        final Label logo = new Label("VAPOR", skin, "cgothic");
        logo.setAlignment(Align.left);
        
        //Bottom Box Labels
        final Label divider2 = new Label("|", skin, "cgothic");
        divider2.setAlignment(Align.right);
        final Label chatLink = new Label("Online (" + nFriends +")" , skin, "cgothic");
        chatLink.setAlignment(Align.right);
        
        final int bWidth = 300;
        final int bHeight = 300;
        final int bX = 150;
        final int bY = 220;
        final int enlarge = 50;
        final int bX2= bX + bWidth + (enlarge);
        final int bX3= bX + 2*(bWidth + enlarge);
 
        
        //make button sizes and positioning
        
        
        recentButton.setSize(bWidth, bHeight);        
        recentButton.setPosition(bX, bY);
        
      
        libraryButton.setSize(bWidth, bHeight);
        libraryButton.setPosition(bX2, bY);
        
        storeButton.setSize(bWidth, bHeight);
        storeButton.setPosition(bX3, bY);
        
        final Table bottomBox = new Table();
        
        
        //set bottom bar properties
        bottomBox.setSize(1279, 30);
        bottomBox.setPosition(0, 0);
        bottomBox.setColor(255, 255, 255, 1);
        bottomBox.setBackground(skin.getDrawable("menuBar"));
        
        //add bottom labels
        
        bottomBox.add(divider2).width(1100).pad(20);
        bottomBox.add(chatLink).width(100);
        
        //adding to stage
        stage.addActor(bottomBox);
 
        for (Game gamename : ArcadeSystem.getArcadeGames()) {
			try {
				skin.add(gamename.id, new Texture(Gdx.files.internal("logos/"
						+ gamename.id.toLowerCase() + ".png")));
			} catch (Exception e) {
				skin.add(gamename.id, new Texture
						(Gdx.files.internal("logos/default.png")));
			}
		}

        // Icon event listeners, mouseOver and mouseClick
        recentButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
           		//recentButton.setSize(bWidth + enlarge,  bHeight + enlarge);
            	//recentButton.setPosition(bX - (enlarge/2), bY-(enlarge/2));
            	
            	//get original width and height and X and Y positions 
           		float cSizeX = recentButton.getWidth();
           		float cSizeY = recentButton.getHeight();
           		float cPosX = recentButton.getX();
           		float cPosY = recentButton.getY();
           		
           		recentButton.setSize(cSizeX + enlarge, cSizeY + enlarge);
            	recentButton.setPosition(cPosX - (enlarge/2), cPosY - (enlarge/2));
           		recentButton.setText(null);
               	recentButton.setText("Recently Played");
            		
            		 			     			
           
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	//get original position and shrink
            	float cSizeX = recentButton.getWidth();
           		float cSizeY = recentButton.getHeight();
           		float cPosX = recentButton.getX();
           		float cPosY = recentButton.getY();
           		
           		recentButton.setSize(cSizeX - enlarge, cSizeY - enlarge);
           		recentButton.setPosition(cPosX + (enlarge/2), cPosY + (enlarge/2));
           		recentButton.setText(null);
            	recentButton.setText("Recently Played");
            }
        }));   
        
	    
		recentButton.addListener((new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	
				Set<Game> gamesList = ArcadeSystem.getArcadeGames();
				int listSize = gamesList.size();
				int item = new Random().nextInt(listSize);
				for (int i = 0; i < 3; i++) {
					Game myGame = (Game)gamesList.toArray()[item + i];
					
				 	//Game myGame = (Game)ArcadeSystem.getGamesList().toArray()[item + i];
				 	
				 	final String gameName = myGame.getName();
				 	final TextButton recentGame = new TextButton("\n\n\n\n\n\n\n" +
				 		myGame.name, skin, "blue");
				 	recentGame.setName("recenticon");
				 	recentGame.setSize(250, 250);
				 	recentGame.setPosition(175, 220 + (250 * ((i%3)-1)));
				 	//recentGame.setName("recenticon");
				 	
				 	final Button recentIcon = new Button(skin.getDrawable(myGame.id));
				 	recentIcon.setName("recenticon");
				 	recentIcon.setSize(150, 150);
					recentIcon.setPosition(225, 280 + (250 * ((i%3)-1)));
					
					
					 recentGame.addListener((new ChangeListener() {
						public void changed (ChangeEvent event, Actor actor) {
							ArcadeSystem.goToGame(gameName);
						}
					})); 
					
					 recentIcon.addListener((new ChangeListener() {
						public void changed (ChangeEvent event, Actor actor) {
							ArcadeSystem.goToGame(gameName);
						}
					})); 
					
					recentButton.remove();
					stage.addActor(recentTable);
					stage.addActor(recentGame);
					stage.addActor(recentIcon);
					recentTable.setZIndex(1);
					recentGame.setZIndex(2);
					recentIcon.setZIndex(3);
				 }
		    }
		})); 
       
        libraryButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	    	
           		 libraryButton.setSize(bWidth + enlarge,  bHeight + enlarge);
           		 libraryButton.setPosition(bX2 -(enlarge/2), bY-(enlarge/2));	
           		 libraryButton.setText(null);
           		 libraryButton.setText("Library");
            	 
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	libraryButton.setSize(bWidth, bHeight);
            	libraryButton.setPosition(bX2, bY);
            	libraryButton.setText(null);
            	libraryButton.setText("Library");
            }}));   
        
	    libraryButton.addListener((new ChangeListener() {
	        public void changed (ChangeEvent event, Actor actor) {
	        	ArcadeSystem.goToGame("gamelibrary");
	        }
	    })); 
	    
	    /* Lobby button */
	    final TextButton lobbyButton = new TextButton("Multiplayer Lobby", skin, "magenta");
	    lobbyButton.setSize(210, 50);
	    lobbyButton.setPosition(800, 580);

	    stage.addActor(lobbyButton);
	    lobbyButton.addListener((new ChangeListener() {
	    public void changed(ChangeEvent event, Actor actor) {
	    	arcadeUI.setScreen(arcadeUI.lobby);
	    }
	    }));
	    /* End Lobby button */
	    
        
        storeButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
            	  
       			storeButton.setSize(bWidth + enlarge,  bHeight + enlarge);
         		storeButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
         		storeButton.setText(null);
         		storeButton.setText("Store");
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	storeButton.setSize(bWidth, bHeight);
                storeButton.setPosition(bX3, bY);
                storeButton.setText(null);
     			storeButton.setText("Store");
            }}));   
        
        storeButton.addListener((new ChangeListener() {	
            public void changed (ChangeEvent event, Actor actor) {
            	arcadeUI.setScreen(arcadeUI.store);
            }
        })); 
        
        //adding to stage
        stage.addActor(recentButton);
        stage.addActor(libraryButton);
        stage.addActor(storeButton);
        
        /**
         * This function listens for a click outside the text boxes.
         * It checks if 'Recently Played' button is on the stage,
         * if not, its add it to stage
         * 
         */
        stage.addListener((new ClickListener() {
    	    public void clicked(InputEvent event, float x, float y) {
    	    	
    	    	//checking for clicks at specific coordinates in the screen
    	    	if ((x > 0 && x < 125) || (x > 475 && x < 1280)){

 
    	    		//Checking for 'Recently Played' Button is not on stage
	    	    	if (!stage.getActors().contains(recentButton, true)){
	    	    		recentTable.remove();
	    	    		stage.addActor(recentButton);
	    	    		
	    	    		for (int i = 0; i < stage.getActors().size ; i++){
	    	    			if (stage.getActors().toArray()[i].getName() == "recenticon"){
	    	    				stage.getActors().toArray()[i].remove();
	    	    				i--;
	    	    			}
	    	    		}
	    	    		
	    	    	}
        		}
    	    }
    	})); 
       
    }
   
    //FIXME Better implementation
    
    public static void setName(String playerName){
    	final Table topBox = new Table();
    	
    	topBox.setSize(1279, 30);
        topBox.setPosition(1, 690);
        topBox.setColor(255, 255, 255, 1);
        topBox.setBackground(skin.getDrawable("menuBar"));
        
        final float boxHeight = 695;
    	
        stage.addActor(topBox);
        
        final Label credits = new Label( creditVal + " Credits", skin, "cgothic");
    	credits.setPosition(stage.getWidth() - credits.getWidth() - 10, boxHeight);
    	stage.addActor(credits);
    	
    	final Label divider = new Label("|", skin, "cgothic");
    	divider.setPosition(credits.getX() - divider.getWidth() - 10, boxHeight);
    	stage.addActor(divider);
        
    	final Label username = new Label(playerName, skin, "cgothic");
    	username.setPosition(divider.getX() - username.getWidth() - 10, boxHeight);
    	stage.addActor(username);
    	
    	final Label vapor = new Label("VAPOR", skin, "cgothic");
    	vapor.setPosition(10, boxHeight);
    	stage.addActor(vapor);
    	
    	final Table bottomBox = new Table();
        
        
        //set bottom bar properties
        bottomBox.setSize(1279, 30);
        bottomBox.setPosition(0, 0);
        bottomBox.setColor(255, 255, 255, 1);
        bottomBox.setBackground(skin.getDrawable("menuBar"));

        //adding to stage
        stage.addActor(bottomBox);
        
        final Label chatLink = new Label("Online (" + nFriends +")" , skin, "cgothic");
        chatLink.setPosition(stage.getWidth() - chatLink.getWidth() - 10, 5);
        stage.addActor(chatLink);
        
        final Label divider2 = new Label("|", skin, "cgothic");
        divider2.setPosition(chatLink.getX() - divider2.getWidth(), 5);
        stage.addActor(divider2);
        
     	
    }
    
    public static String getName(){
    	return pName;
    }
    
    /*for getting index out of the String Set*/
    public static String getRandomGame() {
    	int size = games.size();
		int item = new Random().nextInt(size);
		int i = 0;
		String thisgame = "";
		for(String game : games){
		    if (i == item)
		    	thisgame = game;
		    	//break;
		    	i++;
		}
		return thisgame;
    } 
    /*public void displayChat(){
    	Table chatTable = new Table();
        //table.setFillParent(true);
        chatTable.setBackground(skin.getDrawable("menuBar"));
        chatTable.setPosition(1060 , 50);
        chatTable.setSize(200, nFriends * 30);
        stage.addActor(chatTable);
        System.out.println("i got here");
    	for (int i=0; i < nFriends; i++){
    		Label user = new Label(onlineFriends[i], skin, "cgothic");
    		chatTable.add(user).width(80);
    	}
    }*/

	@Override
	public void show() {
		ArcadeInputMux.getInstance().addProcessor(stage);
	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.begin();
		bgSprite.draw(batch);
		batch.end();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		Table.drawDebug(stage); // Shows table debug lines

	}

	@Override
	public void dispose() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
		stage.dispose();
		skin.dispose();
	}

	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void resize(int arg0, int arg1) {
	}
}