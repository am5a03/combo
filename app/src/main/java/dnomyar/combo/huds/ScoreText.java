package dnomyar.combo.huds;

import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.text.vbo.ITextVertexBufferObject;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Raymond on 2015-01-05.
 */
public class ScoreText extends Text {

    private int score = 0;

    public ScoreText(float pX, float pY, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pFont, pText, pTextOptions, pVertexBufferObjectManager);
        this.init();
    }

    public void addScore(int score) {
        this.score += score;
        this.setText("Score: " + score);
    }

    public void setScore(int score) {
        this.setText("Score: " + score);
    }

    public void init() {
        this.setText("Score: " + score);
    }
}
