package deco2800.arcade.mixmaze;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldFilter;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

import deco2800.arcade.client.ArcadeInputMux;

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
	private TextButton[] p1Buttons = new TextButton[5];
	private TextButton[] p2Buttons = new TextButton[5];
	private int[] p1Controls = new int[6];
	private int[] p2Controls = new int[6];
	private TextFieldListener textFieldListener = new Listener();
	private TextFieldFilter textFieldFilter = new Filter();
	private TextureRegion p1AvatarTexRegion;
	private Image p1AvatarImage;
	private TextureRegion p2AvatarTexRegion;
	private Image p2AvatarImage;
	private Stack p1Avatarstack;
	private Stack p2Avatarstack;
	private TextureRegion p1HeadRegion;
	private Image p1BodyImage;
	private Image p1HeadImage;
	private Image p2HeadImage;
	private TextureRegion p2HeadRegion;
	private Image p1BackImage;
	private Image p2BackImage;
	private Image p2BodyImage;
	private String avatarPng;
	final Logger logger = LoggerFactory.getLogger(GameScreen.class);

	SettingsScreen(final MixMaze game) {
		this.game = game;
		startDebug();
		initialize();
		createAvatarStack(p1Avatarstack, p1BackImage, p1BodyImage, p1HeadImage);
		createAvatarStack(p2Avatarstack, p2BackImage, p2BodyImage, p2HeadImage);
		setTableLayout();
		createSettingsPanel();
		createPlayerPanel(playerOnePanel, p1Texts, p1Buttons, p1Avatarstack);
		createPlayerPanel(playerTwoPanel, p2Texts, p2Buttons, p2Avatarstack);
		stage.addActor(rootTable);
		addTextFeildListeners(p1Texts);
		addTextFeildListeners(p2Texts);
		addTextFieldFilter(p1Texts);
		addTextFieldFilter(p2Texts);

		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				getPlayerControlls(p1Texts, p1Controls);
				getPlayerControlls(p2Texts, p2Controls);

				((GameScreen) game.clientScreen).new Settings(p1Controls,
						p2Controls);
				((GameScreen) game.localScreen).new Settings(p1Controls,
						p2Controls);
				game.setScreen(game.localScreen);
			}

		});

		p1Buttons[1].addListener(new ChangeListnr(p1HeadRegion, true));
		p1Buttons[0].addListener(new ChangeListnr(p1HeadRegion, false));
		p2Buttons[1].addListener(new ChangeListnr(p2HeadRegion, true));
		p2Buttons[0].addListener(new ChangeListnr(p2HeadRegion, false));
	}

	/**
	 * adds textFieldFilters to all the TextFields
	 * 
	 * @param playerTexts
	 *            array containing all TextFields
	 */
	private void addTextFieldFilter(TextField[] playerTexts) {
		for (int i = 0; i < playerTexts.length; i++) {
			playerTexts[i].setTextFieldFilter(textFieldFilter);
			;
		}
	}

	/**
	 * adds textFieldListeners to all the TextFields
	 * 
	 * @param playerTexts
	 *            array containing all TextFields
	 */
	private void addTextFeildListeners(TextField[] playerTexts) {
		for (int i = 0; i < playerTexts.length; i++) {
			playerTexts[i].setTextFieldListener(textFieldListener);
		}
	}

	private void getPlayerControlls(TextField[] playerTexts,
			int[] playerControls) {
		for (int i = 0; i < playerControls.length; i++) {
			playerControls[i] = getKeyCode(playerTexts[i].getText());
		}
	}

	/**
	 * Handles the initialization of all the instance parameters
	 */
	private void initialize() {
		this.skin = game.skin;
		this.stage = new Stage();
		playButton = new TextButton("Play", skin);
		playButton.pad(20);
		avatarPng = "avatars.png";
		skin.add("background", new Texture(Gdx.files.internal("settings.png")));
		difficultyList = new List(new String[] { "Beginner", "Intermediate",
				"Advanced" }, skin);

		p1Texts[0] = new TextField("G", skin);
		p1Texts[1] = new TextField("H", skin);
		p1Texts[2] = new TextField("W", skin);
		p1Texts[3] = new TextField("A", skin);
		p1Texts[4] = new TextField("S", skin);
		p1Texts[5] = new TextField("D", skin);

		p2Texts[0] = new TextField("O", skin);
		p2Texts[1] = new TextField("P", skin);
		p2Texts[2] = new TextField("UP", skin);
		p2Texts[3] = new TextField("LEFT", skin);
		p2Texts[4] = new TextField("DOWN", skin);
		p2Texts[5] = new TextField("RIGHT", skin);

		p1Buttons[0] = new TextButton("<", skin);
		p1Buttons[1] = new TextButton(">", skin);

		p2Buttons[0] = new TextButton("<", skin);
		p2Buttons[1] = new TextButton(">", skin);

		p1AvatarTexRegion = new TextureRegion(new Texture(
				Gdx.files.internal("angel.png")), 0, 0, 256, 256);
		p2AvatarTexRegion = new TextureRegion(new Texture(
				Gdx.files.internal("angel.png")), 0, 0, 256, 256);

		p1AvatarImage = new Image(p1AvatarTexRegion);
		p1AvatarImage.setScaling(Scaling.fill);
		p2AvatarImage = new Image(p2AvatarTexRegion);
		p2AvatarImage.setScaling(Scaling.fill);

		p1BackImage = new Image(new TextureRegion(new Texture(
				Gdx.files.internal("grey.png"))));
		p1BodyImage = new Image(new TextureRegion(new Texture(
				Gdx.files.internal("body.png"))));
		p1BodyImage.setColor(1f, 0f, 0f, 1f);
		p1HeadRegion = new TextureRegion(new Texture(
				Gdx.files.internal(avatarPng)), 256, 0, 256, 256);
		GameScreen.p1HeadRegion = p1HeadRegion;
		p1HeadImage = new Image(p1HeadRegion);

		p2BackImage = new Image(new TextureRegion(new Texture(
				Gdx.files.internal("grey.png"))));
		p2BodyImage = new Image(new TextureRegion(new Texture(
				Gdx.files.internal("body.png"))));
		p2BodyImage.setColor(0f, 0f, 1f, 1f);
		p2HeadRegion = new TextureRegion(new Texture(
				Gdx.files.internal(avatarPng)), 0, 0, 256, 256);
		GameScreen.p2HeadRegion = p2HeadRegion;
		p2HeadImage = new Image(p2HeadRegion);

		p1Avatarstack = new Stack();
		p2Avatarstack = new Stack();
	}

	/**
	 * Creates a complete avatar image as a stack, so that it can be inserted in
	 * to the table layout.
	 * 
	 * @param stack
	 *            stack which holds the image of the avatar
	 * @param background
	 *            background image for the avatar
	 * @param body
	 *            image of the body region of the avatar
	 * @param head
	 *            image of the head region of the avatar
	 */
	private void createAvatarStack(Stack stack, Image background, Image body,
			Image head) {
		background.setScaling(Scaling.fill);
		body.setScaling(Scaling.fill);
		head.setScaling(Scaling.fill);

		stack.add(background);
		stack.add(body);
		stack.add(head);
	}

	/**
	 * Creates the player panel in the settings screen
	 * 
	 * @param panel
	 *            table which acts as a column in the settings table
	 * @param playerDetails
	 *            array of textFields belonging to the specific player
	 * @param playerButtons
	 *            array of textButtons belonging to the specific player
	 * @param avatar
	 *            stack containg the completed avatar image
	 */
	private void createPlayerPanel(Table panel, TextField[] playerDetails,
			TextButton[] playerButtons, Stack avatar) {
		panel.row().padBottom(20).padTop(165);
		panel.add(playerButtons[0]);
		panel.add(avatar).prefHeight(150);
		panel.add(playerButtons[1]);
		panel.row();
		panel.add(playerDetails[0]).padBottom(70).expandX().colspan(3)
				.width(40);
		panel.row();
		panel.add(playerDetails[1]).padBottom(55).colspan(3).width(40);
		panel.row();
		panel.add(playerDetails[2]).width(40).padBottom(10).colspan(3);
		panel.row();
		panel.add(playerDetails[3]).width(40).padLeft(42);
		panel.add(playerDetails[4]).width(40);
		panel.add(playerDetails[5]).width(40).padRight(42);
		panel.row();
	}

	/**
	 * Creates the main settings panel in the settings page
	 */
	private void createSettingsPanel() {
		gridSize = new SelectBox(
				new String[] { "5", "6", "7", "8", "9", "10" }, skin);
		settingsPanel.add(gridSize).expandX().padTop(220).padBottom(110);
		settingsPanel.row();
		settingsPanel.add(difficultyList).padBottom(170);
		settingsPanel.row();
		settingsPanel.add(playButton);
	}

	/**
	 * enables all the debug lines in all the tables
	 */
	private void startDebug() {
		rootTable.debug();
		settingsPanel.debug();
		playerOnePanel.debug();
		playerTwoPanel.debug();
	}

	/**
	 * sets the initial table layout
	 */
	private void setTableLayout() {
		float celHeight = Gdx.graphics.getHeight();
		float settingsWidth = (float) (Gdx.graphics.getWidth() / 2.56);
		float textWidth = (float) (Gdx.graphics.getWidth() / 5.12);
		float playerWidth = (float) (Gdx.graphics.getWidth() / 4.8301); // 256
		Drawable background = skin.getDrawable("background");

		rootTable.setFillParent(true);
		rootTable.setBackground(background);
		rootTable.top().left(); // so that cells are added from the top instead
								// of center
		rootTable.add(settingsPanel).width(settingsWidth).height(celHeight)
				.expandX();
		rootTable.add(playerOnePanel).width(playerWidth).height(celHeight)
				.expandX();
		rootTable.add(textPanel).width(textWidth).height(celHeight).expandX();
		rootTable.add(playerTwoPanel).width(playerWidth).height(celHeight)
				.expandX();
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
		ArcadeInputMux.getInstance().removeProcessor(stage);
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
		if (isFocusedOnNavigation()) {
			setNavigationText();
		}
		// Table.drawDebug(stage);
	}

	/**
	 * sets the navigation texts to the corresponding textFields
	 */
	private void setNavigationText() {
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			((TextField) stage.getKeyboardFocus()).setText("UP");
		}
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			((TextField) stage.getKeyboardFocus()).setText("LEFT");
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			((TextField) stage.getKeyboardFocus()).setText("RIGHT");
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			((TextField) stage.getKeyboardFocus()).setText("DOWN");
		}
	}

	@Override
	public void resize(int arg0, int arg1) {
		stage.setViewport(1280, 720, true);
	}

	@Override
	public void resume() {
	}

	@Override
	public void show() {
		ArcadeInputMux.getInstance().addProcessor(stage);
	}

	/**
	 * 
	 * @return true if the cursor is focused on one of the navigation input
	 *         fields false otherwise.
	 */
	private boolean isFocusedOnNavigation() {
		for (int i = 2; i < p1Texts.length; i++) {
			if (stage.getKeyboardFocus() == p1Texts[i]
					|| stage.getKeyboardFocus() == p2Texts[i]) {
				return true;
			}
		}
		return false;
	}

	private class Listener implements TextFieldListener {
		@Override
		public void keyTyped(TextField textField, char key) {
			if (getKeyCode("" + key) != 0) {
				textField.setText(("" + key).toUpperCase());
			}
		}
	}

	/**
	 * finds matching Keys.code for a given Char or arrow keys
	 * 
	 * @param keyText
	 *            String in the textField
	 * @return the key code for the given keyText
	 */
	private int getKeyCode(String keyText) {
		char key = keyText.charAt(0);
		if (keyText.length() > 1) {
			return getKeyCodeforNavArrows(keyText);
		} else {
			if (Character.isDigit(key)) {
				return key - 41;
			} else if (Character.isUpperCase(key)) {
				return (key - 36);
			} else if (Character.isLowerCase(key)) {
				return (key - 68);
			}
		}
		return 0;
	}

	/**
	 * finds matching Keys.code for a arrow keys
	 * 
	 * @param keyText
	 *            String in the textField
	 * @return the key code for the given keyText
	 */
	private int getKeyCodeforNavArrows(String keyText) {
		int code = 0;
		if (keyText.equalsIgnoreCase("up")) {
			code = Keys.UP;
		}
		if (keyText.equalsIgnoreCase("down")) {
			code = Keys.DOWN;
		}
		if (keyText.equalsIgnoreCase("left")) {
			code = Keys.LEFT;
		}
		if (keyText.equalsIgnoreCase("right")) {
			code = Keys.RIGHT;
		}
		return code;
	}

	private class Filter implements TextFieldFilter {
		@Override
		public boolean acceptChar(TextField textField, char key) {
			return (Character.isLetterOrDigit(key) || key == '\t');
		}
	}

	private class ChangeListnr extends ClickListener {
		private TextureRegion avatar;
		private boolean isNext;

		@Override
		public void clicked(InputEvent event, float x, float y) {
			if (isNext) {
				if (avatar.getRegionX() < 1281) {
					avatar.setRegion(avatar.getRegionX() + 256, 0, 256, 256);
				} else {
					avatar.setRegion(0, 0, 256, 256);
				}
			}
			if (!isNext) {
				if (avatar.getRegionX() > 255) {
					avatar.setRegion(avatar.getRegionX() - 256, 0, 256, 256);
				} else {
					avatar.setRegion(1280, 0, 256, 256);
				}
			}
		}

		public ChangeListnr(TextureRegion avatar, boolean isnext) {
			this.avatar = avatar;
			this.isNext = isnext;
		}
	}
}
