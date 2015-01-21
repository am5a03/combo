package dnomyar.combo.scenes;

import dnomyar.combo.managers.SceneManager;
import dnomyar.combo.managers.SceneManager.SceneType;
import dnomyar.combo.utils.ColorUtils;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

/**
 * Created by Raymond on 2015-01-04.
 */
public class LoadingScene extends BaseScene {
    @Override
    public void createScene() {
        setBackground(new Background(ColorUtils.getDefaultBg()));
        attachChild(new Text(CAMERA_CENTER_POS_X, CAMERA_CENTER_POS_Y, resourcesManager.mFont, "Loading...", vbom));
    }

    @Override
    public void onBackKeyPressed() {
        return;
    }

    @Override
    public void onMenuKeyPressed() {
        return;
    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SCENE_LOADING;
    }

    @Override
    public void disposeScene() {
        this.dispose();
    }
}
