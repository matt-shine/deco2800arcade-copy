package deco2800.arcade.wl6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import deco2800.arcade.client.ArcadeInputMux;

public class SplashScreen implements Screen {

    private class SplashScreenStage extends Stage {}

    private WL6 wl6;
    private Skin skin;
    private SplashScreenStage stage;

    public SplashScreen(WL6 game) {
        wl6 = game;
        stage = new SplashScreenStage();
        skin = new Skin();
        skin.add("background", new Texture("splash_background.png"));
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);
        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("background"));
        stage.addActor(table);
        final Label label = new Label("Press space to continue", skin);
        table.add(label);
    }

    @Override
    public void render(float arg0) {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            wl6.setScreen(wl6.menuScreen);
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        Gdx.input.setCursorCatched(false);
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
