package dev.venturex.engine.components;

import dev.venturex.engine.Game;
import dev.venturex.engine.GameObject;
import dev.venturex.engine.inputs.Inputs;
import dev.venturex.engine.inputs.MouseListener;
import dev.venturex.engine.utils.Settings;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class MouseControls extends Component {

    GameObject holdingObject = null;

    public void pickupObject(GameObject gameObj){
        this.holdingObject = gameObj;
        Game.getCurrentScene().addGameObjectToScene(gameObj);
    }

    public void place(){
        this.holdingObject = null;
    }

    @Override
    public void update(float deltaTime) {
        if (holdingObject != null){
            holdingObject.transform.position.x = MouseListener.getOrthoX();
            holdingObject.transform.position.y = MouseListener.getOrthoY();
            holdingObject.transform.position.x = (int) (holdingObject.transform.position.x / Settings.GRID_WIDTH) * Settings.GRID_WIDTH;
            holdingObject.transform.position.y = (int) (holdingObject.transform.position.y / Settings.GRID_HEIGHT) * Settings.GRID_HEIGHT;


            if (Inputs.isMouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT)){
                place();
            }
        }
    }
}
