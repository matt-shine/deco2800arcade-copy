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

public class LoginScreen implements Screen {
	
	private Skin skin;
    private Skin skin2;
    private Stage stage;

	public LoginScreen() {
        // skin is the skin loaded from loginSkin.json
        // skin2 is for the skin created programatically
        // skin2 will eventually disappear
        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        skin2 = new Skin();
        
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin2.add("white", new Texture(pixmap));
        
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin2.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin2.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin2.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin2.add("default", textButtonStyle);

        skin2.add("background", new Texture("homescreen_bg.png"));

        stage = new Stage();
        ArcadeInputMux.getInstance().addProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(skin2.getDrawable("background"));
        stage.addActor(table);

        Label usernameLabel = new Label("Username:", skin);
        final TextField usernameText = new TextField("", skin);
        usernameText.setMessageText("Enter Username");
        Label passwordLabel = new Label("Password:", skin);
        final TextField passwordText = new TextField("", skin);
        passwordText.setMessageText("Enter Password");
        passwordText.setPasswordMode(true);
        passwordText.setPasswordCharacter('*');
        Label errorLabel = new Label("login/server errors go here", skin, "error");
        TextButton loginButton = new TextButton("Login", skin2);
        TextButton exitButton = new TextButton("Exit", skin2);

        //table.debug();  // Shows table debug lines.  Remove for final product.
        usernameLabel.setAlignment(Align.right);
        table.add(usernameLabel).width(150).padBottom(5).padTop(5).padLeft(10).padRight(10);
        table.add(usernameText).width(150).padBottom(5).padTop(5).padLeft(10).padRight(10);
        table.row();
        passwordLabel.setAlignment(Align.right);
        table.add(passwordLabel).width(150).padBottom(5).padTop(5).padLeft(10).padRight(10);
        table.add(passwordText).width(150).padBottom(5).padTop(5).padLeft(10).padRight(10);
        table.row();
        table.add(errorLabel).width(150).padBottom(5).padTop(5).padLeft(10).padRight(10);
        table.row();
        table.add(loginButton).width(100).pad(10);
        table.add(exitButton).width(100).pad(10);
        
        loginButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                ArcadeSystem.login(usernameText.getText());
            }
        });
        
        exitButton.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                ArcadeSystem.close();
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
	    	dispose(); // <-- Sorry to screw with your code, but this needs to be done.
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
