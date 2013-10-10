package deco2800.arcade.lunarlander;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class LunarLanderTerrain {
	
	//setting ish up here
	private static ShapeRenderer shapeRenderer;
	private static boolean randomMap;
	
	public static int main(String message) {
		int length = message.length();
        return length; //Return the length
    }
	
	public static int returnLength(String message){
		int length = message.length();
		return length;
	}
	
public static List<List<Integer>> createMap(){
		
		List<List<Integer>> terrain = new ArrayList<List<Integer>>();
		
		//creating the landing pads coordinates
		int landPadLeftX = randNum(100, 700);
		int landPadLeftY = randNum(50, 200);
		int landPadRightX = landPadLeftX + 50; //makes the pad 50 pixels wide
		int landPadRightY = landPadLeftY;
		
		//adding the landPad as the first element of the array, TO DO, change color of landing pad
		terrain.add(new ArrayList<Integer>());
		terrain.get(0).add(landPadLeftX);
		terrain.get(0).add(landPadLeftY);
		terrain.get(0).add(landPadRightX);
		terrain.get(0).add(landPadRightY);
		
		int pointX = terrain.get(0).get(2);
		int pointY = terrain.get(0).get(3);
		int arrayPosition = 1;
		
		//automatically adds lines to the right of the landing pad
		while (pointX < 1300){

			int newPointX = randNum(50, 200);
			int newPointY = randNum(100, 400);
			
			terrain.add(new ArrayList<Integer>());
			terrain.get(arrayPosition).add(pointX);
			terrain.get(arrayPosition).add(pointY);
			terrain.get(arrayPosition).add(newPointX + pointX);
			terrain.get(arrayPosition).add(newPointY);
			
			pointX = pointX + newPointX;
			pointY = newPointY;
			arrayPosition++;
			
		}
		
		int pointX2 = terrain.get(0).get(0);
		int pointY2 = terrain.get(0).get(1);
		int arrayPosition2 = arrayPosition;
		
		//adds lines to the left of the platform/landing pad
		while (pointX2 > -100){

			int newPointX2 = randNum(50, 200);
			int newPointY2 = randNum(100, 400);
			
			terrain.add(new ArrayList<Integer>());
			terrain.get(arrayPosition2).add(pointX2 - newPointX2);
			terrain.get(arrayPosition2).add(newPointY2);
			terrain.get(arrayPosition2).add(pointX2);
			terrain.get(arrayPosition2).add(pointY2);
			
			pointX2 = pointX2 - newPointX2;
			pointY2 = newPointY2;
			arrayPosition2++;
			
			
		}
		
		
		return terrain;
	}

	public static void renderMap(List<List<Integer>> terrain){
		shapeRenderer = new ShapeRenderer();
		randomMap = true;
		
		
		//Begin drawing of shapes
	    shapeRenderer.begin(ShapeType.Line);
	    Gdx.gl.glLineWidth(5);
	    
	    //set map to be randomly made, or not
	    if(randomMap == true){
	    for (int i = 0; i < terrain.size(); i++){
	    	shapeRenderer.setColor(180, 180, 5, 1);
	    	shapeRenderer.line(terrain.get(i).get(0), terrain.get(i).get(1), terrain.get(i).get(2), terrain.get(i).get(3));
	    	}
	    }else{
	    	//load pre-made ArrayList of points, and background texture
	    }
	    
	    //colors the landing pad green
	    shapeRenderer.setColor(255, 0, 0, 1);
	    shapeRenderer.line(terrain.get(0).get(0), terrain.get(0).get(1) + 3, terrain.get(0).get(2), terrain.get(0).get(3) + 3);
	    shapeRenderer.end();
	    
	    //fills map with rectangles
	    for (int i = 1; i < terrain.size(); i++){
	    	if (terrain.get(i).get(1) > terrain.get(i).get(3)){
	    		shapeRenderer.begin(ShapeType.FilledRectangle);
			    shapeRenderer.setColor(20, 20, 20, 100);
				shapeRenderer.filledRect(terrain.get(i).get(0), 0, terrain.get(i).get(2) - terrain.get(i).get(0), terrain.get(i).get(3));
				shapeRenderer.end();
	    	}else if (terrain.get(i).get(1) < terrain.get(i).get(3)){
	    		shapeRenderer.begin(ShapeType.FilledRectangle);
			    shapeRenderer.setColor(20, 20, 20, 100);
				shapeRenderer.filledRect(terrain.get(i).get(0), 0, terrain.get(i).get(2) - terrain.get(i).get(0), terrain.get(i).get(1));		
				shapeRenderer.end();
	    	}
	  
	    }
	    
	    //fills underneath landing pad
	    shapeRenderer.begin(ShapeType.FilledRectangle);
	    shapeRenderer.setColor(20, 20, 20, 100);
		shapeRenderer.filledRect(terrain.get(0).get(0), 0, terrain.get(0).get(2), terrain.get(0).get(3));		
		shapeRenderer.end();
	    
	    for (int i = 1; i < terrain.size(); i++){
	    	if (terrain.get(i).get(1) > terrain.get(i).get(3)){
		    	shapeRenderer.begin(ShapeType.FilledTriangle);
			    shapeRenderer.setColor(20, 20, 20, 100);
				shapeRenderer.filledTriangle(terrain.get(i).get(0), terrain.get(i).get(1), terrain.get(i).get(2), terrain.get(i).get(3), terrain.get(i).get(0), terrain.get(i).get(3));		
				shapeRenderer.end();
	    	}else if (terrain.get(i).get(1) < terrain.get(i).get(3)){
	    		shapeRenderer.begin(ShapeType.FilledTriangle);
			    shapeRenderer.setColor(20, 20, 20, 100);
				shapeRenderer.filledTriangle(terrain.get(i).get(0), terrain.get(i).get(1), terrain.get(i).get(2), terrain.get(i).get(3), terrain.get(i).get(2), terrain.get(i).get(1));		
				shapeRenderer.end();
	    	}
	    }
	}

	//creates a random length of a line, used by createMap()
	public static int randNum(int start, int finish){
		int number = start + new Random().nextInt(finish - start + 1 );
		return number;
	}
}