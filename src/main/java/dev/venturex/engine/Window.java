package dev.venturex.engine;

import dev.venturex.engine.inputs.Inputs;
import dev.venturex.engine.inputs.KeyListener;
import dev.venturex.engine.inputs.MouseListener;
import dev.venturex.engine.utils.Time;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private Game game;
    private Window instance;

    private String title;
    private int width, height;
    private long window;

    public Window get(Game game, String title, int width, int height){
        this.game = game;
        this.title = title;
        this.width = width;
        this.height = height;
        if (instance == null) {
            instance = new Window();
            return instance;
        }
        return instance;
    }

    public void run(){
        System.out.println("LWJGL runs on Versiom: " + Version.getVersion());

        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and the free error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init(){
        // Setup error Callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        window = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (window == NULL){
            throw new IllegalStateException("Failed to create the GLFW window");
        }

        glfwSetCursorPosCallback(window, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(window, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(window, KeyListener::keyCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        GL.createCapabilities();

        game.getCurrentScene().init();
    }

    private void loop(){
        float lastTime = Time.getTime();
        float endTime = Time.getTime();
        float deltaTime = -1;

        while (!glfwWindowShouldClose(window)){
            // Poll events
            glfwPollEvents();

            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            if (deltaTime >= 0) {
                game.getCurrentScene().update(deltaTime);
                glfwSetWindowTitle(window, title + " || FPS: " + (int) (1 / deltaTime));
            }

            glfwSwapBuffers(window);

            endTime = Time.getTime();
            deltaTime = endTime - lastTime;
            lastTime = endTime;
        }
    }
}
