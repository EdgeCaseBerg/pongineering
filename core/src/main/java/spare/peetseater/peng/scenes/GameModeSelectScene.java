package spare.peetseater.peng.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import spare.peetseater.peng.GameAssets;
import spare.peetseater.peng.GameRunner;
import spare.peetseater.peng.inputs.PlayerSet;
import spare.peetseater.peng.objects.Ball;

import java.util.LinkedList;
import java.util.List;

import static spare.peetseater.peng.Constants.VIRTUAL_HEIGHT;
import static spare.peetseater.peng.Constants.VIRTUAL_WIDTH;

public class GameModeSelectScene implements Scene {

    public static final String P1_V_P2 =   "P1  v  P2 ";
    public static final String P1_V_CPU =  "P1  v  CPU";
    public static final String CPU_V_CPU = "CPU v  CPU";
    private final GameRunner gameRunner;
    List<String> buttons;
    int currentSelectedButton;

    public GameModeSelectScene(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
        this.currentSelectedButton = 0;
        this.buttons = new LinkedList<>();
        this.buttons.add(P1_V_P2);
        this.buttons.add(P1_V_CPU);
        this.buttons.add(CPU_V_CPU);
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            currentSelectedButton += 1;
            currentSelectedButton = Math.min(this.buttons.size() - 1, currentSelectedButton);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            currentSelectedButton -= 1;
            currentSelectedButton = Math.max(0, currentSelectedButton);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            gameRunner.setPlayersSet(getSelectedPlayerSet());
            gameRunner.changeToNewScene(new BattleScene(gameRunner));
        }
    }

    private PlayerSet getSelectedPlayerSet() {
        String buttonText = this.buttons.get(currentSelectedButton);
        switch (buttonText) {
            case P1_V_CPU:  return PlayerSet.PLAYER_VS_CPU;
            case P1_V_P2: return PlayerSet.PLAYER_VS_PLAYER;
            case CPU_V_CPU:
            default:  return PlayerSet.CPU_VS_CPU;
        }
    }

    @Override
    public void render(float delta) {
        BitmapFont font = gameRunner.assets.getFont(GameAssets.scoreFont);
        Texture wall = gameRunner.assets.getTexture(GameAssets.wall);

        float buttonHeight = Ball.CIRCUMFERENCE * 3;
        float windowTitleTextY = VIRTUAL_HEIGHT - buttonHeight;
        float buttonYStart = windowTitleTextY - buttonHeight * 2;
        float controlsY = buttonYStart - buttonHeight * this.buttons.size() * 2 - Ball.CIRCUMFERENCE;

        ScreenUtils.clear(Color.DARK_GRAY);
        font.draw(
            gameRunner.batch,
            "Game Mode Select",
            0, windowTitleTextY,
            VIRTUAL_WIDTH, Align.center, false
        );

        for (int i = 0; i < this.buttons.size(); i++) {
            float buttonY = buttonYStart - (buttonHeight + 1) * i * 2;
            font.draw(
                gameRunner.batch,
                this.buttons.get(i),
                VIRTUAL_WIDTH/3 - 100, buttonY,
                VIRTUAL_WIDTH, Align.left, false
            );
            if (currentSelectedButton == i) {
                gameRunner.batch.draw(wall, VIRTUAL_WIDTH/3f - 100 - Ball.CIRCUMFERENCE * 2, buttonY - buttonHeight/2f, Ball.CIRCUMFERENCE, Ball.CIRCUMFERENCE);
            }
        }

        font.draw(
            gameRunner.batch,
            "Controls:\nRed: W + S\nBlue: Up + Down",
            0, controlsY,
            VIRTUAL_WIDTH, Align.center, true
        );
    }

    @Override
    public List<AssetDescriptor<?>> requiredAssets() {
        List<AssetDescriptor<?>> list = new LinkedList<>();
        list.add(GameAssets.wall);
        list.add(GameAssets.scoreFont);
        return list;
    }
}
