package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.physics.Vector2D;

public class InkyScatterStrategy implements ScatterStrategy {

    @Override
    public Vector2D getScatterTargetLocation() {
        return new Vector2D(16 * 28, 16 * 34);
    }
}
