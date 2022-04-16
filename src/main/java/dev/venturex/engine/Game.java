package dev.venturex.engine;

public class Game implements Runnable {
    private Thread mainThread;
    private Window window;
    private String title;
    private int width;
    private int height;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
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
        window.get(title, width, height);
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
}
