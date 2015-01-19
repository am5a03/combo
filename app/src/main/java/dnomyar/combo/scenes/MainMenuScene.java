package dnomyar.combo.scenes;

import android.util.Log;

import org.andengine.entity.primitive.Rectangle;
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

import dnomyar.combo.managers.ResourcesManager;
import dnomyar.combo.managers.SceneManager;
import dnomyar.combo.utils.ColorUtils;
import dnomyar.combo.utils.Constants;

/**
 * Created by Raymond on 2015-01-04.
 */
public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener {

    private MenuScene menuChildScene;
    private final int MENU_PLAY = 0;
    private final int MENU_OPTIONS = 1;
    private final int MENU_STATS = 2;

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
        setBackground(new Background(ColorUtils.getDefaultBg()));
    }

    private void createMenuChildScene() {
        menuChildScene = new MenuScene(camera);
        menuChildScene.setScale(1.0f);

        menuChildScene.setPosition(camera.getCenterX(), camera.getCenterY());
        final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new TextMenuItem(MENU_PLAY, resourcesManager.mFont, "PLAY", vbom), 2.2f, 2.0f);
        final IMenuItem statMenuItem = new ScaleMenuItemDecorator(new TextMenuItem(MENU_STATS, resourcesManager.mFont, "Stats", vbom), 2.2f, 2.0f);
        Text title = new Text(0, CAMERA_CENTER_POS_Y - 120, resourcesManager.mFont, "Tile Combo", new TextOptions(HorizontalAlign.CENTER), vbom);
        title.setScale(2.0f);
        menuChildScene.attachChild(title);

        int sWidth = Constants.SQUARE_SIZE;
        int sHeight = Constants.SQUARE_SIZE;
        int xOffset = (int) (Constants.SQUARE_SIZE * 0.7);
        for(int i = 0; i < 14; i++) {
            Rectangle r = new Rectangle(-CAMERA_CENTER_POS_X + xOffset, CAMERA_CENTER_POS_Y - 250, sWidth, sHeight, vbom);
            r.setColor(ColorUtils.getRandomColor());
            xOffset += sWidth + 5;
            menuChildScene.attachChild(r);
        }

        menuChildScene.addMenuItem(playMenuItem);
        menuChildScene.addMenuItem(statMenuItem);

        menuChildScene.buildAnimations();
        menuChildScene.setBackgroundEnabled(false);

        // Child element, place it at center
        playMenuItem.setPosition(0,0);
        statMenuItem.setPosition(0, -150);

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
            case MENU_STATS:
                SceneManager.getInstance().createStatScene(MENU_PLAY, engine);
                return true;
            default:
                return false;
        }
    }
}
