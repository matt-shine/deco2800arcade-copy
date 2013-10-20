package deco2800.arcade.junglejump;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class junglejumpTEST {
	LevelContainer container;
	Level level;
	
	@Rule 
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void init() {
		container = new LevelContainer();
		level = new Level();
	}
	
	@Test (expected = Exception.class)
	public void initWithTooManyLevels () {
		
	}
	
	
	
}
