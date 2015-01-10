package dnomyar.combo.scenes;

import android.util.Log;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import dnomyar.combo.huds.Control;
import dnomyar.combo.huds.GameBoard;
import dnomyar.combo.huds.Score;
import dnomyar.combo.listeners.IGameBoardStateListener;
import dnomyar.combo.managers.SceneManager;
import dnomyar.combo.managers.SceneManager.SceneType;
import dnomyar.combo.utils.ColorUtils;

/**
 * Created by Raymond on 2015-01-04.
 */
public class GameScene extends BaseScene implements IGameBoardStateListener {

    private HUD gameHUD;
    private Score score;
    private Control control;

    @Override
    public void createScene() {
        createBackground();
        createHUD();
        createGameBoard();
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
        setBackground(new Background(ColorUtils.getDefaultBg()));
    }

    private void createHUD() {
        gameHUD = new HUD();
        camera.setCenter(CAMERA_CENTER_POS_X, CAMERA_CENTER_POS_Y);
        score = new Score(CAMERA_CENTER_WIDTH/2, CAMERA_HEIGHT - 50, resourcesManager.mFont, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
        score.init();

        control = new Control(CAMERA_CENTER_POS_X/2, CAMERA_CENTER_POS_Y/6, CAMERA_WIDTH, CAMERA_HEIGHT, vbom);
        gameHUD.attachChild(score);
        gameHUD.attachChild(control);
        gameHUD.registerTouchArea(control.getButton(0));
        gameHUD.registerTouchArea(control.getButton(1));
        gameHUD.registerTouchArea(control.getButton(2));
        gameHUD.registerTouchArea(control.getButton(3));

//        RectangleButton b = new RectangleButton(0, CAMERA_HEIGHT/6, CAMERA_CENTER_WIDTH/2, CAMERA_HEIGHT/6, vbom);
//        gameHUD.attachChild(b);
//        gameHUD.registerTouchArea(b);
        gameHUD.setTouchAreaBindingOnActionDownEnabled(true);
        gameHUD.setTouchAreaBindingOnActionMoveEnabled(false);

//        score.addScore(10000);
        camera.setHUD(gameHUD);
    }

    private void createGameBoard() {
        int tempSize = 1;
        GameBoard b = new GameBoard(CAMERA_CENTER_POS_X, CAMERA_CENTER_POS_Y + (int)(CAMERA_HEIGHT*0.1), CAMERA_WIDTH, (int)(CAMERA_HEIGHT/2), vbom, tempSize);
        control.setListenerForColorButtons(b);
        this.attachChild(b);
        b.setGameBoardStateListener(this);
    }

    @Override
    public void onWon() {
        Log.d("GameScene", "Won");
    }

    @Override
    public void onMiss() {
        Log.d("GameScene", "Miss");
    }

    @Override
    public void onHit() {
        Log.d("GameScene", "Hit");
    }
}
