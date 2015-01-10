package dnomyar.combo.huds;

import android.util.Log;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

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

    private final static int NUM_OF_BTNS = 4;

    public Control(float pX, float pY, float cameraWidth, float cameraHeight, VertexBufferObjectManager vbom) {
        super(pX, pY);
        this.mCameraHeight = cameraHeight;
        this.mCameraWidth = cameraWidth;
        this.initButtons(vbom);

    }

    private void initButtons(VertexBufferObjectManager vbom) {
        this.buttons = new RectangleButton[NUM_OF_BTNS];
        buttons[0] = new RectangleButton(0, 0, this.mCameraWidth/2, this.mCameraHeight/6, vbom, ComboColor.GREEN);
//        rgb(76, 175, 80) GREEN
        buttons[0].setColor(ColorUtils.getDefaultGreen()); //TODO

        buttons[1] = new RectangleButton(this.mCameraWidth/2, 0, this.mCameraWidth/2, this.mCameraHeight/6, vbom, ComboColor.YELLOW);
//      rgb(255, 235, 59) YELLOW
        buttons[1].setColor(ColorUtils.getDefaultYellow()); //TODO

        buttons[2] = new RectangleButton(0, this.mCameraHeight/6, this.mCameraWidth/2, this.mCameraHeight/6, vbom, ComboColor.RED);
//      rgb(244, 67, 54) RED
        buttons[2].setColor(ColorUtils.getDefaultRed()); //TODO

        buttons[3] = new RectangleButton(this.mCameraWidth/2, this.mCameraHeight/6, this.mCameraWidth/2, this.mCameraHeight/6, vbom, ComboColor.BLUE);
//      rgb(33, 150, 243) BLUE
        buttons[3].setColor(ColorUtils.getDefaultBlue()); //TODO

        for (RectangleButton button : buttons) {
            this.attachChild(button);
        }
    }

    public RectangleButton getButton(int idx) {
        return this.buttons[idx];
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
