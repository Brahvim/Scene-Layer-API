package com.brahvim.scene_layer_api.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import com.brahvim.scene_layer_api.Sketch;

public class SceneManager {
    // region `private` ~~/ `protected`~~ fields.
    private final ArrayList<Class<? extends Scene>> SCENE_CLASSES;
    private final HashMap<Class<? extends Scene>, Constructor<? extends Scene>> SCENE_CONSTRUCTORS;
    private final SceneManager.SceneInitializer runner;
    private Scene currentScene, previousScene;

    /**
     * Keeping this just-in-case. It would otherwise be passed
     * straight to the {@linkplain SceneManager.SceneInitializer}
     * constructor instead.
     */
    private final Sketch sketch;
    // endregion

    public class SceneInitializer {
        private SceneManager manager;

        private SceneInitializer(SceneManager p_sceneManager) {
            this.manager = p_sceneManager;
        }

        public SceneManager getSceneManager() {
            return this.manager;
        }
    }

    public SceneManager(Sketch p_sketch) {
        this.sketch = p_sketch;
        this.runner = new SceneManager.SceneInitializer(this);

        this.SCENE_CONSTRUCTORS = new HashMap<>();
        this.SCENE_CLASSES = new ArrayList<>();
    }

    // region `Scene`-callbacks.
    // region App workflow:
    public void setup() {
        this.currentScene.runSetup(this.runner);
    }

    public void pre() {
        this.currentScene.runPre(this.runner);
    }

    public void draw() {
        this.currentScene.runDraw(this.runner);
    }

    public void post() {
        this.currentScene.runPost(this.runner);
    }
    // endregion

    // region Mouse events.
    public void mousePressed() {
        this.currentScene.mousePressed();
        for (Layer l : this.currentScene.getLayers())
            l.mousePressed();
    }

    public void mouseReleased() {
        this.currentScene.mouseReleased();
        for (Layer l : this.currentScene.getLayers())
            l.mouseReleased();
    }

    public void mouseMoved() {
        this.currentScene.mouseMoved();
        for (Layer l : this.currentScene.getLayers())
            l.mouseMoved();
    }

    public void mouseClicked() {
        this.currentScene.mouseClicked();
        for (Layer l : this.currentScene.getLayers())
            l.mouseClicked();
    }

    public void mouseDragged() {
        this.currentScene.mouseDragged();
        for (Layer l : this.currentScene.getLayers())
            l.mouseDragged();
    }

    // @SuppressWarnings("unused")
    public void mouseWheel(processing.event.MouseEvent p_mouseEvent) {
        this.currentScene.mouseWheel(p_mouseEvent);
        for (Layer l : this.currentScene.getLayers())
            l.mouseWheel(p_mouseEvent);
    }
    // endregion

    // region Keyboard events.
    public void keyTyped() {
        this.currentScene.keyTyped();
        for (Layer l : this.currentScene.getLayers())
            l.keyTyped();
    }

    public void keyPressed() {
        this.currentScene.keyPressed();
        for (Layer l : this.currentScene.getLayers())
            l.keyPressed();
    }

    public void keyReleased() {
        this.currentScene.keyReleased();
        for (Layer l : this.currentScene.getLayers())
            l.keyReleased();
    }
    // endregion

    // region Touch events.
    public void touchStarted() {
        this.currentScene.touchStarted();
        for (Layer l : this.currentScene.getLayers())
            l.touchStarted();
    }

    public void touchMoved() {
        this.currentScene.touchMoved();
        for (Layer l : this.currentScene.getLayers())
            l.touchMoved();
    }

    public void touchEnded() {
        this.currentScene.touchEnded();
        for (Layer l : this.currentScene.getLayers())
            l.touchEnded();
    }
    // endregion
    // endregion

    // region `Scene`-operations.
    @SuppressWarnings("unchecked")
    public Class<? extends Scene>[] getScenes() {
        return (Class<? extends Scene>[]) this.SCENE_CLASSES.toArray();
    }

    public void restartScene() {
        this.startSceneImpl(this.currentScene.getClass());
    }

    public void startScene(Class<? extends Scene> p_sceneClass) {
        if (this.SCENE_CLASSES.contains(p_sceneClass))
            throw new IllegalArgumentException("""
                    Use `SceneManager::restartScene()
                    to instantiate a `Scene` more than once!""");

        this.startSceneImpl(p_sceneClass);
    }

    private void startSceneImpl(Class<? extends Scene> p_sceneClass) {
        this.SCENE_CLASSES.add(p_sceneClass);

        this.previousScene = this.currentScene;
        if (this.previousScene != null) {
            this.previousScene.onSceneExit();
        }

        Scene toStart = null;
        Constructor<? extends Scene> sceneConstructor = null;

        // region Getting the constructor.
        try {
            sceneConstructor = p_sceneClass.getConstructor(SceneInitializer.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        // endregion

        // region Constructing the `Scene`.
        try {
            toStart = (Scene) sceneConstructor.newInstance(this.runner);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // endregion

        this.currentScene = toStart;
        this.currentScene.runSetup(this.runner);

        // Don't worry about concurrency, vvv *this* vvv is `final`! ^-^
        this.SCENE_CONSTRUCTORS.put(p_sceneClass, sceneConstructor);
    }
    // endregion

    // region Getters.
    public Sketch getSketch() {
        return this.sketch;
    }

    public Scene getCurrentScene() {
        return this.currentScene;
    }

    public Scene getPreviousScene() {
        return this.previousScene;
    }
    // endregion

}