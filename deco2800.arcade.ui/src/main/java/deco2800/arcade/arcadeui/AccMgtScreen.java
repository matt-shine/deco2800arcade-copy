package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class AccMgtScreen implements Screen {

    private Skin skin;
    private Stage stage;

    public AccMgtScreen() {

        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        stage = new Stage();
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float arg0) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
    }
}
