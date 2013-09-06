package deco2800.arcade.arcadeui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Uses table layout
 * @author s4266321
 *
 */
public class Sidebar extends Group {

	private Skin skin = null;
	private Overlay overlay;
    private NinePatch texture;
    private static float INNER_POS = -180;
    private static float OUTER_POS = 0;
    private float pos = OUTER_POS;
    private float vel = 0;
    private boolean isUIOpen = true;
    private boolean hasTabPressedLast = false;
    private OverlayWindow window;
    
    public Sidebar(Overlay overlay, OverlayWindow window) {
		
		this.overlay = overlay;
		this.window = window;
		texture = new NinePatch(new Texture(Gdx.files.internal("sidebarbg.png")), 30, 30, 30, 30);
		
        skin = new Skin();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        skin.add("default", new BitmapFont());
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);
        
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.WHITE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        
        
        this.setBounds(0, 0, 1280, 720);
        
        SidebarAvatar avatar = new SidebarAvatar(overlay);
        avatar.setPosition(40, 550);
    	this.addActor(avatar);
    	
    	int numItems = 5;
    	for (int i = 0; i < 5; i++) {
    		SidebarMenuItem item = new SidebarMenuItem(skin);
        	item.setText("Option " + i);
        	item.setPosition(50, (numItems - i) * 60 + 50);
        	item.setSize(120, 40);
        	final int buttonNum = i;
            
        	item.addListener(new EventListener() {
        		
				@Override
				public boolean handle(Event e) { 
					if (buttonNum == 0 && e.toString() == "touchDown") {
						addAchievemntsWindow();
					}
					return true;
				}
        		
        	});
        	
        	this.addActor(item);
        	
    	}
    	
    	
    }

	
	@Override
	public void act(float d) {
		
		//toggles isUIOpen on tab key down
		if (Gdx.input.isKeyPressed(Keys.SPACE) != hasTabPressedLast && (hasTabPressedLast = !hasTabPressedLast)) {
			isUIOpen = !isUIOpen;
		}
		
		if (isUIOpen) {
			vel += 1;
		}
		if (!isUIOpen) {
			vel -= 1;
		}
		
		pos += vel;
		if (pos > OUTER_POS) {
			pos = OUTER_POS;
			vel = 0;
		}
		if (pos < INNER_POS) {
			pos = INNER_POS;
			vel = 0;
		}

		
		this.setX(pos);
		
		super.act(d);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		texture.draw(batch, -30, 0, pos + 260, overlay.getHeight());
		super.draw(batch, parentAlpha);
		
	}
	
	
	public void resize(int x, int y) {

	}
	
	
	public void addAchievemntsWindow() {
		window.setContent(new AchievementList(overlay, skin));
	}
	
	
}
