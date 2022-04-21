package dev.venturex.game.scenes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.venturex.engine.*;
import dev.venturex.engine.components.Sprite;
import dev.venturex.engine.components.SpriteRenderer;
import dev.venturex.engine.components.SpriteSheet;
import dev.venturex.engine.gfx.Camera;
import dev.venturex.engine.inputs.Inputs;
import dev.venturex.engine.maths.Transform;
import dev.venturex.engine.utils.AssetPool;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.io.File;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    private final int SPEED = 300;
    private final int GRAVITY = 1400;

    private GameObject object;
    private SpriteSheet sheet;


    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f());
        if (loadedLevel) return;

        sheet = AssetPool.getSpriteSheet("res:assets/textures/Character_Sprite.png");

        object = new GameObject("TestObject", new Transform(new Vector2f(100, 100), new Vector2f(192, 192)), 0);
        SpriteRenderer objSpriteRenderer = new SpriteRenderer();
        Vector4f color = new Vector4f(1, 0, 0, 1);
        objSpriteRenderer.setColor(color);
        object.addComponent(objSpriteRenderer);
        this.addGameObjectToScene(object);
        this.activeGameObject = object;
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

    @Override
    public void update(float deltaTime) {

        for (GameObject go : this.gameObjects){
            go.update(deltaTime);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test Window");
        ImGui.text("Some random text");
        ImGui.end();
    }
}
