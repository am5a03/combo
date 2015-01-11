package dnomyar.combo.scenes;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.adt.align.HorizontalAlign;

import dnomyar.combo.huds.ScoreText;
import dnomyar.combo.managers.SceneManager;
import dnomyar.combo.utils.ColorUtils;

/**
 * Created by Raymond on 2015-01-11.
 */
public class GameOverScene extends BaseScene implements MenuScene.IOnMenuItemClickListener {

    private long score;
    private long scoreCount; //For creating incremental score effect;
    private ScoreText scoreText;
    private MenuScene menuChildScene;
    private final int MENU_PLAY_AGAIN = 0;
    private final int MENU_GO_TO_MAIN_MENU = 1;


    @Override
    public void createScene() {
        setBackground(new Background(ColorUtils.getDefaultGrey()));
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
                    GameOverScene.this.unregisterUpdateHandler(pTimerHandler);
                } else {
                    GameOverScene.this.scoreText.setText("Final Score\n" + ++GameOverScene.this.scoreCount);
                }

            }
        }));
    }

    private void createMenuChildScene() {
        menuChildScene = new MenuScene(camera);
        menuChildScene.setScale(1.0f);

        final IMenuItem playAgainMenuItem = new ScaleMenuItemDecorator(new TextMenuItem(MENU_PLAY_AGAIN, resourcesManager.mFont, "PLAY AGAIN", vbom), 1.2f, 1);
        final IMenuItem goBackToMainMenuItem = new ScaleMenuItemDecorator(new TextMenuItem(MENU_GO_TO_MAIN_MENU, resourcesManager.mFont, "BACK TO MAIN", vbom), 1.2f, 1);
        this.scoreText = new ScoreText(CAMERA_CENTER_POS_X, CAMERA_HEIGHT - 120, resourcesManager.mFont, "Final Score\n 0123456789", new TextOptions(HorizontalAlign.CENTER), vbom);
        this.scoreText.setScale(2.0f);
        menuChildScene.addMenuItem(playAgainMenuItem);
        menuChildScene.addMenuItem(goBackToMainMenuItem);
        menuChildScene.setBackgroundEnabled(false);
        menuChildScene.setAlpha(100);
        menuChildScene.attachChild(this.scoreText);

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

    private void loadScore() {

    }

    private void persistHighestScore() {

    }
}
