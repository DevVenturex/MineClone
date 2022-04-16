package dev.venturex.game;

import dev.venturex.engine.Game;

public class MyGame extends Game {

    public MyGame(String title, int width, int height) {
        super(title, width, height);
    }

    public static void main(String[] args) {
        Game game = new MyGame("MyGame", 1280, 720);
        game.start();
    }
}
