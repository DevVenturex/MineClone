package dev.venturex.game.scenes;
import dev.venturex.engine.GameObject;
import dev.venturex.engine.Scene;
import dev.venturex.engine.components.SpriteRenderer;
import dev.venturex.engine.gfx.Camera;
import dev.venturex.engine.maths.Transform;
import dev.venturex.engine.utils.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.io.File;

public class LevelEditorScene extends Scene {

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f());

        GameObject object = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        object.addComponent(new SpriteRenderer(AssetPool.getTexture("src/main/resources/assets/textures/Character_Sprite.png")));
        this.addGameObjectToScene(object);

        GameObject object1 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        object1.addComponent(new SpriteRenderer(AssetPool.getTexture("src/main/resources/assets/textures/testImage.jpg")));
        this.addGameObjectToScene(object1);

        loadResources();
    }

    private void loadResources(){
        File vertexShaderFile = AssetPool.getFile("src/main/resources/assets/shaders/vertex.glsl");
        File fragmentShaderFile = AssetPool.getFile("src/main/resources/assets/shaders/fragment.glsl");
        AssetPool.getShader(vertexShaderFile, fragmentShaderFile);

    }

    @Override
    public void update(float deltaTime) {
        for (GameObject go : this.gameObjects){
            go.update(deltaTime);
        }

        this.renderer.render();
    }
}
