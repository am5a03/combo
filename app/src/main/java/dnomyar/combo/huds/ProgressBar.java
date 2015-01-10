package dnomyar.combo.huds;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by Raymond on 2015-01-10.
 */
public class ProgressBar extends Rectangle {

    private float progressWidth;

    public ProgressBar(float pX, float pY, float pWidth, float pHeight, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
        this.progressWidth = pWidth;
    }

    public void setProgress(float progress) {
        this.setWidth(this.progressWidth * progress);
    }
}