package dnomyar.combo.models;

import org.json.JSONObject;

/**
 * Created by Raymond on 2015-01-12.
 */
public class Config {
    private Info info;

    public Info getInfo() {
        return this.info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }


    public class Info {
        private int score;
        private int level;
        private int maxCombo;


        public int getScore() {
            return score;
        }

        public int getLevel() {
            return level;
        }

        public int getMaxCombo() {
            return maxCombo;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setMaxCombo(int maxCombo) {
            this.maxCombo = maxCombo;
        }
    }

    public String toJsonString() {
        JSONObject jo = new JSONObject();
        JSONObject infoJo = new JSONObject();

        try {
            infoJo.put("score", this.getInfo().getScore());
            infoJo.put("level", this.getInfo().getLevel());
            infoJo.put("max_combo", this.getInfo().getMaxCombo());
            jo.put("info", infoJo);
            return jo.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }

    }
}
