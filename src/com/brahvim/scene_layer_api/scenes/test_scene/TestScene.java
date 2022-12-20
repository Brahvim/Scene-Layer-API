package com.brahvim.scene_layer_api.scenes.test_scene;

import com.brahvim.scene_layer_api.api.Scene;
import com.brahvim.scene_layer_api.api.SceneManager;

public class TestScene extends Scene {
    public TestScene(SceneManager.SceneInitializer p_sceneInitializer) {
        super(p_sceneInitializer);
        super.startLayer(TestLayer1.class);
        super.startLayer(TestLayer2.class);
    }
}
