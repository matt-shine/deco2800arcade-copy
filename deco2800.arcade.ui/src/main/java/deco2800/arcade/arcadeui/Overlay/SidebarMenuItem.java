package deco2800.arcade.arcadeui.Overlay;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * The sidebar contains a list of these
 * @author s4266321
 *
 */
public class SidebarMenuItem extends TextButton {

    public SidebarMenuItem(Skin skin, String styleName) {
        super("", skin, styleName);

    }

    public SidebarMenuItem(Skin skin) {
        this(skin, "default");
    }

}
