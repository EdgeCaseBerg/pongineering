package spare.peetseater.peng.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
    int redScore;
    int blueScore;
    List<AssetDescriptor<?>> assets;

    public BattleScene(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
        redScore = blueScore = 0;

        red = new Paddle(18, VIRTUAL_HEIGHT / 2f);
        blue = new Paddle(VIRTUAL_WIDTH - Paddle.WIDTH * 2, VIRTUAL_HEIGHT / 2f);
        resetBall();

        assets = new LinkedList<>();
        assets.add(GameAssets.scoreFont);
        assets.add(GameAssets.redPaddle);
        assets.add(GameAssets.bluePaddle);
        assets.add(GameAssets.wall);
        assets.add(GameAssets.ball);
    }

    private void resetBall() {
        ball = new Ball(VIRTUAL_WIDTH/2f, VIRTUAL_HEIGHT/2f);
        ball.setVelocity(new Vector2(MathUtils.random() * VIRTUAL_WIDTH * 0.5f, MathUtils.random() * VIRTUAL_HEIGHT * 0.6f));
    }

    @Override
    public void update(float delta) {
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
        if (red.intersects(ball)) {
            ball.bounceOffOf(red);
        }
        if (blue.intersects(ball)) {
            ball.bounceOffOf(blue);
        }

        // bounce ball off non scoring walls
        if (ball.getAnchorY() <= 0) {
            ball.bounceUp();
        }
        if (ball.getAnchorY() + Ball.CIRCUMFERENCE >= VIRTUAL_HEIGHT) {
            ball.bounceDown();
        }
        if (ball.toTheLeftOf(red)) {
            blueScore += 1;
            resetBall();
        }
        if (ball.toTheRightOf(blue)) {
            redScore += 1;
            resetBall();
        }
        ball.update(delta);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.LIGHT_GRAY);
        BitmapFont font = gameRunner.assets.getFont(GameAssets.scoreFont);
        font.draw(
            gameRunner.batch,
            String.format("%d   %d\n", redScore, blueScore),
            0f, Gdx.graphics.getHeight() - font.getLineHeight()/2f,
            Gdx.graphics.getWidth(),
            Align.center,
            false
        );
        Texture redTexture = gameRunner.assets.getTexture(GameAssets.redPaddle);
        Texture blueTexture = gameRunner.assets.getTexture(GameAssets.bluePaddle);
        Texture ballTexture = gameRunner.assets.getTexture(GameAssets.ball);
        Texture wallTexture = gameRunner.assets.getTexture(GameAssets.wall);

        for (int i = 0; i < VIRTUAL_WIDTH / Ball.CIRCUMFERENCE; i++) {
            int x = i * (int)Ball.CIRCUMFERENCE;
            gameRunner.batch.draw(wallTexture, x, 0, Ball.CIRCUMFERENCE, Ball.RADIUS);
            gameRunner.batch.draw(wallTexture, x, VIRTUAL_HEIGHT - Ball.RADIUS, Ball.CIRCUMFERENCE, Ball.RADIUS);
        }
        for (int i = 0; i < VIRTUAL_HEIGHT / Ball.CIRCUMFERENCE; i++) {
            int y = i * (int)Ball.CIRCUMFERENCE;
            gameRunner.batch.draw(wallTexture, VIRTUAL_WIDTH/2, y, Ball.CIRCUMFERENCE, Ball.CIRCUMFERENCE);
        }

        gameRunner.batch.draw(ballTexture, ball.getAnchorX(), ball.getAnchorY(), Ball.CIRCUMFERENCE, Ball.CIRCUMFERENCE);
        gameRunner.batch.draw(redTexture, red.getX(), red.getY(), Paddle.WIDTH, Paddle.HEIGHT);
        gameRunner.batch.draw(blueTexture, blue.getX(), blue.getY(), Paddle.WIDTH, Paddle.HEIGHT);
    }

    @Override
    public List<AssetDescriptor<?>> requiredAssets() {
        return assets;
    }
}
