package spare.peetseater.peng.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaddleTest {

    // floats lose precision quickly and 179.9999 is the same as 180 to us.
    public static final float EPSILON = 0.0001f;

    @Test
    void test90DegreesLeftPaddle() {
        Paddle left = new Paddle(0,0);
        Ball ball = new Ball(0, Paddle.HEIGHT/2);
        float radians = left.getBounceAngle(ball);
        assertEquals(0, radians * MathUtils.radiansToDegrees, EPSILON);
    }

    @Test
    void test90DegreesRightPaddle() {
        Paddle right = new Paddle(100, 0);
        Ball ball = new Ball(100 - Ball.RADIUS, Paddle.HEIGHT/2);
        float radians = right.getBounceAngle(ball);
        assertEquals(180, radians * MathUtils.radiansToDegrees, EPSILON);
    }

    @Test
    void test90DegreesLeftPaddleHigherOnBoard() {
        Paddle left = new Paddle(10,60);
        Ball ball = new Ball(10, 60 + Paddle.HEIGHT/2);
        float radians = left.getBounceAngle(ball);
        assertEquals(0, radians * MathUtils.radiansToDegrees, EPSILON);
    }

    @Test
    void test90DegreesRightPaddleHigherOnBoard() {
        Paddle right = new Paddle(100, 100);
        Ball ball = new Ball(100 - Ball.CIRCUMFERENCE, 100 + Paddle.HEIGHT/2);
        float radians = right.getBounceAngle(ball);
        assertEquals(180, radians * MathUtils.radiansToDegrees, EPSILON);
    }

    @Test
    void test15DegreesLeftPaddleAt0() {
        Paddle left = new Paddle(10,60);
        Ball ball = new Ball(10, 60);
        float radians = left.getBounceAngle(ball);
        assertEquals(270 + 15, radians * MathUtils.radiansToDegrees, EPSILON);
    }

    @Test
    void test15DegreesRightPaddleAt0() {
        Paddle right = new Paddle(100, 100);
        Ball ball = new Ball(100 - Ball.CIRCUMFERENCE, 100);
        float radians = right.getBounceAngle(ball);
        assertEquals(270 - 15, radians * MathUtils.radiansToDegrees, EPSILON);
    }
}
