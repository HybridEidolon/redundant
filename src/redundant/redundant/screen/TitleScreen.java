package redundant.redundant.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import redundant.redundant.Redundant;
import redundant.redundant.actor.ImageActor;
import redundant.redundant.actor.TextActor;
import redundant.redundant.data.DifficultyConstants;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 7:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class TitleScreen extends RiriScreen {
    private NinePatchDrawable np;
    private Button.ButtonStyle style;

    private Button easyButton;
    private Button normalButton;
    private Button hardButton;
    private Button insaneButton;

    private EventListener listener = new EventListener() {
        @Override
        public boolean handle(Event event) {
            if (event instanceof InputEvent) {
                InputEvent inputEvent = (InputEvent) event;
                if (inputEvent.getType() == InputEvent.Type.touchDown) {
                    Actor actor = inputEvent.getListenerActor();
                    if (actor == easyButton) {
                        gotoGame(DifficultyConstants.EASY);
                        return true;
                    }
                    if (actor == normalButton) {
                        gotoGame(DifficultyConstants.NORMAL);
                        return true;
                    }
                    if (actor == hardButton) {
                        gotoGame(DifficultyConstants.HARD);
                        return true;
                    }
                    if (actor == insaneButton) {
                        gotoGame(DifficultyConstants.INSANE);
                        return true;
                    }
                }
            }
            return false;
        }
    };

    {
        Texture texture = null;
        AssetManager manager = Redundant.getManager();
        manager.load("image/button9p.png", Texture.class);
        manager.finishLoading();
        texture = manager.get("image/button9p.png", Texture.class);
        np = new NinePatchDrawable(new NinePatch(texture, 2, 2, 2, 2));

        style = new Button.ButtonStyle(np, np, np);

        easyButton = new Button(style);
        easyButton.addActor(new TextActor("Easy", 8, 32));
        normalButton = new Button(style);
        normalButton.addActor(new TextActor("Normal", 8, 32));
        hardButton = new Button(style);
        hardButton.addActor(new TextActor("Hard", 8, 32));
        insaneButton = new Button(style);
        insaneButton.addActor(new TextActor("Insane", 8, 32));

        easyButton.addListener(listener);
        normalButton.addListener(listener);
        hardButton.addListener(listener);
        insaneButton.addListener(listener);
    }

    public TitleScreen() {
        Actor title = new ImageActor("image/logo.png", 64, 140);
        title.addAction(Actions.forever(Actions.sequence(
                Actions.scaleTo(1.5f, 1.5f, 1, Interpolation.sine),
                Actions.scaleTo(.5f, .5f, 1, Interpolation.sine)
        )));
        stage.addActor(title);
        stage.addActor(easyButton);
        stage.addActor(normalButton);
        stage.addActor(hardButton);
        stage.addActor(insaneButton);
        easyButton.setPosition(16, 32);
        easyButton.setSize(128, 48);
        normalButton.setPosition(128 + 48, 32);
        normalButton.setSize(128, 48);
        hardButton.setPosition(256 + 80, 32);
        hardButton.setSize(128, 48);
        insaneButton.setPosition(384+112, 32);
        insaneButton.setSize(128, 48);

    }

    @Override
    public void render(float delta) {
        stage.getCamera().position.set(320, 200, 0);
        super.render(delta);
    }

    public void gotoGame(int difficulty) {
        GameScreen gameScreen = new GameScreen(difficulty);
        Redundant.getSingleton().setScreen(gameScreen);
    }
}
