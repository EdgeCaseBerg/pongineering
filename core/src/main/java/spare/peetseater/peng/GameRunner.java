package spare.peetseater.peng;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import spare.peetseater.peng.scenes.BattleScene;
import spare.peetseater.peng.scenes.Scene;

import java.util.Stack;

import static spare.peetseater.peng.Constants.VIRTUAL_HEIGHT;
import static spare.peetseater.peng.Constants.VIRTUAL_WIDTH;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameRunner implements ApplicationListener {
    public GameAssets assets;
    public Batch batch;
    OrthographicCamera camera;
    Stack<Scene> scenes;
    FitViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        camera.setToOrtho(false);
        camera.update();

        Scene scene = new BattleScene(this);
        assets = new GameAssets();
        assets.queueScene(scene);

        // Force the first scene to load all its assets right away
        assets.blockingLoad();

        scenes = new Stack<>();
        scenes.push(scene);
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) { Gdx.app.exit(); }
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        float delta = Gdx.graphics.getDeltaTime();
        scenes.peek().update(delta);

        batch.begin();
        scenes.peek().render(delta);
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        while (!scenes.isEmpty()) {
           scenes.pop();
        }
    }
}
