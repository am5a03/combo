package dnomyar.combo.scenes;

import org.andengine.entity.scene.background.Background;

import dnomyar.combo.managers.SceneManager;
import dnomyar.combo.utils.ColorUtils;

/**
 * Created by Raymond on 2015-01-16.
 */
public class StatScene extends BaseScene {
    @Override
    public void createScene() {

    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_STAT;
    }

    @Override
    public void disposeScene() {

    }

    private void createBackground() {
        setBackground(new Background(ColorUtils.getDefaultBg()));
    }
}
