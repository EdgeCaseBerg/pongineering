package spare.peetseater.peng.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import spare.peetseater.peng.GameAssets;
import spare.peetseater.peng.GameRunner;

import java.util.LinkedList;
import java.util.List;

public class BattleScene implements Scene {

    private final GameRunner gameRunner;
    int p1Score;
    int p2Score;
    List<AssetDescriptor<?>> assets;

    public BattleScene(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
        p1Score = p2Score = 0;

        assets = new LinkedList<>();
        assets.add(GameAssets.scoreFont);
    }

    @Override
    public void update(float delta) {
        p1Score += 1;
        p2Score -= 1;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.LIGHT_GRAY);
        BitmapFont font = gameRunner.assets.getFont(GameAssets.scoreFont);
        font.draw(
            gameRunner.batch,
            String.format("%d | %d\n", p1Score, p2Score),
            0f, Gdx.graphics.getHeight() - font.getLineHeight(),
            Gdx.graphics.getWidth(),
            Align.center,
            false
        );
    }

    @Override
    public List<AssetDescriptor<?>> requiredAssets() {
        return assets;
    }
}
