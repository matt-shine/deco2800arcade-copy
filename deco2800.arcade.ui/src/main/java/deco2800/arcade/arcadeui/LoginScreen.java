package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.esotericsoftware.tablelayout.BaseTableLayout;
import deco2800.arcade.client.ArcadeInputMux;
import deco2800.arcade.client.ArcadeSystem;

public class LoginScreen implements Screen {
	
	private class LoginScreenStage extends Stage {}
	
	public LoginScreen() {

	
		// Move skin stuff to an overall class

        //skin = new Skin(Gdx.files.internal("loginSkin.json"));


        skin = new Skin();

        // skin is the skin loaded from loginSkin.json
        // skin2 is for the skin created programatically
        // skin2 will eventually disappear
        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        skin2 = new Skin();
        
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
		
        skin.add("white", new Texture(pixmap));
        skin2.add("white", new Texture(pixmap));
        
        skin.add("default", new BitmapFont());
        
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);
	private Skin skin;
    private LoginScreenStage stage;
    private ArcadeUI arcadeUI;

	public LoginScreen(ArcadeUI ui) {
        arcadeUI = ui;

        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        skin.add("background", new Texture("homescreen_bg.png"));

        stage = new LoginScreenStage();
        
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("background"));
        stage.addActor(table);

        final Label tempLabel = new Label("To access the store\nlogin with username: store\nTo access homepage\nlogin with username:home\nTo access the games list\nlogin with any username as normal", skin);  // Temporary label to display a message
        tempLabel.setAlignment(Align.center);
        final Label errorLabel = new Label("", skin, "error");
        errorLabel.setAlignment(Align.center);
        final TextField usernameText = new TextField("", skin);
        usernameText.setMessageText("Username");
        final TextField passwordText = new TextField("", skin);
        passwordText.setMessageText("Password");
        passwordText.setPasswordMode(true);
        passwordText.setPasswordCharacter('*');
        TextButton loginButton = new TextButton("Login", skin2);
        TextButton exitButton = new TextButton("Exit", skin2);

        table.add(usernameLabel);
        table.add(usernameText).width(100);
        
        table.row();
        table.add(passwordLabel);
        table.add(passwordText).width(100);
        final TextField serverText = new TextField("", skin);
        serverText.setMessageText("Server") ;
        CheckBox rememberBox = new CheckBox("Remember Me", skin);
        rememberBox.getCells().get(0).size(25, 25);
        rememberBox.getCells().get(0).pad(5);
        rememberBox.getCells().get(1).pad(2);
        TextButton loginButton = new TextButton("Login", skin);
        TextButton registerButton = new TextButton("Register", skin, "default-blue");
        TextButton forgotLogButton = new TextButton("Forgot Login?", skin, "default-red");

        table.add(tempLabel).colspan(2);  // Temporary label to display a message
        table.row();
        table.add(errorLabel).width(400).pad(5).colspan(2);
        table.row();
        table.add(usernameText).width(400).pad(5).colspan(2);
        table.row();
        table.add(passwordText).width(400).pad(5).colspan(2);
        table.row();
        table.add(serverText).width(400).pad(5).colspan(2);
        table.row();
        table.add(rememberBox).width(190).height(25).pad(5).colspan(2).align(BaseTableLayout.LEFT);
        table.row();
        table.add(loginButton).width(190).height(50).pad(5);
        table.add(registerButton).width(190).height(50).pad(5);
        table.row();
        table.add(forgotLogButton).width(400).height(35).pad(5).colspan(2);
        
        loginButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                if (usernameText.getText().equals("")) {
                    // no username entered, throw error
                    errorLabel.setText("No Username Supplied");
                }
                else if (usernameText.getText().toLowerCase().equals("store")) {
                    arcadeUI.setScreen(arcadeUI.store);
                }
                else if (usernameText.getText().toLowerCase().equals("home")) {
                	arcadeUI.setScreen(arcadeUI.main);
                }
                else {
                    ArcadeSystem.login(usernameText.getText());
                    arcadeUI.setScreen(arcadeUI.home);
                }
            }
        });
        
        registerButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                arcadeUI.setScreen(arcadeUI.register);
            }
        });
        
        forgotLogButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
            }
        });
	}


	@Override
	public void show() {
	}
	

	public void firstResize() {
/*
		ArcadeSystem.openConnection();
*/		
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
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
