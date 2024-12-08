package pacman.model.entity.dynamic.ghost.strategy;

import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;

public class InkyChaseStrategy implements ChaseStrategy {
    Vector2D playerPosition; // need to retrieve
    Direction playerDirection; // need to retrieve
    Vector2D ghostPosition; // need to retrieve
//    Blinky blinky;

    @Override
    public Vector2D getChaseTargetLocation() {
        // Position two grid spaces ahead of Pac-Man
        Vector2D twoAhead = calculateTargetPosition(playerPosition, playerDirection, 2);

        // Vector from Blinky to the position two spaces ahead of Pac-Man
        Vector2D blinkyToTwoAhead = twoAhead.subtract(ghostPosition);

        Vector2D doubledVector = blinkyToTwoAhead.scale(2);

        Vector2D targetPosition = ghostPosition.add(doubledVector);

//        System.out.println("Inky's Target Position: " + targetPosition);

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
        this.ghostPosition  = ghostPosition;
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
