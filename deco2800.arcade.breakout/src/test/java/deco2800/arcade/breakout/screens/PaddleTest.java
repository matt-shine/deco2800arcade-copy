/*package deco2800.arcade.breakout.screens;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import org.mockito.Mockito;

import com.badlogic.gdx.math.Vector2;

import deco2800.arcade.breakout.Paddle;

public class PaddleTest {

		GameScreen context = mock(GameScreen.class);
		Paddle mockPaddle = mock(Paddle.class);
		Vector2 mockV2 = mock(Vector2.class);
		
		@Test
		public void setUp() throws Exception {
			Mockito.doCallRealMethod().when(context).setPaddle(mockPaddle);
			Mockito.doCallRealMethod().when(mockPaddle).decreaseSize();
			Mockito.doCallRealMethod().when(mockPaddle).getPaddleShapeWidth();
			Mockito.doCallRealMethod().when(mockPaddle).setWidth(mockPaddle.getStandardWidth());
			Mockito.doCallRealMethod().when(mockPaddle).getStandardWidth();
			//context.setPaddle(mockPaddle);
			mockPaddle.setWidth(mockPaddle.getStandardWidth());
			mockPaddle.decreaseSize();
			assertEquals((int)mockPaddle.getStandardWidth()/2, (int)mockPaddle.getPaddleShapeWidth());
		}
	
}
*/