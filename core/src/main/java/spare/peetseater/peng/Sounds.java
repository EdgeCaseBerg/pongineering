package spare.peetseater.peng;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;

import java.util.LinkedList;
import java.util.List;

public class Sounds {
    private final List<AssetDescriptor<Sound>> bounces;
    private final List<AssetDescriptor<Sound>> scores;
    private final List<AssetDescriptor<Sound>> wins;
    int idx;

    public Sounds() {
        idx = 0;
        this.bounces = bounceAssets();
        this.scores = scoreAssets();
        this.wins = winAssets();
    }

    public AssetDescriptor<Sound> nextWin() {
        AssetDescriptor<Sound> descriptor = this.wins.get(idx % this.wins.size());
        idx++;
        return descriptor;
    }

    public List<AssetDescriptor<Sound>> winAssets() {
        List<AssetDescriptor<Sound>> list = new LinkedList<>();
        list.add(GameAssets.areYouProudOfThatPerformance);
        list.add(GameAssets.congratsOldBoy);
        list.add(GameAssets.congratulationsYouWon);
        list.add(GameAssets.haYouDidIt);
        list.add(GameAssets.takeThatLyingDown);
        list.add(GameAssets.youWon);
        return list;
    }

    public AssetDescriptor<Sound> nextScore() {
        AssetDescriptor<Sound> descriptor = this.scores.get(idx % this.scores.size());
        idx++;
        return descriptor;
    }

    public List<AssetDescriptor<Sound>> scoreAssets() {
        List<AssetDescriptor<Sound>> list = new LinkedList<>();
        list.add(GameAssets.goodjob);
        list.add(GameAssets.defenseisnotyour);
        list.add(GameAssets.score);
        list.add(GameAssets.dobetter);
        list.add(GameAssets.dontletheballthrough);
        list.add(GameAssets.shameful);
        list.add(GameAssets.soClose);
        list.add(GameAssets.victoryIsNigh);
        list.add(GameAssets.youresupposedtohittheball);
        list.add(GameAssets.niceOne);
        return list;
    }

    public AssetDescriptor<Sound> nextBounce() {
        AssetDescriptor<Sound> descriptor = this.bounces.get(idx % this.bounces.size());
        idx++;
        return descriptor;
    }

    public List<AssetDescriptor<Sound>> bounceAssets() {
        List<AssetDescriptor<Sound>> list = new LinkedList<>();
        list.add(GameAssets.bounce1);
        list.add(GameAssets.bounce2);
        list.add(GameAssets.bounce3);
        list.add(GameAssets.bounce4);
        list.add(GameAssets.bounce5);
        list.add(GameAssets.bounce6);
        list.add(GameAssets.bounce7);
        return list;
    }
}
