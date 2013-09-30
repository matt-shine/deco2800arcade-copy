package deco2800.arcade.arcadeui;

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

public class OverlayPopup extends Actor {

    private LinkedList<PopupMessage> msgs = new LinkedList<PopupMessage>();

    private PopupMessage current = null;

    private int state = 0;

    private static float MINSIZE = 90;
    private static float YPOS_GOAL = 40;
    private static float YPOS_START = -MINSIZE;
    private static float EXPAND_GOAL = 400;
    private static float EXPAND_START = MINSIZE;
    private static float EXPAND_ACC = 1;
    private static float YPOS_ACC = 1;

    private float ypos = 0;
    private float yvel = 0;
    private float expandedTime = 0;
    private float expandedAmt = EXPAND_START;
    private float expandedVel = 0;

    private NinePatch texture;
    private BitmapFont font;
    private Overlay overlay;

    public OverlayPopup(Overlay overlay) {
        super();

        font = new BitmapFont(false);
        this.overlay = overlay;

        //texture = new NinePatch(new Texture(Gdx.files.internal("popupbg.png")), 30, 30, 30, 30);
        texture = new NinePatch(new Texture(Gdx.files.internal("iconGreen_plus.png")), 30, 30, 30, 30);

        ypos = YPOS_START;

    }

    public void addMessageToQueue(PopupMessage p) {
        msgs.add(p);
    }



    @Override
    public void act(float d) {
        super.act(d);

        if (state == 0) {

            yvel = 0;
            ypos = YPOS_START;
            if (msgs.size() != 0) {
                current = msgs.remove(0);
                state++;
            }

        } else if (state == 1) {

            if (ypos > YPOS_GOAL) {
                yvel = 0;
                state++;
            } else {
                yvel += YPOS_ACC;
            }

        } else if (state == 2) {

            expandedVel += EXPAND_ACC;
            expandedAmt += expandedVel;
            if (expandedAmt > EXPAND_GOAL) {

                expandedAmt = EXPAND_GOAL;
                expandedVel = 0;
                state++;

            }

        } else if (state == 3) {

            expandedTime += d;
            if (expandedTime > 0.8) {
                expandedTime = 0;
                state++;
            }

        } else if (state == 4) {

            expandedVel -= EXPAND_ACC;
            expandedAmt += expandedVel;
            if (expandedAmt < EXPAND_START) {

                expandedAmt = EXPAND_START;
                expandedVel = 0;

                if (msgs.size() == 0) {
                    state++;
                } else {
                    state = 2;
                    current = msgs.remove(0);
                }

            }

        } else if (state == 5) {

            if (ypos < YPOS_START) {
                yvel = 0;
                state = 0;
            } else {
                yvel -= YPOS_ACC;
            }

        }

        ypos += yvel;
        this.setX(overlay.getWidth() / 2 - expandedAmt / 2);
        this.setY(ypos);

    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, overlay.getWidth(), overlay.getHeight());
        camera.update();
        batch.setProjectionMatrix(camera.combined);


        if (state != 0) {
            texture.draw(batch, getX(), getY(), expandedAmt, MINSIZE);
            font.setColor(Color.WHITE);
            if (current != null && expandedAmt == EXPAND_GOAL) {
                TextBounds b = font.getBounds(current.getMessage());
                font.draw(batch, current.getMessage(), getX() + expandedAmt / 2 - b.width / 2, getY() + MINSIZE / 2 + b.height / 2);

            }
        }

    }




}
