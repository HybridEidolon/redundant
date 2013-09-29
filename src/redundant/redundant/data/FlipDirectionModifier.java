package redundant.redundant.data;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 4:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlipDirectionModifier implements DirectionModifier {
    @Override
    public int get(int direction) {
        if (direction == BeatMap.UP)
            return BeatMap.DOWN;
        else if (direction == BeatMap.DOWN)
            return BeatMap.UP;
        else
            return direction;
    }
}
