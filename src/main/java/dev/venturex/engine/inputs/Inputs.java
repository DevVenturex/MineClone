package dev.venturex.engine.inputs;

public class Inputs {

    public static boolean isMouseButtonPressed(int button){
        if (button < MouseListener.get().getMouseButtons().length)
            return MouseListener.get().getMouseButtons()[button];
        return false;
    }

    public static boolean isKeyPressed(int key){
        if (key < KeyListener.get().getKeyPressed().length)
            return KeyListener.get().getKeyPressed()[key];
        return false;
    }
}
