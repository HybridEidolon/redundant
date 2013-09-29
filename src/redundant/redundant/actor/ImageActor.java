package redundant.redundant.actor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import redundant.redundant.Redundant;
import redundant.redundant.screen.GameScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 7:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class ImageActor extends Actor {
    private Texture texture;

    public ImageActor(String image, float x, float y) {
        AssetManager manager = Redundant.getManager();
        manager.load(image, Texture.class);
        manager.finishLoading();
        texture = manager.get(image, Texture.class);

        setPosition(x, y);
        setSize(texture.getWidth(), texture.getHeight());
        setOrigin(texture.getWidth() / 2, texture.getHeight() / 2);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color c = getColor().cpy();
        c.a *= parentAlpha;
        batch.setColor(c);
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false, false);
        batch.setColor(Color.WHITE);
    }

    public static ImageActor makeMiss(float x, float y) {
        ImageActor actor = new ImageActor("image/miss.png", x, y);
        actor.setScale(.5f);
        actor.addAction(Actions.sequence(
                Actions.parallel(
                        Actions.rotateBy(15, 0.5f, Interpolation.elastic),
                        Actions.scaleBy(.4f, .4f, 0.5f, Interpolation.elasticOut)
                ),
                Actions.delay(0.1f, Actions.sequence(
                        Actions.fadeOut(0.3f),
                        Actions.removeActor()
                ))
        ));

        return actor;
    }

    public static ImageActor makePerfect(float x, float y) {
        ImageActor actor = new ImageActor("image/perfect.png", x, y);
        actor.setScale(.8f);
        actor.addAction(Actions.parallel(
                Actions.parallel(
                        //Actions.rotateBy(-15, 0.5f, Interpolation.elastic),
                        Actions.scaleBy(1f, 1f, 0.5f, Interpolation.elasticOut)
                ),
                Actions.delay(0.6f, Actions.sequence(
                        Actions.fadeOut(0.2f),
                        Actions.removeActor()
                ))
        ));

        return actor;
    }

    public static ImageActor makeGood(float x, float y) {
        ImageActor actor = new ImageActor("image/good.png", x, y);
        actor.setScale(.6f);
        actor.addAction(Actions.parallel(
                Actions.parallel(
                        Actions.rotateBy(-15, 0.5f, Interpolation.elastic),
                        Actions.scaleBy(.4f, .4f, 0.5f, Interpolation.elasticOut)
                ),
                Actions.delay(0.3f, Actions.sequence(
                        Actions.fadeOut(0.1f),
                        Actions.removeActor()
                ))
        ));

        return actor;
    }

    public static ImageActor makeBad(float x, float y) {
        ImageActor actor = new ImageActor("image/bad.png", x, y);
        actor.setScale(.5f);
        actor.addAction(Actions.parallel(
                Actions.parallel(
                        Actions.rotateBy(-20, 0.5f),
                        Actions.scaleBy(.2f, .2f, 0.5f)
                ),
                Actions.delay(0.3f, Actions.sequence(
                        Actions.fadeOut(0.1f),
                        Actions.removeActor()
                ))
        ));

        return actor;
    }
}
