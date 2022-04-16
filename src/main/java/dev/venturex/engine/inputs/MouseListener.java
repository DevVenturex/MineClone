package dev.venturex.engine.inputs;

import org.joml.Vector2d;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {

    private static MouseListener instance;
    private Vector2d scroll;
    private Vector2d position, lastPosition;
    private boolean[] mouseButtons = new boolean[3];
    private boolean isDragging;

    private MouseListener(){
        this.scroll = new Vector2d(0.0d, 0.0d);
        this.position = new Vector2d(0.0d, 0.0d);
        this.lastPosition = new Vector2d(0.0d, 0.0d);

    }

    public static MouseListener get(){
        if (instance == null){
            instance = new MouseListener();
        }
        return instance;
    }

    public static void mousePosCallback(long window, double xPos, double yPos){
        get().lastPosition = get().position;
        get().position.x = xPos;
        get().position.y = yPos;
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods){
        if (action == GLFW_PRESS) {
            if (button < get().mouseButtons.length)
                get().mouseButtons[button] = true;
        }else  if (action == GLFW_RELEASE){
            if (button < get().mouseButtons.length) {
                get().mouseButtons[button] = false;
                get().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset){
        get().scroll.x = xOffset;
        get().scroll.y = yOffset;
    }

    public static void endFrame(){
        get().scroll = new Vector2d(0.0d, 0.0d);
        get().lastPosition = new Vector2d(0.0d, 0.0d);
        get().position = new Vector2d(0.0d, 0.0d);
    }

    public Vector2d getScroll() {
        return scroll;
    }

    public Vector2d getPosition() {
        return position;
    }

    public Vector2d getLastPosition() {
        return lastPosition;
    }

    public boolean[] getMouseButtons() {
        return mouseButtons;
    }

    public boolean isDragging() {
        return isDragging;
    }
}
