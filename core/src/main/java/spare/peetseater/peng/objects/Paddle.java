package spare.peetseater.peng.objects;

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
}
