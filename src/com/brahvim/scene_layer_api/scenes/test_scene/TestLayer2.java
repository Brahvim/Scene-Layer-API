package com.brahvim.scene_layer_api.scenes.test_scene;

import com.brahvim.scene_layer_api.api.Layer;
import com.brahvim.scene_layer_api.api.Scene.LayerInitializer;

public class TestLayer2 extends Layer {
    public TestLayer2(LayerInitializer p_initializer) {
        super(p_initializer);
    }

    @Override
    protected void draw() {
        if (SKETCH.frameCount % 60 == 0)
            System.out.println(SKETCH.frameRate);
        SKETCH.circle(super.SKETCH.mouseX, super.SKETCH.mouseY, 20);
    }

}
