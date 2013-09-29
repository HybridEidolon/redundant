package redundant.redundant.data;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 6:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class RotateRightDirectionModifier implements DirectionModifier {
    @Override
    public int get(int direction) {
        switch (direction) {
            case BeatMap.LEFT:
                return BeatMap.UP;
            case BeatMap.DOWN:
                return BeatMap.LEFT;
            case BeatMap.RIGHT:
                return BeatMap.DOWN;
            case BeatMap.UP:
                return BeatMap.RIGHT;
            default:
                return 0;
        }
    }
}
