package spare.peetseater.peng.scenes;

import com.badlogic.gdx.assets.AssetDescriptor;

import java.util.List;

public interface Scene {
    void update(float delta);
    void render(float delta);
    List<AssetDescriptor<?>> requiredAssets();
}
