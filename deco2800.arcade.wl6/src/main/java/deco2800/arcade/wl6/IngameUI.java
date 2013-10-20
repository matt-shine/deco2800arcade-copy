package deco2800.arcade.wl6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import deco2800.arcade.wl6.WL6Meta.KEY_TYPE;

public class IngameUI extends Stage {

    private static ShapeRenderer r = new ShapeRenderer();
    private Skin skin;
    private Table bottom;
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
        setupUI();
    }

    private void setupUI() {

        Label dffL = new Label("Difficulty", skin, "small");
        Label lvlL = new Label("Level", skin, "small");
        Label scoreL = new Label("Score", skin, "small");
        Label healthL = new Label("Health", skin, "small");
        Label ammoL = new Label("Ammo", skin, "small");
        Label keysL = new Label("Keys", skin, "small");
        Label gunL = new Label("Gun", skin, "small");
        difficulty = new Label("", skin, "small");
        lvl = new Label("null", skin, "small");
        score = new Label("null", skin, "small");
        health = new Label("null", skin, "small");
        ammo = new Label("null", skin, "small");
        keys = new Label("null", skin, "small");
        gun = new Label("null", skin, "small");

        bottom = new Table();
        bottom.setPosition(640, 50);

        bottom.add(dffL).width(150);
        bottom.add(lvlL).width(150);
        bottom.add(scoreL).width(300);
        bottom.add(healthL).width(150);
        bottom.add(ammoL).width(150);
        bottom.add(keysL).width(80);
        bottom.add(gunL).width(300);
        bottom.row();
        bottom.add(difficulty).width(150);
        bottom.add(lvl).width(105);
        bottom.add(score).width(300);
        bottom.add(health).width(150);
        bottom.add(ammo).width(150);
        bottom.add(keys).width(80);
        bottom.add(gun).width(300);


        this.addActor(bottom);
    }
    
    
    public void draw(GameModel game) {
    	super.draw();

        difficulty.setText("" + game.getDifficulty());
        lvl.setText(game.getLevelInChapter());
        score.setText("" + game.getPlayer().getPoints());
        this.health.setText("" + game.getPlayer().getHealth() + "%");
        this.ammo.setText("" + game.getPlayer().getAmmo());
        keys.setText("" + (game.getPlayer().hasKey(KEY_TYPE.GOLD) ? "Gold " : "") + (game.getPlayer().hasKey(KEY_TYPE.SILVER) ? "Silver " : ""));
        this.gun.setText("" + game.getPlayer().getCurrentGun());
    }



}
