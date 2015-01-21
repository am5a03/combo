package dnomyar.combo.scenes;

import android.util.Log;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
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
public class GameScene extends BaseScene implements IGameBoardStateListener, MenuScene.IOnMenuItemClickListener {

    private static final int START_LEVEL = 1;
    private static final int MAX_LEVEL = 140;
    private static final int PENALTY_TIME = 3000;

    private static final int BASE_BONUS_TIME = 2000;
    private static final int BASE_COMBO_BONUS_TIME = 500;

    private static final float DEFAULT_GAME_TIME = 60000f;
    private static final float DEFAULT_COMBO_TIME = 10000f;

    private static final long END_TIME = 45000; //End after x milliseconds
    private static final long COMBO_END_TIME = 5000;

    protected static final int MENU_PAUSE = 0;

    private HUD mGameHUD;
    private ScoreText mScoreText;
    private ComboText mComboText;
    private Control mControl;
    private ProgressBar mProgressBar;
    private ProgressBar mComboProgressBar;
    private MenuScene mMenuScene;

    private long mCurrentTime;
    private long mRemainingTime;
    private long mComboRemainingTime;
    private int mLevel;
    private long mEstFinishTime;
    private long mEstComboFinishTime;
    private int mComboCount = 0;
    private long mScore = 0;
    private int mCurrentScore = 0;
    private long mMaxCombo = 0;

    private boolean isPaused = false;

    @Override
    public void createScene() {
        createBackground();
        createHUD();
        createMenuScene();
        this.mLevel = START_LEVEL;
        this.mScore = 0;
        this.mCurrentTime = System.currentTimeMillis();
        this.mRemainingTime = END_TIME;
        this.mEstFinishTime = mCurrentTime + mRemainingTime;
        this.createGameBoard(this.mLevel);
        this.registerUpdateHandler();
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(engine);
    }

    @Override
    public void onMenuKeyPressed() {
        if (this.hasChildScene()) {
            this.isPaused = false;
            for(RectangleButton rb : mControl.getButtons()) {
                this.mGameHUD.registerTouchArea(rb);
            }
            this.mCurrentTime = System.currentTimeMillis();
            this.mEstFinishTime = this.mCurrentTime + this.mRemainingTime;
            this.mEstComboFinishTime = this.mCurrentTime + this.mComboRemainingTime;
            this.mMenuScene.back();
        } else {
            // On pause
            this.isPaused = true;
            for(RectangleButton rb : mControl.getButtons()) {
                this.mGameHUD.unregisterTouchArea(rb);
            }
            this.mRemainingTime = this.mEstFinishTime - System.currentTimeMillis();
            this.mComboRemainingTime = Math.max(0, this.mEstComboFinishTime - System.currentTimeMillis());
            this.setChildScene(this.mMenuScene, false, true, true);

        }
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
        mGameHUD = new HUD();
        camera.setCenter(CAMERA_CENTER_POS_X, CAMERA_CENTER_POS_Y);
        mScoreText = new ScoreText(CAMERA_CENTER_POS_X/2, CAMERA_HEIGHT - 50, resourcesManager.mFont, "Score: 0123456789", new TextOptions(HorizontalAlign.LEFT), vbom);
        mComboText = new ComboText(CAMERA_CENTER_POS_X, CAMERA_HEIGHT - 90, resourcesManager.mFont, "x1234567890", new TextOptions(HorizontalAlign.RIGHT), vbom);
        mComboText.setText("");
        mControl = new Control(CAMERA_CENTER_POS_X/2, CAMERA_CENTER_POS_Y/6, CAMERA_WIDTH, CAMERA_HEIGHT, vbom);
        mProgressBar = new ProgressBar(CAMERA_CENTER_WIDTH, CAMERA_HEIGHT, CAMERA_WIDTH, 25, vbom);
        mComboProgressBar = new ProgressBar(CAMERA_CENTER_WIDTH, CAMERA_HEIGHT - 15, CAMERA_WIDTH/2, 10, vbom);


        mGameHUD.attachChild(mScoreText);
        mGameHUD.attachChild(mControl);
        mGameHUD.attachChild(mProgressBar);
        mGameHUD.attachChild(mComboText);
        mGameHUD.attachChild(mComboProgressBar);

        mProgressBar.setProgress(END_TIME / DEFAULT_GAME_TIME);
        mComboProgressBar.setProgress(0);


        for (RectangleButton rb : mControl.getButtons()) {
            mGameHUD.registerTouchArea(rb);
        }

        mGameHUD.setTouchAreaBindingOnActionDownEnabled(false);
        mGameHUD.setTouchAreaBindingOnActionMoveEnabled(false);

//        mScoreText.addScore(10000);
        camera.setHUD(mGameHUD);
    }

    private void createGameBoard(int level) {
        Log.d("Level", this.mLevel + "..... ");
        GameBoard b = new GameBoard(CAMERA_CENTER_POS_X, CAMERA_CENTER_POS_Y + (int)(CAMERA_HEIGHT*0.1), CAMERA_WIDTH, (int)(CAMERA_HEIGHT/2), vbom, level);
        mControl.setListenerForColorButtons(b);
        this.attachChild(b);
        b.setGameBoardStateListener(this);
    }

