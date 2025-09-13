package spare.peetseater.peng.objects;

import com.badlogic.gdx.math.MathUtils;

import static spare.peetseater.peng.Constants.VIRTUAL_HEIGHT;

public class Paddle {
    float x;
    float y;
    public static final float WIDTH = 18f;
    public static final float HEIGHT = 72f;
    private final float speed = VIRTUAL_HEIGHT; // 1 screen height per second

    public Paddle(float startX, float startY) {
        this.x = startX;
        this.y = startY;
    }

    public void moveUp(float delta) {
        float yShift = speed * delta;
        if (yShift + y + HEIGHT < VIRTUAL_HEIGHT) {
            y += yShift;
        }
    }

    public void moveDown(float delta) {
        float yShift = speed * delta;
        if (y - yShift > 0) {
            y -= yShift;
        }
    }

    /*
    public boolean intersects(Ball ball) {
        // TODO: usual code here
    }
    */
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean intersects(Ball ball) {
        // For the time being, proper square check
        float pLeftX = ball.getAnchorX();
        float pLeftY = ball.getAnchorY();
        float pRightX = pLeftX + Ball.CIRCUMFERENCE;
        float pRightY = pLeftY + Ball.CIRCUMFERENCE;

        boolean overlapsXProjection = pRightX >= x && (x + WIDTH) >= pLeftX;
        boolean overlapsYProjection = pRightY >= y && (y + HEIGHT) >= pLeftY;
        return overlapsXProjection && overlapsYProjection;
    }

    public float getBounceAngle(Ball ball) {
        // use center point for bounce determination!
        float yTouchPoint = ball.getAnchorY() + Ball.RADIUS;
        float normalizedBallY = Math.max(0, yTouchPoint - y); // this shouldn't go negative, but just in case.

        boolean paddleToTheLeft = x <= ball.getAnchorX() + Ball.RADIUS;
        float amplitude = MathUtils.degreesToRadians * (90 - 15);
        float shift = (paddleToTheLeft ? 0 : MathUtils.PI) + MathUtils.degreesToRadians * 15;
        float radians = amplitude * MathUtils.sin(MathUtils.PI/HEIGHT * normalizedBallY) + shift;
        return radians;
    }
}
