package com.brahvim.scene_layer_api.scenes.test_scene;

import com.brahvim.scene_layer_api.SineWave;
import com.brahvim.scene_layer_api.api.Layer;
import com.brahvim.scene_layer_api.api.Scene.LayerInitializer;

import processing.core.PApplet;
import processing.core.PConstants;

public class TestLayer1 extends Layer {
    private SineWave fadeWave;

    public TestLayer1(LayerInitializer p_initializer) {
        super(p_initializer);
    }

    @Override
    protected void setup() {
        this.fadeWave = new SineWave(SKETCH, 0.001f);
        this.fadeWave.start();
    }

    @Override
    protected void draw() {
        SKETCH.background(0x006699);

        // ...kinda' like `with()` from Python mode in Processing?:
        SKETCH.in2d(() -> {
            SKETCH.rectMode(PConstants.CORNER);
            SKETCH.fill(0, 255 * PApplet.abs(this.fadeWave.get()));
            SKETCH.rect(0, 0, SKETCH.width, SKETCH.height);
        });
    }

}
