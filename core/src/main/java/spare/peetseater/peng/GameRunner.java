package spare.peetseater.peng;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameRunner extends Game {
    @Override
    public void create() {
        setScreen(new BattleScreen());
    }
}
