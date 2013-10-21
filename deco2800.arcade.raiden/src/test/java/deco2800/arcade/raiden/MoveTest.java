package deco2800.arcade.raiden;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class MoveTest {
	PPlane p = new PPlane(250, 400, 100, 100);
	PBullet b = new PBullet(p.x + 20, 
			p.y + 50, 8, 15);
	@Test
	public void initTest() {
		
		//Test the initial position.
		Assert.assertEquals(250, p.x);
		Assert.assertEquals(400, p.y);
	}
	@Test
	public void moveTest(){
		if(p.UP == true){
			Assert.assertEquals(385, p.y);
		}
		if(p.DOWN == true){
			Assert.assertEquals(415, p.y);
		}
		if(p.LEFT == true){
			Assert.assertEquals(235, p.x);
		}
		if(p.RIGHT == true){
			Assert.assertEquals(265, p.x);
		}
	}
	@Test
	public void fireTest(){
		if(p.isFired){
			Assert.assertEquals(p.x+20, b.x);
			Assert.assertEquals(p.y+50, b.y);
		}
	}
}
