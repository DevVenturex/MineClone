package dev.venturex.game.scenes;

import dev.venturex.engine.Game;
import dev.venturex.engine.Scene;

public class SceneManager {

    private Scene lastScene;
    private Scene currentScene;

    public SceneManager(Game game, Scene defaultScene) {
        this.currentScene = defaultScene;
    }

    public void setScene(Scene nextScene){
        this.lastScene = currentScene;
        this.currentScene = nextScene;
        currentScene.init();
        currentScene.start();
    }
}
