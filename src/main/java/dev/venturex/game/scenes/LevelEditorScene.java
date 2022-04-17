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

        int xOffset = 10;
        int yOffset = 10;

        float totalWidth = (float) (600 - xOffset * 2);
        float totalHeight = (float) (300 - yOffset * 2);
        float sizeX = totalWidth / 100.0f;
        float sizeY = totalHeight / 100.0f;

        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                float xPos = xOffset + (x * sizeX);
                float yPos = yOffset + (y * sizeY);

                GameObject go = new GameObject("Obj " + x + "" + y, new Transform(new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY)));
                go.addComponent(new SpriteRenderer(new Vector4f(xPos / totalWidth, yPos / totalHeight, 1, 1)));
                this.addGameObjectToScene(go);
            }
        }

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
