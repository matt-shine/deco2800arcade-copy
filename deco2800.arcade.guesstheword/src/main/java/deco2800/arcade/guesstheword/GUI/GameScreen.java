package deco2800.arcade.guesstheword.GUI;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.client.ArcadeInputMux;

public class GameScreen implements Screen {
	
	@SuppressWarnings("unused")
	private GuessTheWord game;
	private Skin skin;
	private SpriteBatch batch;
	private Stage stage;
	private ButtonPanel buttonPanel;
	private PicturePanel picturePanel;

//	private Stack stack;
//	private Table  topTable, buttonTable;
//	leftTable , rightTable, bottomTable,rootTable ,
//				  scoreTable, pictureTable
	
	private TextButton button1, button2, button3, button4, button5, 
	button6	, button7, button8, button9, button10, NextPicButton;
	
//					   ExitButton ;
//	
	private TextField textfield1, textfield2, textfield3, 
					  textfield4, textfield5, textfield6;  
	
	private Label scoreLabel, levelLabel;
	
	Texture rain1, rain2, rain3;
	ArrayList<Texture> rainStore;

    int count = 0;
	
	String level;
	
	GameScreen(final GuessTheWord game){
		this.game = game;
		this.skin = game.skin;
		batch =  new SpriteBatch();
		stage =  new Stage();
		//batch = new SpriteBatch();
	}
	
	private void setLevel(){		
		String level = game.getterSetter.getLevel();
		
		if(level.equalsIgnoreCase("Level 1 - 4 letters") 
				|| level.equalsIgnoreCase("Default")){
			this.level = "Level 1";			
		}else if(level.equalsIgnoreCase("Level 2 - 5 letters")){
			this.level = "Level 2";
		}else if(level.equalsIgnoreCase("Level 3 - 6 letters")){
			this.level = "Level 3";
		}
		//System.out.println(this.level);
	}
	private void createGameScreen(){
		
		buttonPanel =  new ButtonPanel();
		picturePanel =  new PicturePanel();

		Table rootTable =  new Table();
		rootTable.setFillParent(true);
		
		levelLabel = new Label(level , skin);
		scoreLabel =  new Label("Score: " , skin);
		rootTable.add(levelLabel);
		
		stage.addActor(rootTable.top());
		stage.addActor(picturePanel.align(5));
		stage.addActor(buttonPanel.bottom());
	}
	
	@Override
	public void render(float arg0) {
		setLevel();
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        
		switch(level){		
		case "Level 1": 
					createGameScreen();
					checkInput();
					stage.act(arg0);
					stage.draw();
					Table.drawDebug(stage);

		case "Level 2": 
					createGameScreen();
					stage.act(arg0);
					stage.draw();
					Table.drawDebug(stage);
			
		case "Level 3":
					createGameScreen();
					stage.act(arg0);
					stage.draw();
					Table.drawDebug(stage);
		}

	}
	
