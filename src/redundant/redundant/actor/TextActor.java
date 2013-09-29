package redundant.redundant.actor;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import redundant.redundant.Redundant;

/**
 * Created with IntelliJ IDEA.
 * User: Furyhunter
 * Date: 9/28/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class TextActor extends Actor {

    private BitmapFont font;


    private String text;

    public TextActor(String text, float x, float y) {
        AssetManager manager = Redundant.getManager();
        manager.load("font/font.fnt", BitmapFont.class);
        manager.finishLoading();
        font = manager.get("font/font.fnt", BitmapFont.class);

        if (text == null) {
            this.text = "";
        } else {
            this.text = text;
        }
        setPosition(x, y);
        setTouchable(Touchable.childrenOnly);
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        font.draw(batch, text, getX(), getY());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
