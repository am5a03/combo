package dnomyar.combo.managers;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import dnomyar.combo.models.Stat;
import dnomyar.combo.scenes.BaseScene;
import dnomyar.combo.scenes.GameOverScene;
import dnomyar.combo.scenes.GameScene;
import dnomyar.combo.scenes.LoadingScene;
import dnomyar.combo.scenes.MainMenuScene;
import dnomyar.combo.scenes.SplashScene;
import dnomyar.combo.scenes.StatScene;

/**
 * Created by Raymond on 2015-01-04.
 */
public class SceneManager {
    //---------------------------------------------
    // SCENES
    //---------------------------------------------

    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene loadingScene;
    private GameOverScene gameOverScene;
    private StatScene statScene;

    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------

    private static final SceneManager INSTANCE = new SceneManager();

    private SceneType currentSceneType = SceneType.SCENE_SPLASH;

    private BaseScene currentScene;

    private Engine engine = ResourcesManager.getInstance().engine;

    public enum SceneType
    {
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING,
        SCENE_GAME_OVER,
        SCENE_STAT
    }

    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------

    public void setScene(BaseScene scene)
    {
        engine.setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
    }

    public void setScene(SceneType sceneType)
    {
        switch (sceneType)
        {
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_GAME:
                setScene(gameScene);
                break;
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_LOADING:
                setScene(loadingScene);
                break;
            case SCENE_GAME_OVER:
                setScene(gameOverScene);
            case SCENE_STAT:
                setScene(menuScene);
                break;
            default:
                break;
        }
    }

    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------

    public static SceneManager getInstance()
    {
        return INSTANCE;
    }

    public SceneType getCurrentSceneType()
    {
        return currentSceneType;
    }

    public BaseScene getCurrentScene()
    {
        return currentScene;
    }

    public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
    {
        ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    private void disposeSplashScene()
    {
        ResourcesManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }

    public void createMenuScene()
    {
        ResourcesManager.getInstance().loadMenuResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        SceneManager.getInstance().setScene(menuScene);
        disposeSplashScene();
    }

    public void createGameOverScene(Stat stat) {
        gameOverScene = new GameOverScene();
        gameOverScene.setStat(stat);
        SceneManager.getInstance().setScene(gameOverScene);

        gameScene.disposeScene();
    }

    public void loadGameScene(final Engine mEngine) {
        setScene(loadingScene);
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback(){
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameResources();
                gameScene = new GameScene();
                setScene(gameScene);
            }
        }));
    }

    public void createStatScene(int createFrom, final Engine mEngine) {
        statScene = new StatScene();
        statScene.setSceneCreatedFrom(createFrom);
        setScene(statScene);
    }

    /**
     *
     * @param mEngine
     */
    public void loadMenuScene(final Engine mEngine) {
        setScene(loadingScene);
        if (gameScene != null) {
            gameScene.disposeScene();
        }
        ResourcesManager.getInstance().unloadGameTextures();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler)
            {
                mEngine.unregisterUpdateHandler(pTimerHandler);
//                ResourcesManager.getInstance().loadMenuTextures();
                setScene(menuScene);
            }
        }));
    }
}
