package deco2800.arcade.mixmaze;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;





import deco2800.arcade.mixmaze.GameScreen.Settings;

import org.lwjgl.input.Keyboard;


public class SettingsScreen implements Screen {

	private final MixMaze game;
	private Skin skin;
	private Stage stage;
	private TextButton playButton;
	private List difficultyList;
	private Table rootTable = new Table();
	private Table settingsPanel = new Table();
	private Table playerOnePanel = new Table();
	private Table playerTwoPanel = new Table();
	private Table textPanel = new Table();
	private SelectBox gridSize;
	private TextField[] p1Texts = new TextField[6];
	private TextField[] p2Texts = new TextField[6];
	private int[] p1Controls = new int[6];
	private int[] p2Controls = new int[6];
	private TextFieldListener textFieldListener = new Listener();
	private TextFieldFilter  textFieldFilter = new Filter();
	
	SettingsScreen(final MixMaze game) {
		this.game = game;

		startDebug();
		initialize();
		setTableLayout();
		createSettingsPanel();
		createPlayerPanel(playerOnePanel,p1Texts);
		createPlayerPanel(playerTwoPanel,p2Texts);
		stage.addActor(rootTable);
		addTextFeildListeners(p1Texts);
		addTextFeildListeners(p2Texts);
		addTextFieldFilter(p1Texts);
		addTextFieldFilter(p2Texts);
		playButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				getPlayerControlls(p1Texts,p1Controls);
				getPlayerControlls(p2Texts,p2Controls);				
				//((GameScreen) game.gameScreen).new Settings(p1Controls, p2Controls);
				//game.setScreen(game.gameScreen);
			}			

		});

	}

	private void addTextFieldFilter(TextField[] playerTexts) {
		for (int i = 0;i<playerTexts.length;i++){
			playerTexts[i].setTextFieldFilter(textFieldFilter);;
		}	
		
	}

	private void addTextFeildListeners(TextField[] playerTexts) {
		for (int i = 0;i<playerTexts.length;i++){
			playerTexts[i].setTextFieldListener(textFieldListener);
		}		
		
	}

	private void getPlayerControlls(TextField[] playerTexts, int[] playerControls) {
		for (int i = 0;i<playerControls.length;i++){
			playerControls[i] = getKeyCode(playerTexts[i].getText());
		}				
	}
	
	private void initialize() {
		this.skin = game.skin;
		this.stage = new Stage();
		playButton = new TextButton("Play", skin);
		playButton.pad(20);
		skin.add("background", new Texture(Gdx.files.internal("settings.png")));
		difficultyList = new List(new String [] {"Beginner", "Intermediate","Advanced"}, skin);

		p1Texts[0] = new TextField("G", skin);
		p1Texts[1] = new TextField("H", skin);
		p1Texts[2] = new TextField("W", skin);
		p1Texts[3] = new TextField("A", skin);
		p1Texts[4] = new TextField("S", skin);
		p1Texts[5] = new TextField("D", skin);

		p2Texts[0] = new TextField("5", skin);
		p2Texts[1] = new TextField("6", skin);
		p2Texts[2] = new TextField("UP", skin);
		p2Texts[3] = new TextField("LEFT", skin);
		p2Texts[4] = new TextField("DOWN", skin);
		p2Texts[5] = new TextField("RIGHT", skin);
		
		
		
		
	}

	private void createPlayerPanel(Table panel,TextField[] playerDetails) {
		panel.add(playerDetails[0]).padTop(245).padBottom(80).expandX().colspan(3);
		panel.row();
		panel.add(playerDetails[1]).padBottom(80).colspan(3);
		panel.row();
		panel.add(playerDetails[2]).width(40).padBottom(10).colspan(3);
		panel.row();
		panel.add(playerDetails[3]).width(40).right();
		panel.add(playerDetails[4]).width(40);
		panel.add(playerDetails[5]).width(40).left();
	}

	private void createSettingsPanel() {
		gridSize= new SelectBox(new String []{"5","6","7","8","9","10"}, skin);

		settingsPanel.add(gridSize).expandX().padTop(220).padBottom(110);
		settingsPanel.row();
		settingsPanel.add(difficultyList).padBottom(170);
		settingsPanel.row();
		settingsPanel.add(playButton);
	}

	private void startDebug() {
		rootTable.debug();
		settingsPanel.debug();
		playerOnePanel.debug();
		playerTwoPanel.debug();
	}

	private void setTableLayout() {
		float celHeight = Gdx.graphics.getHeight();
		float settingsWidth = (float) (Gdx.graphics.getWidth()/2.56);
		float textWidth = (float) (Gdx.graphics.getWidth()/5.12);
		float playerWidth = (float) (Gdx.graphics.getWidth()/4.8301);	//256
		Drawable background = skin.getDrawable("background");

		rootTable.setFillParent(true);
		rootTable.setBackground(background);
		rootTable.top().left(); // so that cells are added from the top instead of center
		rootTable.add(settingsPanel).width(settingsWidth).height(celHeight).expandX();
		rootTable.add(textPanel).width(textWidth).height(celHeight).expandX();
		rootTable.add(playerOnePanel).width(playerWidth).height(celHeight).expandX();
		rootTable.add(playerTwoPanel).width(playerWidth).height(celHeight).expandX();

		settingsPanel.top().columnDefaults(4);
		playerOnePanel.top().columnDefaults(3);
		playerTwoPanel.top().columnDefaults(3);
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		stage.dispose();

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		if(stage.getKeyboardFocus() == p1Texts[2] || stage.getKeyboardFocus() == p1Texts[3] || stage.getKeyboardFocus() == p1Texts[4]
				|| stage.getKeyboardFocus() == p1Texts[5] || stage.getKeyboardFocus() == p2Texts[2] || stage.getKeyboardFocus() == p2Texts[3] || stage.getKeyboardFocus() == p2Texts[4]
						|| stage.getKeyboardFocus() == p2Texts[5]){
			if (Gdx.input.isKeyPressed(Keys.UP)){
				((TextField)stage.getKeyboardFocus()).setText("UP");
			}
			if (Gdx.input.isKeyPressed(Keys.LEFT)){
				((TextField)stage.getKeyboardFocus()).setText("LEFT");
			}
			if (Gdx.input.isKeyPressed(Keys.RIGHT)){
				((TextField)stage.getKeyboardFocus()).setText("RIGHT");
			}
			if (Gdx.input.isKeyPressed(Keys.DOWN)){
				((TextField)stage.getKeyboardFocus()).setText("DOWN");
			}
		}
		//Table.drawDebug(stage);
	}

	@Override
	public void resize(int arg0, int arg1) {
		stage.setViewport(1280, 720, true);


	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

	}
	
	private int getKeyCode(String keyText){
		int code = 0;
	if (keyText.equals("0")) code = Keys.NUM_0;
	if (keyText.equals("1")) code = Keys.NUM_1;
	if (keyText.equals("2")) code = Keys.NUM_2;
	if (keyText.equals("3")) code = Keys.NUM_3;
	if (keyText.equals("4")) code = Keys.NUM_4;
	if (keyText.equals("5")) code = Keys.NUM_5;
	if (keyText.equals("6")) code = Keys.NUM_6;
	if (keyText.equals("7")) code = Keys.NUM_7;
	if (keyText.equals("8")) code = Keys.NUM_8;
	if (keyText.equals("9")) code = Keys.NUM_9;
	if (keyText.equalsIgnoreCase("q")) code = Keys.Q;
	if (keyText.equalsIgnoreCase("w")) code = Keys.W;
	if (keyText.equalsIgnoreCase("e")) code = Keys.E;
	if (keyText.equalsIgnoreCase("r")) code = Keys.R;
	if (keyText.equalsIgnoreCase("t")) code = Keys.T;
	if (keyText.equalsIgnoreCase("y")) code = Keys.Y;
	if (keyText.equalsIgnoreCase("u")) code = Keys.U;
	if (keyText.equalsIgnoreCase("i")) code = Keys.I;
	if (keyText.equalsIgnoreCase("o")) code = Keys.O;
	if (keyText.equalsIgnoreCase("p")) code = Keys.P;
	if (keyText.equalsIgnoreCase("a")) code = Keys.A;
	if (keyText.equalsIgnoreCase("s")) code = Keys.S;
	if (keyText.equalsIgnoreCase("d")) code = Keys.D;
	if (keyText.equalsIgnoreCase("f")) code = Keys.F;
	if (keyText.equalsIgnoreCase("g")) code = Keys.G;
	if (keyText.equalsIgnoreCase("h")) code = Keys.H;
	if (keyText.equalsIgnoreCase("j")) code = Keys.J;
	if (keyText.equalsIgnoreCase("k")) code = Keys.K;
	if (keyText.equalsIgnoreCase("l")) code = Keys.L;
	if (keyText.equalsIgnoreCase("z")) code = Keys.Z;
	if (keyText.equalsIgnoreCase("x")) code = Keys.X;
	if (keyText.equalsIgnoreCase("c")) code = Keys.C;
	if (keyText.equalsIgnoreCase("v")) code = Keys.V;
	if (keyText.equalsIgnoreCase("b")) code = Keys.B;
	if (keyText.equalsIgnoreCase("n")) code = Keys.N;
	if (keyText.equalsIgnoreCase("m")) code = Keys.N;
	if (keyText.equalsIgnoreCase("up")) code = Keys.UP;
	if (keyText.equalsIgnoreCase("down")) code = Keys.DOWN;
	if (keyText.equalsIgnoreCase("left")) code = Keys.LEFT;
	if (keyText.equalsIgnoreCase("right")) code = Keys.RIGHT;
	
	if (keyText.equalsIgnoreCase("`")) code = -1;
	if (keyText.equalsIgnoreCase("-")) code = -1;
	if (keyText.equalsIgnoreCase("=")) code = -1;
	if (keyText.equalsIgnoreCase("[")) code = -1;
	if (keyText.equalsIgnoreCase("]")) code = -1;
	if (keyText.equalsIgnoreCase("\\")) code = -1;
	if (keyText.equalsIgnoreCase(";")) code = -1;
	if (keyText.equalsIgnoreCase("'")) code = -1;
	if (keyText.equalsIgnoreCase(",")) code = -1;
	if (keyText.equalsIgnoreCase(".")) code = -1;
	if (keyText.equalsIgnoreCase("/")) code = -1;
	if (keyText.equalsIgnoreCase("")) code = -1;
	if (keyText.equalsIgnoreCase("")) code = -1;
	if (keyText.equalsIgnoreCase("")) code = -1;
	if (keyText.equalsIgnoreCase("")) code = -1;
	if (keyText.equalsIgnoreCase("")) code = -1;
	if (keyText.equalsIgnoreCase("")) code = -1;
	if (keyText.equalsIgnoreCase(" ")) code = -1;
	
	return code;
	}
		
	private class Listener implements TextFieldListener{
		@Override
		public void keyTyped(TextField textField, char key) {
			if(getKeyCode(""+key) != 0){
				textField.setText((""+key).toUpperCase());	
			}
		}		        
    }
	
	private class Filter implements TextFieldFilter{
		@Override
		public boolean acceptChar(TextField textField, char key) {
			if(getKeyCode(""+key) == -1){
				return false;
			}
			return true;
		}
	}	
	
}
