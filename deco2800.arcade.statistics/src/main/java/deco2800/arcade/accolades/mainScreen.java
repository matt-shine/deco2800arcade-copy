package deco2800.arcade.accolades;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;


public class mainScreen implements Screen {
	
    private Skin skin;
    private Stage stage;
	

    Texture bg;
    Sprite bgSprite;
    SpriteBatch batch;
    
    public mainScreen(AccoladeContainer ac) {
    	//FIXME big method
        skin = new Skin(Gdx.files.internal("data/statsSkin.json"));
        stage = new Stage();
        
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.cursor = skin.newDrawable("white", Color.WHITE);
        textFieldStyle.selection = skin.newDrawable("white", Color.WHITE);
        skin.add("default", textFieldStyle);
        
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        //textButtonStyle.checked = skin2.newDrawable("white", Color.WHITE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        
        
        skin.add("tableBg", new Texture("data/header.png"));
        skin.add("accoladesBg", new Texture("data/b.png"));
        skin.add("achievementsBg", new Texture("data/w.png"));
        skin.add("userCountBg", new Texture("data/w.png"));
        
        bg = new Texture("data/bg.png");
        bg.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        bgSprite = new Sprite(bg);
        batch = new SpriteBatch();
      
        final Table leftBox = new Table();
        final Table topBox = new Table();
        final Table gameIcon = new Table();
        final Table accoladesTable = new Table();
        final Table achievementsTable = new Table();
        final Table userCountTable = new Table();
        
     
        
        leftBox.setSize(250, 680);
        leftBox.setPosition(20, 20);
        topBox.setSize(980, 160);
        topBox.setPosition(280, 540);
        accoladesTable.setSize(400, 500);
        accoladesTable.setPosition(280, 20);
        achievementsTable.setSize(560,250);
        achievementsTable.setPosition(700, 20);
        userCountTable.setSize(560,240);
        userCountTable.setPosition(700, 280);
        
        stage.addActor(leftBox);
        stage.addActor(topBox);
        stage.addActor(accoladesTable);
        stage.addActor(achievementsTable);
        stage.addActor(userCountTable);
        
        
        
        final Label userProfile = new Label("User Profile",skin);
        leftBox.add(userProfile).expand().top();
        

        topBox.setBackground(skin.getDrawable("tableBg"));
		//topBox.add(new Image(new TextureRegion(new Texture(Gdx.files.internal("data/header.png"))))).width(980).height(160);
		gameIcon.add(new Image(new TextureRegion(new Texture(Gdx.files.internal("data/game_icon.png")))));
	    final Label gameName = new Label("Game Name",skin);
	   
	    topBox.add(gameIcon).expand().left();
	    topBox.add(gameName).expand().left();
        
	    final Label accoladesTitle = new Label("Accolades",skin);
	    final Label userCountTitle = new Label("Previous 'n' Current number of users",skin);
	    final Label achievementsTitle = new Label("Achievements",skin);
	    
	    accoladesTable.add(accoladesTitle).expand().top();
	    userCountTable.add(userCountTitle).expand().top();
	    achievementsTable.add(achievementsTitle).expand().top();
	    
	    /*
	     * Adding accolades into the table and see more
	     */
	  
	   int count = 0;
	   for(Accolade a : ac){
		   count++;
		   if(count <= 4){
			   Table accolade = new Table ();
			   Table accoladeIcon = new Table();
			  /*
			   * replace the image path later
			   accoladeIcon.add(new Image(new TextureRegion(
					   new Texture(Gdx.files.internal(a.getImagePath())))));
			 */
			   accoladeIcon.add(new Image(new TextureRegion(
					   new Texture(Gdx.files.internal("data/w.png")))));
			   
			   accolade.setBackground(skin.getDrawable("accoladesBg"));
			   Label accoladeName = new Label(a.getName(),skin);
			   if(count%2 == 0){
				   accolade.add(accoladeIcon).height(50).width(50);
				   accolade.add(accoladeName);
			   }else{
				   accolade.add(accoladeName);
				   accolade.add(accoladeIcon).height(50).width(50);
			   }
			   accoladesTable.row();
			   accoladesTable.add(accolade).height(100).width(400); 
		   }else{
			   Table accolade = new Table ();
			   //accolade.setBackground(skin.getDrawable("accoladesBg"));
			   Label accoladeName = new Label("more",skin);
			   accolade.add(accoladeName);
			   accoladesTable.row();
			   accoladesTable.add(accolade).bottom().right();
		   }
	   }
	   Table SeeMore = new Table ();
	   Label more = new Label("More",skin);
	   SeeMore.add(more);
	   achievementsTable.row();
	   achievementsTable.add(SeeMore).bottom().right();
	   
	    
	    
	    
	    accoladesTable.debug();
	    achievementsTable.debug();
	    userCountTable.debug();
        
  
        leftBox.debug(); // Shows table debug lines

	}
    



	public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }
    
    
    
	@Override
	public void show() {
		//ArcadeInputMux.getInstance().addProcessor(stage);
	}
	
	@Override
	public void render(float arg0) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		bgSprite.draw(batch);
		batch.end();
		
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        Table.drawDebug(stage);  // Shows table debug lines
	}
	
	@Override
	public void dispose() {
        stage.dispose();
        skin.dispose();
        //ArcadeInputMux.getInstance().removeProcessor(stage);
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
	}
}
