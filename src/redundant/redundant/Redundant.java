package redundant.redundant;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.Stage;

import redundant.redundant.data.BeatMap;
import redundant.redundant.data.DifficultyConstants;
import redundant.redundant.screen.GameScreen;
import redundant.redundant.screen.TitleScreen;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 8:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class Redundant extends Game {

    public static void main(String[] args) {
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        conf.width = 640;
        conf.height = 400;
        conf.fullscreen = false;
        conf.useGL20 = true;
        conf.title = "ReReRe | レレレ | by IDOLA";
        conf.vSyncEnabled = true;

        LwjglApplication app = new LwjglApplication(new Redundant(), conf);
    }

    private static Redundant singleton;

    public static Redundant getSingleton() {
        return singleton;
    }

    public Redundant() {
        singleton = this;
    }

    private static AssetManager manager = new AssetManager();

    @Override
    public void create() {
        //GameScreen gameScreen = new GameScreen(DifficultyConstants.HARD);
        TitleScreen titleScreen = new TitleScreen();
        setScreen(titleScreen);
    }

    public static AssetManager getManager() {
        return manager;
    }
}