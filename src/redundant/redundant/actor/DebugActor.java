package redundant.redundant.actor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import redundant.redundant.Redundant;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 1:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class DebugActor extends Actor {

    private Texture texture;

    public DebugActor(float x, float y) {
        AssetManager manager = Redundant.getManager();
        manager.load("image/debug.png", Texture.class);
        manager.finishLoading();
        texture = manager.get("image/debug.png", Texture.class);
        addAction(Actions.delay(1, Actions.removeActor()));

        setX(x);
        setY(y);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY());
    }
}
