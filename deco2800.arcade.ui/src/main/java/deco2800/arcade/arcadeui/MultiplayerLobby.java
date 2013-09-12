package deco2800.arcade.arcadeui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.*;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.client.ArcadeInputMux;

import deco2800.arcade.client.ArcadeSystem;



public class MultiplayerLobby implements Screen {

	private OrthographicCamera camera;
	private Stage stage;
	private Screen screen;
	private Skin skin;
    private Skin skin2;
	private ShapeRenderer shapeRenderer;
	private ShapeRenderer shapeRenderer2;
	
	
public void show() {
		shapeRenderer = new ShapeRenderer();
		stage = new Stage();
		ArcadeInputMux.getInstance().addProcessor(stage);
                
		skin = new Skin();
        Skin skin = new Skin(Gdx.files.internal("loginSkin.json"));
        Label label = new Label("Matchmaking Lobby", skin);
		Label label2 = new Label("Chat:", skin);
	    skin2 = new Skin();
		
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
	
        skin.add("white", new Texture(pixmap));
        skin2.add("white", new Texture(pixmap));
        
        skin.add("default", new BitmapFont());
        
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin2.add("default", labelStyle);
		
	    TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.cursor = skin2.newDrawable("white", Color.BLACK);
        textFieldStyle.selection = skin2.newDrawable("white", Color.BLUE);
		textFieldStyle.background = skin2.newDrawable("white", Color.DARK_GRAY);
        skin2.add("default", textFieldStyle);
				
			
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
      
	    textButtonStyle.up = skin2.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin2.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin2.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin2.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        
		skin2.add("default", textButtonStyle);
				
				
				
                TextButton button = new TextButton("Match Me!", skin2);
				TextButton button2 = new TextButton("Return to Menu", skin2);
				
				TextButton button3 = new TextButton(">>", skin2);
                
				final TextField chatfield = new TextField("", skin2);
				chatfield.setMessageText("Enter Chat");
				
				
				final Table table4 = new Table();
				final Table table = new Table();
				final Table table2 = new Table();
				final Table table3 = new Table();
				
				
				
				Table chattable = new Table();
				
				stage.addActor(chattable);
				stage.addActor(table3);
				stage.addActor(table2);
                stage.addActor(table);
                
				table.setFillParent(true);
				table2.setFillParent(true);
				table3.setFillParent(true);
				chattable.setFillParent(true);
				table4.setFillParent(true);
                
				
				
				
				
				chattable.add(label2).padTop(480).padLeft(610);
                //table.defaults().space(6);
                //table2.add(label).padBottom(620);
				//table.row();
				
				
				
				table3.add(chatfield).width(150).padLeft(850).padTop(480);
				
				table3.add(button3).width(30).padLeft(3).padTop(480);
				
                table.add(button).width(300).height(40).padRight(80).padTop(600);
				
				table.add(button2).width(300).height(40).padLeft(80).padTop(600);
				
				
	
				
				
				
		//.padBottom(5).padTop(5).padLeft(10).padRight(10)
		
		 button.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                
				
				//table2.removeActor();
				table2.clear();
				
				int[] anArray = { 
        	    1, 2, 3,
        	    4, 5, 6, 
        	    7, 8, 9, 10
        	};
 
        if (anArray.length > 0){
        	//for(int i : anArray){
        		
			
        		for(int i= anArray[0]; i < anArray.length +1; i++) {
				
				
				final TextButton button4 = new TextButton("Join Game: " + i, skin2);
				button4.setName(""+ i);
				table2.add(button4).width(130).padTop(20);
				table2.row();
				
				System.out.println(button4.getName());
                System.out.println("Element:" + i);
				
			
				
				
				 button4.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("You Clicked: " + button4.getName());
				
			}
            
        });
				
				
				
				
				}
				
				
				
            }
			
			else {
			
			//Do Nothing
			
			}
			
			}
        });
		
		
		 button2.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                
				
				
            }
        });
		
		 button3.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                
				chatfield.getText();
				System.out.println("You Said: " + chatfield.getText());
				chatfield.setText("");
				
				
            }
        });

			camera = new OrthographicCamera();
		camera.setToOrtho(true, 1280, 720);
 
		//Gdx.app.exit();
	}
	
	
	@Override
	public void render(float arg0) {
		
		camera.update();
	    shapeRenderer.setProjectionMatrix(camera.combined);
		//stage.pause();
		//screen.dispose();
	
		//Gdx.gl.glClearColor(0.9f, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		shapeRenderer.begin(ShapeType.FilledRectangle);
	    
	    shapeRenderer.filledRect(50,
	        100,
	        1280 - 100,
	        720 - 200);
  
	    shapeRenderer.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
 
	}
	

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	stage.dispose();
    skin.dispose();
	skin2.dispose();
    ArcadeInputMux.getInstance().removeProcessor(stage);
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
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	

}
