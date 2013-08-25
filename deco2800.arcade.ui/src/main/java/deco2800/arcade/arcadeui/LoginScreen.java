package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import deco2800.arcade.client.ArcadeSystem;

public class LoginScreen implements Screen {
	
	
    private Skin skin;
    private Stage stage;
	
	
	
	public LoginScreen() {
        //skin = new Skin(Gdx.files.internal("loginSkin.json"));

        // Move skin stuff to an overall class
        skin = new Skin();
        
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        
        skin.add("default", new BitmapFont());
        
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);

        // Specify font, fontColor, cursor, selection, and background
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.WHITE;
        //textFieldStyle.cursor = ;
        //textFieldStyle.selection = ;
        //textFieldStyle.background = ;
        skin.add("default", textFieldStyle);
        
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label usernameLabel = new Label("Username:", skin);
        final TextField usernameText = new TextField("", skin);
        usernameText.setMessageText("Enter Username");
        Label passwordLabel = new Label("Password:", skin);
        final TextField passwordText = new TextField("", skin);
        passwordText.setMessageText("Enter Password");
        TextButton loginButton = new TextButton("Login", skin);
        TextButton exitButton = new TextButton("Exit", skin);

        table.debug();
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
        Table.drawDebug(stage);

	    if (ArcadeSystem.isLoggedIn()) {
	    	ArcadeSystem.goToGame("arcadeui");
	    }
	}

	@Override
	public void dispose() {
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
		// TODO Auto-generated method stub
		
	}
	
	
}
