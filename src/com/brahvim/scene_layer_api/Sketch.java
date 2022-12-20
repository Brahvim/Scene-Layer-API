package com.brahvim.scene_layer_api;

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;

import com.brahvim.scene_layer_api.api.SceneManager;
import com.brahvim.scene_layer_api.scenes.test_scene.TestScene;

import processing.core.PApplet;
import processing.core.PConstants;

// TODO: Bring stuff from Nerd - let's make a Game Engine! ":D!~

public class Sketch extends PApplet {
    // region `public` fields.
    public final static int REFRESH_RATE = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getScreenDevices()[0].getDisplayMode().getRefreshRate();
    public int frameStartTime, pframeTime, frameTime;
    public float cx, cy, qx, qy, q3x, q3y;
    public int pwidth, pheight;

    public final static File ROOT_DIR = new File("");
    public final static File DATA_DIR = new File("data");
    // endregion

    // region `private` ~~/ `protected`~~ fields.
    private SceneManager sceneMan = new SceneManager(this);
    private final ArrayList<Integer> keysHeld = new ArrayList<>();
    // endregion

    // region Constructors, `main()`, `settings()`...
    public static void main(String[] p_args) {
        Sketch constructedSketch = new Sketch();
        String[] args = new String[] { constructedSketch.getClass().getName() };

        if (p_args == null || p_args.length == 0)
            PApplet.runSketch(args, constructedSketch);
        else
            PApplet.runSketch(PApplet.concat(p_args, args), constructedSketch);
    }

    @Override
    public void settings() {
        super.size(400, 400, PConstants.P3D);
    }
    // endregion

    // region Processing sketch workflow.
    @Override
    public void setup() {
        super.frameRate(Sketch.REFRESH_RATE);
        super.registerMethod("pre", this);
        super.registerMethod("post", this);

        this.sketchSetup();
    }

    private void sketchSetup() {
        this.updateRatios();
        this.sceneMan.startScene(TestScene.class);
    }

    public void pre() {
        if (!(this.pwidth == super.width || this.pheight == super.height))
            this.updateRatios();

        this.sceneMan.pre();
    }

    @Override
    public void draw() {
        this.frameStartTime = super.millis(); // Timestamp.
        this.frameTime = this.frameStartTime - this.pframeTime;
        this.pframeTime = this.frameStartTime;

        this.sceneMan.draw();
    }

    public void post() {
        this.sceneMan.post();
    }
    // endregion

    // region `public` event callbacks. Call from anywhere, ":D! REMEMBER `super()`!
    // region Mouse events.
    public void mousePressed() {
        this.sceneMan.mousePressed();
    }

    public void mouseReleased() {
        this.sceneMan.mouseReleased();
    }

    public void mouseMoved() {
        this.sceneMan.mouseMoved();
    }

    public void mouseClicked() {
        this.sceneMan.mouseClicked();
    }

    public void mouseDragged() {
        this.sceneMan.mouseDragged();
    }

    // @SuppressWarnings("unused")
    public void mouseWheel(processing.event.MouseEvent p_mouseEvent) {
        this.sceneMan.mouseWheel(p_mouseEvent);
    }
    // endregion

    // region Keyboard events.
    public void keyTyped() {
        this.sceneMan.keyTyped();
    }

    public void keyPressed() {
        if (super.keyCode == 27)
            super.key = ' ';

        this.keysHeld.add(this.keysHeld.indexOf(super.keyCode));
        this.sceneMan.keyPressed();
    }

    public void keyReleased() {
        try {
            this.keysHeld.remove(this.keysHeld.indexOf(super.keyCode));
        } catch (IndexOutOfBoundsException e) {
        }
        this.sceneMan.keyReleased();
    }
    // endregion

    // region Touch events.
    public void touchStarted() {
        this.sceneMan.touchStarted();
    }

    public void touchMoved() {
        this.sceneMan.touchMoved();
    }

    public void touchEnded() {
        this.sceneMan.touchEnded();
    }
    // endregion
    // endregion

    // region Boilerplate-y extras.
    public void updateRatios() {
        cx = width * 0.5f;
        cy = height * 0.5f;
        qx = cx * 0.5f;
        qy = cy * 0.5f;
        q3x = cx + qx;
        q3y = cy + qy;
    }

    public void in2d(Runnable p_run) {
        this.begin2d();
        p_run.run();
        this.end2d();
    }

    public void begin2d() {
        super.hint(PConstants.DISABLE_DEPTH_TEST);
        super.pushMatrix();
        super.pushStyle();
    }

    public void end2d() {
        super.popStyle();
        super.popMatrix();
        super.hint(PConstants.ENABLE_DEPTH_TEST);
    }

    // endregion
}
