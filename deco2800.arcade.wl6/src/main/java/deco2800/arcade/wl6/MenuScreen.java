package deco2800.arcade.wl6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import deco2800.arcade.client.ArcadeInputMux;

public class MenuScreen implements Screen {

    private class MenuScreenStage extends Stage {}

    private WL6 wl6;
    @SuppressWarnings("unused")
	private GameModel model;
    private Skin skin;
    private MenuScreenStage stage;

    private static final String[] exitStrings = new String[] {
            "Dost thou wish to\nleave with such hasty\nabandon?",
            "Chickening out...\nalready?",
            "Press N for more carnage.\nPress Y to be a weenie.",
            "So, you think you can\nquit this easily, huh?",
            "Press N to save the world.\nPress Y to abandon it in\nits hour of need.",
            "Press N if you are brave.\nPress Y to cower in shame.",
            "Heroes, press N.\n Wimps, press Y.",
            "You are at an intersection.\n A sign says, 'Press Y to quit.'\n>",
            "For guns and glory, press N.\nFor work and worry, press Y."
    };
    private static final String[] difficultyStrings = new String[] {
            "Can I play, Daddy?",
            "Don't hurt me.",
            "Bring 'em on!",
            "I am Death incarnate!"
    };
    private static final String[] episodeStrings = new String[] {
            "Episode 1: Escape from Wolfenstein",
            "Episode 2: Operation: Eisenfaust",
            "Episode 3: Die, Fuhrer, Die!",
            "Episode 4: A Dark Secret",
            "Episode 5: Trail of the Madman",
            "Episode 6: Confrontation",
    };
    private static final String[] levelStrings = new String[] {
	    	"Level 1",
	    	"Level 2",
	    	"Level 3",
	    	"Level 4",
	    	"Level 5",
	    	"Level 6",
	    	"Level 7",
	    	"Level 8",
	    	"Boss",
    };

    public MenuScreen(WL6 game) {
        wl6 = game;
        model = new GameModel();
        stage = new MenuScreenStage();
        skin = new Skin(Gdx.files.internal("menuSkin.json"));
        skin.add("background", new Texture("wolf_background.png"));

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("background"));
        stage.addActor(table);

        TextButton button = new TextButton("New Game", skin);
        SelectBox difficulty = new SelectBox(difficultyStrings, skin);
        SelectBox episode = new SelectBox(episodeStrings,skin);
        SelectBox level = new SelectBox(levelStrings, skin);

        table.add(difficulty);
        table.row();
        table.add(episode);
        table.add(level);
        table.row();
        table.add(button);

        button.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                wl6.goToGame();
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
