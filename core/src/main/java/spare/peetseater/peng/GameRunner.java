package spare.peetseater.peng;

import com.badlogic.gdx.ApplicationListener;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameRunner implements ApplicationListener {
    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;
    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
