package dnomyar.combo.scenes;

import android.util.Log;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import dnomyar.combo.huds.Control;
import dnomyar.combo.huds.GameBoard;
import dnomyar.combo.huds.ProgressBar;
import dnomyar.combo.huds.RectangleButton;
import dnomyar.combo.huds.Score;
import dnomyar.combo.listeners.IGameBoardStateListener;
import dnomyar.combo.managers.SceneManager;
import dnomyar.combo.managers.SceneManager.SceneType;
import dnomyar.combo.utils.ColorUtils;

/**
 * Created by Raymond on 2015-01-04.
 */
public class GameScene extends BaseScene implements IGameBoardStateListener {

    private static final int START_LEVEL = 1;
    private static final int MAX_LEVEL = 140;
    private static final int PENALTY_TIME = 3;
    private static final int BASE_BONUS_TIME = 1;
    private static final int DEFAULT_GAME_TIME = 60;

    private HUD gameHUD;
    private Score scoreText;
    private Control control;
    private ProgressBar progressBar;

    private int level;
    private float globalTime = 60f; //Set to 1 min
    private int comboCount = 0;
    private int score = 0;
    private int currentScore = 0;

    @Override
    public void createScene() {
        createBackground();
        createHUD();
        this.level = START_LEVEL;
        this.score = 0;
        this.globalTime = DEFAULT_GAME_TIME;
        this.createGameBoard(this.level);
        this.registerUpdateHandler();
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
        scoreText = new Score(CAMERA_CENTER_WIDTH/2, CAMERA_HEIGHT - 100, resourcesManager.mFont, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
        control = new Control(CAMERA_CENTER_POS_X/2, CAMERA_CENTER_POS_Y/6, CAMERA_WIDTH, CAMERA_HEIGHT, vbom);
        progressBar = new ProgressBar(CAMERA_CENTER_WIDTH, CAMERA_HEIGHT, CAMERA_WIDTH, 50, vbom);
//        progressBar.setProgress(40);
        gameHUD.attachChild(scoreText);
        gameHUD.attachChild(control);
        gameHUD.attachChild(progressBar);


        for (RectangleButton rb : control.getButtons()) {
            gameHUD.registerTouchArea(rb);
        }

        gameHUD.setTouchAreaBindingOnActionDownEnabled(true);
        gameHUD.setTouchAreaBindingOnActionMoveEnabled(false);

//        scoreText.addScore(10000);
        camera.setHUD(gameHUD);
    }

    private void createGameBoard(int level) {
        Log.d("Level", this.level + "..... ");
        GameBoard b = new GameBoard(CAMERA_CENTER_POS_X, CAMERA_CENTER_POS_Y + (int)(CAMERA_HEIGHT*0.1), CAMERA_WIDTH, (int)(CAMERA_HEIGHT/2), vbom, level);
        control.setListenerForColorButtons(b);
        this.attachChild(b);
        b.setGameBoardStateListener(this);
    }

    private void registerUpdateHandler() {

        this.registerUpdateHandler(new TimerHandler(1f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                globalTime--;
                Log.d("Time", globalTime/DEFAULT_GAME_TIME + "...");
                Log.d("Time", globalTime + "...");
                Log.d("Width", GameScene.this.progressBar.getWidth() + "...");
                GameScene.this.progressBar.setProgress(globalTime/DEFAULT_GAME_TIME);

                if(globalTime <= 0){
                    GameScene.this.progressBar.setProgress(0);
                    GameScene.this.unregisterUpdateHandler(pTimerHandler);
                    //GameOver();
                }
                pTimerHandler.reset();

            }
        }));
    }

    @Override
    public void onWon() {
        if (this.globalTime <= 0) {
            return;
        }
        this.level = Math.min(++this.level, MAX_LEVEL);
        int multiplier = (this.comboCount == 0) ? 1 : this.comboCount;
        this.score += this.currentScore * multiplier;
        this.detachChildren();
        this.createGameBoard(this.level);
        this.scoreText.setScore(this.score);
        this.currentScore = 0;
        Log.d("GameScene", "Won");
        if (this.globalTime <= DEFAULT_GAME_TIME) {
            this.globalTime += BASE_BONUS_TIME;
        }
    }

    @Override
    public void onMiss() {
        if (this.globalTime <= 0) {
            return;
        }
        Log.d("GameScene", "Miss");
        this.detachChildren();
        this.createGameBoard(this.level);
        this.comboCount = 0;
        this.currentScore = 0;
        this.globalTime -= PENALTY_TIME;
    }

    @Override
    public void onHit() {
        if (this.globalTime <= 0) {
            return;
        }
        Log.d("GameScene", "Hit");
        this.comboCount++;
        this.currentScore++;
    }


}
