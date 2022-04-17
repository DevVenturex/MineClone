package dev.venturex.engine.components;

import dev.venturex.engine.Component;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    Vector4f color;

    public SpriteRenderer(Vector4f color) {
        this.color = color;
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float deltaTime) {

    }

    public Vector4f getColor() {
        return this.color;
    }
}