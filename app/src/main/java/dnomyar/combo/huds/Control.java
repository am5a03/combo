package dnomyar.combo.huds;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Raymond on 2015-01-06.
 */
public class Control extends Entity {
    private Rectangle[] buttons;
    private VertexBufferObjectManager vbom;

    private final static int NUM_OF_BTNS = 4;

    public Control(float pX, float pY, VertexBufferObjectManager vbom) {
        super(pX, pY);
        this.initButtons(vbom);
    }

    private void initButtons(VertexBufferObjectManager vbom) {
        this.buttons = new Rectangle[NUM_OF_BTNS];
        buttons[0] = new Rectangle(0, 0, 50, 50, vbom);
        buttons[0].setColor(1, 0, 0); //TODO

        buttons[1] = new Rectangle(50, 0, 50, 50, vbom);
        buttons[1].setColor(0, 1, 0); //TODO

        buttons[2] = new Rectangle(0, 50, 50, 50, vbom);
        buttons[2].setColor(1, 0, 1); //TODO

        buttons[3] = new Rectangle(50, 50, 50, 50, vbom);
        buttons[3].setColor(0, 1, 1); //TODO

        for (Rectangle button : buttons) {
            this.attachChild(button);
        }
    }


}
