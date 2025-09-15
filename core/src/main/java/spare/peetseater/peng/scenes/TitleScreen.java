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
import spare.peetseater.peng.objects.Ball;

import java.util.LinkedList;
import java.util.List;

import static spare.peetseater.peng.Constants.VIRTUAL_HEIGHT;
import static spare.peetseater.peng.Constants.VIRTUAL_WIDTH;

public class TitleScreen implements Scene {
    private final GameRunner gameRunner;
    private List<AssetDescriptor<?>> requiredAssets;
    private int selectedButton;
    public final int START_BUTTON_CHOICE = 0;
    public final int QUIT_BUTTON_CHOICE = 1;

    public TitleScreen(GameRunner gameRunner) {
        this.gameRunner = gameRunner;

        requiredAssets = new LinkedList<>();
        requiredAssets.add(GameAssets.wall);
        requiredAssets.add(GameAssets.scoreFont);

        selectedButton = 0;
    }

    @Override
    public List<AssetDescriptor<?>> requiredAssets() {
        return requiredAssets;
    }

    @Override
    public void update(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectedButton -= 1;
            selectedButton = Math.max(0, selectedButton);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectedButton += 1;
            selectedButton = Math.max(Math.max(START_BUTTON_CHOICE, QUIT_BUTTON_CHOICE), selectedButton);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (selectedButton) {
                case START_BUTTON_CHOICE:
                    gameRunner.changeToNewScene(new GameModeSelectScene(gameRunner));
                    break;
                case QUIT_BUTTON_CHOICE:
                    Gdx.app.exit();
            }
        }
    }

    @Override
    public void render(float delta) {
        Texture wall = gameRunner.assets.getTexture(GameAssets.wall);
        BitmapFont font = gameRunner.assets.getFont(GameAssets.scoreFont);

        ScreenUtils.clear(Color.DARK_GRAY);

        for (int x = 0; x < VIRTUAL_WIDTH; x += (int)Ball.CIRCUMFERENCE) {
            gameRunner.batch.draw(wall, x, 0, Ball.CIRCUMFERENCE, Ball.CIRCUMFERENCE);
            gameRunner.batch.draw(wall, x, VIRTUAL_HEIGHT - Ball.CIRCUMFERENCE, Ball.CIRCUMFERENCE, Ball.CIRCUMFERENCE);
        }
        for (int y = 0; y < VIRTUAL_HEIGHT; y += (int)Ball.CIRCUMFERENCE) {
            gameRunner.batch.draw(wall, 0, y, Ball.CIRCUMFERENCE, Ball.CIRCUMFERENCE);
            gameRunner.batch.draw(wall, VIRTUAL_WIDTH - Ball.CIRCUMFERENCE, y, Ball.CIRCUMFERENCE, Ball.CIRCUMFERENCE);
        }

        float buttonHeight = Ball.CIRCUMFERENCE * 3f;
        float buttonsStartY = VIRTUAL_HEIGHT / 2f + buttonHeight;
        float buttonsQuitY = VIRTUAL_HEIGHT / 2f - buttonHeight;
        float selectedOffset = selectedButton == 0 ? buttonsStartY : buttonsQuitY;

        float selectedY = selectedOffset - buttonHeight/2f;

        gameRunner.batch.draw(
            wall,
            VIRTUAL_WIDTH /3f - Ball.CIRCUMFERENCE*2f, selectedY,
            Ball.CIRCUMFERENCE, Ball.CIRCUMFERENCE
        );

        font.draw(
            gameRunner.batch,
            "P I N G P O N G",
            0, VIRTUAL_HEIGHT - Ball.CIRCUMFERENCE * 3,
            VIRTUAL_WIDTH, Align.center, false
        );

        font.draw(
            gameRunner.batch,
            "Start Game",
            VIRTUAL_WIDTH /3f, buttonsStartY,
            VIRTUAL_WIDTH /3f, Align.left, false
        );

        font.draw(
            gameRunner.batch,
            "Quit Game",
            VIRTUAL_WIDTH /3f, buttonsQuitY,
            VIRTUAL_WIDTH /3f, Align.left, false
        );
    }

}
