package deco2800.arcade.arcadeui;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

/**
 * 
 * @author Carl Beaverson
 * 
 */

public class BettingWindow implements Screen {

	private OrthographicCamera camera;
	private Stage stage;
	private Skin skin;
	private Skin skin2;
	private ShapeRenderer shapeRenderer;
	private ArcadeUI arcadeUI;

	public BettingWindow(ArcadeUI ui) {
		arcadeUI = ui;
	}

	public void show() {
		shapeRenderer = new ShapeRenderer();
		stage = new Stage();
		ArcadeInputMux.getInstance().addProcessor(stage);

		/* Add player to servers list of lobby users */
		ArcadeSystem.addPlayerToLobby();

		// Gui button & label Styles
		skin = new Skin();
		final Skin skin = new Skin(Gdx.files.internal("loginSkin.json"));
		skin.add("background", new Texture("homescreen_bg.png"));

		Label label2 = new Label("Please place your bet:", skin);

		skin2 = new Skin();

		final Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
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

		// Create buttons, labels and tables for layout purposes
		// Changed open games

		TextButton button2 = new TextButton("Return to Lobby", skin);

		TextButton button3 = new TextButton("Place Bet", skin);

		final TextField betfield = new TextField("", skin);

		final Label invalidLabel = new Label("Bet not valid", skin);
		invalidLabel.setVisible(false);

		final Table table = new Table();
		final Table table2 = new Table();
		final Table table3 = new Table();

		final Table bettable = new Table();

		table.setFillParent(true);
		table2.setFillParent(true);
		table3.setFillParent(true);
		bettable.setFillParent(true);

		table3.setBackground(skin.getDrawable("background"));

		// Add tables to stage.
		stage.addActor(table3);
		stage.addActor(bettable);
		stage.addActor(table2);
		stage.addActor(table);

		// Add tables and set position.

		table3.add(betfield).width(200).height(35).padLeft(0).padTop(140);

		table3.add(button3).width(100).height(35).padLeft(3).padTop(140);

		bettable.add(invalidLabel).padTop(250).padLeft(0);

		table.add(button2).width(300).height(40).padLeft(0).padTop(600);

		// "Return to Menu" Button Event Listener
		button2.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				dispose();
				ArcadeSystem.setPlayerBetting(false);
				ArcadeSystem.setMultiplayerEnabled(true);
				arcadeUI.setScreen(arcadeUI.getLobby());

			}
		});

		button3.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				int bet = -1;
				String input = betfield.getText();
				try {
					bet = Integer.parseInt(input);
				} catch (Exception e) {
					invalidLabel.setVisible(true);
					return;
				}

				if ((bet < 1000) && (bet > 0)) {
					System.out.println("Bet Placed: " + betfield.getText());
					dispose();
					ArcadeSystem.setPlayerBetting(false);
					ArcadeSystem.setMultiplayerEnabled(true);
					arcadeUI.setScreen(arcadeUI.getLobby());
				} else {
					System.out.println("Bet not valid");
				}

			}
		});

		camera = new OrthographicCamera();
		camera.setToOrtho(true, 640, 360);

		// Gdx.app.exit();
	}

	public void refreshGames() {

	}

	@Override
	public void render(float arg0) {

		camera.update();
		shapeRenderer.setProjectionMatrix(camera.combined);
		Gdx.gl.glClearColor(0.2f, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

	}

	@Override
	public void dispose() {
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