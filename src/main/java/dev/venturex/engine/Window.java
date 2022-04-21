package dev.venturex.engine;

import dev.venturex.engine.imgui.ImGuiLayer;
import dev.venturex.engine.inputs.KeyListener;
import dev.venturex.engine.inputs.MouseListener;
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
    private static int width, height;
    private long window;
    private ImGuiLayer layer;

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
        glfwSetWindowSizeCallback(window, (w, width, height) -> {
            Window.setWidth(width);
            Window.setHeight(height);
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
        this.layer = new ImGuiLayer(window);
        this.layer.initImGui();

        game.getCurrentScene().init();
        game.getCurrentScene().start();
    }

    private void loop(){
        float lastTime = (float) glfwGetTime();
        float endTime = (float) glfwGetTime();
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

            this.layer.update(deltaTime, game.getCurrentScene());
            glfwSwapBuffers(window);

            endTime = (float) glfwGetTime();
            deltaTime = endTime - lastTime;
            lastTime = endTime;
        }
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setWidth(int width) {
        Window.width = width;
    }

    public static void setHeight(int height) {
        Window.height = height;
    }
}
