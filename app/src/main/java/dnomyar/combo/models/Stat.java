package dnomyar.combo.models;

/**
 * Created by Raymond on 2015-01-15.
 */
public class Stat {
    private long score;
    private long combo;
    private long level;

    public long getScore() {
        return score;
    }

    public long getCombo() {
        return combo;
    }

    public long getLevel() {
        return level;
    }

    public void setCombo(long combo) {
        this.combo = combo;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public void setLevel(long level) {
        this.level = level;
    }
}
