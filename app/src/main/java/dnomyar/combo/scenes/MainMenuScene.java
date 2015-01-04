package dnomyar.combo.scenes;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;

import dnomyar.combo.managers.SceneManager;

/**
 * Created by Raymond on 2015-01-04.
 */
public class MainMenuScene extends BaseScene {

    private MenuScene menuChildScene;
    private final int MENU_PLAY = 0;
    private final int MENU_OPTIONS = 1;

    @Override
    public void createScene() {
        createBackgroud();
    }

    @Override
    public void onBackKeyPressed() {
        System.exit(0);
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_MENU;
    }

    @Override
    public void disposeScene() {

    }

    private void createBackgroud() {
        setBackground(new Background(0,0,0));
    }

    private void createMenuChildScene() {

    }
}
