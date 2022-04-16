package dev.venturex.game;

import dev.venturex.engine.Game;
import dev.venturex.engine.Scene;
import dev.venturex.game.scenes.LevelEditorScene;
import dev.venturex.game.scenes.LevelScene;
import dev.venturex.game.scenes.SceneManager;

public class MyGame extends Game {

    private SceneManager sceneManager;

    public MyGame(String title, int width, int height, Scene mainScene) {
        super(title, width, height, mainScene);
        this.sceneManager = new SceneManager(this, mainScene);
    }

    public static void main(String[] args) {
        Game game = new MyGame("MyGame", 1920, 1200, new LevelEditorScene());
        game.start();

        MyGame myGame = (MyGame) game;
        myGame.logic();
    }

    public void logic(){

    }
}
