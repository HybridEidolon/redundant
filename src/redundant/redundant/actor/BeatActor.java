package redundant.redundant.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import redundant.redundant.Redundant;
import redundant.redundant.data.BeatMap;
import redundant.redundant.data.DifficultyConstants;
import redundant.redundant.screen.GameScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 8:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeatActor extends Actor implements EventListener {

    private BeatMap map;
    private int beat;

    private Texture texture;

    public static final float ORIGIN_X = 16;
    public static final float ORIGIN_Y = ORIGIN_X;
    public static final float WIDTH = 32;
    public static final float HEIGHT = WIDTH;

    private boolean dying = false;

    private static ShaderProgram shader = null;
    private float time;

    public Vector2 spawnLoc = new Vector2();

    private int side;

    public BeatActor(BeatMap map, int difficulty, int beat, int direction, int side) {
        this.map = map;
        this.beat = beat;

        if (shader == null) {
            shader = new ShaderProgram(Gdx.files.internal("shader/healthbar.vert"), Gdx.files.internal("shader/healthbar.frag"));
            System.out.println(shader.getLog());
        }

        AssetManager manager = Redundant.getManager();
        manager.load("image/beat.png", Texture.class);
        manager.finishLoading();
        texture = manager.get("image/beat.png", Texture.class);

        // use the direction to set the action
        Action actionToAdd = Actions.moveBy(256, 256, BeatMap.SPB);
        MoveByAction secondAction = Actions.moveBy(256, 256, BeatMap.SPB / 2);
        switch (direction) {
            case BeatMap.UP:
                ((MoveByAction)actionToAdd).setAmount(0, PadActor.HEIGHT/2);
                secondAction.setAmount(0, 32);
                break;
            case BeatMap.DOWN:
                ((MoveByAction)actionToAdd).setAmount(0, -PadActor.HEIGHT/2);
                secondAction.setAmount(0, -32);
                break;
            case BeatMap.LEFT:
                ((MoveByAction)actionToAdd).setAmount(-PadActor.WIDTH/2, 0);
                secondAction.setAmount(-32, 0);
                break;
            case BeatMap.RIGHT:
                ((MoveByAction)actionToAdd).setAmount(PadActor.WIDTH/2, 0);
                secondAction.setAmount(32, 0);
                break;
        }
        final int safeSide = side;
        if (GameScreen.difficulty == DifficultyConstants.INSANE) {
            actionToAdd = Actions.parallel(actionToAdd, Actions.fadeOut(BeatMap.SPB / 1.2f));
        }
        addAction(Actions.sequence(actionToAdd,
                Actions.parallel(secondAction,
                        Actions.fadeOut(BeatMap.SPB / 2f)),
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        GameScreen gameScreen = GameScreen.getSingleton();
                        gameScreen.resetMultiplier();
                        switch (safeSide) {
                            case 0:
                                gameScreen.leftHealth.setHealth(gameScreen.leftHealth.getHealth() - DifficultyConstants.getHealthLostMiss(GameScreen.difficulty));
                                break;
                            case 1:
                                gameScreen.rightHealth.setHealth(gameScreen.rightHealth.getHealth() - DifficultyConstants.getHealthLostMiss(GameScreen.difficulty));
                                break;
                        }
                        return true;
                    }
                },
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        getStage().addActor(ImageActor.makeMiss(spawnLoc.x, spawnLoc.y));
                    }
                }),
                Actions.removeActor()));

        setOrigin(ORIGIN_X, ORIGIN_Y);
        setSize(WIDTH, HEIGHT);
        setTouchable(Touchable.disabled);

        setColor(1, 1, 1, 1);

        this.side = side;

        addListener(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        time += delta;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setShader(shader);
        shader.setUniformf("time", time * 10);
        Color c = getColor().cpy();
        c.a *= parentAlpha;
        batch.setColor(c);
        batch.draw(texture, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), 0, 0, texture.getWidth(), texture.getHeight(), false, false);
        batch.setShader(null);
    }

    @Override
    public boolean handle(Event event) {
        if (dying)
            return false;
        if (event instanceof InputEvent) {
            InputEvent inputEvent = (InputEvent) event;
            if (inputEvent.getType() == InputEvent.Type.touchDown) {
                // Touched
                Vector2 myPos = new Vector2(getX() + getOriginX(), getY() + getOriginY());
                Vector2 touchPos = new Vector2(inputEvent.getStageX(), inputEvent.getStageY());
                float dist = myPos.cpy().sub(touchPos).len();

                GameScreen gameScreen = GameScreen.getSingleton();

                float gain = 0;
                if (dist < WIDTH / 8) {// perfect
                    getStage().addActor(ImageActor.makePerfect(spawnLoc.x, spawnLoc.y));
                    gain = DifficultyConstants.getHealthGainPerfect(GameScreen.difficulty);
                    gameScreen.setScore(gameScreen.getScore() + gameScreen.getMultiplier() * DifficultyConstants.getScoreGainPerfect(gameScreen.getDifficulty()));

                    // multiplier
                    gameScreen.increaseMultiplier();
                } else if (dist < WIDTH / 4) {
                    getStage().addActor(ImageActor.makeGood(spawnLoc.x, spawnLoc.y));
                    gain = DifficultyConstants.getHealthGainGood(GameScreen.difficulty);
                    gameScreen.setScore(gameScreen.getScore() + gameScreen.getMultiplier() * DifficultyConstants.getScoreGainGood(gameScreen.getDifficulty()));
                } else if (dist < WIDTH / 2) {
                    getStage().addActor(ImageActor.makeBad(spawnLoc.x, spawnLoc.y));
                    gain = DifficultyConstants.getHealthGainBad(GameScreen.difficulty);
                    gameScreen.setScore(gameScreen.getScore() + gameScreen.getMultiplier() * DifficultyConstants.getScoreGainBad(gameScreen.getDifficulty()));

                    // reset multiplier
                    gameScreen.resetMultiplier();
                }

                dying = true;
                clearActions();
                addAction(Actions.sequence(
                        Actions.parallel(
                                Actions.fadeOut(1),
                                Actions.scaleTo(3, 3, .5f)),
                        Actions.removeActor()
                ));

                switch (side) {
                    case 0:
                        gameScreen.leftHealth.setHealth(gameScreen.leftHealth.getHealth() + gain);
                        break;
                    case 1:
                        gameScreen.rightHealth.setHealth(gameScreen.rightHealth.getHealth() + gain);
                        break;
                }
                return true;
            }
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
