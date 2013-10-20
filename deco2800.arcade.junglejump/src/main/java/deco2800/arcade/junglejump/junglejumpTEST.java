package deco2800.arcade.junglejump;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class junglejumpTEST {
	
	@Rule 
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void init() {
		LevelContainer container = new LevelContainer();
		Level level = new Level();
	}
	
	@Test (expected = Exception.class)
	public void initWithTooManyLevels () {
		
	}
	
	
	
}
