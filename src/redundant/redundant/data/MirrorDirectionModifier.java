package redundant.redundant.data;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 4:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class MirrorDirectionModifier implements DirectionModifier {
    @Override
    public int get(int direction) {
        if (direction == BeatMap.LEFT)
            return BeatMap.RIGHT;
        else if (direction == BeatMap.RIGHT)
            return BeatMap.LEFT;
        else
            return direction;
    }
}
