package deco2800.arcade.mixmaze;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


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
	private TextField[] p1Texts = new TextField[10];
	private TextField[] p2Texts = new TextField[10];

	SettingsScreen(final MixMaze game) {
		this.game = game;

		startDebug();
		initialize();
		setTableLayout();
		createSettingsPanel();
		createPlayerPanel(playerOnePanel,p1Texts);
		createPlayerPanel(playerTwoPanel,p2Texts);
		stage.addActor(rootTable);

		playButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				//game.gameScreen.new Settings();
				//game.setScreen(game.gameScreen);
			}
		});

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
}
