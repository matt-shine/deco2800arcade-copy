package deco2800.arcade.arcadeui.Overlay;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import deco2800.arcade.client.UIOverlay.PopupMessage;

public class Popup extends Actor {

    private LinkedList<PopupMessage> msgs = new LinkedList<PopupMessage>();

    private PopupMessage current = null;

    private final int STATE_WAITING = 0;
    private final int STATE_RISING = 1;
    private final int STATE_EXPANDING = 2;
    private final int STATE_DISPLAYING = 3;
    private final int STATE_SHRINKING = 4;
    private final int STATE_FALLING = 5;
    private int state = STATE_WAITING;

    private final float IMAGE_SIZE = 64.0f;
    private final float PADDING_X = 24.0f;
    private final float PADDING_Y = 24.0f;
    private final float IMAGE_LABEL_PADDING = 15.0f;
    private final float WIDTH_ACCEL = 1.0f;
    private final float HEIGHT_ACCEL = 1.0f;
    private float height = 0;
    private float heightVel = 0;
    private float displayTime = 0;
    private float width = 0;
    private float widthVel = 0;    
    private float messageHeight = 0;

    private NinePatch texture;
    private BitmapFont font;
    private Overlay overlay;

    public Popup(Overlay overlay) {
        super();
        font = new BitmapFont(false);
        this.overlay = overlay;
        texture = new NinePatch(new Texture(Gdx.files.internal("iconGreen_small.png")), (int)PADDING_X, (int)PADDING_X, (int)PADDING_Y, (int)PADDING_Y);
	state = STATE_WAITING;
    }

    public void addMessageToQueue(PopupMessage p) {
        msgs.add(p);
    }

    private float getHeightTarget() {
	return 60.0f;
    }

    private float getWidthTarget() {
	float target = font.getBounds(current.getMessage()).width; // start with our message
	target += 2 * PADDING_X; // add in the far left and far right padding
	if (current.getTexture() != null) {
	    // add in the image if we have one
	    target += IMAGE_SIZE;
	    target += IMAGE_LABEL_PADDING; // padding between the image and message
	}
	
	return target;
    }

    private float getMessageHeight() {
	return 2 * PADDING_Y + IMAGE_SIZE;
    }

    @Override
    public void act(float d) {
        super.act(d);
	
	switch (state) {
	case STATE_WAITING:
	    heightVel = 0;
	    height = 0 - getMessageHeight();
            if (!msgs.isEmpty()) {
                current = msgs.remove(0);
                state = STATE_RISING;
            }
	    break;
	case STATE_RISING:
	    if (height > getHeightTarget()) {
		heightVel = 0;
		height = getHeightTarget();
		width = 2 * PADDING_X;
                state = STATE_EXPANDING;
	    } else {
		heightVel += HEIGHT_ACCEL;
	    }
	    break;
	case STATE_EXPANDING:
	    widthVel += WIDTH_ACCEL;
            if (width > getWidthTarget()) {
		width = getWidthTarget();
		widthVel = 0;
                state = STATE_DISPLAYING;
            }
	    break;
	case STATE_DISPLAYING:
	    displayTime += d;
	    if (displayTime > current.displayTime()) {
		displayTime = 0;
		state = STATE_SHRINKING;
	    }
	    break;
	case STATE_SHRINKING:
	    widthVel -= WIDTH_ACCEL;
            if (width < 2 * PADDING_X) {
                width = 2 * PADDING_X;
		widthVel = 0;

		// reopen immediately if we have more messages to show
		if (!msgs.isEmpty()) {
		    current = msgs.remove(0);
		    state = STATE_EXPANDING;
		} else {
		    state = STATE_FALLING;
		}
            }	    
	    break;
	case STATE_FALLING:
	    heightVel -= HEIGHT_ACCEL;
	    if (height < 0 - getMessageHeight()) {
                height = 0 - getMessageHeight();
                state = STATE_WAITING;
            }
	    break;
	}

	width += widthVel;
	height += heightVel;
        this.setX(overlay.getWidth() / 2 - width / 2);
        this.setY(height);
	texture.setMiddleWidth(width - 2 * PADDING_X);
	texture.setMiddleHeight(height - 2 * PADDING_Y);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, overlay.getWidth(), overlay.getHeight());
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        if (state != 0) {
            texture.draw(batch, getX(), getY(), width, getMessageHeight());
            font.setColor(Color.WHITE);
            if (state == STATE_DISPLAYING) {
 		float x = getX() + PADDING_X;

		// first draw the image (it's on the left)
		Texture currTex = current.getTexture();
		if (currTex != null) {
		    batch.draw(current.getTexture(), x, getY() + PADDING_Y, IMAGE_SIZE, IMAGE_SIZE);
		    x += IMAGE_SIZE + IMAGE_LABEL_PADDING;
		}

		// then our text		
                font.draw(batch, current.getMessage(), x, getY() + (getMessageHeight() / 2.0f) + (font.getBounds(current.getMessage()).height / 2.0f));
            }
        }

    }




}
