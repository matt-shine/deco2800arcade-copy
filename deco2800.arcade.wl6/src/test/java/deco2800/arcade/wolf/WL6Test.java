package deco2800.arcade.wolf;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Test;

public class WL6Test {



    @Test
    public void modelTest() {


        String content = "";

        try {
            content = new Scanner(new File("./src/main/resources/wl6maps/e1l1.json")).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        GameModel g = new GameModel();
        g.addLevel("e1l1", content);
        g.goToLevel("e1l1");

    }









}
