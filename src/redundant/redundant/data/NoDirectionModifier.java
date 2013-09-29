package redundant.redundant.data;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class NoDirectionModifier implements DirectionModifier {

    @Override
    public int get(int direction) {
        return direction;
    }
}
