package deco2800.arcade.wolf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import com.badlogic.gdx.scenes.scene2d.utils.Align;
import deco2800.arcade.wolf.WL6Meta.KEY_TYPE;

public class IngameUI extends Stage {

    private static ShapeRenderer r = new ShapeRenderer();
    private Skin skin;
    private Table bottom;
    private Label epi;
    private Label lvl;
    private Label score;
    private Label difficulty;
    private Label health;
    private Label ammo;
    private Label gun;
    private Label keys;
    
    public static void drawCeiling(int color, float w, float h) {

        r.begin(ShapeRenderer.ShapeType.FilledRectangle);
        Color c = new Color();
        Color.rgb888ToColor(c, color);
        r.setColor(c);
        r.filledRect(0, h / 2, w, h / 2);
        r.end();
        
    }
    
    
    public IngameUI() {
        skin = new Skin(Gdx.files.internal("wl6Skin.json"));
        skin.add("background", new Texture("wolfUI.png"));
        setupUI();
    }

    private void setupUI() {

        Label epiL = new Label("Episode", skin, "small");
        epiL.setAlignment(Align.center);
        Label lvlL = new Label("Level", skin, "small");
        lvlL.setAlignment(Align.center);
        Label dffL = new Label("Difficulty", skin, "small");
        dffL.setAlignment(Align.center);
        Label scoreL = new Label("Score", skin, "small");
        scoreL.setAlignment(Align.center);
        Label healthL = new Label("Health", skin, "small");
        healthL.setAlignment(Align.center);
        Label ammoL = new Label("Ammo", skin, "small");
        ammoL.setAlignment(Align.center);
        Label keysL = new Label("Keys", skin, "small");
        keysL.setAlignment(Align.center);
        Label gunL = new Label("Gun", skin, "small");
        gunL.setAlignment(Align.center);
        epi = new Label("null", skin, "small");
        epi.setAlignment(Align.center);
        lvl = new Label("null", skin, "small");
        lvl.setAlignment(Align.center);
        difficulty = new Label("", skin, "small");
        difficulty.setAlignment(Align.center);
        score = new Label("null", skin, "small");
        score.setAlignment(Align.center);
        health = new Label("null", skin, "small");
        health.setAlignment(Align.center);
        ammo = new Label("null", skin, "small");
        ammo.setAlignment(Align.center);
        keys = new Label("null", skin, "small");
        keys.setAlignment(Align.center);
        gun = new Label("null", skin, "small");
        gun.setAlignment(Align.center);

        bottom = new Table();
        bottom.setPosition(640, 50);
        bottom.setBackground(skin.getDrawable("background"));
        this.addActor(bottom);

        bottom.add(epiL).width(134);
        bottom.add(lvlL).width(134);
        bottom.add(dffL).width(134);
        bottom.add(scoreL).width(268);
        bottom.add(healthL).width(134);
        bottom.add(ammoL).width(134);
        bottom.add(keysL).width(74);
        bottom.add(gunL).width(268);
        bottom.row();
        bottom.add(epi).width(134);
        bottom.add(lvl).width(134);
        bottom.add(difficulty).width(134);
        bottom.add(score).width(268);
        bottom.add(health).width(134);
        bottom.add(ammo).width(134);
        bottom.add(keys).width(74);
        bottom.add(gun).width(268);
    }
    
    
    public void draw(GameModel game) {
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.draw();

        epi.setText(game.getChapter());
        lvl.setText(game.getLevelInChapter());
        difficulty.setText("" + game.getDifficulty());
        score.setText("" + game.getPlayer().getPoints());
        health.setText("" + game.getPlayer().getHealth() + "%");
        ammo.setText("" + game.getPlayer().getAmmo());
        keys.setText("" + (game.getPlayer().hasKey(KEY_TYPE.GOLD) ? "Gold " : "") + (game.getPlayer().hasKey(KEY_TYPE.SILVER) ? "Silver " : ""));
        gun.setText("" + game.getPlayer().getCurrentGun());
    }



}
