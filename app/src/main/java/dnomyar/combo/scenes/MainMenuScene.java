package dnomyar.combo.scenes;

import android.util.Log;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import dnomyar.combo.managers.SceneManager;
import dnomyar.combo.utils.ColorUtils;

/**
 * Created by Raymond on 2015-01-04.
 */
public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {

    private MenuScene menuChildScene;
    private final int MENU_PLAY = 0;
    private final int MENU_OPTIONS = 1;

    @Override
    public void createScene() {
        createBackgroud();
        createMenuChildScene();
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
        setBackground(new Background(ColorUtils.getDefaultGrey()));
    }

    private void createMenuChildScene() {
        menuChildScene = new MenuScene(camera);
        menuChildScene.setScale(1.0f);
        Log.d("sss",  "Center X: " + camera.getCenterX());
        Log.d("sss",  "Center Y: " + camera.getCenterY());

        menuChildScene.setPosition(camera.getCenterX(), camera.getCenterY());
        final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new TextMenuItem(MENU_PLAY, resourcesManager.mFont, "PLAY", vbom), 1.2f, 1);
        Text title = new Text(0, CAMERA_CENTER_POS_Y - 120, resourcesManager.mFont, "Combo", new TextOptions(HorizontalAlign.CENTER), vbom);
        title.setScale(2.0f);
        menuChildScene.attachChild(title);
        menuChildScene.addMenuItem(playMenuItem);

        menuChildScene.buildAnimations();
        menuChildScene.setBackgroundEnabled(false);

        // Child element, place it at center
        playMenuItem.setPosition(0,0);

        menuChildScene.setOnMenuItemClickListener(this);
        setChildScene(menuChildScene);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch(pMenuItem.getID()) {
            case MENU_PLAY:
                SceneManager.getInstance().loadGameScene(engine);
                return true;
            case MENU_OPTIONS:
                return true;
            default:
                return false;
        }
    }
}
