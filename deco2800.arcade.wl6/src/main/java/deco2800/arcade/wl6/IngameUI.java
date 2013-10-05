package deco2800.arcade.wl6;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;

public class IngameUI extends Group {

    private static ShapeRenderer r = new ShapeRenderer();

    public static void drawCeiling(int color, float w, float h) {

        r.begin(ShapeRenderer.ShapeType.FilledRectangle);
        Color c = new Color();
        Color.rgb888ToColor(c, color);
        r.setColor(c);
        r.filledRect(0, h / 2, w, h / 2);
        r.end();


    }



}
