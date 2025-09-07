package spare.peetseater.peng.scenes;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.LinkedList;
import java.util.List;

public class BattleScene implements Scene {

    int p1Score;
    int p2Score;

    enum Turn {
        Player1Turn,
        Player2Turn
    };
    Turn toServe;

    enum GameState {
        CountDown,
        Playing,
        Paused,
    }
    GameState currentState;

    BattleScene() {
        p1Score = p2Score = 0;
        toServe = MathUtils.randomBoolean() ? Turn.Player1Turn : Turn.Player2Turn;
        currentState = GameState.CountDown;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.LIGHT_GRAY);
    }

    @Override
    public List<AssetDescriptor<?>> requiredAssets() {
        return new LinkedList<>();
    }
}
