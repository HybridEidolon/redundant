package redundant.redundant.actor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import redundant.redundant.Redundant;
import redundant.redundant.data.BeatMap;
import redundant.redundant.screen.GameScreen;
import redundant.redundant.screen.TitleScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 9:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class InputActor extends Actor implements EventListener {

    private PadActor leftPad;
    private PadActor rightPad;

    public InputActor(PadActor leftPad, PadActor rightPad) {
        addListener(this);
        this.leftPad = leftPad;
        this.rightPad = rightPad;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    @Override
    public boolean handle(Event event) {
        InputEvent inputEvent = null;
        try {
            inputEvent = (InputEvent)event;

            if (inputEvent.getType() == InputEvent.Type.keyDown) {
                switch (inputEvent.getKeyCode()) {
                    case Input.Keys.W:
                        leftPad.button(BeatMap.UP);
                        break;
                    case Input.Keys.S:
                        leftPad.button(BeatMap.DOWN);
                        break;
                    case Input.Keys.A:
                        leftPad.button(BeatMap.LEFT);
                        break;
                    case Input.Keys.D:
                        leftPad.button(BeatMap.RIGHT);
                        break;


                    case Input.Keys.I:
                        rightPad.button(BeatMap.UP);
                        break;
                    case Input.Keys.K:
                        rightPad.button(BeatMap.DOWN);
                        break;
                    case Input.Keys.J:
                        rightPad.button(BeatMap.LEFT);
                        break;
                    case Input.Keys.L:
                        rightPad.button(BeatMap.RIGHT);
                        break;

                    case Input.Keys.ESCAPE:
                        Redundant.getSingleton().setScreen(new TitleScreen());
                        GameScreen.getSingleton().getBeatMap().stopMusic();
                        break;
                }
                return true;
            }
        } catch (Exception e) {

        }
        return false;
    }
}
