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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.client.ArcadeInputMux;

public class FrontPage implements Screen {
	
	private class FrontPageStage extends Stage {}
	
    private Skin skin;
    private FrontPageStage stage;
	
    private float funds;
    private int tokens;
    
    Texture bg;
    Sprite bgSprite;
    SpriteBatch batch;
    
    public FrontPage() {
    	//FIXME big method
        
        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        skin.add("background", new Texture("homescreen_bg.png"));
        stage = new FrontPageStage();
               
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("background"));
        stage.addActor(table);
        
        bg = new Texture("homescreen_bg.png");
        bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bgSprite = new Sprite(bg);
        batch = new SpriteBatch();
        
        //Text Buttons
        final TextButton storeButton = new TextButton("Store", skin);
        final TextButton chatButton = new TextButton("Chat", skin);
        final TextButton libraryButton = new TextButton("Library", skin);
        final TextButton recentButton = new TextButton("Recently Played", skin);
        
        //make button sizes and positioning
        storeButton.setSize(190, 190);
        storeButton.setPosition(190, 300);
        
        libraryButton.setSize(190, 190);
        libraryButton.setPosition(490, 300);
        
        recentButton.setSize(190, 190);
        recentButton.setPosition(790, 300);
        
        //adding to stage
        stage.addActor(storeButton);
        stage.addActor(libraryButton);
        stage.addActor(recentButton);
        
        //something to be added here
        /*Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.cursor = skin.newDrawable("white", Color.WHITE);
        textFieldStyle.selection = skin.newDrawable("white", Color.WHITE);
        skin.add("default", textFieldStyle);
        
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        //textButtonStyle.checked = skin2.newDrawable("white", Color.WHITE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);*/
        
       
        
        /*final Table leftBox = new Table();
        final Table featuredBox = new Table();		// 'featured' box at top left of screen
        final Table leftButtonBox = new Table();	// buttons to cycle through featured games
        final Table rightButtonBox = new Table();	// ...
        final Table topBox = new Table();
        final Table botBox = new Table();
        final Table transactions = new Table();
        final Table search = new Table();
        
        leftBox.setSize(680, 500);
        leftBox.setPosition(20, 20);
        featuredBox.setSize(540, 160);
        featuredBox.setPosition(90, 540);
        leftButtonBox.setSize(50, 160);
        leftButtonBox.setPosition(20, 540);
        rightButtonBox.setSize(50, 160);
        rightButtonBox.setPosition(650, 540);
        topBox.setSize(540, 160);
        topBox.setPosition(720, 540);
        botBox.setSize(540, 500);
        botBox.setPosition(720, 20);
        
        stage.addActor(leftBox);
        stage.addActor(featuredBox);
        stage.addActor(leftButtonBox);
        stage.addActor(rightButtonBox);
        stage.addActor(topBox);
        stage.addActor(botBox);
        
        final Label searchLabel = new Label("Search:", skin);
        final TextField searchField = new TextField("", skin);
        final TextButton searchButton = new TextButton("Search", skin);
        searchField.setMessageText("Search");
        
        final TextButton transactionsButton = new TextButton("Transactions", skin);
        final TextButton buyGameButton = new TextButton("Buy Game", skin);
        final TextButton buyTokenButton = new TextButton("Buy Tokens", skin);
        final TextButton addFundsButton = new TextButton("Add Funds", skin);
        final TextField buyField = new TextField("0", skin);
        final Label fundLabel = new Label("$" + funds, skin);
        final Label tokenLabel = new Label(tokens + " Tokens", skin);
        
        leftBox.debug(); // Shows table debug lines
        featuredBox.debug();
        leftButtonBox.debug();
        rightButtonBox.debug();
        topBox.debug();
        botBox.debug();
        
        leftBox.add(search);
        search.add(searchLabel).width(65);
        search.add(searchField).width(135);
        search.add(searchButton);
        
        // 'Featured games' box
        final Label featuredLabel = new Label("Featured games!", skin);
        
        featuredBox.add(featuredLabel);
        
        
        // Left and right button boxes
        final TextButton prevFeatButton = new TextButton("<<", skin);
        final TextButton nextFeatButton = new TextButton(">>", skin);
        leftButtonBox.add(prevFeatButton).width(50);
        rightButtonBox.add(nextFeatButton).width(50);
        
        
        // Transactions Tab
        botBox.add(transactionsButton).width(200);
        botBox.row();
        botBox.add(transactions);
        
        
        topBox.add(fundLabel).width(100);
        topBox.add(tokenLabel).width(100)*/;
        
        /*searchButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Searching For: " + searchField.getText());
            }
        });
        transactionsButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("toggle");
                if(transactions.isVisible()) {
                	transactions.setVisible(false);
                } else {
                	transactions.setVisible(true);
                }
            }
        });
        buyGameButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("bought game");
            }
        });
        buyTokenButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	if (isInteger(buyField.getText())) {
            		tokens += Integer.parseInt(buyField.getText());
                    tokenLabel.setText(tokens + " Tokens");
                    System.out.println("bought " + buyField.getText() + " tokens\nNow have " + tokens);
            	}
            	else {
            		System.out.println("please enter positive integers only");
            	}
                
            }
        });
        addFundsButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	if (Float.parseFloat(buyField.getText()) < 0) {
            		// prevent users from subtracting from their funds
            		System.out.println("please enter positive values only");
            	}
            	else {
            		// add funds
            		funds += Float.parseFloat(buyField.getText());
                    fundLabel.setText("$" + funds);
                    System.out.println("added " + buyField.getText() + " funds\nNow have $" + funds);
            	}
                
            }
        });*/
	}
    
    
    
    /*public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
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
        Table.drawDebug(stage);  // Shows table debug lines
	}
	
	@Override
	public void dispose() {
        stage.dispose();
        skin.dispose();
        ArcadeInputMux.getInstance().removeProcessor(stage);
	}
	
	@Override
	public void hide() {
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