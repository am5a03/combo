package dnomyar.combo.scenes;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import dnomyar.combo.managers.SceneManager;
import dnomyar.combo.managers.SceneManager.SceneType;

/**
 * Created by Raymond on 2015-01-04.
 */
public class SplashScene extends BaseScene {

    private Sprite splash;

    @Override
    public void createScene() {
        splash = new Sprite(0, 0, resourcesManager.splash_region, vbom)
        {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera)
            {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        splash.setScale(1.5f);
        splash.setPosition(CAMERA_WIDTH, CAMERA_HEIGHT);
        attachChild(splash);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public SceneType getSceneType() {
        return SceneType.SCENE_SPLASH;
    }

    @Override
    public void disposeScene() {
        splash.detachSelf();
        splash.dispose();
        this.detachSelf();
        this.dispose();
    }
}
