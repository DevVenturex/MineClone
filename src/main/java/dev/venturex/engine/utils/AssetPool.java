package dev.venturex.engine.utils;

import dev.venturex.engine.gfx.Shader;
import dev.venturex.engine.gfx.Texture;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {

    private static Map<String, File> files = new HashMap<>();
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();

    public static File getFile(String resourceName){
        File file = new File(resourceName);
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
        File file = new File(resourceName);
        if (AssetPool.textures.containsKey(file.getAbsolutePath())){
            return AssetPool.textures.get(file.getAbsolutePath());
        } else {
            Texture texture = new Texture(resourceName);
            AssetPool.textures.put(file.getAbsolutePath(), texture);
            return texture;
        }
    }
}
