package deco2800.arcade.wl6;

import java.util.ArrayList;

public class CubeGen {

    public static void getCube(
            float x,
            float y,
            float u,
            float v,
            float tileSize,
            boolean left,
            boolean right,
            boolean front,
            boolean back,
            boolean debug,
            ArrayList<Float> terrainScratch) {



        //SIDE: TOP
        //triangle 1
        //bottom left
        if (debug) {
            terrainScratch.add((float) x);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v);

            //bottom right
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v);

            //top left
            terrainScratch.add((float) x);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v + tileSize);

            //triangle 2
            //top left
            terrainScratch.add((float) x);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v + tileSize);

            //bottom right
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v);

            //top right
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v + tileSize);
        }

        //SIDE: FRONT
        //triangle 1
        //bottom left
        if (front) {
            terrainScratch.add((float) x);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v);


            //bottom right
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v);

            //top left
            terrainScratch.add((float) x);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 1);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v + tileSize);

            //triangle 2
            //top left
            terrainScratch.add((float) x);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 1);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v + tileSize);

            //bottom right
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v);

            //top right
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 1);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v + tileSize);
        }



        //SIDE: BACK
        //triangle 1
        //bottom left
        if (back) {
            terrainScratch.add((float) x);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v);


            //bottom right
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v);

            //top left
            terrainScratch.add((float) x);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 1);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v + tileSize);

            //triangle 2
            //top left
            terrainScratch.add((float) x);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 1);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v + tileSize);

            //bottom right
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v);

            //top right
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 1);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v + tileSize);

        }


        //SIDE: LEFT SIDE
        //triangle 1
        //bottom left
        if (left) {
            terrainScratch.add((float) x);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v);


            //bottom right
            terrainScratch.add((float) x);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v);

            //top left
            terrainScratch.add((float) x);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 1);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v + tileSize);

            //triangle 2
            //top left
            terrainScratch.add((float) x);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 1);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v + tileSize);

            //bottom right
            terrainScratch.add((float) x);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v);

            //top right
            terrainScratch.add((float) x);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 1);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v + tileSize);
        }


        //SIDE: RIGHT SIDE
        //triangle 1
        //bottom left
        if (right) {
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v);


            //bottom right
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v);

            //top left
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 1);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v + tileSize);

            //triangle 2
            //top left
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y);
            terrainScratch.add((float) 1);

            terrainScratch.add((float) u);
            terrainScratch.add((float) v + tileSize);

            //bottom right
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 0);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v);

            //top right
            terrainScratch.add((float) x + 1);
            terrainScratch.add((float) y + 1);
            terrainScratch.add((float) 1);

            terrainScratch.add((float) u + tileSize);
            terrainScratch.add((float) v + tileSize);
        }

    }



    public static void genQuad(float texS, ArrayList<Float> quadMesh) {


        //triangle 1
        //bottom left
        quadMesh.add((float) -0.5f);
        quadMesh.add((float) 0);
        quadMesh.add((float) -0.5f);

        quadMesh.add((float) 0);
        quadMesh.add((float) 0);

        //bottom right
        quadMesh.add((float) 0.5f);
        quadMesh.add((float) 0);
        quadMesh.add((float) -0.5f);

        quadMesh.add((float) texS);
        quadMesh.add((float) 0);

        //top left
        quadMesh.add((float) -0.5f);
        quadMesh.add((float) 0);
        quadMesh.add((float) 0.5f);

        quadMesh.add((float) 0);
        quadMesh.add((float) texS);

        //triangle 2
        //top left
        quadMesh.add((float) -0.5f);
        quadMesh.add((float) 0);
        quadMesh.add((float) 0.5f);

        quadMesh.add((float) 0);
        quadMesh.add((float) texS);

        //bottom right
        quadMesh.add((float) 0.5f);
        quadMesh.add((float) 0);
        quadMesh.add((float) -0.5f);

        quadMesh.add((float) texS);
        quadMesh.add((float) 0);

        //top right
        quadMesh.add((float) 0.5f);
        quadMesh.add((float) 0);
        quadMesh.add((float) 0.5f);

        quadMesh.add((float) texS);
        quadMesh.add((float) texS);

    }


}
