package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;
import deco2800.arcade.model.Game;

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

        Label errorLabel = new Label("Placeholder Error Message", skin, "error");
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
        TextButton storeButton = new TextButton("Store", skin);

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
        table.add(storeButton).pad(5);
        
        loginButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                ArcadeSystem.login(usernameText.getText());
            }
        });
        
        registerButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
            }
        });
        
        forgotLogButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
            }
        });
            	
        storeButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	ArcadeSystem.login("store");
            	// Please find a way to fix this. I'm so tired. -Addison(GameHost)
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

	    if (ArcadeSystem.isLoggedIn()) {
	    	this.dispose();
	    	ArcadeSystem.goToGame("arcadeui");
	    }
	}

	@Override
	public void dispose() {
        ArcadeInputMux.getInstance().removeProcessor(stage);
        stage.dispose();
        skin.dispose();
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
