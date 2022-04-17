package dev.venturex.engine;

public class Game implements Runnable {

    private static Game instance;
    private Thread mainThread;
    private Window window;
    private String title;
    private int width;
    private int height;
    private static Scene currentScene;
    private Scene mainScene;

    public Game(String title, int width, int height, Scene mainScene) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.mainScene = mainScene;
        this.currentScene = mainScene;
    }

    public synchronized void start(){
        mainThread = new Thread(this, "MAIN_THREAD");
        mainThread.start();
    }

    public synchronized void stop(){
        try {
            if (mainThread != null)
                mainThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        window = new Window();
        window.get(this, title, width, height);
        window.run();
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Scene getMainScene() {
        return currentScene;
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public void setScene(Scene nextScene){
        this.currentScene = nextScene;
        currentScene.init();
        currentScene.start();
    }
}
