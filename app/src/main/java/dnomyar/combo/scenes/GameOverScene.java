package dnomyar.combo.scenes;

import android.util.Log;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
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
import dnomyar.combo.models.Stat;
import dnomyar.combo.utils.ColorUtils;
import dnomyar.combo.utils.Constants;
import dnomyar.combo.utils.JsonHelper;

/**
 * Created by Raymond on 2015-01-11.
 */
public class GameOverScene extends BaseScene implements MenuScene.IOnMenuItemClickListener, IOnSceneTouchListener {

    private Stat stat;
    private long scoreCount; //For creating incremental score effect;
    private ScoreText scoreText;
    private Text gradingText;
    private Text highestScoreText;
    private MenuScene menuChildScene;
    private final int MENU_PLAY_AGAIN = 0;
    private final int MENU_GO_TO_MAIN_MENU = 1;


    @Override
    public void createScene() {
        createBackgroud();
        createMenuChildScene();
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuScene(engine);
    }

    @Override
    public void onMenuKeyPressed() {

    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_GAME_OVER;
    }

    @Override
    public void disposeScene() {
        this.dispose();
    }

    private void createBackgroud() {
        setBackground(new Background(ColorUtils.getDefaultBg()));
    }

    /**
     *
     * @param stat
     */
    public void setStat(final Stat stat) {
        this.stat = stat;
        compareStat();
        this.registerUpdateHandler(new TimerHandler(0.001f, true, new ITimerCallback() {

            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                if (GameOverScene.this.scoreCount == stat.getScore()) {
                    GameOverScene.this.scoreText.setText("Final Score\n" + GameOverScene.this.scoreCount);

                    GameOverScene.this.gradingText.setText(GameOverScene.this.getGradingText());
                    GameOverScene.this.gradingText.setScale(8f);
                    GameOverScene.this.gradingText.registerEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.2f, 8, 2f)));
                    GameOverScene.this.gradingText.setColor(ColorUtils.getRandomColor());

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

        menuChildScene.setPosition(camera.getCenterX(), camera.getCenterY());
        final IMenuItem playAgainMenuItem = new ScaleMenuItemDecorator(new TextMenuItem(MENU_PLAY_AGAIN, resourcesManager.mFont, "PLAY AGAIN", vbom), 1.5f, 1.3f);
        final IMenuItem goBackToMainMenuItem = new ScaleMenuItemDecorator(new TextMenuItem(MENU_GO_TO_MAIN_MENU, resourcesManager.mFont, "BACK TO MAIN", vbom), 1.5f, 1.3f);
        this.scoreText = new ScoreText(0, CAMERA_CENTER_POS_Y - 120, resourcesManager.mFont, "Final Score\n 0123456789", new TextOptions(HorizontalAlign.CENTER), vbom);
        this.gradingText = new Text(0, CAMERA_CENTER_POS_Y - 350, resourcesManager.mFont, "abcdefghijklmnopqrstuvwxyz?!.", new TextOptions(HorizontalAlign.CENTER), vbom);

        this.scoreText.setScale(2.0f);
        this.gradingText.setScale(1.3f);
        this.gradingText.setText("");

        menuChildScene.addMenuItem(playAgainMenuItem);
        menuChildScene.addMenuItem(goBackToMainMenuItem);
        menuChildScene.attachChild(this.scoreText);
        menuChildScene.attachChild(this.gradingText);

        menuChildScene.buildAnimations();
        menuChildScene.setBackgroundEnabled(false);

        // Child element, place it at center
        playAgainMenuItem.setPosition(0, 0);
        goBackToMainMenuItem.setPosition(0, -100);

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

    private void compareStat() {
        Config c = JsonHelper.getConfig(activity);

        long score = c.getInfo().getScore();
        long maxCombo = c.getInfo().getMaxCombo();
        long level = c.getInfo().getLevel();

        if (stat.getScore() > score) {
            c.getInfo().setScore(stat.getScore());
        }

        if (stat.getCombo() > maxCombo) {
            c.getInfo().setMaxCombo(stat.getCombo());
        }

        if (stat.getLevel() > level) {
            c.getInfo().setLevel(stat.getLevel());
        }

        JsonHelper.setConfig(activity, c);
    }

    private String getGradingText() {
        String[] low = {
                Constants.GameOverMessage.YOU_AWAKE,
                Constants.GameOverMessage.HOW_SAD
        };

        String[] midLow = {
                Constants.GameOverMessage.GOOD_JOB,
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

        long score = stat.getScore();
        Random r = new Random(System.currentTimeMillis());
        int idx = r.nextInt(2);
        if (score > 0 && score <= 1000) {
            return low[idx];
        } else if (score > 1000 && score <= 2000) {
            return midLow[idx];
        } else if (score > 2000 && score <= 3200) {
            return mid[idx];
        } else if (score > 3200 && score <= 4200) {
            return midHigh[idx];
        } else if (score > 4200 && score <= 8000) {
            return high[idx];
        } else if (score > 8000) {
            return veryHigh[idx];
        } else {
            return "";
        }

    };

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionDown()) {
            this.scoreCount = stat.getScore();
        }
        return false;
    }
}
