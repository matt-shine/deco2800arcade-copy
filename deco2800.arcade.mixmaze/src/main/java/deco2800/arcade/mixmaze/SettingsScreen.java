package deco2800.arcade.mixmaze;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import java.nio.channels.SeekableByteChannel;

import javax.swing.text.Style;




import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


public class SettingsScreen implements Screen {

	private static final String LOG = SettingsScreen.class.getSimpleName();

	private final MixMaze game;
	private Skin skin;
	private Stage stage;
	private TextButton playButton;
	private List list;
	private Table rootTable = new Table();
	private Table settingsPanel = new Table();
	private Table playerOnePanel = new Table();
	private Table playerTwoPanel = new Table();
	private TextField pOneSwitchActionText;
	private TextField pOneUseActionText;
	private TextField pTwoSwitchActionText;
	private TextField pTwoUseActionText;
	private SelectBox rows;
	private SelectBox columns;
	
	SettingsScreen(final MixMaze game) {
		this.game = game;	
		
		startDebug();
		initialize();		
		setTableLayout();
		createSettingsPanel();
		createPlayerPanel(new Label("PLayer 1", skin),playerOnePanel,
				pOneSwitchActionText,pOneUseActionText);
		createPlayerPanel(new Label("PLayer 2", skin),playerTwoPanel,
				pTwoSwitchActionText,pTwoUseActionText);
		stage.addActor(rootTable);		
		
		playButton.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(game.gameScreen);
			}
		});
		
	}

	private void initialize() {
		this.skin = game.skin;
		this.stage = new Stage();
		playButton = new TextButton("Play", skin);		
		skin.add("background", new Texture(Gdx.files.internal("settings.png")));
		
		list = new List(new String [] {"Beginner", "Intermediate","Advanced"}, skin);
		
		pOneSwitchActionText = new TextField("G", skin);
		pOneUseActionText = new TextField("H", skin);
		pTwoSwitchActionText = new TextField("5", skin);
		pTwoUseActionText = new TextField("6", skin);		
	}

	private void createPlayerPanel(Label playerLabel, Table panel,TextField swithAction,TextField useAction) {
		Label switchActionLabel = new Label("Swith Action: ", skin);
		Label useActionLabel = new Label("Use Action: ", skin);
		Label forwardLabel = new Label("Forward: ",skin);
		Label backwordLabel = new Label("Backword: ",skin);
		Label rightLabel = new Label("Right: ",skin);
		Label leftLabel = new Label("left: ",skin);
		Label controlLabel = new Label("CONTROLS",skin);		
		
		
		playerLabel.setFontScale(2);		
		controlLabel.setFontScale(2);
		panel.add(playerLabel).padTop(70).padBottom(100);
		panel.row();
		panel.add(controlLabel).expandX();
		panel.row().pad(10);
		panel.add(switchActionLabel).padRight(20);
		panel.add(swithAction);
		panel.row().pad(10);
		panel.add(useActionLabel).padRight(20);
		panel.add(useAction);
		panel.row().pad(10);
		
		
	}

	private void createSettingsPanel() {
		Label difficultyLabel = new Label("DIFFICULTY", skin);
		Label settingsLabel = new Label("SETTINGS", skin);
		Label sizeLabel = new Label("SIZE", skin);
		rows= new SelectBox(new String []{"5","6","7","8","9","10"}, skin);
		columns= new SelectBox(new String []{"5","6","7","8","9","10"}, skin);
		
		difficultyLabel.setFontScale(2);
		settingsLabel.setFontScale(2);
		sizeLabel.setFontScale(2);
		settingsPanel.defaults();
		settingsPanel.add(settingsLabel).padTop(70).padBottom(100);
		settingsPanel.row();
		settingsPanel.add(difficultyLabel);
		settingsPanel.row();
		settingsPanel.add(list).width(200);
		settingsPanel.row();
		settingsPanel.add(sizeLabel).padTop(30);
		settingsPanel.row();
		settingsPanel.add(new Label("# of rows: ", skin));
		settingsPanel.add(rows);		
		settingsPanel.add(new Label("  # of columns: ", skin)).expandX();
		settingsPanel.add(columns);
		settingsPanel.row();
		settingsPanel.add(playButton).padTop(30);
		
	}

	private void startDebug() {
		rootTable.debug();
		settingsPanel.debug();
		playerOnePanel.debug();
		playerTwoPanel.debug();		
	}

	private void setTableLayout() {
		float celHeight = Gdx.graphics.getHeight();
		float cellWidth = Gdx.graphics.getWidth()/3;
		Drawable background = skin.getDrawable("background");
		rootTable.setFillParent(true);
		//rootTable.setBackground(background);
		rootTable.add(settingsPanel).width(cellWidth).height(celHeight);
		rootTable.add(playerOnePanel).width(cellWidth).height(celHeight);	
		rootTable.add(playerTwoPanel).width(cellWidth).height(celHeight);
		settingsPanel.top();
		playerOnePanel.top();
		playerTwoPanel.top();
		
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
