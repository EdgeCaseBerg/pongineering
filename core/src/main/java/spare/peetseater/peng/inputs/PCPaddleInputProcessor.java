package spare.peetseater.peng.inputs;

import com.badlogic.gdx.InputAdapter;

public class PCPaddleInputProcessor extends InputAdapter {

    private final int upKey;
    private final int downKey;
    public PADDLE_COMMAND currentDecision;

    public PCPaddleInputProcessor(int upKey, int downKey) {
        this.upKey = upKey;
        this.downKey = downKey;
        this.currentDecision = PADDLE_COMMAND.NOTHING;
    }

    public PADDLE_COMMAND getCurrentCommand() {
        return this.currentDecision;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == upKey) {
            currentDecision = PADDLE_COMMAND.UP;
            return true;
        }
        if (keycode == downKey) {
            currentDecision = PADDLE_COMMAND.DOWN;
            return true;
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        currentDecision = PADDLE_COMMAND.NOTHING;
        return super.keyUp(keycode);
    }
}
