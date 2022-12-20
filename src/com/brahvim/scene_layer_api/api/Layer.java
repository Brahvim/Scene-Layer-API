package com.brahvim.scene_layer_api.api;

import com.brahvim.scene_layer_api.Sketch;

/**
 * Just like {@code Scene}s, {@code Layer}s
 * are used by extending this class.
 * To add a {@code Layer} to a {@code Scene}, use the
 * {@code SceneClass::startLayer(LayerClass.class)}
 * method, where {@code LayerClass} and {@code SceneClass}
 * are any two classes extending the {@code Layer}
 * and {@code Scene} classes, respectively.
 *
 * @author Brahvim Bhaktvatsal
 */
public class Layer {
    // region `private` / `protected` fields.
    protected final Scene SCENE;
    protected final Sketch SKETCH;
    protected final Layer LAYER = this;
    // endregion

    public Layer(Scene.LayerInitializer p_initializer) {
        this.SCENE = p_initializer.getScene();
        this.SKETCH = p_initializer.getSketch();
    }

    // region `public` event callbacks. Can be called from anywhere - ":D!
    // region App workflow:
    protected void setup() {
    }

    protected void pre() {
    }

    protected void draw() {
    }

    protected void post() {
    }
    // endregion

    // region Mouse events.
    public void mousePressed() {
    }

    public void mouseReleased() {
    }

    public void mouseMoved() {
    }

    public void mouseClicked() {
    }

    public void mouseDragged() {
    }

    // @SuppressWarnings("unused")
    public void mouseWheel(processing.event.MouseEvent p_mouseEvent) {
    }
    // endregion

    // region Keyboard events.
    public void keyTyped() {
    }

    public void keyPressed() {
    }

    public void keyReleased() {
    }
    // endregion

    // region Touch events.
    public void touchStarted() {
    }

    public void touchMoved() {
    }

    public void touchEnded() {
    }
    // endregion
    // endregion

}