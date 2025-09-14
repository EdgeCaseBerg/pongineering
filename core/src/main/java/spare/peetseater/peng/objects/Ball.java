package spare.peetseater.peng.objects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static spare.peetseater.peng.Constants.VIRTUAL_HEIGHT;
import static spare.peetseater.peng.Constants.VIRTUAL_WIDTH;

public class Ball {
    public final static float RADIUS = 9f;
    public final static float CIRCUMFERENCE = RADIUS * 2f;
    public final static float INITIAL_SPEED = VIRTUAL_WIDTH;
    private Vector2 v;
    private float x;
    private float y;

    public Ball(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        this.v = new Vector2(0, 0);
    }

    public void update(float delta) {
        x += v.x * delta;
        y += v.y * delta;
    }

    public void setVelocity(Vector2 velocity) {
        this.v = velocity;
    }

    public Vector2 getVelocity() {
        return this.v.cpy();
    }

    public float getAnchorX() {
        return x - RADIUS;
    }
    public float getAnchorY() {
        return y - RADIUS;
    }

    public void bounceOffOf(Paddle paddle) {
        // TODO: add speed multiplier
        float radians =  paddle.getBounceAngle(this);
        float dx = MathUtils.cos(radians);
        float dy = MathUtils.sin(radians);
        v.x = dx * INITIAL_SPEED;
        v.y = dy * INITIAL_SPEED;
    }

    public void bounceUp() {
        v.y = -1 * v.y;
    }

    public void bounceDown() {
        v.y = -1 * v.y;
    }

    public boolean toTheLeftOf(Paddle paddle) {
        return x + RADIUS < paddle.getX();
    }

    public boolean toTheRightOf(Paddle paddle) {
        return x - RADIUS > paddle.getX();
    }
}