    private void registerUpdateHandler() {

        this.registerUpdateHandler(new TimerHandler(0.01f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                long currentTime = System.currentTimeMillis();
                long diffInGameTime = (mEstFinishTime - currentTime);
                long diffInComboTime = (mEstComboFinishTime - currentTime);
                diffInComboTime = (diffInComboTime < 0) ? 0 : diffInComboTime;

                GameScene.this.mProgressBar.setProgress(diffInGameTime / DEFAULT_GAME_TIME);
                GameScene.this.mComboProgressBar.setProgress(diffInComboTime / DEFAULT_COMBO_TIME);


                if (currentTime >= mEstComboFinishTime) {
                    GameScene.this.resetComob();
                }

                if (currentTime >= mEstFinishTime) {
                    GameScene.this.mProgressBar.setProgress(0);
                    GameScene.this.unregisterUpdateHandler(pTimerHandler);
                    GameScene.this.gameOver();
                }
                pTimerHandler.reset();
            }
        }));

    }

    private void createMenuScene() {
        this.mMenuScene = new MenuScene(camera);
        final IMenuItem pauseMenuItem = new ScaleMenuItemDecorator(new TextMenuItem(MENU_PAUSE, resourcesManager.mFont, "PAUSE", vbom), 1.0f, 1.0f);

        this.mMenuScene.addMenuItem(pauseMenuItem);
        this.mMenuScene.buildAnimations();
        this.mMenuScene.setBackgroundEnabled(false);
        this.mMenuScene.setOnMenuItemClickListener(this);
    }

    @Override
    public void onWon() {
        long currentTime = System.currentTimeMillis();
        if (currentTime >= mEstFinishTime) {
            return;
        }
        this.mLevel = Math.min(++this.mLevel, MAX_LEVEL);
        int multiplier = (this.mComboCount == 0) ? 1 : this.mComboCount;
        this.mScore += (this.mCurrentScore * multiplier == 0) ? 1 : this.mCurrentScore * multiplier;

        this.detachChildren();
        this.createGameBoard(this.mLevel);

        this.mScoreText.setScore(this.mScore);
        this.mCurrentScore = 0;

        Log.d("GameScene", "Won");
        if ((mEstFinishTime - currentTime) <= DEFAULT_GAME_TIME) {
            this.mEstFinishTime += BASE_BONUS_TIME;
            if (this.mEstComboFinishTime <= 0) {
                this.mEstComboFinishTime = System.currentTimeMillis() + COMBO_END_TIME;
            } else {
                this.mEstComboFinishTime += BASE_COMBO_BONUS_TIME; // Extending the combo time
            }
        }

        this.mComboCount++;
        this.mComboText.setComboTextAccordingToComboCount(this.mComboCount);

    }

    @Override
    public void onMiss() {
        long currentTime = System.currentTimeMillis();
        if (currentTime >= mEstFinishTime) {
            return;
        }
        Log.d("GameScene", "Miss");
        this.detachChildren();
        this.createGameBoard(this.mLevel);
        this.mComboCount = 0;
        this.mCurrentScore = 0;
        this.mEstFinishTime -= PENALTY_TIME;
        this.mProgressBar.setProgress((mEstFinishTime - currentTime) / DEFAULT_GAME_TIME);
        this.resetComob();
    }

    @Override
    public void onHit() {
        long currentTime = System.currentTimeMillis();
        if (currentTime >= mEstFinishTime) {
            return;
        }
        Log.d("GameScene", "Hit");
        this.mComboCount++;
        this.mCurrentScore++;
        this.mComboText.setComboTextAccordingToComboCount(this.mComboCount);
        if (this.mEstComboFinishTime <= 0) {
            this.mEstComboFinishTime = System.currentTimeMillis() + COMBO_END_TIME;
        } else {
            this.mEstComboFinishTime += BASE_COMBO_BONUS_TIME; // Extending the combo time
        }
        if (this.mComboCount > mMaxCombo) {
            this.mMaxCombo = this.mComboCount;
        }
    }


    /**
     * Gameover action
     */
    protected void gameOver() {
        int multiplier = (this.mComboCount == 0) ? 1 : this.mComboCount;
        this.mScore += this.mCurrentScore * multiplier;
        this.mScoreText.setScore(this.mScore);
        this.mGameHUD.clearTouchAreas();
        this.resetComob();

        Stat stat = new Stat();
        stat.setScore(this.mScore);
        stat.setLevel(this.mLevel);
        stat.setCombo(this.mMaxCombo);
        SceneManager.getInstance().createGameOverScene(stat);
    }

    private void resetComob() {
        this.mComboText.reset();
        this.mComboProgressBar.setProgress(0);
        this.mEstComboFinishTime = 0;
        this.mComboCount = 0;
        this.mComboRemainingTime = 0;
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()) {
            case MENU_PAUSE:
                return false;
            default:
                return false;
        }
    }
}
