package deco2800.arcade.guesstheword.GUI;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
import deco2800.arcade.guesstheword.gameplay.KeyValues;

public class GameScreen implements Screen {
	
	@SuppressWarnings("unused")
	private GuessTheWord game;
	private Skin skin;
	private SpriteBatch batch;
	private Stage stage;
	private ButtonPanel buttonPanel;
	private PicturePanel picturePanel;
	private RightPanel rightPanel;
	
	private TextButton button1, button2, button3, button4, button5, 
	button6	, button7, button8, button9, button10;
	
	private TextField textfield1, textfield2, textfield3, 
					  textfield4, textfield5, textfield6;  
	
	private Label scoreLabel, levelLabel , timeLabel;

	Texture rain1, rain2, rain3;
	ArrayList<Texture> rainStore;
    int count = 0;
	int numTextfield = 0;
	String level;
	
	GameScreen(final GuessTheWord game){
		this.game = game;
		this.skin = game.skin;
		batch =  new SpriteBatch();
		stage =  new Stage();
		
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
		rightPanel = new RightPanel();
		
		Table rootTable =  new Table();
		rootTable.setFillParent(true);
		
		levelLabel = new Label(level , skin);
	
		rootTable.add(levelLabel);
		stage.addActor(rootTable.top());
		stage.addActor(rightPanel.right());
		stage.addActor(buttonPanel.bottom()); 
		checkKeyboardInputs();
	}
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor( 0f, 0f, 75f, 70f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
        setLevel();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		createGameScreen();
		
		picturePanel =  new PicturePanel();

		// back to main menu
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			System.out.println("Back to MainScreen");
			game.setScreen(game.mainScreen);
		} 

	}
	
