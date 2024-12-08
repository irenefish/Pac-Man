package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public class BlinkyChaseStrategy implements ChaseStrategy {
    Vector2D playerPosition; // need to retrieve

    @Override
    public Vector2D getChaseTargetLocation() {
        if (playerPosition == null) {
            throw new IllegalStateException("Player position is not set in BlinkyChaseStrategy.");
        }
        // Target Pac-Man's position
        return playerPosition;
    }

    @Override
    public void setPlayerPosition(Vector2D playerPosition) {
        this.playerPosition = playerPosition;
    }

    @Override
    public void setPlayerDirection(Direction playerDirection) {

    }

    @Override
    public void setGhostPosition(Vector2D ghostPosition) {

    }
}
