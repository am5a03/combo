package dnomyar.combo.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import dnomyar.combo.managers.SceneManager;
import dnomyar.combo.models.Config;
import dnomyar.combo.utils.ColorUtils;
import dnomyar.combo.utils.JsonHelper;

/**
 * Created by Raymond on 2015-01-16.
 */
public class StatScene extends BaseScene {

    private int sceneCreatedFrom;

    private Text scoreText;
    private Text comboText;
    private Text levelText;

    @Override
    public void createScene() {
        createBackground();
        loadStatFromFile();
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(engine);
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_STAT;
    }

    @Override
    public void disposeScene() {
        this.dispose();
    }

    private void createBackground() {
        setBackground(new Background(ColorUtils.getDefaultBg()));
    }

    private void loadStatFromFile() {
        camera.setCenter(CAMERA_CENTER_POS_X, CAMERA_CENTER_POS_Y);
        Config c = JsonHelper.getConfig(activity);

        long level = c.getInfo().getLevel();
        long score = c.getInfo().getScore();
        long maxCombo = c.getInfo().getMaxCombo();

        this.scoreText = new Text(CAMERA_CENTER_POS_X, CAMERA_CENTER_POS_Y + CAMERA_CENTER_POS_Y/2, resourcesManager.mFont, "High score " + score, new TextOptions(HorizontalAlign.CENTER), vbom);
        this.comboText = new Text(CAMERA_CENTER_POS_X, CAMERA_CENTER_POS_Y + CAMERA_CENTER_POS_Y/2 - 150, resourcesManager.mFont, "Max combo " + maxCombo, new TextOptions(HorizontalAlign.CENTER), vbom);
        this.levelText = new Text(CAMERA_CENTER_POS_X, CAMERA_CENTER_POS_Y + CAMERA_CENTER_POS_Y/2 - 300, resourcesManager.mFont, "Max level " + level, new TextOptions(HorizontalAlign.CENTER), vbom);

        this.attachChild(scoreText);
        this.attachChild(comboText);
        this.attachChild(levelText);

    }

    public int getSceneCreatedFrom() {
        return sceneCreatedFrom;
    }

    public void setSceneCreatedFrom(int sceneCreatedFrom) {
        this.sceneCreatedFrom = sceneCreatedFrom;
    }
}
