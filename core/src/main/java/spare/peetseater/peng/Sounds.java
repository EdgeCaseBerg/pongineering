package spare.peetseater.peng;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;

import java.util.LinkedList;
import java.util.List;

public class Sounds {
    private final List<AssetDescriptor<Sound>> bounces;
    int idx;

    public Sounds() {
        idx = 0;
        this.bounces = bounceAssets();

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
