package deco2800.arcade.towerdefence.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


/* Class for drawing parts of an image
 * @author Rahul_Srivastava
 * @Tuddz added some methods
 */
public class TexturePart {

	private Texture tex;
        private Vector2 position;

        // Target Dimension of image

        private int targetWidth;
        private int targetHeight;

        // Src Dimensions of Image

        private int srcWidth;
        private int srcHeight;
        private int srcX;
        private int srcY;

        // Ratio of dimension of target and source

        private float srcTargetRatioX;
        private float srcTargetRatioY;

        // ImagePart variables with values between 0-100 to draw part of image

        private int startPercentX;
        private int endPercentX;
        private int startPercentY;
        private int endPercentY;

        private int clipWidth;
        private int clipHeight;

        private int clipSrcWidth;
        private int clipSrcHeight;

        public TexturePart(TextureRegion reg, float x, float y) {

                tex = reg.getTexture();
                position = new Vector2(x, y);
                srcX = reg.getRegionX();
                srcY = reg.getRegionY();
                srcWidth = reg.getRegionWidth();
                srcHeight = reg.getRegionHeight();
                clipSrcWidth = srcWidth;
                clipSrcHeight = srcHeight;
                startPercentX = 0;
                startPercentY = 0;
                endPercentX = 100;
                endPercentY = 100;
                setTargetDimension(srcWidth, srcHeight);
        }

        public void setTargetDimension(int targetWidth, int targetHeight) {
                this.targetWidth = targetWidth;
                this.targetHeight = targetHeight;
                clipWidth = targetWidth;
                clipHeight = targetHeight;
                srcTargetRatioX = (float) targetWidth / (float) srcWidth;
                srcTargetRatioY = (float) targetHeight / (float) srcHeight;
        }

        public void setStart(int x, int y) {
                startPercentX = x;
                startPercentY = y;
        }

        public void setEnd(int x, int y) {
                endPercentX = x;
                endPercentY = y;
        }

        public void draw(SpriteBatch sp)
{
                clipSrcWidth = (int) (Math.abs(startPercentX - endPercentX) / 100f * srcWidth);
                clipSrcHeight = (int) (Math.abs(startPercentX - endPercentY) / 100f * srcHeight);
                int startX = srcX + (int) ((float) startPercentX / 100f * (float) srcX);
                int startY = srcY + (int) ((float) startPercentY / 100f * (float) srcY);
                clipWidth = (int) (srcTargetRatioX * clipSrcWidth);
                clipHeight = (int) (srcTargetRatioY * clipSrcHeight);

                sp.draw(tex, position.x - targetWidth / 2, position.y - targetHeight / 2, targetWidth / 2, targetHeight / 2, clipWidth, clipHeight, 1, 1, 0, startX, startY, clipSrcWidth, clipSrcHeight, false, false);
}
        public float getY() {
                return position.y;
        }
        
        public float getX() {
                return position.x;
        }

        public float getHeight() {
                return srcHeight;
        }
        
        public float getWidth() {
                return srcWidth;
        }
}