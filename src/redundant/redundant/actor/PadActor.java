package redundant.redundant.actor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import redundant.redundant.Redundant;
import redundant.redundant.data.BeatMap;
import redundant.redundant.data.DifficultyConstants;
import redundant.redundant.screen.GameScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 10:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class PadActor extends Group {

    private Texture texture = null;

    public static final float WIDTH = 192;
    public static final float HEIGHT = WIDTH;
    public static final float ORIGIN_X = 192/2;
    public static final float ORIGIN_Y = ORIGIN_X;

    private int side;

    public PadActor(int side) {
        AssetManager manager = Redundant.getManager();
        manager.load("image/pad.png", Texture.class);

        manager.finishLoading();

        texture = manager.get("image/pad.png", Texture.class);

        setSize(WIDTH, HEIGHT);
        setOrigin(ORIGIN_X, ORIGIN_Y);
        setColor(1, 1, 1, 1);
        this.side = side;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color c = getColor().cpy();
        c.a *= parentAlpha;
        batch.setColor(c);
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public void beat() {
        addAction(Actions.sequence(
                Actions.scaleTo(1.1f, 1.1f, BeatMap.SPB / 12f),
                Actions.scaleTo(1f, 1f, (BeatMap.SPB / 2f) - (BeatMap.SPB / 12f))
        ));
    }

    public void button(int direction) {
        Actor actor = null;
        Vector2 touch = null;
        switch (direction) {
            case BeatMap.UP:
                touch = new Vector2(getX(), getY() + ORIGIN_X);
                break;
            case BeatMap.DOWN:
                touch = new Vector2(getX(), getY() - ORIGIN_Y);
                break;
            case BeatMap.LEFT:
                touch = new Vector2(getX() - ORIGIN_X, getY());
                break;
            case BeatMap.RIGHT:
                touch = new Vector2(getX() + ORIGIN_Y, getY());
                break;
        }
        touch.add(ORIGIN_X, ORIGIN_Y);
        //getStage().addActor(new DebugActor(touch.x, touch.y));
        actor = getStage().hit(touch.x, touch.y, false);
        if (actor != null && actor instanceof BeatActor) {
            InputEvent ev = new InputEvent();
            ev.setType(InputEvent.Type.touchDown);
            ev.setStageX(touch.x);
            ev.setStageY(touch.y);

            actor.fire(ev);
        } else {
            // miss!!
            GameScreen gameScreen = GameScreen.getSingleton();
            gameScreen.resetMultiplier();
            if (side == 0)
                gameScreen.leftHealth.setHealth(gameScreen.leftHealth.getHealth() - DifficultyConstants.getHealthLostMiss(GameScreen.difficulty));
            if (side == 1)
                gameScreen.rightHealth.setHealth(gameScreen.rightHealth.getHealth() - DifficultyConstants.getHealthLostMiss(GameScreen.difficulty));

            getStage().addActor(ImageActor.makeMiss(touch.x, touch.y));
            // TODO miss indicator, kill combo
        }
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return null;
    }
}
