package dnomyar.combo.scenes;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;

import dnomyar.combo.huds.Score;
import dnomyar.combo.managers.SceneManager;
import dnomyar.combo.managers.SceneManager.SceneType;

/**
 * Created by Raymond on 2015-01-04.
 */
public class GameScene extends BaseScene {

    private HUD gameHUD;
    private Score score;

    @Override
    public void createScene() {
        createBackground();
        createHUD();
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(engine);
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneType.SCENE_GAME;
    }

    @Override
    public void disposeScene() {
        camera.setHUD(null);
        camera.setCenter(CAMERA_CENTER_POS_X, CAMERA_CENTER_POS_Y);

        //TODO
    }

    private void createBackground() {
        setBackground(new Background(Color.BLUE));
    }

    private void createHUD() {
        gameHUD = new HUD();
        score = new Score(CAMERA_CENTER_WIDTH/2, CAMERA_HEIGHT - 20, resourcesManager.mFont, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
        score.init();
        gameHUD.attachChild(score);


        camera.setHUD(gameHUD);
    }
}
