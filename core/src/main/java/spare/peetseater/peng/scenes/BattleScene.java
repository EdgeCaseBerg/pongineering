package spare.peetseater.peng.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import spare.peetseater.peng.GameAssets;
import spare.peetseater.peng.GameRunner;
import spare.peetseater.peng.objects.Ball;
import spare.peetseater.peng.objects.Paddle;

import java.util.LinkedList;
import java.util.List;

import static spare.peetseater.peng.Constants.VIRTUAL_HEIGHT;
import static spare.peetseater.peng.Constants.VIRTUAL_WIDTH;

public class BattleScene implements Scene {

    private final GameRunner gameRunner;
    Paddle red;
    Paddle blue;
    Ball ball;
    int p1Score;
    int p2Score;
    List<AssetDescriptor<?>> assets;

    public BattleScene(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
        p1Score = p2Score = 0;

        red = new Paddle(18, VIRTUAL_HEIGHT / 2f);
        blue = new Paddle(VIRTUAL_WIDTH - Paddle.WIDTH * 2, VIRTUAL_HEIGHT / 2f);
        ball = new Ball(VIRTUAL_WIDTH/2f, VIRTUAL_HEIGHT/2f);

        assets = new LinkedList<>();
        assets.add(GameAssets.scoreFont);
        assets.add(GameAssets.redPaddle);
        assets.add(GameAssets.bluePaddle);
        assets.add(GameAssets.ball);
    }

    @Override
    public void update(float delta) {
        p1Score += 1;
        p2Score -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            red.moveUp(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            red.moveDown(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            blue.moveUp(delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            blue.moveDown(delta);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.LIGHT_GRAY);
        BitmapFont font = gameRunner.assets.getFont(GameAssets.scoreFont);
        font.draw(
            gameRunner.batch,
            String.format("%d | %d\n", p1Score, p2Score),
            0f, Gdx.graphics.getHeight() - font.getLineHeight()/2f,
            Gdx.graphics.getWidth(),
            Align.center,
            false
        );
        Texture redTexture = gameRunner.assets.getTexture(GameAssets.redPaddle);
        Texture blueTexture = gameRunner.assets.getTexture(GameAssets.bluePaddle);
        Texture ballTexture = gameRunner.assets.getTexture(GameAssets.ball);

        gameRunner.batch.draw(redTexture, red.getX(), red.getY(), Paddle.WIDTH, Paddle.HEIGHT);
        gameRunner.batch.draw(blueTexture, blue.getX(), blue.getY(), Paddle.WIDTH, Paddle.HEIGHT);
        gameRunner.batch.draw(ballTexture, ball.getAnchorX(), ball.getAnchorY(), Ball.CIRCUMFERENCE, Ball.CIRCUMFERENCE);
    }

    @Override
    public List<AssetDescriptor<?>> requiredAssets() {
        return assets;
    }
}