	private void checkInput(){
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			System.out.println("Back to MainScreen");
			game.setScreen(game.mainScreen);
		} 
//		else if(Gdx.input.isKeyPressed(Keys.ANY_KEY)){
//			System.out.println("Letter is " + (char) Keys.ANY_KEY);
//			textfield1.setTextFieldListener(new TextFieldListener(){
//
//				@Override
//				public void keyTyped(TextField textfield, char arg1) {
//					// TODO Auto-generated method stub
//					System.out.println("Letter is " + (char) Keys.ANY_KEY);
//				}});
//		}
	}

	private void checkTextfield(String text){
		
		if(textfield1.getText().equals("")){
			textfield1.setText(text);
			System.out.println("checkTextfield = " +text);
		}else if(textfield2.getText().equals("")){
			textfield2.setText(text);
		}else if(textfield3.getText().equals("")){
			textfield3.setText(text);
		}else if(textfield4.getText().equals("")){
			textfield4.setText(text);
		}else if(textfield5.getText().equals("")){
			textfield5.setText(text);
		}else if(textfield6.getText().equals("")){
			textfield6.setText(text);
		}
	
	}// end of checkTextfield
	
	private class ButtonPanel extends Table{
		
		ButtonPanel(){
//			buttonTable = new Table();
			this.setFillParent(true);
			
			textfield1 =  new TextField("",skin); 
			
			textfield2 =  new TextField("",skin); 
			textfield3 =  new TextField("",skin); 
			textfield4 =  new TextField("",skin); 
			textfield5 =  new TextField("",skin); 
			textfield6 =  new TextField("",skin); 
			
			if(level.equalsIgnoreCase("Level 1")){
				this.add(textfield1).width(50).height(30).spaceBottom(30);
				this.add(textfield2).width(50).height(30).spaceBottom(30);
				this.add(textfield3).width(50).height(30).spaceBottom(30);
				this.add(textfield4).width(50).height(30).spaceBottom(30);
			}else if(level.equalsIgnoreCase("Level 2")){
				this.add(textfield1).width(50).height(30).spaceBottom(30);
				this.add(textfield2).width(50).height(30).spaceBottom(30);
				this.add(textfield3).width(50).height(30).spaceBottom(30);
				this.add(textfield4).width(50).height(30).spaceBottom(30);
				this.add(textfield5).width(50).height(30).spaceBottom(30);
			}else if(level.equalsIgnoreCase("Level 3")){
				this.add(textfield1).width(50).height(30).spaceBottom(30);
				this.add(textfield2).width(50).height(30).spaceBottom(30);
				this.add(textfield3).width(50).height(30).spaceBottom(30);
				this.add(textfield4).width(50).height(30).spaceBottom(30);
				this.add(textfield5).width(50).height(30).spaceBottom(30);
				this.add(textfield6).width(50).height(30).spaceBottom(30);
			}
			
			this.row();

			button1 =  new TextButton("A" , skin);
			button1.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent arg0, Actor arg1) {
					checkTextfield("" + button1.getText());
					button1.setText("AAA");
				}});
			
			button2 =  new TextButton("B" , skin);	
			button2.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent arg0, Actor arg1) {
					checkTextfield("" + button2.getText());	
				}});
			
			button3 =  new TextButton("C" , skin);
			button3.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent arg0, Actor arg1) {
					checkTextfield("" + button3.getText());	
				}});
			
			button4 =  new TextButton("D" , skin);
			button4.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent arg0, Actor arg1) {
					checkTextfield("" + button4.getText());	
				}});
			button5 =  new TextButton("E" , skin);
			button6 =  new TextButton("F" , skin);
			button7 =  new TextButton("G" , skin);
			button8 =  new TextButton("H" , skin);
			button9 =  new TextButton("I" , skin);
			button10 =  new TextButton("J" , skin);
			
			this.add(button1).width(70).height(50);
			this.add(button2).width(70).height(50);
			this.add(button3).width(70).height(50);
			this.add(button4).width(70).height(50);
			this.add(button5).width(70).height(50);
			this.row();
			this.add(button6).width(70).height(50);
			this.add(button7).width(70).height(50);
			this.add(button8).width(70).height(50);
			this.add(button9).width(70).height(50);
			this.add(button10).width(70).height(50);
		}
	}

	private class PicturePanel extends Table  {
		
		PicturePanel(){
			//Stack stack =  new Stack();
			rain1 =  new Texture("rain1.jpg");
			rain2 =  new Texture("rain2.jpg");
			rain3 =  new Texture("rain3.jpg");
			
			rainStore = new ArrayList<Texture>();
			rainStore.add(rain1);
			rainStore.add(rain2);
			rainStore.add(rain3);

			batch.begin();
			batch.draw(rainStore.get(count), 350, 200, 500,400);
			batch.end(); 
			
			this.setFillParent(true);
			NextPicButton =  new TextButton("Next Picture" , skin);		
			
			if(Gdx.input.isKeyPressed(Keys.RIGHT)){
				System.out.println("Next Picture");
				batch.begin();
				batch.draw(rainStore.get(count), 350, 200, 500,400);
				batch.end();
				count++;	
				if(count >= rainStore.size()){
					count = 0;
				}
			}
				
			NextPicButton.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent arg0, Actor arg1) {
					batch.begin();
					batch.draw(rainStore.get(count), 350, 200, 500,400);
					batch.end();
					count++;	
					if(count >= rainStore.size()){
						count = 0;
					}
				}});
			this.add(NextPicButton);
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		//Gdx.input.setInputProcessor(stage);
		ArcadeInputMux.getInstance().addProcessor(stage);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		ArcadeInputMux.getInstance().removeProcessor(stage);
		rain1.dispose(); rain2.dispose(); rain3.dispose();
		batch.dispose();
		stage.dispose();
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
	
}// end of GameScreen