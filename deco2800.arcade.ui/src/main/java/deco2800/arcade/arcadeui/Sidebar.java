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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import deco2800.arcade.client.ArcadeSystem;

/**
 * Uses table layout
 * @author s4266321
 *
 */
public class Sidebar extends Group {

	private Skin skin = null;
	private Overlay overlay;
    private NinePatch texture;
    private static float WIDTH = 160;
    private static float INNER_POS = -WIDTH;
    private static float OUTER_POS = 0;
    private float pos = INNER_POS;
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
        
        generateButtons();
    	
    }

	
    private void generateButtons() {
    	
    	for (int i = this.getChildren().size - 1; i > 0; i--) {
    		this.removeActor(this.getChildren().get(i));
    	}
    	
        SidebarAvatar avatar = new SidebarAvatar(overlay);
        avatar.setPosition(WIDTH / 2 - avatar.getPrefWidth() / 2, overlay.getHeight() - 140);
    	this.addActor(avatar);
    	
    	String[] buttonText = new String[]{"Achievements", "Chat", "Matchmaking", "Quit Game"};
    	
    	int numItems = buttonText.length;
    	for (int i = 0; i < numItems; i++) {
    		SidebarMenuItem item = new SidebarMenuItem(skin);
    		item.setSize(120, 40);
        	item.setText(buttonText[i]);
        	item.setPosition(WIDTH / 2 - item.getWidth() / 2, overlay.getHeight() - i * 60 - 200);
        	final int buttonNum = i;
            
        	item.addListener(new EventListener() {
        		
				@Override
				public boolean handle(Event e) {
					if (e.toString() == "touchDown") {
						if (buttonNum == 0) {
							addAchievemntsWindow();
						} else if (buttonNum == 3) {
							ArcadeSystem.goToGame(ArcadeSystem.UI);
						}
					}
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
		
		if (Gdx.input.getX() < 30) {
			isUIOpen = true;
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
		
		texture.draw(batch, -50, 0, pos + WIDTH + 70, overlay.getHeight());
		super.draw(batch, parentAlpha);
		
	}
	
	
	public void resize(int x, int y) {
		generateButtons();
	}
	
	
	public void addAchievemntsWindow() {
		window.setContent(new AchievementList(overlay, skin));
		this.isUIOpen = false;
	}
	
	
}
