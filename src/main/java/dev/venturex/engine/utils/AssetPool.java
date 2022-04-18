package dev.venturex.engine.utils;

import dev.venturex.engine.components.SpriteSheet;
import dev.venturex.engine.gfx.Shader;
import dev.venturex.engine.gfx.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {

    private static final String LONG = "src/main/resources/";
    private static Map<String, File> files = new HashMap<>();
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    private static Map<String, SpriteSheet> spriteSheets = new HashMap<>();

    public static File getFile(String resourceName){
        File file = null;
        if (resourceName.startsWith("res:")){
            String[] paths = resourceName.split(":");
            String path = LONG + paths[1];
            file = new File(path);
        } else
            file = new File(resourceName);
        if (files.containsKey(file.getAbsolutePath())){
            return files.get(file.getAbsolutePath());
        } else {
            AssetPool.files.put(file.getAbsolutePath(), file);
            return file;
        }
    }

    public static Shader getShader(File vertexShaderFile, File fragmentShaderFile){
        Shader shader = new Shader(vertexShaderFile.getAbsolutePath(), fragmentShaderFile.getAbsolutePath());
        shader.compile();
        if (shaders.containsKey(String.valueOf(shader.getShaderProgramID()))){
            return shaders.get(String.valueOf(shader.getShaderProgramID()));
        } else {
            AssetPool.shaders.put(String.valueOf(shader.getShaderProgramID()), shader);
            return shader;
        }
    }

    public static Texture getTexture(String resourceName){
        File file = AssetPool.getFile(resourceName);
        if (AssetPool.textures.containsKey(file.getAbsolutePath())){
            return AssetPool.textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture(file.getAbsolutePath());
            AssetPool.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }

    public static void addSpritesSheet(String resourceName, SpriteSheet sheet){
        File file = AssetPool.getFile(resourceName);
        if (!AssetPool.spriteSheets.containsKey(file.getAbsolutePath())){
            AssetPool.spriteSheets.put(file.getAbsolutePath(), sheet);
        }
    }

    public static SpriteSheet getSpriteSheet(String resourceName){
        File file = AssetPool.getFile(resourceName);
        if (!AssetPool.spriteSheets.containsKey(file.getAbsolutePath())){
            throw new RuntimeException("Error: Tried to access spritesheet '" + file.getAbsolutePath() + "'");
        }
        return AssetPool.spriteSheets.getOrDefault(file.getAbsolutePath(), null);
    }


}
