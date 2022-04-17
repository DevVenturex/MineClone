package dev.venturex.engine.renderer;

import dev.venturex.engine.GameObject;
import dev.venturex.engine.components.SpriteRenderer;

import java.util.ArrayList;
import java.util.List;

public class Renderer {

    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;

    public Renderer() {
        this.batches = new ArrayList<>();
    }

    public void add(GameObject gameObject){
        SpriteRenderer spriteRenderer = gameObject.getComponent(SpriteRenderer.class);
        if (spriteRenderer != null){
            add(spriteRenderer);
        }
    }

    private void add(SpriteRenderer spriteRenderer){
        boolean added = false;
        for (RenderBatch batch : batches){
            if (batch.hasRoom()){
                batch.addSprite(spriteRenderer);
                added = true;
                break;
            }
        }

        if (!added){
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprite(spriteRenderer);
        }
    }

    public void render(){
        for (RenderBatch batch : batches){
            batch.render();
        }
    }
}
