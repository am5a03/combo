package dnomyar.combo.managers;

import android.content.res.AssetManager;
import android.graphics.Color;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import dnomyar.combo.activities.GameActivity;
import dnomyar.combo.utils.Constants;
import dnomyar.combo.utils.JsonHelper;


/**
 * Created by Raymond on 2015-01-04.
 */
public class ResourcesManager {
    //---------------------------------------------
    // VARIABLES
    //---------------------------------------------

    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public Engine engine;
    public GameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;

    //---------------------------------------------
    // TEXTURES & TEXTURE REGIONS
    //---------------------------------------------
    public ITextureRegion splash_region;
    private BitmapTextureAtlas mSplashTextureAtlas;
    private BitmapTextureAtlas mFontTextureAtlas;
    public Font mFont;
    public Font mSmallerFont;
    private TextureRegion mFaceTextureRegion;


    //---------------------------------------------
    // CLASS LOGIC
    //---------------------------------------------

    public void loadMenuResources()
    {
        loadMenuGraphics();
        loadMenuAudio();
        loadMenuFonts();
    }

    public void loadGameResources()
    {
        loadGameGraphics();
        loadGameFonts();
        loadGameAudio();
    }

    private void loadMenuGraphics()
    {

    }

    private void loadMenuFonts()
    {
        /* Load Font/Textures. */
        FontFactory.setAssetBasePath("gfx/fonts/");
        this.mFontTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        this.mFont = FontFactory.createFromAsset(activity.getFontManager(), this.mFontTextureAtlas, activity.getAssets(), "manteka.ttf", Constants.TEXT_SIZE_NORMAL, true, Color.WHITE);
//        this.mSmallerFont = FontFactory.createFromAsset(activity.getFontManager(), this.mFontTextureAtlas, activity.getAssets(), "manteka.ttf", Constants.TEXT_SIZE_NORMAL, true, Color.WHITE);
//        activity.getTextureManager().loadTexture(this.mFontTextureAtlas);
//        activity.getFontManager().loadFont(this.mFont);
        this.mFont.load();
    }

    private void loadMenuAudio()
    {

    }

    private void loadGameGraphics()
    {

    }

    private void loadGameFonts()
    {

    }

    private void loadGameAudio()
    {

    }

    public void loadSplashScreen()
    {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        mSplashTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
        splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mSplashTextureAtlas, activity, "splash.png", 0, 0);
        mSplashTextureAtlas.load();
    }

    public void unloadSplashScreen()
    {
        mSplashTextureAtlas.unload();
        splash_region = null;
    }

    public void unloadGameTextures() {
        //Todo
    }


    /**
     * @param engine
     * @param activity
     * @param camera
     * @param vbom
     * <br><br>
     * We use this method at beginning of game loading, to prepare Resources Manager properly,
     * setting all needed parameters, so we can latter access them from different classes (eg. scenes)
     */
    public static void prepareManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom)
    {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
    }

    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------

    public static ResourcesManager getInstance()
    {
        return INSTANCE;
    }
}

