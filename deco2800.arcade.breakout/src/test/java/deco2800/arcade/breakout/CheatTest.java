/*package deco2800.arcade.breakout;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.mockito.Mockito;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;


public class CheatTest {

	GameScreen context = mock(GameScreen.class);
	ReadyState rs = mock(ReadyState.class);

	@Before
	public void setUp() throws Exception {
		Mockito.doCallRealMethod().when(context).getPressed();
		Mockito.doCallRealMethod().when(context).setPressed(Keys.DOWN);
		Mockito.doCallRealMethod().when(context).getCurrentButton();
		Mockito.doCallRealMethod().when(context).setCurrentButton(0);
		doCallRealMethod().when(rs).handleState(context);
		doCallRealMethod().when(context).mute();
		doCallRealMethod().when(context).music.stop();
	}

	@Test
	public void test() {
		context.mute = false;
		context.mute();
		verify(context).music.stop();
	}

}*/
