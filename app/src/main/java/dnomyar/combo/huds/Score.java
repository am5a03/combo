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
public class Score extends Text {

    private int score = 0;

    public Score(float pX, float pY, IFont pFont, CharSequence pText, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pFont, pText, pVertexBufferObjectManager);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(pX, pY, pFont, pText, pVertexBufferObjectManager, pShaderProgram);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(pX, pY, pFont, pText, pVertexBufferObjectManager, pDrawType);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(pX, pY, pFont, pText, pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pFont, pText, pTextOptions, pVertexBufferObjectManager);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(pX, pY, pFont, pText, pTextOptions, pVertexBufferObjectManager, pShaderProgram);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(pX, pY, pFont, pText, pTextOptions, pVertexBufferObjectManager, pDrawType);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(pX, pY, pFont, pText, pTextOptions, pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pFont, pText, pCharactersMaximum, pVertexBufferObjectManager);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(pX, pY, pFont, pText, pCharactersMaximum, pVertexBufferObjectManager, pShaderProgram);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(pX, pY, pFont, pText, pCharactersMaximum, pVertexBufferObjectManager, pDrawType);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(pX, pY, pFont, pText, pCharactersMaximum, pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pFont, pText, pCharactersMaximum, pTextOptions, pVertexBufferObjectManager);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, ShaderProgram pShaderProgram) {
        super(pX, pY, pFont, pText, pCharactersMaximum, pTextOptions, pVertexBufferObjectManager, pShaderProgram);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType) {
        super(pX, pY, pFont, pText, pCharactersMaximum, pTextOptions, pVertexBufferObjectManager, pDrawType);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, VertexBufferObjectManager pVertexBufferObjectManager, DrawType pDrawType, ShaderProgram pShaderProgram) {
        super(pX, pY, pFont, pText, pCharactersMaximum, pTextOptions, pVertexBufferObjectManager, pDrawType, pShaderProgram);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, ITextVertexBufferObject pTextVertexBufferObject) {
        super(pX, pY, pFont, pText, pCharactersMaximum, pTextOptions, pTextVertexBufferObject);
    }

    public Score(float pX, float pY, IFont pFont, CharSequence pText, int pCharactersMaximum, TextOptions pTextOptions, ITextVertexBufferObject pTextVertexBufferObject, ShaderProgram pShaderProgram) {
        super(pX, pY, pFont, pText, pCharactersMaximum, pTextOptions, pTextVertexBufferObject, pShaderProgram);
    }

    public void addScore(int score) {
        this.score += score;
        this.setText("Score: " + score);
    }

    public void init() {
        this.setText("Score: " + score);
    }
}
