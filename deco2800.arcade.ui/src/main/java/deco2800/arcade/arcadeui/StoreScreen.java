package deco2800.arcade.arcadeui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import deco2800.arcade.client.ArcadeInputMux;

public class StoreScreen implements Screen {
	
	private Skin skin;
    private Skin skin2;
    private Stage stage;
	
    public StoreScreen() {
        skin = new Skin(Gdx.files.internal("loginSkin.json"));
        skin2 = new Skin();
        
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin2.add("white", new Texture(pixmap));
        
        // THIS IS THE CODE THAT SPECIFYS TEXT FIELD STYLES
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.cursor = skin2.newDrawable("white", Color.WHITE);
        textFieldStyle.selection = skin2.newDrawable("white", Color.WHITE);
        //textFieldStyle.background = ;
        skin2.add("default", textFieldStyle);
        
        // THIS IS THE CODE THAT SPECIFYS TEXT BUTTON STYLES
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin2.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin2.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin2.newDrawable("white", Color.WHITE);
        textButtonStyle.over = skin2.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin2.add("default", textButtonStyle);
        
        stage = new Stage();
        ArcadeInputMux.getInstance().addProcessor(stage);
        
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        
        Label labelName = new Label("Label:", skin); // Label for Text Field
        final TextField labelText = new TextField("", skin2); // Text Field
        labelText.setMessageText("Message Prompt"); // Shadow Text for Text Field
        labelName.setAlignment(Align.left);

        TextButton buttonName = new TextButton("Login", skin2); // Button
        
        // Stuff below adds the things that you've created up there^.
        table.debug();  // Shows table debug lines
        table.add(labelName).width(150).padBottom(5).padTop(5).padLeft(10).padRight(10);
        table.row();
        table.add(labelText).width(150).padBottom(5).padTop(5).padLeft(10).padRight(10);
        table.row();
        table.add(buttonName).width(100).pad(10);
        
        
        buttonName.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println(labelText.getText());
            }
        });
	}
    
	@Override
	public void show() {
	}
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        Table.drawDebug(stage);  // Shows table debug lines
	}
	
	@Override
	public void dispose() {
        stage.dispose();
        skin.dispose();
        ArcadeInputMux.getInstance().removeProcessor(stage);
	}
	
	@Override
	public void hide() {
	}
	
	@Override
	public void pause() {
	}
	
	@Override
	public void resume() {
	}
	
	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
	}
}
