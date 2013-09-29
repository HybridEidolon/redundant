package redundant.redundant.data;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import redundant.redundant.Redundant;
import redundant.redundant.actor.BeatActor;
import redundant.redundant.actor.PadActor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 8:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeatMap {
    public static final int LENGTH = 16;
    private int[] directions = new int[LENGTH];

    public static final int NUM_DIFFICULTIES = 1;

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    public static final float BPM = 120;
    public static final float SPB = 1f/(BPM/60f);

    public static Sound kick;
    public static Sound snare;
    public static Sound tom1;
    public static Sound tom2;
    public static Sound hihat;

    private Music melody;

    public BeatMap(FileHandle handle) {
        _readFile(handle);
    }

    private void _readFile(FileHandle file) {
        Scanner scanner = new Scanner(file.read());

        String melodyFile = scanner.nextLine();

        AssetManager manager = Redundant.getManager();
        if (!melodyFile.equals("blank"))
            manager.load("melody/" + melodyFile, Music.class);
        manager.finishLoading();

        // load beat sounds
        if (!melodyFile.equals("blank"))
            melody = manager.get("melody/" + melodyFile, Music.class);

        // Read beats
        for (int i = 0; i < LENGTH; i++) {
            directions[i] = scanner.nextInt();
        }
    }

    public void playMusic() {
        if (melody != null) {
            if (melody.isPlaying()) {
                melody.stop();
            }
            melody.play();
        }
    }

    public void createActors(Stage stage, Group leftPad, Group rightPad, int difficulty, int beat, DirectionModifier leftModifier, DirectionModifier rightModifier) {
        if (directions[beat] > 3 || directions[beat] < 0) {
            return;
        }

        BeatActor leftBeat = new BeatActor(this, difficulty, beat, leftModifier.get(directions[beat]), 0);
        BeatActor rightBeat = new BeatActor(this, difficulty, beat, rightModifier.get(directions[beat]), 1);

        stage.addActor(leftBeat);
        stage.addActor(rightBeat);

        leftBeat.setPosition(leftPad.getX() + PadActor.ORIGIN_X - BeatActor.ORIGIN_Y, leftPad.getY() + PadActor.ORIGIN_X - BeatActor.ORIGIN_Y);
        leftBeat.spawnLoc.x = leftBeat.getX() - 16;
        leftBeat.spawnLoc.y = leftBeat.getY();
        rightBeat.setPosition(rightPad.getX() + PadActor.ORIGIN_X - BeatActor.ORIGIN_Y, rightPad.getY() + PadActor.ORIGIN_X - BeatActor.ORIGIN_Y);
        rightBeat.spawnLoc.x = rightBeat.getX() - 16;
        rightBeat.spawnLoc.y = rightBeat.getY();
    }

    public int getDirection(int beat) {
        return directions[beat];
    }

    public void stopMusic() {
        melody.stop();
    }
}
