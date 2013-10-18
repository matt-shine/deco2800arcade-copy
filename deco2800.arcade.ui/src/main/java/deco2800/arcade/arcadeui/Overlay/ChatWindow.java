package deco2800.arcade.arcadeui.Overlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class ChatWindow extends WindowContent {

    Overlay overlay = null;
    Skin skin = null;
    boolean initialised = false;

    
    public ChatWindow(Overlay overlay, Skin skin) {
        this.overlay = overlay;
        this.skin = skin;
    }


    @Override
    public void act(float d) {
        if (!initialised) {
            initialised = true;
            generateInterface();
        }
    }

    @Override
    public void resize(int w, int h) {
    	generateInterface();
    }
    
    private void generateInterface() {
    	float width = getWidth();
    	float height = getHeight();
    	
        final Label outputField = new Label("", skin, "chat"); 
        final TextField inputField = new TextField("", skin);
        final TextButton send = new TextButton("Send", skin);
        final ScrollPane scroll = new ScrollPane(outputField);
        
        scroll.setSize(width - 120, height - 180);
        scroll.setPosition(60, 120);
        
        outputField.setFillParent(true);
        outputField.setWrap(true);
        outputField.setAlignment(Align.bottom | Align.left);
        
        this.addActor(scroll);
        
        inputField.setSize(width - 120, 47);
        inputField.setPosition(60, 60);
        inputField.setCursorPosition(10);
        
        this.addActor(inputField);
        
        send.setSize(100, 47);
        send.setPosition(width - 155, 60);
        this.addActor(send);

        
        send.addListener((new ChangeListener() {
		    public void changed (ChangeEvent event, Actor actor) {

            	String message = inputField.getText();
            	inputField.setText("");
            	
            	CharSequence output = outputField.getText();
            	outputField.setText(output + "\n" + message);
		    }
		}));
        
    }
}
