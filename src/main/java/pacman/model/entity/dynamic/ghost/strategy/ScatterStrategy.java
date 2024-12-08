package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.physics.Vector2D;

public interface ScatterStrategy {
    Vector2D getScatterTargetLocation();
}
