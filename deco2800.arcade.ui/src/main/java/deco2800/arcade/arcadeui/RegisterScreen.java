package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

public class RegisterScreen implements Screen {

	private class RegisterScreenStage extends Stage {
	}

	private Skin skin;
	private RegisterScreenStage stage;
	private ArcadeUI arcadeUI;

	public RegisterScreen(ArcadeUI ui) {
		arcadeUI = ui;

		skin = new Skin(Gdx.files.internal("loginSkin.json"));
		skin.add("background", new Texture("homescreen_bg.png"));

		stage = new RegisterScreenStage();

		Table table = new Table();
		table.setFillParent(true);
		table.setBackground(skin.getDrawable("background"));
		stage.addActor(table);

		final Label errorLabel = new Label("Passwords are invalid", skin,
				"error");
		errorLabel.setVisible(false);
		final TextField usernameText = new TextField("", skin);
		usernameText.setMessageText("Enter Username");
		final TextField passwordText = new TextField("", skin);
		passwordText.setMessageText("Enter Password");
		passwordText.setPasswordMode(true);
		passwordText.setPasswordCharacter('*');
		final TextField passwordTextCheck = new TextField("", skin);
		passwordTextCheck.setMessageText("Re-enter Password");
		passwordTextCheck.setPasswordMode(true);
		passwordTextCheck.setPasswordCharacter('*');
		TextButton registerButton = new TextButton("Register", skin);
		TextButton cancelButton = new TextButton("Cancel", skin, "default-red");

		table.add(errorLabel).colspan(2);
		table.row();
		table.add(usernameText).width(400).pad(5).colspan(2);
		table.row();
		table.add(passwordText).width(400).pad(5).colspan(2);
		table.row();
		table.add(passwordTextCheck).width(400).pad(5).colspan(2);
		table.row();
		table.add(registerButton).width(190).height(50).pad(5);
		table.add(cancelButton).width(190).height(50).pad(5);

		registerButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				if (!passwordText.getText().equals(passwordTextCheck.getText())
						|| passwordText.getText().equals("")) {
					errorLabel.setVisible(true);
				} else { // sends register user request to server
					ArcadeSystem.registerUser(usernameText.getText(),
							passwordText.getText());
				}
			}
		});
		cancelButton.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				arcadeUI.setScreen(arcadeUI.login);
			}
		});
	}

	@Override
	public void render(float arg0) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		ArcadeInputMux.getInstance().addProcessor(stage);
	}

	@Override
	public void hide() {
		ArcadeInputMux.getInstance().removeProcessor(stage);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
}