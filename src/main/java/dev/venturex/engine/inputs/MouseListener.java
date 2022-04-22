package dev.venturex.engine.inputs;

import dev.venturex.engine.Game;
import dev.venturex.engine.Window;
import org.joml.Vector2d;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

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

    public static float getOrthoX(){
        float currentX = (float) get().getPosition().x;
        currentX = (currentX / (float) Window.getWidth()) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(currentX, 0, 0, 1);
        tmp.mul(Game.getCurrentScene().camera().getInverseProjection()).mul(Game.getCurrentScene().camera().getInverseView());
        currentX = tmp.x;
        
        return currentX;
    }

    public static float getOrthoY(){
        float currentY = (float) (Window.getHeight() - get().getPosition().y);
        currentY = (currentY / (float) Window.getHeight()) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(0, currentY, 0, 1);
        tmp.mul(Game.getCurrentScene().camera().getInverseProjection()).mul(Game.getCurrentScene().camera().getInverseView());
        currentY = tmp.y;

        return currentY;
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
