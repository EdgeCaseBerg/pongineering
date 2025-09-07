package spare.peetseater.peng;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import spare.peetseater.peng.scenes.BattleScene;
import spare.peetseater.peng.scenes.Scene;

import java.util.Stack;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameRunner implements ApplicationListener {
    Stack<Scene> scenes;
    @Override
    public void create() {
        Scene scene = new BattleScene();
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
        float delta = Gdx.graphics.getDeltaTime();
        scenes.peek().update(delta);
        scenes.peek().render(delta);
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
