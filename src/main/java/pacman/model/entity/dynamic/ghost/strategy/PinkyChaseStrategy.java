package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public class PinkyChaseStrategy implements ChaseStrategy {

    Vector2D playerPosition; // need to retrieve
    Direction playerDirection; // need to retrieve

    @Override
    public Vector2D getChaseTargetLocation() {
        // Four grid spaces ahead of Pac-Man, based on his direction
        Vector2D targetPosition = calculateTargetPosition(playerPosition, playerDirection, 4);
        return targetPosition;
    }

    @Override
    public void setPlayerPosition(Vector2D playerPosition) {
        this.playerPosition = playerPosition;
    }

    @Override
    public void setPlayerDirection(Direction playerDirection) {
        this.playerDirection = playerDirection;
    }

    @Override
    public void setGhostPosition(Vector2D ghostPosition) {

    }

    private Vector2D calculateTargetPosition(Vector2D position, Direction direction, int gridSpaces) {
        // Assuming each grid space is 16 units (adjust as per your game's grid size)
        double scale = 16.0 * gridSpaces;

        switch (direction) {
            case UP:
                return new Vector2D(position.getX(), position.getY() - scale);
            case DOWN:
                return new Vector2D(position.getX(), position.getY() + scale);
            case LEFT:
                return new Vector2D(position.getX() - scale, position.getY());
            case RIGHT:
                return new Vector2D(position.getX() + scale, position.getY());
            default:
                throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }
}
