package spare.peetseater.peng.objects;

import static spare.peetseater.peng.Constants.VIRTUAL_WIDTH;

public class Ball {
    public final static float RADIUS = 9f;
    public final static float CIRCUMFERENCE = RADIUS * 2f;
    public final static float INITIAL_SPEED = VIRTUAL_WIDTH;
    private float x;
    private float y;

    public Ball(float startX, float startY) {
        this.x = startX;
        this.y = startY;
    }

    public float getAnchorX() {
        return x - RADIUS;
    }
    public float getAnchorY() {
        return y - RADIUS;
    }

}
