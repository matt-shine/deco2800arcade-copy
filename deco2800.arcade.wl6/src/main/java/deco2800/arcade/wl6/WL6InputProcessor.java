package deco2800.arcade.wl6;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import deco2800.arcade.client.ArcadeSystem;

public class WL6InputProcessor implements InputProcessor {

    private WL6 game = null;
    private GameModel model = null;
    private float MOUSE_SENSITIVITY = 0.25f;


    public WL6InputProcessor(WL6 game, GameModel model) {
        this.game = game;
        this.model = model;
    }


    @Override
    public boolean keyDown(int c) {

        //debug
        if (c == Keys.NUM_1) {
            game.toggleDebugMode();
        }

        //panic
        if (c == Keys.NUM_2) {
            ArcadeSystem.exit();
        }

        //reset level
        if (c == Keys.NUM_3) {
            model.reset();
        }

        //increment level
        if (c == Keys.NUM_4) {
            model.nextLevel();
        }

        //go to secret level
        if (c == Keys.NUM_5) {
            model.secretLevel();
        }

        //increment difficulty
        if (c == Keys.NUM_6) {
            model.setDifficulty(model.getDifficulty() + 1);
        }

        //decrement difficulty
        if (c == Keys.NUM_7) {
        	model.setDifficulty(model.getDifficulty() - 1);
        }

        if (c == Keys.W || c == Keys.S || c == Keys.A || c == Keys.D) {
            updatePlayerSpeed();
        }
        return false;
    }


    public void updatePlayerSpeed() {
        Player p = model.getPlayer();
        float x = 0;
        float y = 0;
        if (Gdx.input.isKeyPressed(Keys.W)) y -= 1;
        if (Gdx.input.isKeyPressed(Keys.S)) y += 1;
        if (Gdx.input.isKeyPressed(Keys.A)) x -= 1;
        if (Gdx.input.isKeyPressed(Keys.D)) x += 1;

        //velocity = rotate(-p.getAngle(), normalize((x, y)) * speed * delta)
        p.setVel(
                new Vector2(x, y)
                .nor()
                .mul(Player.SPEED * model.delta())
                .rotate(-p.getAngle())
        );
    }


    @Override
    public boolean keyTyped(char c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int c) {
        if (c == Keys.W || c == Keys.S || c == Keys.A || c == Keys.D) {
            updatePlayerSpeed();
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        //Gdx.input.setCursorCatched(true);
        Gdx.input.setCursorPosition(game.getWidth() / 2, game.getHeight() / 2);
        float lookOffset = x - (game.getWidth() / 2);
        float newLook = model.getPlayer().getAngle() - (lookOffset * MOUSE_SENSITIVITY);
        model.getPlayer().setAngle(newLook);
        updatePlayerSpeed();
        return false;
    }

    @Override
    public boolean scrolled(int arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
        Player p = model.getPlayer();
        p.doDamage(model);
        return false;
    }

    @Override
    public boolean touchDragged(int arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        return false;
    }

}
