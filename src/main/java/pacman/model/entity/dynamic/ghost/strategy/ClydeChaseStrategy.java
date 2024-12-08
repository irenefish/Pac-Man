package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.state.GhostState;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;



public class ClydeChaseStrategy implements ChaseStrategy {

    Vector2D playerPosition; // need to retrieve
    Vector2D ghostPosition; // need to retrieve

    @Override
    public Vector2D getChaseTargetLocation() {
        if (Vector2D.calculateEuclideanDistance(playerPosition, ghostPosition) > 128) {
            return playerPosition;
        } else {
            return new Vector2D(0, 16 * 34);
        }
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
        this.ghostPosition = ghostPosition;
    }
}
