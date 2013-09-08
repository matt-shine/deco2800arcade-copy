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

public class LoginScreen implements Screen {
	
	private Skin skin;
    private Stage stage;

	public LoginScreen() {
        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        skin.add("background", new Texture("homescreen_bg.png"));

        stage = new Stage();
        ArcadeInputMux.getInstance().addProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("background"));
        stage.addActor(table);

        Label errorLabel = new Label("login/server errors go here", skin, "error");
        final TextField usernameText = new TextField("", skin);
        usernameText.setMessageText("Username");
        final TextField passwordText = new TextField("", skin);
        passwordText.setMessageText("Password");
        passwordText.setPasswordMode(true);
        passwordText.setPasswordCharacter('*');
        final TextField serverText = new TextField("", skin);
        serverText.setMessageText("Server") ;
        CheckBox rememberBox = new CheckBox("Remember Me", skin);
        TextButton loginButton = new TextButton("Login", skin);
        TextButton forgotLogButton = new TextButton("Forgot Login?", skin);
        TextButton registerButton = new TextButton("Register", skin);

        //table.debug();  // Shows table debug lines.  Remove for final product.
        table.add(errorLabel).colspan(2);
        table.row();
        table.add(usernameText).width(400).pad(5).colspan(2);
        table.row();
        table.add(passwordText).width(400).pad(5).colspan(2);
        table.row();
        table.add(serverText).width(400).pad(5).colspan(2);
        table.row();
        table.add(rememberBox);
        table.row();
        table.add(loginButton).width(200).pad(5);
        table.add(registerButton).width(200).pad(5);
        table.row();
        table.add(forgotLogButton).pad(5);

        loginButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                ArcadeSystem.login(usernameText.getText());
            }
        });
	}


	@Override
	public void show() {
	}
	

	@Override
	public void render(float arg0) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        //Table.drawDebug(stage);  // Shows table debug lines.  Remove for final product.

	    if (ArcadeSystem.isLoggedIn()) {
	    	dispose();
	    	ArcadeSystem.goToGame("arcadeui");
	    }
	}

	@Override
	public void dispose() {
        stage.dispose();
        skin.dispose();
        
        ArcadeInputMux.getInstance().removeProcessor(stage);
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void resize(int arg0, int arg1) {
	}
}
