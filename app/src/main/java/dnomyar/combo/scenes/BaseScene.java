package dnomyar.combo.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;

import dnomyar.combo.managers.ResourcesManager;
import dnomyar.combo.managers.SceneManager.SceneType;

/**
 * Created by Raymond on 2015-01-04.
 */
public abstract class BaseScene extends Scene {

    protected static final int CAMERA_WIDTH = 480;
    protected static final int CAMERA_HEIGHT = 800;

    protected static final int CAMERA_CENTER_WIDTH = CAMERA_WIDTH / 2;
    protected static final int CAMERA_CENTER_HEIGHT = CAMERA_HEIGHT / 2;

    protected static final int CAMERA_CENTER_POS_X = CAMERA_WIDTH / 2;
    protected static final int CAMERA_CENTER_POS_Y = CAMERA_HEIGHT / 2;

    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------

    protected Engine engine;
    protected Activity activity;
    protected ResourcesManager resourcesManager;
    protected VertexBufferObjectManager vbom;
    protected Camera camera;

    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------

    public BaseScene()
    {
        this.resourcesManager = ResourcesManager.getInstance();
        this.engine = resourcesManager.engine;
        this.activity = resourcesManager.activity;
        this.vbom = resourcesManager.vbom;
        this.camera = resourcesManager.camera;
        createScene();
    }

    //---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------

    public abstract void createScene();

    public abstract void onBackKeyPressed();

    public abstract SceneType getSceneType();

    public abstract void disposeScene();
}
