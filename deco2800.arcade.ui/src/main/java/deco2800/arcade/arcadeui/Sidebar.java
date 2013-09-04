package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Uses table layout
 * @author s4266321
 *
 */
public class Sidebar extends Group {

	private Table table = new Table();
	private Skin skin = null;
	private Overlay overlay;
    private NinePatch texture;
    private float pos = 0;
    private float vel = 0;
    private static float INNER_POS = -80;
    private static float OUTER_POS = 100;
    private boolean isUIOpen = false;
    private boolean hasTabPressedLast = false;
    OverlayWindow window;
    
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
        
        

    	table.setFillParent(true);
    	this.addActor(table);
    	table.row();
    	table.add(new SidebarAvatar(overlay)).top();
    	
    	for (int i = 0; i < 6; i++) {
    		SidebarMenuItem item = new SidebarMenuItem(skin);
        	item.setText("Option " + i);
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
        	table.row();
        	table.add(item).top().left().pad(20, 20, 0, 0);
    	}
    	
    	table.setFillParent(true);
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
		if (vel != 0) {
			table.layout();
		}

		
		table.setX(pos);
		
		super.act(d);
		table.act(d);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		texture.draw(batch, 0, -50, pos + 150, overlay.getHeight() + 50);
		table.debug();
		table.debugTable();
		table.draw(batch, parentAlpha);
		
	}
	
	
	public void resize(int x, int y) {
		table.setX(100);
		table.setY(y - 300);
		
	}
	
	
	public void addAchievemntsWindow() {
		window.setContent(new AchievementList(overlay));
	}
	
	
}
