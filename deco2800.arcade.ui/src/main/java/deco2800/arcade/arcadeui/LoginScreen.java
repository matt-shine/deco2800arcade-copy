package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.ArcadeInputMux;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
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

        // Specify font, fontColor, cursor, selection, and background
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("FreeSans_16");
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.cursor = skin2.newDrawable("white", Color.WHITE);
        textFieldStyle.selection = skin2.newDrawable("white", Color.WHITE);
        //textFieldStyle.background = ;
        skin2.add("default", textFieldStyle);
        
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin2.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin2.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin2.newDrawable("white", Color.WHITE);
        textButtonStyle.over = skin2.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("FreeSans_16");
        skin2.add("default", textButtonStyle);

        stage = new Stage();
        ArcadeInputMux.getInstance().addProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label usernameLabel = new Label("Username:", skin);
        final TextField usernameText = new TextField("", skin2);
        usernameText.setMessageText("Enter Username");
        Label passwordLabel = new Label("Password:", skin);
        final TextField passwordText = new TextField("", skin2);
        passwordText.setMessageText("Enter Password");
        passwordText.setPasswordMode(true);
        passwordText.setPasswordCharacter('*');
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
		// TODO Auto-generated method stub
		
	}
	
	
}
