/*
 * MenuScreen
 */
package deco2800.arcade.mixmaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import static com.badlogic.gdx.Input.*;
import static com.badlogic.gdx.graphics.Color.*;
import static com.badlogic.gdx.graphics.GL20.*;

final class MenuScreen implements Screen {
	private static final String LOG = MenuScreen.class.getSimpleName();

	private final MixMaze game;
	private final Stage stage;

	/**
	 * This constructor associate MenuScreen with MixMaze.
	 */
	MenuScreen(final MixMaze game) {
		this.game = game;

		stage = new Stage();
		Table rootTable = new Table();
		rootTable.setFillParent(true);
		rootTable.debug();

		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

		Label nameLabel = new Label("Name:", skin);
		TextField nameText = new TextField("name text", skin);
		Label addressLabel = new Label("Address:", skin);
		TextField addressText = new TextField("address text", skin);

		rootTable.add(nameLabel).uniform();
		rootTable.add(nameText).uniform();
		rootTable.row();
		rootTable.add(addressLabel);
		rootTable.add(addressText).space(10);

		stage.addActor(rootTable);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0.2f, 1);
		Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			// Start a game from the user's view.
			Gdx.app.debug(LOG, "switching to game screen");
			game.setScreen(game.gameScreen);
		}

		stage.draw();
		Table.drawDebug(stage);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void show() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
