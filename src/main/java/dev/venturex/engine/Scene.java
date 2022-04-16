package dev.venturex.engine;

import dev.venturex.engine.gfx.Camera;

public abstract class Scene {

    protected Camera camera;
    public Scene() {

    }
    public  abstract void init();
    public abstract void update(float deltaTime);
}
