package dnomyar.combo.huds;

import org.andengine.entity.Entity;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import dnomyar.combo.listeners.IButtonTouchListener;
import dnomyar.combo.models.ComboColor;
import dnomyar.combo.utils.ColorUtils;

/**
 * Created by Raymond on 2015-01-06.
 */
public class Control extends Entity {
    private RectangleButton[] buttons;
    private VertexBufferObjectManager vbom;
    private float mCameraWidth;
    private float mCameraHeight;

    private final static int NUM_OF_BTNS = 7;

    public Control(float pX, float pY, float cameraWidth, float cameraHeight, VertexBufferObjectManager vbom) {
        super(pX, pY);
        this.mCameraHeight = cameraHeight;
        this.mCameraWidth = cameraWidth;
        this.initButtons(vbom);

    }

    private void initButtons(VertexBufferObjectManager vbom) {
        this.buttons = new RectangleButton[NUM_OF_BTNS];
        float upperRectangleWidth = this.mCameraWidth/3f;
        float lowerRectangleWidth = this.mCameraWidth/4;
        float rectangleHeight = this.mCameraHeight/8;

        buttons[0] = new RectangleButton(-lowerRectangleWidth/2, 0, lowerRectangleWidth, rectangleHeight, vbom, ComboColor.GREEN);
        buttons[0].setColor(ColorUtils.getDefaultGreen()); //TODO

        buttons[1] = new RectangleButton(-lowerRectangleWidth/2 + lowerRectangleWidth, 0, lowerRectangleWidth, rectangleHeight, vbom, ComboColor.BLUE);
        buttons[1].setColor(ColorUtils.getDefaultBlue()); //TODO

        buttons[2] = new RectangleButton(-lowerRectangleWidth/2 + lowerRectangleWidth * 2, 0, lowerRectangleWidth, rectangleHeight, vbom, ComboColor.INDIGO);
        buttons[2].setColor(ColorUtils.getDefaultIndigo()); //TODO

        buttons[3] = new RectangleButton(-lowerRectangleWidth/2 + lowerRectangleWidth * 3, 0, lowerRectangleWidth, rectangleHeight, vbom, ComboColor.PURPLE);
        buttons[3].setColor(ColorUtils.getDefaultPurple()); //TODO



        buttons[4] = new RectangleButton(-upperRectangleWidth/4, rectangleHeight, upperRectangleWidth, rectangleHeight, vbom, ComboColor.RED);
        buttons[4].setColor(ColorUtils.getDefaultRed());

        buttons[5] = new RectangleButton(-upperRectangleWidth/4 + upperRectangleWidth, rectangleHeight, upperRectangleWidth, rectangleHeight, vbom, ComboColor.ORANGE);
        buttons[5].setColor(ColorUtils.getDefaultOrange());

        buttons[6] = new RectangleButton(-upperRectangleWidth/4 + upperRectangleWidth * 2, rectangleHeight, upperRectangleWidth, rectangleHeight, vbom, ComboColor.YELLOW);
        buttons[6].setColor(ColorUtils.getDefaultYellow());


        for (RectangleButton button : buttons) {
            this.attachChild(button);
        }
    }

    public RectangleButton getButton(int idx) {
        return this.buttons[idx];
    }

    public RectangleButton[] getButtons() {
        return this.buttons;
    }

    /**
     * Register control buttons, so that when buttons are touched, relevant colour is dispatched to the board and
     * for checking
     * @param l
     */
    public void setListenerForColorButtons(IButtonTouchListener l) {
        for (RectangleButton r : this.buttons) {
            r.setButtonTouchListener(l);
        }
    }
}
