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
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.InternalGame;

@ArcadeGame(id="frontpage")
public class FrontPage implements Screen {
	
    private class FrontPageStage extends Stage {}
	
        public static Skin skin;
        public static String pName = "<USERNAME>";
        private static FrontPageStage stage;
        private static int creditVal = 0;//temp placeholder for credits
        //temp array of online friends
        private String[] onlineFriends = {"adeleen", "alina", "moji", "will"};
        private int nFriends = onlineFriends.length;
        private static Set<Game> games = ArcadeSystem.getArcadeGames();
        private static ArcadeUI arcadeUI;
        
        Texture bg;
        Texture mB;
        Texture rP;
        Sprite bgSprite;
        Sprite mbSprite;
        Sprite rpSprite;
        SpriteBatch batch;
        
        final int bSize = 300;
        final int bX = 150;
        final int bY = 220;
        final int enlarge = 50;
        final int bX2= bX + bSize + (enlarge);
        final int bX3= bX + 2*(bSize + enlarge);
    
        public FrontPage(ArcadeUI ui) {
            //FIXME big method
            
            /*
             * Creating Skin and Textures
             */
            arcadeUI = ui;
            skin = new Skin(Gdx.files.internal("loginSkin.json"));
            skin.add("background", new Texture("homescreen_bg.png"));
            skin.add("menuBar", new Texture("menuBar.png"));
            skin.add("recentBar", new Texture("recent.png"));
            skin.add("chatOverlay", new Texture("overlayPopUp.png"));
            skin.add("settingsIcon", new Texture("settings.png"));
            skin.add("toolTip", new Texture("tooltip.png"));
            skin.add("toolTipHover", new Texture("tooltip_hover.png"));
            stage = new FrontPageStage();
            
            
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
            
            //background
            Table table = new Table();
            table.setFillParent(true);
            table.setBackground(skin.getDrawable("background"));
            stage.addActor(table);
            
            Table trial = new Table();
          //  trial.setFillParent(true);
            trial.setBackground(skin.getDrawable("toolTip"));
            trial.setPosition(950,580);
            trial.setSize(200, 100);
            stage.addActor(trial);
            
            //Text Buttons
            final TextButton storeButton = new TextButton("Store", skin, "green");
            storeButton.setSize(300, 300);
            storeButton.setPosition(bX3, bY);
            
            final TextButton libraryButton = new TextButton("Library", skin, "magenta");
            libraryButton.setSize(300, 300);
            libraryButton.setPosition(bX2, bY);
            
            final TextButton recentButton = new TextButton("Recently Played", skin, "blue");
            recentButton.setSize(300, 300);        
            recentButton.setPosition(bX, bY);
            
            final Table recentTable = new Table();
            recentTable.setBackground(skin.getDrawable("recentBar"));
            recentTable.setSize(350, 800);
            recentTable.setPosition(125, 0);
            recentTable.setName("recenticon");
 
            //Bottom Box Labels
            final Label divider2 = new Label("|", skin, "cgothic");
            divider2.setAlignment(Align.right);
            final Label chatLink = new Label("Online (" + nFriends +")" , skin, "cgothic");
            chatLink.setAlignment(Align.right);
            
            //Bottom bar
            final Table bottomBox = new Table();
            bottomBox.setSize(1279, 30);
            bottomBox.setPosition(0, 0);
            bottomBox.setColor(255, 255, 255, 1);
            bottomBox.setBackground(skin.getDrawable("menuBar"));
            
            //add bottom labels to bar
            bottomBox.add(divider2).width(1100).pad(20);
            bottomBox.add(chatLink).width(100);
            
            //adding to buttons and menu bar on stage
            stage.addActor(bottomBox);
            stage.addActor(recentButton);
            stage.addActor(libraryButton);
            stage.addActor(storeButton);
            
            //Creating Game Icons
            for (Game game : games) {
            	try {
            	    skin.add(game.name, new Texture(Gdx.files.internal("logos/"
            		+ game.id.toLowerCase() + ".png")));
            	} catch (Exception e) {
            	    skin.add(game.name, new Texture
            		(Gdx.files.internal("logos/default.png")));
            	}
            }
    
            // Icon event listeners, mouseOver and mouseClick
            recentButton.addListener((new ClickListener() {        	
                public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
               		
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
            
            //Three Games are displayed when Recently played button is clicked.
            recentButton.addListener((new ChangeListener() {
    	    	public void changed (ChangeEvent event, Actor actor) {
    	    	    
    	    	    //Get a random game (for Prototype)
    	    	    int item = new Random().nextInt(games.size());
    	    	    for (int i = 0; i < 3; i++) {
    	    		Game myGame = (Game)games.toArray()[item + i];
                	final String gameName = myGame.getName();
                	final TextButton recentGame = new TextButton(
                		"\n\n\n\n\n\n\n" + myGame.name, skin, "blue");
                        recentGame.setName("recenticon");
                        recentGame.setSize(250, 250);
                        recentGame.setPosition(175, 220 + (250 * ((i%3)-1)));
                        
                        
                        final Button recentIcon = new Button(
                        	skin.getDrawable(myGame.name));
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
           
            //Mouse Over Listener for Library Button
            libraryButton.addListener((new ClickListener() {        	
                public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	    	
                    libraryButton.setSize(bSize + enlarge,  bSize + enlarge);
                    libraryButton.setPosition(bX2 -(enlarge/2), bY-(enlarge/2));	
                    libraryButton.setText(null);
                    libraryButton.setText("Library");
                }
                public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
                    libraryButton.setSize(bSize, bSize);
                    libraryButton.setPosition(bX2, bY);
                    libraryButton.setText(null);
                    libraryButton.setText("Library");
            }}));   
            
           //Click Listener for Library Button
            libraryButton.addListener((new ChangeListener() {
                public void changed (ChangeEvent event, Actor actor) {
                	ArcadeSystem.goToGame("gamelibrary");
                }
            })); 
    	    
            //Hover Listener for Store Button
            storeButton.addListener((new ClickListener() {        	
                public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	  
                    storeButton.setSize(bSize + enlarge,  bSize + enlarge);
                    storeButton.setPosition(bX3 -(enlarge/2), bY-(enlarge/2));			     			
                    storeButton.setText(null);
                    storeButton.setText("Store");
                }
                public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
                    storeButton.setSize(bSize, bSize);
                    storeButton.setPosition(bX3, bY);
                    storeButton.setText(null);
                    storeButton.setText("Store");
                }}));   
            
            //Click Listener for Store Button
            storeButton.addListener((new ChangeListener() {	
                public void changed (ChangeEvent event, Actor actor) {
                	arcadeUI.setScreen(arcadeUI.store);
                }
            })); 
            
    		
            
            
            
            /*
             * This method listens for a click outside the text boxes.
             * It checks if 'Recently Played' button is on the stage,
             * if not, its add it to stage.
             * 
             */
            
            stage.addListener((new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    
                   //checking only for clicks at specific coordinates in the screen
                    if ((x > 0 && x < 125) || (x > 475 && x < 1280)){
                        
                	//Checking for 'Recently Played' Button is not on stage
                        if (!stage.getActors().contains(recentButton, true)){
                            recentTable.remove();
                            stage.addActor(recentButton);
                        		
                		for (int i = 0; i < stage.getActors().size ; i++){
                		    if (stage.getActors().toArray()[i].getName() == 
                			    "recenticon"){
                			stage.getActors().toArray()[i].remove();
                			i--; //doesnt increment i if something is removed.
                		    }
                		}
                        }
                    }
                }
            })); 
        }
    
    /**
     * Takes the player's name from login and creates the menu bar.
     * 
     * @author William, Adeleen
     * @param playerName player name
     */
    public static void setName(String playerName){
		pName = playerName;
		System.out.println("Logged in as:" + " " + pName);
		//String uName = pName;
		
		//Top Box Labels
        final Label logo = new Label("VAPOR", skin, "cgothic");
        logo.setAlignment(Align.left);
        
        final Label username = new Label(pName , skin, "cgothic");
        
        if (username.getText() != pName){
        	System.out.println("Label is null");
        	
        	username.setText(pName);
        }
        
        username.setAlignment(Align.right);
        
        final Label credits = new Label( creditVal + " Credits", skin, "cgothic");
        credits.setAlignment(Align.right);
        final Label divider = new Label("|", skin, "cgothic");
        divider.setAlignment(Align.right);
        
        final Button settingsIcon = new Button(skin.getDrawable("settingsIcon"));
       // recentIcon.setName("recenticon");
        settingsIcon.setSize(15, 15);
     
        final Table topBox = new Table();
        
        //set panel sizes and positions
        topBox.setSize(1279, 30);
        topBox.setPosition(1, 690);
        topBox.setColor(255, 255, 255, 1);
        topBox.setBackground(skin.getDrawable("menuBar"));
        
        //set top bar labels
        topBox.add(logo).width(465);
        topBox.add(username).width(615);
        topBox.add(settingsIcon).width(15).height(15).padLeft(10);
        topBox.add(divider).width(5).pad(15);
        topBox.add(credits).width(100);
        
        stage.addActor(topBox);
		
    }

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
        Table.drawDebug(stage);  // Shows table debug lines
        
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