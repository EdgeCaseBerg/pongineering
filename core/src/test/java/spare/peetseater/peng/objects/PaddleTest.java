package spare.peetseater.peng.objects;

import com.badlogic.gdx.math.MathUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaddleTest {
    @Test
    void test90DegreesLeftPaddle() {
        Paddle left = new Paddle(0,0);
        Ball ball = new Ball(0, Paddle.HEIGHT/2);
        float radians = left.getBounceAngle(ball);
        assertEquals(MathUtils.degreesToRadians * 90, radians);
    }

    @Test
    void test90DegreesRightPaddle() {
        Paddle right = new Paddle(100, 0);
        Ball ball = new Ball(100 - Ball.RADIUS, Paddle.HEIGHT/2);
        float radians = right.getBounceAngle(ball);
        assertEquals(MathUtils.degreesToRadians * 270, radians);
    }
}
