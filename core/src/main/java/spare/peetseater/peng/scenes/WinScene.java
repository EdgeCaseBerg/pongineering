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
import spare.peetseater.peng.Sounds;
import spare.peetseater.peng.objects.Ball;

import java.util.LinkedList;
import java.util.List;

import static spare.peetseater.peng.Constants.VIRTUAL_HEIGHT;
import static spare.peetseater.peng.Constants.VIRTUAL_WIDTH;

public class WinScene implements Scene {
    private final GameRunner gameRunner;
    private Sounds sounds;
    private final String message;
    private final List<AssetDescriptor<?>> requiredAssets;
    private int selectedButton;
    public final int REMATCH_CHOICE = 0;
    public final int TO_TITLE_CHOICE = 1;
    boolean hasPlayedSFX = false;

    public WinScene(GameRunner gameRunner, String msg) {
        this.sounds = new Sounds();
        this.gameRunner = gameRunner;
        this.message = msg;
        this.selectedButton = 0;
        requiredAssets = new LinkedList<>();
        requiredAssets.add(GameAssets.scoreFont);
        requiredAssets.add(GameAssets.countdownFont);
        requiredAssets.add(GameAssets.wall);
        requiredAssets.addAll(sounds.winAssets());
    }

    @Override
    public void update(float delta) {
        if (!hasPlayedSFX) {
            gameRunner.assets.getSound(sounds.nextWin()).play();
            hasPlayedSFX = true;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            selectedButton -= 1;
            selectedButton = Math.max(0, selectedButton);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            selectedButton += 1;
            selectedButton = Math.max(Math.max(REMATCH_CHOICE, TO_TITLE_CHOICE), selectedButton);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            switch (selectedButton) {
                case REMATCH_CHOICE:
                    gameRunner.changeToNewScene(new BattleScene(gameRunner));
                    break;
                case TO_TITLE_CHOICE:
                default:
                    gameRunner.changeToNewScene(new TitleScreen(gameRunner));
                    break;
            }
        }
    }

    @Override
    public void render(float delta) {
        Texture wall = gameRunner.assets.getTexture(GameAssets.wall);
        BitmapFont btnFont = gameRunner.assets.getFont(GameAssets.scoreFont);
        BitmapFont msgFont = gameRunner.assets.getFont(GameAssets.countdownFont);

        ScreenUtils.clear(Color.LIGHT_GRAY);

        msgFont.draw(
            gameRunner.batch,
            message,
            0, VIRTUAL_HEIGHT - Ball.CIRCUMFERENCE * 3,
            VIRTUAL_WIDTH, Align.center, false
        );

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

        btnFont.draw(
            gameRunner.batch,
            "Rematch",
            VIRTUAL_WIDTH /3f, buttonsStartY,
            VIRTUAL_WIDTH /3f, Align.left, false
        );

        btnFont.draw(
            gameRunner.batch,
            "To Title",
            VIRTUAL_WIDTH /3f, buttonsQuitY,
            VIRTUAL_WIDTH /3f, Align.left, false
        );
    }

    @Override
    public List<AssetDescriptor<?>> requiredAssets() {
        return requiredAssets;
    }
}
