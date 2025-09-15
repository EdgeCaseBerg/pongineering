package spare.peetseater.peng.inputs;

import spare.peetseater.peng.objects.Ball;
import spare.peetseater.peng.objects.Paddle;

public class CPUPaddleProcessor implements PaddleInputProcessor {

    private Ball ball;
    private final Paddle myPaddle;

    public CPUPaddleProcessor(Ball ball, Paddle cpuControlledPaddle) {
        this.ball = ball;
        this.myPaddle = cpuControlledPaddle;
    }

    @Override
    public PADDLE_COMMAND getCurrentCommand() {
        if (ball.getAnchorY() < myPaddle.getY()) {
            return PADDLE_COMMAND.DOWN;
        } else if (ball.getAnchorY() > myPaddle.getY()) {
            return PADDLE_COMMAND.UP;
        }
        return PADDLE_COMMAND.NOTHING;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }
}
