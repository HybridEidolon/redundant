package redundant.redundant.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import redundant.redundant.actor.HealthBarActor;
import redundant.redundant.actor.InputActor;
import redundant.redundant.actor.PadActor;
import redundant.redundant.actor.TextActor;
import redundant.redundant.data.*;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 8:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameScreen extends RiriScreen {
    private PadActor leftPad = new PadActor(0);
    private PadActor rightPad = new PadActor(1);
    private InputActor inputActor = new InputActor(leftPad, rightPad);
    private Actor cameraFocus = new Actor();
    public HealthBarActor leftHealth = new HealthBarActor(HealthBarActor.Direction.Left);
    public HealthBarActor rightHealth = new HealthBarActor(HealthBarActor.Direction.Right);
    private TextActor scoreActor = new TextActor("", 16, 350);
    private TextActor multiplierActor = new TextActor("", 16, 400);

    private BeatMap beatMap;
    private int currentMap = 0;

    public static final int NUM_MAPS = 8;

    private DirectionModifier leftMod = new NoDirectionModifier();
    private DirectionModifier rightMod = new NoDirectionModifier();

    private float time = 0;
    private int partials = 0;
    private int currentBeat = 0;
    private int currentMeasure = 0;
    private int currentPhrase = 0;

    public static int difficulty = DifficultyConstants.EASY;

    private int score = 0;
    private int multiplier = 1;

    private static GameScreen singleton;

    public static GameScreen getSingleton() {
        return singleton;
    }

    public GameScreen(int difficulty) {
        leftPad.setPosition(64, 120);
        rightPad.setPosition(640 - 64 - PadActor.WIDTH, 120);
        cameraFocus.setPosition(320, 200);
        stage.addActor(leftPad);
        stage.addActor(rightPad);
        stage.addActor(inputActor);
        stage.addActor(leftHealth);
        stage.addActor(rightHealth);
        stage.addActor(scoreActor);
        stage.addActor(multiplierActor);
        stage.setKeyboardFocus(inputActor);
        this.difficulty = difficulty;
        score = 0;

        singleton = this;

        pickNewBeatmap();
    }

    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public void render(float delta) {
        stage.getCamera().position.set(320, 200, 0);

        time += delta;
        if (time >= (BeatMap.SPB / 4f)) {
            partials++;
            beatMap.createActors(stage, leftPad, rightPad, difficulty, currentBeat, leftMod, rightMod);
            if (currentBeat == 3 && currentMeasure == 0) {
                beatMap.playMusic();
            }
            currentBeat += 1;


            time -= (BeatMap.SPB / 4f);
        }
        if (partials == 4) {
            leftPad.beat();
            rightPad.beat();
            partials = 0;
        }

        if (currentBeat == 16) {
            currentBeat = 0;
            currentMeasure++;
            // pick new modifiers if on > EASY

            leftPad.beat();
            rightPad.beat();
        }

        if (currentMeasure == 4) {
            currentMeasure = 0;
            currentPhrase++;
            System.out.println("Phrase");
            Random r = new Random();
            int nextSide = r.nextInt(2);
            int nextModifier = r.nextInt(3);

            DirectionModifier mod = null;
            switch (nextModifier) {
                case 0: //NoDirectionModifier
                    mod = new NoDirectionModifier();
                    break;
                case 1: //FlipDirectionModifier
                    mod = new FlipDirectionModifier();
                    break;
                case 2: //Mirror
                    mod = new MirrorDirectionModifier();
                    break;
                default:
                    mod = new NoDirectionModifier();
                    break;
            }

            if (nextSide == 0) {
                leftMod = mod;
            } else {
                rightMod = mod;
            }
        }

        if (currentPhrase == 2) {
            currentPhrase = 0;
            System.out.println("Change rhythm");
            pickNewBeatmap();
        }

        super.render(delta);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        scoreActor.setText(Integer.toString(score));
    }

    public void increaseMultiplier() {
        multiplier++;
        multiplierActor.setText("Multiplier: " + multiplier + "x");
    }

    public void resetMultiplier() {
        multiplier = 1;
        multiplierActor.setText("Multiplier: " + multiplier + "x");
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void pickNewBeatmap() {
        Random r = new Random();
        int rand = currentMap;
        while (rand == currentMap) {
            rand = r.nextInt(NUM_MAPS);
        }
        FileHandle[] handles = new FileHandle[NUM_MAPS];
        for (int i = 0; i < NUM_MAPS; i++) {
            handles[i] = Gdx.files.internal("beatmap/map" + i + ".map");
        }
        currentMap = rand;
        beatMap = new BeatMap(handles[currentMap]);
    }

    public BeatMap getBeatMap() {
        return beatMap;
    }
}
