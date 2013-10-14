package deco2800.arcade.arcadeui.Overlay;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

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
    	//skin.add("TextFieldStyle", new Texture("textField.png"));
    	
        final TextField inputField = new TextField("Input", skin); 
        final TextField outputField = new TextField("Output", skin);
        
        inputField.setSize(this.getWidth() - 220, 50);
        inputField.setPosition(110, 75);
        this.addActor(inputField);
        
        outputField.setSize(this.getWidth() - 220, 250);
        outputField.setPosition(110, 165);
        this.addActor(outputField);
    }
}
