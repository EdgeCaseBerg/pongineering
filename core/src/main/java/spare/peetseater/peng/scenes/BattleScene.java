package spare.peetseater.peng.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import spare.peetseater.peng.GameAssets;
import spare.peetseater.peng.GameRunner;
import spare.peetseater.peng.Sounds;
import spare.peetseater.peng.inputs.PADDLE_COMMAND;
import spare.peetseater.peng.inputs.PCPaddleInputProcessor;
import spare.peetseater.peng.objects.Ball;
import spare.peetseater.peng.objects.Countdown;
import spare.peetseater.peng.objects.Paddle;

import java.util.LinkedList;
import java.util.List;

import static spare.peetseater.peng.Constants.VIRTUAL_HEIGHT;
import static spare.peetseater.peng.Constants.VIRTUAL_WIDTH;

public class BattleScene implements Scene {

    enum ToReceive {
        Red,
        Blue
    };
    ToReceive sendBallTo;

    private final GameRunner gameRunner;
    Paddle red;
    Paddle blue;
    Ball ball;
    int redScore;
    int blueScore;
    List<AssetDescriptor<?>> assets;
    Countdown countdown;
    Sounds sounds;
    PCPaddleInputProcessor redInputProcessor;
    PCPaddleInputProcessor blueInputProcessor;

    public BattleScene(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
        sounds = new Sounds();
        redScore = blueScore = 0;

        red = new Paddle(18, VIRTUAL_HEIGHT / 2f);
        redInputProcessor = new PCPaddleInputProcessor(Input.Keys.W, Input.Keys.S);

        blue = new Paddle(VIRTUAL_WIDTH - Paddle.WIDTH * 2, VIRTUAL_HEIGHT / 2f);
        blueInputProcessor = new PCPaddleInputProcessor(Input.Keys.UP, Input.Keys.DOWN);

        InputMultiplexer multiPlexer = new InputMultiplexer();
        multiPlexer.addProcessor(redInputProcessor);
        multiPlexer.addProcessor(blueInputProcessor);
        Gdx.input.setInputProcessor(multiPlexer);

        sendBallTo = MathUtils.randomBoolean() ? ToReceive.Blue : ToReceive.Red;
        resetBall();
        resetCountdown();

        assets = new LinkedList<>();
        assets.add(GameAssets.scoreFont);
        assets.add(GameAssets.countdownFont);
        assets.add(GameAssets.redPaddle);
        assets.add(GameAssets.bluePaddle);
        assets.add(GameAssets.wall);
        assets.add(GameAssets.ball);
        for (AssetDescriptor<?> soundAsset : sounds.bounceAssets()) {
            assets.add(soundAsset);
        }
        for (AssetDescriptor<?> soundAsset : sounds.scoreAssets()) {
            assets.add(soundAsset);
        }
    }

    private void resetCountdown() {
        countdown = new Countdown(3);
    }

    private void resetBall() {
        ball = new Ball(VIRTUAL_WIDTH/2f, VIRTUAL_HEIGHT/2f);
        float from, to;
        switch (sendBallTo) {
            case Red:
                from   = MathUtils.degreesToRadians * 120;
                to = MathUtils.degreesToRadians * 240;
                break;
            case Blue:
            default:
                from = MathUtils.degreesToRadians * 300;
                to   = MathUtils.degreesToRadians * (360 + 60);
                break;
        }
        float angle = MathUtils.lerpAngle(from, to, MathUtils.random());
        float speedX = VIRTUAL_WIDTH * 0.5f;
        float speedY = VIRTUAL_HEIGHT * 0.6f;
        ball.setVelocity(
            new Vector2(
                MathUtils.cos(angle) * speedX,
                MathUtils.sin(angle) * speedY
            )
        );
    }

    @Override
    public void update(float delta) {

        if (redInputProcessor.getCurrentCommand().equals(PADDLE_COMMAND.UP)) {
            red.moveUp(delta);
        }
        if (redInputProcessor.getCurrentCommand().equals(PADDLE_COMMAND.DOWN)) {
            red.moveDown(delta);
        }

        if (blueInputProcessor.getCurrentCommand().equals(PADDLE_COMMAND.UP)) {
            blue.moveUp(delta);
        }
        if (blueInputProcessor.getCurrentCommand().equals(PADDLE_COMMAND.DOWN)) {
            blue.moveDown(delta);
        }

        boolean bounced = false;

        if (red.intersects(ball)) {
            ball.bounceOffOf(red);
            bounced = true;
        }
        if (blue.intersects(ball)) {
            ball.bounceOffOf(blue);
            bounced = true;
        }

        // bounce ball off non scoring walls
        if (ball.getAnchorY() <= 0) {
            ball.bounceUp();
            bounced = true;
        }
        if (ball.getAnchorY() + Ball.CIRCUMFERENCE >= VIRTUAL_HEIGHT) {
            ball.bounceDown();
            bounced = true;
        }

        if (bounced) {
            AssetDescriptor<Sound> sfx = sounds.nextBounce();
            gameRunner.assets.getSound(sfx).play();
        }

        boolean scored = false;
        if (ball.toTheLeftOf(red)) {
            scored = true;
            blueScore += 1;
            sendBallTo = ToReceive.Blue;
            resetBall();
            resetCountdown();
        }
        if (ball.toTheRightOf(blue)) {
            scored = true;
            redScore += 1;
            sendBallTo = ToReceive.Red;
            resetBall();
            resetCountdown();
        }

        if (scored) {
            AssetDescriptor<Sound> sfx = sounds.nextScore();
            gameRunner.assets.getSound(sfx).play();
        }

        if (redScore >= 5 || blueScore >= 5) {
            String msg = redScore < blueScore ? "Blue" : "Red";
            msg += " wins!";
            gameRunner.changeToNewScene(new WinScene(gameRunner, msg));
        }

        if (countdown.isCountingDown()) {
            countdown.update(delta);
        } else {
            ball.update(delta);
        }
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


        if (countdown.isCountingDown()) {
            BitmapFont countdownFont = gameRunner.assets.getFont(GameAssets.countdownFont);
            Color toRestore = countdownFont.getColor().cpy();

            if (sendBallTo.equals(ToReceive.Blue)) {
                countdownFont.setColor(Color.BLUE);
            } else {
                countdownFont.setColor(Color.RED);
            }
            countdownFont.draw(
                gameRunner.batch,
                String.format("%d", countdown.getSecondToDisplay()),
                0f, VIRTUAL_HEIGHT / 2f,
                VIRTUAL_WIDTH,
                Align.center,
                false
            );
            countdownFont.setColor(toRestore);
        }

    }

    @Override
    public List<AssetDescriptor<?>> requiredAssets() {
        return assets;
    }
}
