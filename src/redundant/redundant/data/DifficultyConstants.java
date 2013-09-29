package redundant.redundant.data;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 4:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class DifficultyConstants {
    public static final int EASY = 0;
    public static final int NORMAL = 1;
    public static final int HARD = 2;
    public static final int INSANE = 3;

    public static float getHealthGainPerfect(int difficulty) {
        switch (difficulty) {
            case EASY:
                return BeatMap.SPB / 2f;
            case NORMAL:
                return BeatMap.SPB / 4f;
            case HARD:
                return BeatMap.SPB / 8f;
            case INSANE:
                return BeatMap.SPB / 16f;
            default:
                return 0;
        }
    }

    public static float getHealthGainGood(int difficulty) {
        switch (difficulty) {
            case EASY:
                return BeatMap.SPB / 4f;
            case NORMAL:
                return BeatMap.SPB / 8f;
            case HARD:
                return BeatMap.SPB / 16f;
            case INSANE:
                return 0;
            default:
                return 0;
        }
    }

    public static float getHealthGainBad(int difficulty) {
        switch (difficulty) {
            case EASY:
                return BeatMap.SPB / 8f;
            case NORMAL:
                return 0;
            case HARD:
                return 0;
            case INSANE:
                return 0;
            default:
                return 0;
        }
    }

    public static float getHealthLostMiss(int difficulty) {
        switch (difficulty) {
            case EASY:
                return BeatMap.SPB / 8f;
            case NORMAL:
                return BeatMap.SPB / 4f;
            case HARD:
                return BeatMap.SPB / 4f;
            case INSANE:
                return BeatMap.SPB / 2f;
            default:
                return 0;
        }
    }

    public static int getScoreGainPerfect(int difficulty) {
        switch (difficulty) {
            case EASY:
                return 3;
            case NORMAL:
                return 5;
            case HARD:
                return 10;
            case INSANE:
                return 20;
            default:
                return 0;
        }
    }

    public static int getScoreGainGood(int difficulty) {
        switch (difficulty) {
            case EASY:
                return 2;
            case NORMAL:
                return 2;
            case HARD:
                return 5;
            case INSANE:
                return 5;
            default:
                return 0;
        }
    }

    public static int getScoreGainBad(int difficulty) {
        switch (difficulty) {
            case EASY:
                return 1;
            case NORMAL:
                return 1;
            case HARD:
                return 0;
            case INSANE:
                return 0;
            default:
                return 0;
        }
    }
}
