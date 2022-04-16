package dev.venturex.game.scenes;

import dev.venturex.engine.Scene;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {

    private String vertexShaderSrc = "#version 330 core\n" +
            "\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main() {\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";

    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "}";

    private float[] vertexArray = {
            // position                 // color
            0.5f, -0.5f, 0.0f,          1.0f, 0.0f, 0.0f, 1.0f, // Bottom right
            -0.5f, 0.5f, 0.0f,          0.0f, 1.0f, 0.0f, 1.0f, // Top left
            0.5f, 0.5f, 0.0f,           0.0f, 0.0f, 1.0f, 1.0f, // Top right
            -0.5f, -0.5f, 0.0f,         1.0f, 1.0f, 0.0f, 1.0f, // Bottom left
    };

    private int[] elementArray = {
        2, 1, 0, 0, 1, 3
    };

    private int vertexId, fragmentId, shaderProgram;
    private int vaoId, vboId, eboId;

    public LevelEditorScene() {
    }

    @Override
    public void init() {
        System.out.println("LevelEditorScene");
        // Compile and link shaders

        // -> First load and compile the vertex shader
        vertexId = glCreateShader(GL_VERTEX_SHADER);
        // -> Pass the shader source to the GPU
        glShaderSource(vertexId, vertexShaderSrc);
        glCompileShader(vertexId);

        // -> Check for errors in compilation
        int success = glGetShaderi(vertexId, GL_COMPILE_STATUS);
        if (success == GL_FALSE){
            int len = glGetShaderi(vertexId, GL_INFO_LOG_LENGTH);
            System.err.println("ERROR: 'vertex.glsl'\n\tVertex shader compilation failed.");
            System.err.println(glGetShaderInfoLog(vertexId, len));
        }

        // -> Then load and compile the fragment shader
        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        // -> Pass the shader source to the GPU
        glShaderSource(fragmentId, fragmentShaderSrc);
        glCompileShader(fragmentId);

        // -> Check for errors in compilation
        success = glGetShaderi(fragmentId, GL_COMPILE_STATUS);
        if (success == GL_FALSE){
            int len = glGetShaderi(fragmentId, GL_INFO_LOG_LENGTH);
            System.err.println("ERROR: 'fragment.glsl'\n\tFragment shader compilation failed.");
            System.err.println(glGetShaderInfoLog(fragmentId, len));
        }

        // -> Link shaders and check for errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexId);
        glAttachShader(shaderProgram, fragmentId);
        glLinkProgram(shaderProgram);

        // -> Check for linking errors
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if (success == GL_FALSE){
            int len = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.err.println("ERROR: Linking of shaders failed.");
            System.err.println(glGetProgramInfoLog(shaderProgram, len));
        }

        // Generate VAO, VBO, and EBO buffer objects, and send to GPU
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO upload the vertex buffer
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        int positionSize = 3;
        int colorSize = 4;
        int vertexSizeBytes = (positionSize + colorSize) * Float.BYTES;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionSize * Float.BYTES);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void update(float deltaTime) {
        // Bind shader program
        glUseProgram(shaderProgram);
        // Bind the VAO that we're using
        glBindVertexArray(vaoId);

        // Enable vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        glUseProgram(0);
    }
}
