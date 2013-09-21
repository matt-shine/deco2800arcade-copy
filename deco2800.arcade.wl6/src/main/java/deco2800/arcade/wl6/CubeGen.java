package deco2800.arcade.wl6;

import java.util.ArrayList;

public class CubeGen {

	public static void getCube(
			float i,
			float j,
			float texX,
			float texY,
			float texS,
			ArrayList<Float> terrainScratch) {
		
		

		//SIDE: TOP
		//triangle 1
		//bottom left
		terrainScratch.add((float) i);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 0);
		
		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY);
		
		//bottom right
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 0);
		
		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY);
		
		//top left
		terrainScratch.add((float) i);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 0);
		
		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY + texS);
		
		//triangle 2
		//top left
		terrainScratch.add((float) i);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 0);

		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY + texS);
		
		//bottom right
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 0);

		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY);
		
		//top right
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 0);

		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY + texS);
		

		//SIDE: FRONT
		//triangle 1
		//bottom left
		terrainScratch.add((float) i);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 0);
		
		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY);
		

		//bottom right
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 0);
		
		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY);
		
		//top left
		terrainScratch.add((float) i);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 1);
		
		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY + texS);
		
		//triangle 2
		//top left
		terrainScratch.add((float) i);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 1);

		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY + texS);
		
		//bottom right
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 0);

		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY);
		
		//top right
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 1);

		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY + texS);

		

		
		//SIDE: BACK
		//triangle 1
		//bottom left
		terrainScratch.add((float) i);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 0);
		
		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY);
		

		//bottom right
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 0);
		
		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY);
		
		//top left
		terrainScratch.add((float) i);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 1);
		
		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY + texS);
		
		//triangle 2
		//top left
		terrainScratch.add((float) i);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 1);

		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY + texS);
		
		//bottom right
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 0);

		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY);
		
		//top right
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 1);

		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY + texS);
		
		

		//SIDE: LEFT SIDE
		//triangle 1
		//bottom left
		terrainScratch.add((float) i);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 0);
		
		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY);
		

		//bottom right
		terrainScratch.add((float) i);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 0);
		
		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY);
		
		//top left
		terrainScratch.add((float) i);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 1);
		
		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY + texS);
		
		//triangle 2
		//top left
		terrainScratch.add((float) i);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 1);

		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY + texS);
		
		//bottom right
		terrainScratch.add((float) i);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 0);

		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY);
		
		//top right
		terrainScratch.add((float) i);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 1);

		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY + texS);

		

		//SIDE: RIGHT SIDE
		//triangle 1
		//bottom left
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 0);
		
		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY);
		

		//bottom right
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 0);
		
		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY);
		
		//top left
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 1);
		
		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY + texS);
		
		//triangle 2
		//top left
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j);
		terrainScratch.add((float) 1);

		terrainScratch.add((float) texX);
		terrainScratch.add((float) texY + texS);
		
		//bottom right
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 0);

		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY);
		
		//top right
		terrainScratch.add((float) i + 1);
		terrainScratch.add((float) j + 1);
		terrainScratch.add((float) 1);

		terrainScratch.add((float) texX + texS);
		terrainScratch.add((float) texY + texS);

	}
	
	
	
	public static void genQuad(float texX, float texY, float texS, ArrayList<Float> quadMesh) {
		
		
		//triangle 1
		//bottom left
		quadMesh.add((float) -0.5f);
		quadMesh.add((float) 0);
		quadMesh.add((float) -0.5f);
		
		quadMesh.add((float) texX);
		quadMesh.add((float) texY);
		
		//bottom right
		quadMesh.add((float) 0.5f);
		quadMesh.add((float) 0);
		quadMesh.add((float) -0.5f);

		quadMesh.add((float) texX + texS);
		quadMesh.add((float) texY);
		
		//top left
		quadMesh.add((float) -0.5f);
		quadMesh.add((float) 0);
		quadMesh.add((float) 0.5f);

		quadMesh.add((float) texX);
		quadMesh.add((float) texY + texS);
		
		//triangle 2
		//top left
		quadMesh.add((float) -0.5f);
		quadMesh.add((float) 0);
		quadMesh.add((float) 0.5f);

		quadMesh.add((float) texX);
		quadMesh.add((float) texY + texS);
		
		//bottom right
		quadMesh.add((float) 0.5f);
		quadMesh.add((float) 0);
		quadMesh.add((float) -0.5f);

		quadMesh.add((float) texX + texS);
		quadMesh.add((float) texY);
		
		//top right
		quadMesh.add((float) 0.5f);
		quadMesh.add((float) 0);
		quadMesh.add((float) 0.5f);

		quadMesh.add((float) texX + texS);
		quadMesh.add((float) texY + texS);
		
	}
	
	
}
