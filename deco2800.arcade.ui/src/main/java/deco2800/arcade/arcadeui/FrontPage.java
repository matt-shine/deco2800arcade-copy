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
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
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
                                arcadeUI.setScreen(arcadeUI.store);
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
}