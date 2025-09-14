package spare.peetseater.peng;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import spare.peetseater.peng.scenes.Scene;

import static spare.peetseater.peng.Constants.VIRTUAL_HEIGHT;
import static spare.peetseater.peng.Constants.VIRTUAL_WIDTH;

public class GameAssets {
    private final AssetManager assetManager;

    public GameAssets() {
        assetManager = new AssetManager();
        Texture.setAssetManager(assetManager);
        FileHandleResolver resolver = assetManager.getFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
    }

    public static AssetDescriptor<BitmapFont> visitorFontDescriptor(int sizeInPixels, String key) {
        FreetypeFontLoader.FreeTypeFontLoaderParameter params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        params.fontFileName = "visitor/visitor1.ttf";
        params.fontParameters.size = sizeInPixels;
        return new AssetDescriptor<>(key, BitmapFont.class, params);
    }

    public BitmapFont getFont(AssetDescriptor<BitmapFont> key) {
        BitmapFont font;
        if (!assetManager.isLoaded(key)) {
            Gdx.app.log(getClass().getSimpleName(), String.format("Failed to load key %s", key.toString()));
            font = new BitmapFont();
        } else {
            font = assetManager.get(key);
        }
        font.setUseIntegerPositions(false);
        font.getData().setScale(
            (float) VIRTUAL_WIDTH / Gdx.graphics.getWidth(),
            (float) VIRTUAL_HEIGHT / Gdx.graphics.getHeight()
        );
        return font;
    }

    public void queueScene(Scene scene) {
        for (AssetDescriptor<?> assetDescriptor : scene.requiredAssets()) {
            assetManager.load(assetDescriptor);
        }
    }

    public void blockingLoad() {
        assetManager.finishLoading();
    }

    public static final String SCORE_FONT_KEY = "scorefont.ttf";
    public static final AssetDescriptor<BitmapFont> scoreFont = visitorFontDescriptor(72, SCORE_FONT_KEY);

    public static final String COUNTDOWN_FONT_KEY = "countdownfont.ttf";
    public static final AssetDescriptor<BitmapFont> countdownFont = visitorFontDescriptor(200, COUNTDOWN_FONT_KEY);

    public static final String RED_PADDLE_FILE = "red-paddle.png";
    public static final AssetDescriptor<Texture> redPaddle = new AssetDescriptor<>(RED_PADDLE_FILE, Texture.class);

    public static final String BLUE_PADDLE_FILE = "blue-paddle.png";
    public static final AssetDescriptor<Texture> bluePaddle = new AssetDescriptor<>(BLUE_PADDLE_FILE, Texture.class);

    public static final String BALL_FILE = "ball.png";
    public static final AssetDescriptor<Texture> ball = new AssetDescriptor<>(BALL_FILE, Texture.class);

    public static final String WALL_FILE = "wall.png";
    public static final AssetDescriptor<Texture> wall = new AssetDescriptor<>(WALL_FILE, Texture.class);

    public Texture getTexture(AssetDescriptor<Texture> assetDescriptor) {
        if (!assetManager.isLoaded(assetDescriptor)) {
            String reason = String.format("Could not load texture asset %s. Is the scene misconfigured?", assetDescriptor.toString());
            Gdx.app.log(getClass().getSimpleName(), reason);
            throw new RuntimeException(String.format("THE DEV IS AN IDIOT: %s", reason));
        }
        return assetManager.get(assetDescriptor);
    }

    public boolean isLoaded(Scene topScene) {
        boolean allLoaded = assetManager.update(17);
        // All assets are loaded?
        if (!allLoaded) {
            // No, but are all the assets for THIS scene loaded?
            boolean allSceneAssetsLoaded = true;
            for(AssetDescriptor<?> asset : topScene.requiredAssets()) {
                allSceneAssetsLoaded = allSceneAssetsLoaded && assetManager.isLoaded(asset);
                if (!allSceneAssetsLoaded) {
                    break;
                }
            }
            return allSceneAssetsLoaded;
        }
        return allLoaded;
    }

    public void unload(Scene scene) {
        for (AssetDescriptor<?> assetDescriptor : scene.requiredAssets()) {
            assetManager.unload(assetDescriptor.fileName);
        }
    }
}
