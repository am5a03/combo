package dnomyar.combo.scenes;

import android.util.Log;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.align.HorizontalAlign;

import java.util.Random;

import dnomyar.combo.huds.ScoreText;
import dnomyar.combo.managers.SceneManager;
import dnomyar.combo.models.Config;
import dnomyar.combo.utils.ColorUtils;
import dnomyar.combo.utils.Constants;
import dnomyar.combo.utils.JsonHelper;

/**
 * Created by Raymond on 2015-01-11.
 */
public class GameOverScene extends BaseScene implements MenuScene.IOnMenuItemClickListener, IOnSceneTouchListener {

    private long score;
    private long scoreCount; //For creating incremental score effect;
    private ScoreText scoreText;
    private Text gradingText;
    private Text highestScoreText;
    private MenuScene menuChildScene;
    private final int MENU_PLAY_AGAIN = 0;
    private final int MENU_GO_TO_MAIN_MENU = 1;


    @Override
    public void createScene() {
        setBackground(new Background(ColorUtils.getDefaultBg()));
        createMenuChildScene();
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(engine);
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_GAME_OVER;
    }

    @Override
    public void disposeScene() {

    }

    /**
     *
     * @param score
     */
    public void setScore(final long score) {
        this.score = score;
        this.registerUpdateHandler(new TimerHandler(0.001f, true, new ITimerCallback() {

            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (GameOverScene.this.scoreCount == score) {
                    GameOverScene.this.scoreText.setText("Final Score\n" + GameOverScene.this.scoreCount);
                    GameOverScene.this.gradingText.setText(GameOverScene.this.getGradingText());
                    GameOverScene.this.unregisterUpdateHandler(pTimerHandler);
                } else {
                    GameOverScene.this.scoreText.setText("Final Score\n" + ++GameOverScene.this.scoreCount);
                }

            }
        }));
    }

    private void createMenuChildScene() {
        this.setOnSceneTouchListener(this);
        menuChildScene = new MenuScene(camera);
        menuChildScene.setScale(1.0f);
        Config c = JsonHelper.getConfig(activity);



        final IMenuItem playAgainMenuItem = new ScaleMenuItemDecorator(new TextMenuItem(MENU_PLAY_AGAIN, resourcesManager.mFont, "PLAY AGAIN", vbom), 1.2f, 1);
        final IMenuItem goBackToMainMenuItem = new ScaleMenuItemDecorator(new TextMenuItem(MENU_GO_TO_MAIN_MENU, resourcesManager.mFont, "BACK TO MAIN", vbom), 1.2f, 1);
        this.scoreText = new ScoreText(CAMERA_CENTER_POS_X, CAMERA_HEIGHT - 120, resourcesManager.mFont, "Final Score\n 0123456789", new TextOptions(HorizontalAlign.CENTER), vbom);
        this.gradingText = new Text(CAMERA_CENTER_POS_X, CAMERA_HEIGHT - 350, resourcesManager.mFont, "abcdefghijklmnopqrstuvwxyz?!.", new TextOptions(HorizontalAlign.CENTER), vbom);
        this.scoreText.setScale(2.0f);
        this.gradingText.setScale(1.3f);
        this.gradingText.setText("");

        menuChildScene.addMenuItem(playAgainMenuItem);
        menuChildScene.addMenuItem(goBackToMainMenuItem);
        menuChildScene.setBackgroundEnabled(false);
        menuChildScene.setAlpha(100);

        menuChildScene.attachChild(this.scoreText);
        menuChildScene.attachChild(this.gradingText);

        // Child element, place it at center
        playAgainMenuItem.setPosition(0,0);
        goBackToMainMenuItem.setPosition(0, -30);


        menuChildScene.buildAnimations();
        menuChildScene.setOnMenuItemClickListener(this);
        setChildScene(menuChildScene);


    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        switch (pMenuItem.getID()){
            case MENU_PLAY_AGAIN:
                SceneManager.getInstance().loadGameScene(engine);
                return true;
            case MENU_GO_TO_MAIN_MENU:
                SceneManager.getInstance().loadMenuScene(engine);
                return true;
            default:
                return false;
        }
    }

    private String getGradingText() {
        String[] low = {
                Constants.GameOverMessage.YOU_AWAKE,
                Constants.GameOverMessage.HOW_SAD
        };

        String[] midLow = {
                Constants.GameOverMessage.OK,
                Constants.GameOverMessage.WORK_HARDER,
        };

        String[] mid = {
                Constants.GameOverMessage.NOT_BAD,
                Constants.GameOverMessage.AWWW
        };

        String[] midHigh = {
                Constants.GameOverMessage.SO_CLOSE,
                Constants.GameOverMessage.BRILLIANT
        };

        String[] high = {
                Constants.GameOverMessage.WICKED_SICK,
                Constants.GameOverMessage.LIKE_A_BOSS,
        };

        String[] veryHigh = {
                Constants.GameOverMessage.GOD_LIKE,
                Constants.GameOverMessage.YOU_MAD
        };

        Random r = new Random(System.currentTimeMillis());
        int idx = r.nextInt(2);
        if (this.score > 0 && this.score <= 200) {
            return low[idx];
        } else if (this.score > 200 && this.score <= 800) {
            return midLow[idx];
        } else if (this.score > 800 && this.score <= 1500) {
            return mid[idx];
        } else if (this.score > 1500 && this.score <= 3500) {
            return midHigh[idx];
        } else if (this.score > 3500 && this.score <= 7000) {
            return high[idx];
        } else if (this.score > 8000) {
            return veryHigh[idx];
        } else {
            return "";
        }

    };

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionDown()) {
            this.scoreCount = this.score;
        }
        return false;
    }
}
