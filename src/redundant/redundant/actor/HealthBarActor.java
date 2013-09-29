package redundant.redundant.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import redundant.redundant.Redundant;
import redundant.redundant.data.BeatMap;
import redundant.redundant.screen.GameScreen;
import redundant.redundant.screen.ScoreScreen;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 3:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class HealthBarActor extends Actor {

    public enum Direction {
        Left,
        Right
    }

    private Direction direction;
    private Texture texture;
    private float health = 1;
    private float time = 0;

    public static final float WIDTH = 320;
    public static final float HEIGHT = 64;
    private static ShaderProgram shader;

    public HealthBarActor(Direction direction) {
        AssetManager manager = Redundant.getManager();
        manager.load("image/healthbar.png", Texture.class);
        manager.finishLoading();
        texture = manager.get("image/healthbar.png", Texture.class);

        if (shader == null) {
            shader = new ShaderProgram(Gdx.files.internal("shader/healthbar.vert"), Gdx.files.internal("shader/healthbar.frag"));
            System.out.println(shader.getLog());
        }

        switch (direction) {
            case Left:
                //setPosition(0, 0);
                setOrigin(320, 0);
                break;
            case Right:
                setPosition(320, 0);
                //setOrigin(0, 0);
                break;
        }
        //setPosition(320, 0);
        setSize(WIDTH, HEIGHT);

        this.direction = direction;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        time += delta;
        //setHealth(getHealth() - (BeatMap.SPB / 4f) * delta);
        if (getHealth() <= 0) {
            GameScreen.getSingleton().getBeatMap().stopMusic();
            Redundant.getSingleton().setScreen(new ScoreScreen(GameScreen.getSingleton().getScore()));
        }
    }

    public void setHealth(float health) {
        float oldHealth = this.health;
        if (health > 1)
            health = 1;
        if (health < 0)
            health = 0;
        this.health = health;

        // scale to target
        clearActions();
        addAction(Actions.scaleTo(health, 1, 0.5f, Interpolation.pow2Out));
    }

    public float getHealth() {
        return health;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setShader(shader);
        shader.setUniformf("time", time);
        Color c = getColor().cpy();
        c.a *= parentAlpha;
        batch.setColor(c);
        if (direction == Direction.Left)
            batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false, false);
        else
            batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), true, false);

        batch.setShader(null);
    }
}
