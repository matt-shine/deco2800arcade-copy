package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import deco2800.arcade.client.ArcadeInputMux;

public class RegisterScreen implements Screen {

    private class RegisterScreenStage extends Stage {}

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

        Label errorLabel = new Label("Placeholder Error Message", skin, "error");
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
        TextButton cancelButton = new TextButton("Cancel", skin, "alt");

        table.add(errorLabel).colspan(2);
        table.row();
        table.add(usernameText).width(400).pad(5).colspan(2);
        table.row();
        table.add(passwordText).width(400).pad(5).colspan(2);
        table.row();
        table.add(passwordTextCheck).width(400).pad(5).colspan(2);
        table.row();
        table.add(registerButton).width(200).pad(5);
        table.add(cancelButton).width(200).pad(5);

        registerButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
            }
        });
        cancelButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }

    @Override
    public void show() {
    	ArcadeInputMux.getInstance().addProcessor(stage);
    }

    @Override
    public void render(float arg0) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
