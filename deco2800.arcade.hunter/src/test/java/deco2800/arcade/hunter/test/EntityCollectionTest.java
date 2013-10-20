package deco2800.arcade.hunter.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.hunter.model.Item;
import deco2800.arcade.hunter.platformergame.Entity;
import deco2800.arcade.hunter.platformergame.EntityCollection;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class EntityCollectionTest {
    //Dummy item texture
    Texture itemTexture = new Texture(Gdx.files.internal("textures/tilemap.png"));

    @Test
    public void testAddEntities() {
        EntityCollection ec = new EntityCollection();
        Texture t = new Texture(Gdx.files.internal("textures/tilemap.png"));
        //Start with one item
        ec.add(new Item(Vector2.Zero, 10, 10, "Coin", itemTexture));
        Assert.assertTrue(ec.size() == 1);

        //Make sure it isn't null
        Assert.assertTrue(ec.getById(ec.idSet().iterator().next()) != null);

        //Add another 5
        for (int i = 0; i < 5; i++) {
            ec.add(new Item(Vector2.Zero, 10, 10, "Coin", itemTexture));
        }
        Assert.assertTrue(ec.size() == 6);
    }

    @Test
    public void testRemoveEntities() {
        EntityCollection ec = new EntityCollection();

        for (int i = 0; i < 20; i++) {
            ec.add(new Item(Vector2.Zero, 10, 10, "Coin", itemTexture));
        }
        Assert.assertTrue(ec.size() == 20);

        //Remove one
        ec.remove(ec.getById(ec.idSet().iterator().next()));
        Assert.assertTrue(ec.size() == 19);

        //Remove the rest
        Iterator<Entity> it = ec.iterator();
        while (it.hasNext()) {
            ec.remove(it.next());
        }
        Assert.assertTrue(ec.size() == 0);
    }
}