//	private Time getTime(){
//		
//	}
	
	private void checkKeyboardInputs(){
		if(Gdx.input.isKeyPressed(Keys.A)){
			getInputText("A"); 
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.B)){
			getInputText("B");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.C)){
			getInputText("C");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.D)){
			getInputText("D");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.E)){
			getInputText("E");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.F)){
			getInputText("F");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.G)){
			getInputText("G"); 
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.H)){
			getInputText("H");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.I)){
			getInputText("I");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.J)){
			getInputText("J");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.K)){
			getInputText("K");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.L)){
			getInputText("L");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.M)){
			getInputText("N");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.O)){
			getInputText("O");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.P)){
			getInputText("P");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.Q)){
			getInputText("Q");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.R)){
			getInputText("R");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.S)){
			getInputText("S");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.T)){
			getInputText("T");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.U)){
			getInputText("U");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.V)){
			getInputText("V");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.W)){
			getInputText("W");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.X)){
			getInputText("X");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.Y)){
			getInputText("Y");
//			game.setScreen(new GameScreen(game));
		}else if(Gdx.input.isKeyPressed(Keys.Z)){
			getInputText("Z");
//			game.setScreen(new GameScreen(game));
		}
		
		if(Gdx.input.isKeyPressed(Keys.BACKSPACE)){
			game.getterSetter.setText("");
			game.getterSetter.setText1("");
			game.getterSetter.setText2("");
			game.getterSetter.setText3("");
			game.getterSetter.setText4("");
		} 
	}
	
	private void getInputText(String input){
		if(game.getterSetter.getText1().isEmpty())
			game.getterSetter.setText1(input);
		else if(game.getterSetter.getText2().isEmpty())
			game.getterSetter.setText2(input);
		else if(game.getterSetter.getText3().isEmpty())
			game.getterSetter.setText3(input);
		else if(game.getterSetter.getText4().isEmpty())
			game.getterSetter.setText4(input);
		else if(game.getterSetter.getText5().isEmpty())
			game.getterSetter.setText5(input);
		else if(game.getterSetter.getText6().isEmpty())
			game.getterSetter.setText6(input);
	}

	
	//Right Panel to add score and time
	private class RightPanel extends Table{
		
		RightPanel(){
			this.setFillParent(true);
			scoreLabel =  new Label("Score: 0" , skin);
			this.add(scoreLabel).padBottom(30).width(100).row();
			
			timeLabel = new Label("Time: " , skin);
			this.add(timeLabel).padBottom(30).width(100).row();
		}
	}
	
	//ButtonPanel to store all the button.
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
		
			
			checkTextfield();
			
			if(level.equalsIgnoreCase("Level 1")){
				this.add(textfield1).width(50).height(50).spaceBottom(30);
				this.add(textfield2).width(50).height(50).spaceBottom(30);
				this.add(textfield3).width(50).height(50).spaceBottom(30);
				this.add(textfield4).width(50).height(50).spaceBottom(30);
				numTextfield = 4 ;
			}else if(level.equalsIgnoreCase("Level 2")){
				this.add(textfield1).width(50).height(50).spaceBottom(30);
				this.add(textfield2).width(50).height(50).spaceBottom(30);
				this.add(textfield3).width(50).height(50).spaceBottom(30);
				this.add(textfield4).width(50).height(50).spaceBottom(30);
				this.add(textfield5).width(50).height(50).spaceBottom(30);
				numTextfield = 5;
			}else if(level.equalsIgnoreCase("Level 3")){
				this.add(textfield1).width(50).height(50).spaceBottom(30);
				this.add(textfield2).width(50).height(50).spaceBottom(30);
				this.add(textfield3).width(50).height(50).spaceBottom(30);
				this.add(textfield4).width(50).height(50).spaceBottom(30);
				this.add(textfield5).width(50).height(50).spaceBottom(30);
				this.add(textfield6).width(50).height(50).spaceBottom(30);
				numTextfield = 6;
			}
			this.row();
			
			button1 =  new TextButton("A" , skin);
			button2 =  new TextButton("B" , skin);	
			button3 =  new TextButton("C" , skin);
			button4 =  new TextButton("D" , skin);
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
			
			buttonListeners();
		}
	}
	
	private void checkTextfield(){
		
		textfield1.setText(game.getterSetter.getText1()); 
		textfield2.setText(game.getterSetter.getText2()); 
		textfield3.setText(game.getterSetter.getText3());
		textfield4.setText(game.getterSetter.getText4()); 
	}// end of checkTextfield
	
	private void buttonListeners(){
		button1.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button1);
			}});
		button2.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button2);
			}});	
		button3.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button3);
			}});	
		button4.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button4);
			}});	
		button5.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button5);
			}});	
		button6.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button6);
			}});
		button7.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button7);
			}});
		button8.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button8);
			}});
		button9.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button9);
			}});
		button10.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent arg0, Actor arg1) {
				getButtonText(button10);
			}});
	}
	
	private void getButtonText(TextButton button){
		if(game.getterSetter.getText1().isEmpty())
			game.getterSetter.setText1("" + button.getText());
		else if(game.getterSetter.getText2().isEmpty())
			game.getterSetter.setText2("" + button.getText());
		else if(game.getterSetter.getText3().isEmpty())
			game.getterSetter.setText3("" + button.getText());
		else if(game.getterSetter.getText4().isEmpty())
			game.getterSetter.setText4("" + button.getText());
		else if(game.getterSetter.getText5().isEmpty())
			game.getterSetter.setText5("" + button.getText());
		else if(game.getterSetter.getText6().isEmpty())
			game.getterSetter.setText6("" + button.getText());
	}

	private class PicturePanel{
		
		PicturePanel(){
			
			rain1 =  new Texture("rain1.jpg");
			rain2 =  new Texture("rain2.jpg");
			rain3 =  new Texture("rain3.jpg");
			
			rainStore = new ArrayList<Texture>();
			rainStore.add(rain1);
			rainStore.add(rain2);
			rainStore.add(rain3);
			
			batch.begin(); 
			if(Gdx.input.isKeyPressed(Keys.ENTER) == true){
				count = 0; 
			}else if(Gdx.input.isKeyPressed(Keys.RIGHT) == true){
				System.out.println("RIGHT Picture: " + count);
				count++;	
				if(count >= rainStore.size())
					count = 0;
			}else if(Gdx.input.isKeyPressed(Keys.LEFT) == true){
				System.out.println("LEFT Picture: " + count);
				count -- ;	
				if(count < 0)
					count = rainStore.size()-1;	
			}
			batch.draw(rainStore.get(count), 350, 200, 600,400);
	        batch.end();
		}
	}
	
	@Override
	public void dispose() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
		rain1.dispose(); rain2.dispose(); rain3.dispose();
		batch.dispose();
		skin.dispose();
		stage.dispose();
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}

	
}// end of GameScreen