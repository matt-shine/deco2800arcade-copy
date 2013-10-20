package deco2800.arcade.hunter.test;

import com.badlogic.gdx.math.Vector2;
import deco2800.arcade.hunter.model.BackgroundSprite;
import deco2800.arcade.hunter.platformerGame.Entity;
import deco2800.arcade.hunter.platformerGame.EntityCollection;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class EntityCollectionTest {

    @Test
    public void testAddEntities() {
        EntityCollection ec = new EntityCollection();
        //Start with one item
        ec.add(new BackgroundSprite(Vector2.Zero, 10, 10, 1));
        Assert.assertTrue(ec.size() == 1);

        //Make sure it isn't null
        Assert.assertTrue(ec.getById(ec.idSet().iterator().next()) != null);

        //Add another 5
        for (int i = 0; i < 5; i++) {
            ec.add(new BackgroundSprite(Vector2.Zero, 10, 10, 1));
        }
        Assert.assertTrue(ec.size() == 6);
    }

    @Test
    public void testRemoveEntities() {
        EntityCollection ec = new EntityCollection();

        for (int i = 0; i < 20; i++) {
            ec.add(new BackgroundSprite(Vector2.Zero, 10, 10, 1));
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
