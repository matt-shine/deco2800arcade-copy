package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

public class FrontPage implements Screen {
	
	private class FrontPageStage extends Stage {}
	
    private Skin skin;
    private FrontPageStage stage;
	
    private float funds;
    private int tokens;
    private int creditVal = 0;
    private boolean bclicked;
    
    Texture bg;
    Texture mB;
    Sprite bgSprite;
    Sprite mbSprite;
    
    SpriteBatch batch;
    
    public FrontPage() {
    	//FIXME big method
        
        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        skin.add("background", new Texture("homescreen_bg.png"));
        stage = new FrontPageStage();
        skin.add("menuBar", new Texture("menuBar.png"));
               
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
        
        
        /*skin.add("blue", new Texture("iconBlue.png"));
        
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        
        textButtonStyle.up = skin.newDrawable("blue");*/
        
        
        //Text Buttons
        final TextButton storeButton = new TextButton("Store", skin, "green");
        //skin.add("Store", new Texture("homescreen_bg.png"));
        final TextButton chatButton = new TextButton("Chat", skin);
        final TextButton libraryButton = new TextButton("Library", skin, "magenta");
        final TextButton recentButton = new TextButton("Recently Played", skin, "blue");
    
        final Label username = new Label("Adeleen Pavia", skin, "cgothic");
        username.setAlignment(Align.right);
        final Label credits = new Label( creditVal + " Credits", skin, "cgothic");
        credits.setAlignment(Align.right);
        final Label divider = new Label("|", skin, "cgothic");
        divider.setAlignment(Align.right);
    
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
        
        
        //adding panel for top and bottom bar
        final Table topBox = new Table();
        final Table bottomBox = new Table();
        
        //set panel sizes and positions
        topBox.setSize(1279, 30);
        topBox.setPosition(1, 690);
        topBox.setColor(255, 255, 255, 1);
        topBox.setBackground(skin.getDrawable("menuBar"));
        
        topBox.add(username).width(1000);
        topBox.add(divider).width(5).pad(20);
        topBox.add(credits).width(90);
        bottomBox.setSize(1279, 30);
        bottomBox.setPosition(1, 1);
        bottomBox.setColor(255, 255, 255, 1);
        
        //adding to stage
        stage.addActor(topBox);
        stage.addActor(bottomBox);
        
        
   
        
        
        //float height = storeButton.getHeight();
    	//float width = storeButton.getWidth();
        
        

        // Icon event listeners, mouseOver and mouseClick
        recentButton.addListener((new ClickListener() {        	
            public void enter (InputEvent event, float x, float y, int pointer, Actor fromActor) {	
           		recentButton.setSize(bWidth + enlarge,  bHeight + enlarge);
           		recentButton.setPosition(bX -(enlarge/2), bY-(enlarge/2));
           		recentButton.setText(null);
               	recentButton.setText("Recently Played");
            		
            		 			     			
           
            }
            public void exit (InputEvent event, float x, float y, int pointer, Actor fromActor){
            	recentButton.setSize(bWidth, bHeight);
            	recentButton.setPosition(bX, bY);
            	recentButton.setText(null);
            	recentButton.setText("Recently Played");
            }}));   
        
	    
		recentButton.addListener((new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {
		    	ArcadeSystem.login("recent");
		    	bclicked = true;
		    	System.out.println("recent clicked");
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
	        	ArcadeSystem.login("library");
	        	bclicked = true;
	        	System.out.println("library clicked");
	        }
	    })); 
	    
        
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
            	ArcadeSystem.login("store");
            	System.out.println("store clicked");
            	bclicked = true;
            }
        })); 
        
		
        
        //adding to stage
        stage.addActor(recentButton);
        stage.addActor(libraryButton);
        stage.addActor(storeButton);
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
        
        
        
		if (bclicked == true) {
        	System.out.println("going to arcadeui");
	    	ArcadeSystem.goToGame("arcadeui");
	    }
    	
        
        
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