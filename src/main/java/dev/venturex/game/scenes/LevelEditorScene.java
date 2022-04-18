package dev.venturex.game.scenes;
import dev.venturex.engine.GameObject;
import dev.venturex.engine.Scene;
import dev.venturex.engine.components.Sprite;
import dev.venturex.engine.components.SpriteRenderer;
import dev.venturex.engine.components.SpriteSheet;
import dev.venturex.engine.gfx.Camera;
import dev.venturex.engine.maths.Transform;
import dev.venturex.engine.utils.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.io.File;

public class LevelEditorScene extends Scene {

    private GameObject object;
    private SpriteSheet sheet;

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f());

        sheet = AssetPool.getSpriteSheet("res:assets/textures/Character_Sprite.png");

        object = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)), 2);
        object.addComponent(new SpriteRenderer(new Sprite(
                AssetPool.getTexture("res:assets/textures/blendImage1.png")
        )));
        this.addGameObjectToScene(object);

        GameObject object1 = new GameObject("Object 2", new Transform(new Vector2f(300, 150), new Vector2f(256, 256)), 3);
        object1.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("res:assets/textures/blendImage2.png"))));
        this.addGameObjectToScene(object1);

    }

    private void loadResources(){
        File vertexShaderFile = AssetPool.getFile("res:assets/shaders/vertex.glsl");
        File fragmentShaderFile = AssetPool.getFile("res:assets/shaders/fragment.glsl");
        AssetPool.getShader(vertexShaderFile, fragmentShaderFile);
        AssetPool.addSpritesSheet("res:assets/textures/Character_Sprite.png",
                new SpriteSheet(
                        AssetPool.getTexture("res:assets/textures/Character_Sprite.png"),
                        350 / 7, 592 / 16, 7 * 16, 0));
    }

    int spriteIndex = 7;
    float spriteFlipTime = 0.15f;
    float spriteFlipTimeLeft = 0.0f;
    @Override
    public void update(float deltaTime) {


        for (GameObject go : this.gameObjects){
            go.update(deltaTime);
        }

        this.renderer.render();
    }
}
