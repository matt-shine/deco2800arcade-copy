package deco2800.arcade.wolf;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Test;

import com.badlogic.gdx.math.Vector2;

public class WL6Test {



    @Test
    public void levelGenTest() {


        String content = "";

        try {
            content = new Scanner(new File("./src/main/resources/wl6maps/e1l1.json")).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        GameModel g = new GameModel();
        g.addLevel("e1l1", content);
        g.goToLevel("e1l1");

        
        //test that level 1 has been spawned correctly
        assertEquals(g.getPlayer().getPos(), new Vector2(29.5f, 57.5f));
        assertEquals(g.getMap().getDoodadAt(29, 33), 27);
        assertEquals(g.getMap().getTerrainAt(28, 6), 12);
        
        
    }

    @Test
    public void tickTest() {


        String content = "";

        try {
            content = new Scanner(new File("./src/main/resources/wl6maps/e1l1.json")).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        GameModel g = new GameModel();
        g.addLevel("e1l1", content);
        g.goToLevel("e1l1");

        
		g.beginTick();
		Iterator<Doodad> itr = g.getDoodadIterator();
		while (itr.hasNext()) {
			Doodad d = itr.next();
			d.tick(g);
		}
		g.endTick();
		

        //test that everything is still here
        assertEquals(g.getPlayer().getPos(), new Vector2(29.5f, 57.5f));
        assertEquals(g.getMap().getDoodadAt(29, 33), 27);
        assertEquals(g.getMap().getTerrainAt(28, 6), 12);
        
		
		
    }
    
    
    

    @Test
    public void levelProgressionTest() {


        String content = "";


        try {
            content = new Scanner(new File("./src/main/resources/wl6maps/e1l1.json")).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        GameModel g = new GameModel();
        g.addLevel("e1l1", content);
        g.goToLevel("e1l1");

        try {
            content = new Scanner(new File("./src/main/resources/wl6maps/e1l2.json")).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        g.addLevel("e1l2", content);
        
        g.getPlayer().setPos(new Vector2(10.5f, 51.5f));

        
        g.getPlayer().setPos(new Vector2(25.5f, 47.5f));
        
        
        g.beginTick();
		Iterator<Doodad> itr = g.getDoodadIterator();
		while (itr.hasNext()) {
			Doodad d = itr.next();
			d.tick(g);
		}
		g.endTick();
		
		g.beginTick();
		itr = g.getDoodadIterator();
		while (itr.hasNext()) {
			Doodad d = itr.next();
			d.tick(g);
		}
		g.endTick();

		g.beginTick();
		itr = g.getDoodadIterator();
		while (itr.hasNext()) {
			Doodad d = itr.next();
			d.tick(g);
		}
		g.endTick();
		

        //test that we are on the next level
        assertEquals(g.getLevel(), "e1l2");
        
		
		
    }









}
