package dnomyar.combo.models;

import java.util.ArrayList;

/**
 * Created by Raymond on 2015-01-04.
 */
public class ComboBoard {
    private ComboColor[] mComboColors;
    private int mBoardSize;

    public ComboBoard(int size) {
        this.mBoardSize = size;
        this.mComboColors = new ComboColor[size];
        this.generateSequence();
    }

    private void generateSequence() {
        for (int i = 0; i < this.mBoardSize; i++) {
            this.mComboColors[i] = ComboColor.randomColor();
        }
    }


    public boolean isMissed(ArrayList<ComboColor> inputColors, int idx) {
        if (idx >= this.mBoardSize) {
            // Idx should not greater than size
            return true;
        }

        if (this.mComboColors[idx].equals(inputColors.get(idx))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @param inputColors
     * @return
     */
    public boolean isWon(ArrayList<ComboColor> inputColors) {

        int range = 0;

        if (inputColors.size() == this.mBoardSize) {
            range = this.mBoardSize;
        } else {
            return false;
        }

        for (int i = 0; i < range; i++) {
            if (!inputColors.get(i).equals(this.mComboColors[i])) {
                return false;
            }
        }

        return true;
    }

    public ComboColor[] getComboColors() {
        return this.mComboColors;
    }

    public int getSize() {
        return this.mBoardSize;
    }
}
