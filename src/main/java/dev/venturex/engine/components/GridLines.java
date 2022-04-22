package dev.venturex.engine.components;

import dev.venturex.engine.Game;
import dev.venturex.engine.renderer.DebugDraw;
import dev.venturex.engine.utils.Settings;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class GridLines extends Component {

    @Override
    public void update(float deltaTime) {
        Vector2f camPos = Game.getCurrentScene().camera().position;
        Vector2f projectionSize = Game.getCurrentScene().camera().getProjectionSize();

        int firstX = ((int) (camPos.x / Settings.GRID_WIDTH) * Settings.GRID_WIDTH);
        int firstY = ((int) (camPos.y / Settings.GRID_HEIGHT)) * Settings.GRID_HEIGHT;

        int numVtLines = (int) (projectionSize.x / Settings.GRID_WIDTH);
        int numHzLines = (int) (projectionSize.y / Settings.GRID_HEIGHT);

        int width = (int) projectionSize.x;
        int height = (int) projectionSize.y;

        int maxLines = Math.max(numVtLines, numHzLines);
        Vector3f color = new Vector3f(0.8f, 0.8f, 0.8f);
        for (int i = 0; i < maxLines; i++) {
            int x = firstX + (Settings.GRID_WIDTH * i);
            int y = firstY + (Settings.GRID_HEIGHT * i);

            if (i < numVtLines){
                DebugDraw.addLine2D(new Vector2f(x, firstY), new Vector2f(x, firstY + height), color);
            }

            if (i < numHzLines){
                DebugDraw.addLine2D(new Vector2f(firstX, y), new Vector2f(firstX + width, y), color);
            }
        }
    }
}
