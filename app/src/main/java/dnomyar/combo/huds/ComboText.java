package dnomyar.combo.huds;

import android.graphics.Color;

import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.text.vbo.ITextVertexBufferObject;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import dnomyar.combo.utils.ColorUtils;

/**
 * Created by Raymond on 2015-01-11.
 */
public class ComboText extends Text {

    public ComboText(float pX, float pY, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pFont, pText, pTextOptions, pVertexBufferObjectManager);
    }

    public void reset() {
        this.setText("");
        this.setColor(Color.WHITE);
    }

    public void setComboTextAccordingToComboCount(int count) {
        this.setText("x" + count);

        if (count <= 10) {
            this.setColor(Color.WHITE);
        } else if (count > 10 && count <= 30) {
            this.setColor(ColorUtils.getDefaultBlue());
        } else if (count > 30 && count <= 50) {
            this.setColor(ColorUtils.getDefaultGreen());
        } else if (count > 50 && count <= 100) {
            this.setColor(ColorUtils.getDefaultYellow());
        } else if (count > 100) {
            this.setColor(ColorUtils.getDefaultRed());
        }
        this.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.1f, getScaleByCount(count), getScaleByCount(count) * 1.5f), new ScaleModifier(0.1f, getScaleByCount(count) * 1.5f, getScaleByCount(count))));
    }

    public float getScaleByCount(int count) {
        if (count <= 10) {
           return 1.0f;
        } else if (count > 10 && count <= 30) {
           return 1.2f;
        } else if (count > 30 && count <= 50) {
           return 1.3f;
        } else if (count > 50 && count <= 100) {
           return 1.6f;
        } else if (count > 100) {
            return 2.0f;
        }
        return 1.0f;
    }

    public void resetScaleByCount(int count) {
        this.setScale(getScaleByCount(count));
    }
}
