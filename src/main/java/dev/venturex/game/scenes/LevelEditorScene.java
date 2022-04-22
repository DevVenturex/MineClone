package dev.venturex.game.scenes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.venturex.engine.*;
import dev.venturex.engine.components.*;
import dev.venturex.engine.gfx.Camera;
import dev.venturex.engine.inputs.Inputs;
import dev.venturex.engine.inputs.MouseListener;
import dev.venturex.engine.maths.Transform;
import dev.venturex.engine.renderer.DebugDraw;
import dev.venturex.engine.utils.AssetPool;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.File;

import static org.lwjgl.glfw.GLFW.*;

public class LevelEditorScene extends Scene {

    private final int SPEED = 300;
    private final int GRAVITY = 1400;

    private GameObject object;
    private SpriteSheet sheet;

    GameObject levelEditorStuff = new GameObject("LevelEditor", new Transform(new Vector2f()), 0);


    @Override
    public void init() {
        levelEditorStuff.addComponent(new MouseControls());
        levelEditorStuff.addComponent(new GridLines());

        loadResources();
        this.camera = new Camera(new Vector2f());
        sheet = AssetPool.getSpriteSheet("res:assets/textures/TileSet.png");
        if (loadedLevel) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }


//        object = new GameObject("TestObject", new Transform(new Vector2f(100, 100), new Vector2f(192, 192)), 0);
//        SpriteRenderer objSpriteRenderer = new SpriteRenderer();
//        Vector4f color = new Vector4f(1, 0, 0, 1);
//        objSpriteRenderer.setColor(color);
//        object.addComponent(objSpriteRenderer);
//        object.addComponent(new RigidBody());
//        this.addGameObjectToScene(object);
//        this.activeGameObject = object;


    }

    private void loadResources(){
        File vertexShaderFile = AssetPool.getFile("res:assets/shaders/vertex.glsl");
        File fragmentShaderFile = AssetPool.getFile("res:assets/shaders/fragment.glsl");
        AssetPool.getShader(vertexShaderFile, fragmentShaderFile);
        AssetPool.addSpritesSheet("res:assets/textures/TileSet.png",
                new SpriteSheet(AssetPool.getTexture("res:assets/textures/TileSet.png"), 16, 16, 256, 0));
    }

    @Override
    public void update(float deltaTime) {
        levelEditorStuff.update(deltaTime);

        for (GameObject go : this.gameObjects){
            go.update(deltaTime);
        }

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test Window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sheet.size(); i++) {
            Sprite sprite = sheet.getSprite(i);
            float spriteWidth = sprite.getWidth() * 3;
            float spriteHeight = sprite.getHeight() * 3;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)){
                GameObject obj = Prefabs.generateSpriteObject(sprite, 32, 32);
                levelEditorStuff.getComponent(MouseControls.class).pickupObject(obj);
            }

            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sheet.size() && nextButtonX2 < windowX2){
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }
}
