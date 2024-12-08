package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.physics.Vector2D;

public class BlinkyScatterStrategy implements ScatterStrategy{

    @Override
    public Vector2D getScatterTargetLocation() {
        // Top-right corner
        return new Vector2D(16 * 28, 0);
    }
}
