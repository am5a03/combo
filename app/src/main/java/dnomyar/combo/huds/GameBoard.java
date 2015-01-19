package dnomyar.combo.huds;

import android.util.Log;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.vbo.IRectangleVertexBufferObject;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

import java.lang.reflect.Method;
import java.util.ArrayList;

import dnomyar.combo.listeners.IButtonTouchListener;
import dnomyar.combo.listeners.IGameBoardStateListener;
import dnomyar.combo.managers.ResourcesManager;
import dnomyar.combo.models.ComboBoard;
import dnomyar.combo.models.ComboColor;
import dnomyar.combo.utils.ColorUtils;
import dnomyar.combo.utils.Constants;

/**
 * Created by Raymond on 2015-01-09.
 */
public class GameBoard extends Rectangle implements IButtonTouchListener {

    private static final int LINE_WARP_LIMIT = 14; // The squares should place at next line

    private ComboBoard mComboBoard;
    private ArrayList<ComboColor> mInputColors;
    private int mInputIndex;
    private IGameBoardStateListener mGameBoardListener;

    public GameBoard(float pX, float pY, float pWidth, float pHeight, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
    }

    public GameBoard(float pX, float pY, float pWidth, float pHeight, VertexBufferObjectManager pVertexBufferObjectManager, int size) {
        this(pX, pY, pWidth, pHeight, pVertexBufferObjectManager);
        this.mComboBoard = new ComboBoard(size);
        this.mInputColors = new ArrayList<ComboColor>();
        this.initGameboard(pVertexBufferObjectManager);
        this.setColor(ColorUtils.getDefaultBg());
    }

    private void initGameboard(VertexBufferObjectManager vbom) {
        ComboColor[] comboColors = this.mComboBoard.getComboColors();
        int sWidth = Constants.SQUARE_SIZE;
        int sHeight = Constants.SQUARE_SIZE;
        int xOffset = (int) (Constants.SQUARE_SIZE * 0.7);
        int yStart = (int) (this.getHeight() - (Constants.SQUARE_SIZE * 0.833)); //Make sure the square is placed at center of the board
        int count = 0;
        for(ComboColor color : comboColors) {
            count++;
            Rectangle r = new Rectangle(xOffset, yStart, sWidth, sHeight, vbom);
            try {
                Class clazz = ColorUtils.class;
                Method m = clazz.getMethod("getDefault" + color, null);
                Color c = (Color) m.invoke(null, null);
                r.setColor(c);
                Log.d("Init Game", r.getColor() + " ");
            } catch (Exception e) {
                e.printStackTrace();
            }
            xOffset += sWidth + 5;
            //Warp line
            if (count >= LINE_WARP_LIMIT && count % LINE_WARP_LIMIT == 0) {
                yStart -= (sHeight + 10);
                xOffset = (int) (Constants.SQUARE_SIZE * 0.7);
            }
            this.attachChild(r);
        }
    }

    @Override
    public void onButtonTouched(ComboColor c) {
        this.mInputColors.add(c);
        this.mInputIndex = this.mInputColors.size() - 1;

        if (this.mComboBoard.isMissed(mInputColors, this.mInputIndex)) {
//            Log.d("Board", " Missed " + c);
            this.mGameBoardListener.onMiss();
        } else {
//            Log.d("Board", " Hit " + c);
            this.getChildByIndex(this.mInputIndex).setAlpha(150f);
            if (this.mComboBoard.isWon(mInputColors)) {
//                Log.d("Board", " Won ");
                this.mGameBoardListener.onWon();
            } else {
                this.mGameBoardListener.onHit();
            }
        }
    }

    public void setGameBoardStateListener(IGameBoardStateListener l) {
        this.mGameBoardListener = l;
    }
}
