package deco2800.arcade.wl6;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class IngameUI extends Stage {

    private static ShapeRenderer r = new ShapeRenderer();
    private Label text = null;
    
    
    public static void drawCeiling(int color, float w, float h) {

        r.begin(ShapeRenderer.ShapeType.FilledRectangle);
        Color c = new Color();
        Color.rgb888ToColor(c, color);
        r.setColor(c);
        r.filledRect(0, h / 2, w, h / 2);
        r.end();
        
    }
    
    
    public IngameUI() {
    	Skin s = new Skin();
    	s.add("default", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        text = new Label("test", s, "default");
        this.addActor(text);
    }
    
    
    public void draw(GameModel game) {
    	super.draw();
    	
    	int health = game.getPlayer().getHealth();
    	int ammo = game.getPlayer().getAmmo();
    	int gun = game.getPlayer().getCurrentGun();
    	int points = game.getPlayer().getPoints();
    	
    	text.setText("Health: " + health + " Ammo: " + ammo + " Gun: " + gun + " Points: " + points);
    	
    }



}
