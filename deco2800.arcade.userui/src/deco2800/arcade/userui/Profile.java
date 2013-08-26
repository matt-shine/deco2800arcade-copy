package deco2800.arcade.userui;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Profile {
	public static void main(String[] args) {
		//The Main Page
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "User Profile";
		cfg.useGL20 = true;
		cfg.width = 1080;
		cfg.height = 600;
		//The edit page
		/*LwjglApplicationConfiguration edit = new LwjglApplicationConfiguration();
		edit.title = "User Profile";
		edit.useGL20 = true;
		edit.width = 300;
		edit.height = 600;	
		*/	
		//Create Main page		
		new LwjglApplication(new ProfileUI(), cfg);
	}
}