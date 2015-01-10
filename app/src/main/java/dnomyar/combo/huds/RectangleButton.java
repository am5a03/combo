package dnomyar.combo.huds;

import android.util.Log;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.vbo.IRectangleVertexBufferObject;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import java.lang.reflect.Method;

import dnomyar.combo.listeners.IButtonTouchListener;
import dnomyar.combo.models.ComboColor;
import dnomyar.combo.utils.ColorUtils;

/**
 * Created by Raymond on 2015-01-07.
 */
public class RectangleButton extends Rectangle {

    private ComboColor mColor;
    private Color onClickColor;

    private IButtonTouchListener mButtonTouchListener;

    public RectangleButton(float pX, float pY, float pWidth, float pHeight, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
    }

    public RectangleButton(float pX, float pY, float pWidth, float pHeight, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager, pDrawType);
    }

    public RectangleButton(float pX, float pY, float pWidth, float pHeight, IRectangleVertexBufferObject pRectangleVertexBufferObject) {
        super(pX, pY, pWidth, pHeight, pRectangleVertexBufferObject);
    }

    public RectangleButton(float pX, float pY, float pWidth, float pHeight, VertexBufferObjectManager pVertexBufferObjectManager, ComboColor color) {
        this(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
        this.mColor = color;
    }

    @Override
    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
        Class clazz = ColorUtils.class;
        if (pSceneTouchEvent.isActionDown()) {
            try {
                Method m = clazz.getMethod("getPressed" + this.mColor, null);
                Color c = (Color) m.invoke(null, null);
                this.setColor(c);
                this.mButtonTouchListener.onButtonTouched(this.mColor);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else if (pSceneTouchEvent.isActionUp()) {
            try {
                Method m = clazz.getMethod("getDefault" + this.mColor, null);
                Color c = (Color) m.invoke(null, null);
                this.setColor(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    };

    /**
     * Register who is interested in the colour of this button
     * @param l The listener
     */
    public void setButtonTouchListener(IButtonTouchListener l) {
        this.mButtonTouchListener = l;
    }
}
