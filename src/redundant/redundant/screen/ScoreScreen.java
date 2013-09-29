package redundant.redundant.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import redundant.redundant.Redundant;
import redundant.redundant.actor.TextActor;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScoreScreen extends RiriScreen {

    private TextActor score;

    public ScoreScreen(int score) {
        stage.addActor(new TextActor("Game over!", 256, 340));
        stage.addActor(new TextActor("Your score is:", 192, 300));
        stage.addActor(new TextActor(Integer.toString(score), 128, 250));
        stage.addActor(new TextActor("Press enter to continue", 128, 200));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            Redundant.getSingleton().setScreen(new TitleScreen());
        }
    }
}
