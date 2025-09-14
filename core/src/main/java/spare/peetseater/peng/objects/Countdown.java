package spare.peetseater.peng.objects;

public class Countdown {

    float elaspedSeconds = 0;
    int currentSecond;

    public Countdown(int from) {
        this.currentSecond = from;
    }

    public void update(float delta) {
        elaspedSeconds += delta;
        if (elaspedSeconds > 1) {
            currentSecond -= 1;
            elaspedSeconds = 1 - elaspedSeconds;
        }
        currentSecond = Math.max(currentSecond, 0);
    }

    public int getSecondToDisplay() {
        return currentSecond;
    }
}
