package deco2800.arcade.arcadeui;

import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Game.ArcadeGame;
@ArcadeGame(id="frontpage")
public class FrontPage implements Screen {
        
        private static Stage stage = new Stage();
        private static Skin skin = new Skin(Gdx.files.internal("loginSkin.json"));
        private ArcadeUI arcadeUI;
        
        private static String[] friends = {"adeleen", "alina", "moji", "will"};
        private static String pName = "<USERNAME>";
        private static int pCredits = 165;

        private final static Label menuInfo = new Label("Loading...", skin, "cgothic");

        public FrontPage(ArcadeUI ui) {
                arcadeUI = ui;
                
                skin.add("background", new Texture("homescreen_bg.png"));
                skin.add("menubar", new Texture("menuBar.png"));
                skin.add("recent", new Texture("recent.png"));
                for (Game gamename : ArcadeSystem.getArcadeGames()) {
                        try {
                                skin.add(gamename.id, new Texture(Gdx.files.internal("logos/"
                                                + gamename.id.toLowerCase() + ".png")));
                        } catch (Exception e) {
                                skin.add(gamename.id, new Texture
                                                (Gdx.files.internal("logos/default.png")));
                        }
                }
                
                final Table bg = new Table();
                final Table menubar = new Table();
                final Table bottomBar = new Table();
                final Label vaporTitle = new Label("VAPOR", skin, "cgothic");
                final TextButton recentButton = new TextButton("Recently Played", skin, "blue");
                final TextButton libraryButton = new TextButton("Library", skin, "magenta");
                final TextButton storeButton = new TextButton("Store", skin, "green");
                final TextButton friendsButton = new TextButton("Friends [" + friends.length + "]", skin, "magenta");
                
                final Table recent_bg = new Table();
                
                // The background for the front page.
                bg.setFillParent(true);
                bg.setBackground(skin.getDrawable("background"));
                stage.addActor(bg);
                
                // The MenuBar at the top of the front page.
                menubar.setBackground(skin.getDrawable("menubar"));
                menubar.setSize(1280, 30);
                menubar.setPosition(0, 690);
                stage.addActor(menubar);
                
                // The word "Vapor" at the top left of the MenuBar.
                vaporTitle.setSize(100, 25);
                vaporTitle.setPosition(15, 695);
                stage.addActor(vaporTitle);
                
                // The text at the right of the top MenuBar.
                menuInfo.setSize(500, 25);
                menuInfo.setPosition(780, 695);
                menuInfo.setAlignment(Align.right);
                stage.addActor(menuInfo);

                // The MenuBar at the bottom of the front page.
                bottomBar.setSize(1280, 30);
                bottomBar.setPosition(0, 0);
                bottomBar.setBackground(skin.getDrawable("menubar"));
                stage.addActor(bottomBar);
                
                recentButton.setSize(300, 300);
                recentButton.setPosition(120, 200);
                stage.addActor(recentButton);
                
                libraryButton.setSize(300, 300);
                libraryButton.setPosition(490, 200);
                stage.addActor(libraryButton);

                storeButton.setSize(300, 300);
                storeButton.setPosition(860, 200);
                stage.addActor(storeButton);
                
                friendsButton.setSize(200, 50);
                friendsButton.setPosition(540, 550);
                stage.addActor(friendsButton);
                
                recent_bg.setSize(300, 660);
                recent_bg.setPosition(120, 30);
                recent_bg.setBackground(skin.getDrawable("recent"));
                
                generate_game(recent_bg);
                
                final int grow = 20; // The size the Buttons grow by. (Even number please);
                
                friendsButton.addListener(new ClickListener() {
                        public void clicked(InputEvent event, float x, float y) {
                                toggleChat();
                        }
                });
                
                /* Lobby button */
        	    final TextButton lobbyButton = new TextButton("Multiplayer Lobby", skin, "magenta");
        	    lobbyButton.setSize(210, 50);
        	    lobbyButton.setPosition(760, 550);

        	    stage.addActor(lobbyButton);
        	    lobbyButton.addListener((new ChangeListener() {
        	    public void changed(ChangeEvent event, Actor actor) {
        	    	arcadeUI.setScreen(arcadeUI.lobby);
        	    }
        	    }));
        	    /* End Lobby button */
                
                // Icon event listeners, mouseOver and mouseClick
                recentButton.addListener(new ClickListener() {
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                   recentButton.setSize(recentButton.getWidth() + grow, recentButton.getWidth() + grow);
                                   recentButton.setPosition(recentButton.getX() - grow/2, recentButton.getY() - grow/2);
                                   recentButton.setText(null);
                                   recentButton.setText("Recently Played");
                        }
                        public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                                   recentButton.setSize(recentButton.getWidth() - grow, recentButton.getWidth() - grow);
                                   recentButton.setPosition(recentButton.getX() + grow/2, recentButton.getY() + grow/2);
                                   recentButton.setText(null);
                                   recentButton.setText("Recently Played");
                        }
                        public void clicked(InputEvent event, float x, float y) {
                                recentButton.remove();
                                stage.addActor(recent_bg);
//                                
//                                mouse_out.addListener(new ClickListener() {
//                                        public void clicked(InputEvent event, float x, float y) {
//                                                recent_bg.remove();
//                                                stage.addActor(recentButton);
//                                        }
//                                });
                        }
                });
                
                /**
                 * @author Adeleen
                 * This function listens for a click outside the text boxes. It checks
                 * if 'Recently Played' button is on the stage, if not, its add it to stage
                 */
                stage.addListener(new ClickListener() {
                        public void clicked(InputEvent event, float x, float y) {
                                //checking for clicks at specific coordinates in the screen
                                if (x < 120 || x > 420) {
                                        //Checking for 'Recently Played' Button is not on stage
                                        if (!stage.getActors().contains(recentButton, true)) {
                                                recent_bg.remove();
                                                stage.addActor(recentButton);
                                        }
                                }
                        }
                });
           
                libraryButton.addListener(new ClickListener() {                        
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                   libraryButton.setSize(libraryButton.getWidth() + grow, libraryButton.getWidth() + grow);
                                   libraryButton.setPosition(libraryButton.getX() - grow/2, libraryButton.getY() - grow/2);
                                   libraryButton.setText(null);
                                   libraryButton.setText("Library");
                        }
                        public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                                   libraryButton.setSize(libraryButton.getWidth() - grow, libraryButton.getWidth() - grow);
                                   libraryButton.setPosition(libraryButton.getX() + grow/2, libraryButton.getY() + grow/2);
                                   libraryButton.setText(null);
                                   libraryButton.setText("Library");
                        }
                        public void clicked(InputEvent event, float x, float y) {
                                ArcadeSystem.goToGame("gamelibrary");
                        }
                });
                
                storeButton.addListener(new ClickListener() {                        
                        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                                   storeButton.setSize(storeButton.getWidth() + grow, storeButton.getWidth() + grow);
                                   storeButton.setPosition(storeButton.getX() - grow/2, storeButton.getY() - grow/2);
                                   storeButton.setText(null);
                                   storeButton.setText("Store");
                        }
                        public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor){
                                   storeButton.setSize(storeButton.getWidth() - grow, storeButton.getWidth() - grow);
                                   storeButton.setPosition(storeButton.getX() + grow/2, storeButton.getY() + grow/2);
                                   storeButton.setText(null);
                                   storeButton.setText("Store");
                        }
                        public void clicked(InputEvent event, float x, float y) {
                                arcadeUI.setScreen(arcadeUI.getStoreHome());
                        }
                });
        }
        
        private void generate_game(Table recent_bg) {
                Set<Game>gamesList1 = ArcadeSystem.getArcadeGames();
                for (int i = 0; i < 3; i++) {
                        final Game myGame = (Game)gamesList1.toArray()[i];
                        final TextButton recent = new TextButton("\n\n\n\n\n\n\n" + myGame.name, skin, "blue");
                        recent.setSize(220, 220);
                        recent.setPosition(40, i * 220);
                        recent_bg.addActor(recent);

                         final Button recentIcon = new Button(skin.getDrawable(myGame.id));
                         recentIcon.setName("recenticon");
                         recentIcon.setSize(140, 140);
                        recentIcon.setPosition(40, 50);
                        recent.addActor(recentIcon);
                        
                        recent.addListener(new ChangeListener() {
                                public void changed(ChangeEvent event, Actor actor) {
                                        ArcadeSystem.goToGame(myGame.id);
                                }
                        }); 
                        
                        recentIcon.addListener(new ChangeListener() {
                                public void changed(ChangeEvent event, Actor actor) {
                                        ArcadeSystem.goToGame(myGame.id);
                                }
                        });
                }
        }

        public static void setName(String playerName) {
                pName = playerName;
                menuInfo.setText(pName + " | " + pCredits + " Credits");
        }
        
        public static void setCredits(int credits) {
                pCredits = credits;
        }
        
        public static String[] getFriends() {
                return friends; // REALLY BAD. HOPEFULLY TEMPORARY.
        }
        
        public static String[] getOnlineFriends() {
                String[] onlineFriends = null;
                /*for (Player friend : friends) {
                        if (friend.isOnline()) {
                        }
                }*/
                // I Don't know^, do some sort of sorcery. Friends are NOT static, unchanging
                // or permanent strings, they are of the Player class, and we need to return
                // a List of Players to be able to do anything other than show their names.-Add
                return onlineFriends;
        }
        
        private void toggleChat() {
                // Haven't figured out how to toggle this yet. -Addison
                Table chatTable = new Table();
                chatTable.setBackground(skin.getDrawable("menubar"));
                chatTable.setPosition(1060 , 30);
                chatTable.setSize(200, (getFriends().length * 30) + 20);
                stage.addActor(chatTable);
                for (int i = 0; i < getFriends().length; i++){
                        Label friend = new Label(getFriends()[i], skin, "cgothic");
                        friend.setSize(200, 50);
                        friend.setPosition(1080, 30 * (i + 1));
                        stage.addActor(friend);
                }
        }
        
        @Override
        public void show() {
                ArcadeInputMux.getInstance().addProcessor(stage);
        }
        
        @Override
        public void render(float arg0) {
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
                stage.act(Gdx.graphics.getDeltaTime());
                stage.draw();
        }
        
        @Override
        public void dispose() {
                stage.dispose();
                skin.dispose();
                ArcadeInputMux.getInstance().removeProcessor(stage);
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
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.Arcade;
import deco2800.arcade.client.network.NetworkClient;
import deco2800.arcade.model.Game.ArcadeGame;
import deco2800.arcade.model.Game;
import deco2800.arcade.model.Player;

@ArcadeGame(id="frontpage")
public class FrontPage implements Screen {
	
    private class FrontPageStage extends Stage {}
	
        public static Skin skin;
        private static String pName;
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
        
        static boolean settingsClicked = false;
        static Table popUp = new Table();
        
    
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
            final Label lobby = new Label ("Lobby", skin, "cgothic");
            lobby.setAlignment(Align.right);
            
            //Bottom bar
            final Table bottomBox = new Table();
            bottomBox.setSize(1279, 30);
            bottomBox.setPosition(0, 0);
            bottomBox.setColor(255, 255, 255, 1);
            bottomBox.setBackground(skin.getDrawable("menuBar"));
            
            //add bottom labels to bar
            bottomBox.add(lobby).width(1100);
            bottomBox.add(divider2).width(5).pad(20);
            bottomBox.add(chatLink).width(100);
            
            //adding to buttons and menu bar on stage
            stage.addActor(bottomBox);
            stage.addActor(recentButton);
            stage.addActor(libraryButton);
            stage.addActor(storeButton);
           // stage.addActor(lobbyButton);
            
            
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
            
            lobby.addListener((new ClickListener() {
            	public void clicked (InputEvent event, float x, float y){
            		arcadeUI.setScreen(arcadeUI.lobby);
            	}
            })); 
    
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
        
    public static void userName(String playerName){
    	pName = playerName.toString();
		System.out.println("Logged in as: " + pName);
    }
    
    public static void setName(String playerName){
	          
	    	//Top Box Labels
	            final Label logo = new Label("VAPOR", skin, "cgothic");
	            logo.setAlignment(Align.left);
	           
	            final Label username = new Label(pName , skin, "cgothic");
	            
	            if (username.getText() != pName){
	            	System.out.println("Label is null");
	            	username.setText(pName);
	            }
	            
	            username.setAlignment(Align.right);
	            
	            //TODO profile
	            
	            username.addListener((new ClickListener() {
	            	public void clicked (InputEvent event, float x, float y){
	            		
	            		System.out.println("WHERE IS PROFILE GOD DAMN");
	            	}
	            }));
	            
	            final Label credits = new Label( creditVal + " Credits", skin, "cgothic");
	            credits.setAlignment(Align.right);
	            final Label divider = new Label("|", skin, "cgothic");
	            divider.setAlignment(Align.right);
	            
	            final Button settingsIcon = new Button(skin.getDrawable("settingsIcon"));
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
	            
	            settingsIcon.addListener((new ClickListener() {
	            	public void clicked(InputEvent event, float x, float y) {

		            	if (settingsClicked == false){
		            		popUp.setBackground(skin.getDrawable("toolTip"));
		            		popUp.setPosition(950,530);
		            		popUp.setSize(200, 150);
		            		popUp.setName("tooltip");
		            		
		            		final Label profile = new Label ("Profile", skin, "cgothic");
		            		profile.setAlignment(Align.left);
		            		
		            		final Label forum = new Label ("Forum", skin, "cgothic");
		            		forum.setAlignment(Align.left);
		            		
		            		final Label logout = new Label ("Logout", skin, "cgothic");
		            		logout.setAlignment(Align.left);
		            		
		            		final Label space = new Label (" ", skin, "cgothic");
		            		
		            		stage.addActor(popUp);
		            		
		            		popUp.add(profile).padBottom(10);
		            		popUp.row();
		            		popUp.add(forum).padBottom(10);
		            		popUp.row();
		            		popUp.add(logout).padBottom(10);
		            		
		            		logout.addListener((new ClickListener() {
		            			
		     	            	public void clicked (InputEvent event, float x, float y){
		     	            		topBox.reset();
		     	            		topBox.remove();
		     	            		popUp.reset();
				            		popUp.remove();
				            		settingsClicked = false;
		     	            		arcadeUI.setScreen(arcadeUI.login);
		     	            	}
		     	            }));
		            		
		            		forum.addListener((new ClickListener() {
		     	            	public void clicked (InputEvent event, float x, float y){
		     	            		System.out.println("LINK TO FORUM GOD DAMN");
		     	            	}
		     	            }));
			                      
		            		settingsClicked = true;
			                      
		            	}else{
		            		popUp.reset();
		            		popUp.remove();
		            		settingsClicked = false;
		            	}
		                	
		                	
		                }
	            })); 
	            
	            logo.addListener((new ClickListener() {
	            	public void clicked (InputEvent event, float x, float y){
	            		topBox.reset();
	            		topBox.remove();
	            		arcadeUI.setScreen(arcadeUI.main);
	            	}
	            }));
	            
	            
    }

    @Override
    public void show() {
        ArcadeInputMux.getInstance().addProcessor(stage);
        setName(pName);
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