package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.physics.Vector2D;

public class PinkyScatterStrategy implements ScatterStrategy {

    @Override
    public Vector2D getScatterTargetLocation() {
        // Top-left corner
        return new Vector2D(0, 16 * 3);
    }
}
