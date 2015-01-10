package dnomyar.combo.huds;

import android.graphics.Color;

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
            this.setScale(1.0f);
        } else if (count > 10 && count <= 30) {
            this.setColor(ColorUtils.getDefaultBlue());
            this.setScale(1.2f);
        } else if (count > 30 && count <= 50) {
            this.setColor(ColorUtils.getDefaultGreen());
            this.setScale(1.3f);
        } else if (count > 50 && count <= 100) {
            this.setColor(ColorUtils.getDefaultYellow());
            this.setScale(1.6f);
        } else if (count > 100) {
            this.setColor(ColorUtils.getDefaultRed());
            this.setScale(2.0f);
        }

    }
}
