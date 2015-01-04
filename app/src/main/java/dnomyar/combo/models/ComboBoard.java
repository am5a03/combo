package dnomyar.combo.models;

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

    /**
     *
     * @param inputColors User input
     * @param idx Index
     * @return boolean
     */
    public boolean isMissed(ComboColor[] inputColors, int idx) {
        if (idx >= this.mBoardSize) {
            // Idx should not greater than size
            return true;
        }

        if (this.mComboColors[idx].equals(inputColors[idx])) {
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
    public boolean isWon(ComboColor[] inputColors) {

        int range = 0;

        if (inputColors.length == this.mBoardSize) {
            range = this.mBoardSize;
        } else {
            range = inputColors.length;
        }

        for (int i = 0; i < range; i++) {
            if (!inputColors[i].equals(this.mComboColors[i])) {
                return false;
            }
        }

        return false;
    }
}
