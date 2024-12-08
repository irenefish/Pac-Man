package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public interface ChaseStrategy {
    Vector2D getChaseTargetLocation();
    void setPlayerPosition(Vector2D playerPosition);
    void setPlayerDirection(Direction playerDirection);
    void setGhostPosition(Vector2D ghostPosition);
}
