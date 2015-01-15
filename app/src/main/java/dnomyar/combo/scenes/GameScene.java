package dnomyar.combo.scenes;

import android.util.Log;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import dnomyar.combo.huds.ComboText;
import dnomyar.combo.huds.Control;
import dnomyar.combo.huds.GameBoard;
import dnomyar.combo.huds.ProgressBar;
import dnomyar.combo.huds.RectangleButton;
import dnomyar.combo.huds.ScoreText;
import dnomyar.combo.listeners.IGameBoardStateListener;
import dnomyar.combo.managers.SceneManager;
import dnomyar.combo.managers.SceneManager.SceneType;
import dnomyar.combo.models.Stat;
import dnomyar.combo.utils.ColorUtils;

/**
 * Created by Raymond on 2015-01-04.
 */
public class GameScene extends BaseScene implements IGameBoardStateListener {

    private static final int START_LEVEL = 1;
    private static final int MAX_LEVEL = 140;
    private static final int PENALTY_TIME = 3000;

    private static final int BASE_BONUS_TIME = 2000;
    private static final int BASE_COMBO_BONUS_TIME = 500;

    private static final float DEFAULT_GAME_TIME = 60000f;
    private static final float DEFAULT_COMBO_TIME = 10000f;

    private static final long END_TIME = 45000; //End after x milliseconds
    private static final long COMBO_END_TIME = 5000;

    private HUD gameHUD;
    private ScoreText scoreText;
    private ComboText comboText;
    private Control control;
    private ProgressBar progressBar;
    private ProgressBar comboProgressBar;

    private int level;
    private long estFinishTime;
    private long estComboFinishTime;
    private int comboCount = 0;
    private long score = 0;
    private int currentScore = 0;
    private long maxCombo = 0;

    @Override
    public void createScene() {
        createBackground();
        createHUD();
        this.level = START_LEVEL;
        this.score = 0;
        this.estFinishTime = System.currentTimeMillis() + END_TIME;
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
        scoreText = new ScoreText(CAMERA_CENTER_POS_X/2, CAMERA_HEIGHT - 100, resourcesManager.mFont, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
        comboText = new ComboText(CAMERA_CENTER_POS_X, CAMERA_HEIGHT - 180, resourcesManager.mFont, "x1234567890", new TextOptions(HorizontalAlign.RIGHT), vbom);
        comboText.setText("");
        control = new Control(CAMERA_CENTER_POS_X/2, CAMERA_CENTER_POS_Y/6, CAMERA_WIDTH, CAMERA_HEIGHT, vbom);
        progressBar = new ProgressBar(CAMERA_CENTER_WIDTH, CAMERA_HEIGHT, CAMERA_WIDTH, 50, vbom);
        comboProgressBar = new ProgressBar(CAMERA_CENTER_WIDTH, CAMERA_HEIGHT - 30, CAMERA_WIDTH/2, 10, vbom);


        gameHUD.attachChild(scoreText);
        gameHUD.attachChild(control);
        gameHUD.attachChild(progressBar);
        gameHUD.attachChild(comboText);
        gameHUD.attachChild(comboProgressBar);

        progressBar.setProgress(END_TIME/DEFAULT_GAME_TIME);
        comboProgressBar.setProgress(0);


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

        this.registerUpdateHandler(new TimerHandler(0.01f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                long currentTime = System.currentTimeMillis();
                long diffInGameTime = (estFinishTime - currentTime);
                long diffInComboTime = (estComboFinishTime - currentTime);
                diffInComboTime = (diffInComboTime < 0) ? 0 : diffInComboTime;

                GameScene.this.progressBar.setProgress(diffInGameTime/DEFAULT_GAME_TIME);
                GameScene.this.comboProgressBar.setProgress(diffInComboTime/DEFAULT_COMBO_TIME);


                if (currentTime >= estComboFinishTime) {
                    GameScene.this.resetComob();
                }

                if (currentTime >= estFinishTime) {
                    GameScene.this.progressBar.setProgress(0);
                    GameScene.this.unregisterUpdateHandler(pTimerHandler);
                    GameScene.this.gameOver();
                }
                pTimerHandler.reset();
            }
        }));

    }

    @Override
    public void onWon() {
        long currentTime = System.currentTimeMillis();
        if (currentTime >= estFinishTime) {
            return;
        }
        this.level = Math.min(++this.level, MAX_LEVEL);
        int multiplier = (this.comboCount == 0) ? 1 : this.comboCount;
        this.score += (this.currentScore * multiplier == 0) ? 1 : this.currentScore * multiplier;

        this.detachChildren();
        this.createGameBoard(this.level);

        this.scoreText.setScore(this.score);
        this.currentScore = 0;

        Log.d("GameScene", "Won");
        if ((estFinishTime - currentTime) <= DEFAULT_GAME_TIME) {
            this.estFinishTime += BASE_BONUS_TIME;
            if (this.estComboFinishTime <= 0) {
                this.estComboFinishTime = System.currentTimeMillis() + COMBO_END_TIME;
            } else {
                this.estComboFinishTime += BASE_COMBO_BONUS_TIME; // Extending the combo time
            }
        }

        this.comboCount++;
        this.comboText.setComboTextAccordingToComboCount(this.comboCount);

    }

    @Override
    public void onMiss() {
        long currentTime = System.currentTimeMillis();
        if (currentTime >= estFinishTime) {
            return;
        }
        Log.d("GameScene", "Miss");
        this.detachChildren();
        this.createGameBoard(this.level);
        this.comboCount = 0;
        this.currentScore = 0;
        this.estFinishTime -= PENALTY_TIME;
        this.progressBar.setProgress((estFinishTime - currentTime)/DEFAULT_GAME_TIME);
        this.resetComob();
    }

    @Override
    public void onHit() {
        long currentTime = System.currentTimeMillis();
        if (currentTime >= estFinishTime) {
            return;
        }
        Log.d("GameScene", "Hit");
        this.comboCount++;
        this.currentScore++;
        this.comboText.setComboTextAccordingToComboCount(this.comboCount);
        if (this.estComboFinishTime <= 0) {
            this.estComboFinishTime = System.currentTimeMillis() + COMBO_END_TIME;
        } else {
            this.estComboFinishTime += BASE_COMBO_BONUS_TIME; // Extending the combo time
        }
        if (this.comboCount > maxCombo) {
            this.maxCombo = this.comboCount;
        }
    }

    protected void gameOver() {
        int multiplier = (this.comboCount == 0) ? 1 : this.comboCount;
        this.score += this.currentScore * multiplier;
        this.scoreText.setScore(this.score);
        this.gameHUD.clearTouchAreas();
        this.resetComob();

        Stat stat = new Stat();
        stat.setScore(this.score);
        stat.setLevel(this.level);
        stat.setCombo(this.maxCombo);
        SceneManager.getInstance().createGameOverScene(stat);
    }

    private void resetComob() {
        this.comboText.reset();
        this.comboProgressBar.setProgress(0);
        this.estComboFinishTime = 0;
        this.comboCount = 0;
    }

}
