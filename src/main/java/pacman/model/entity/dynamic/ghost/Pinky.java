package pacman.model.entity.dynamic.ghost;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.ghost.strategy.ChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.PinkyChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.PinkyScatterStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ScatterStrategy;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.KinematicState;
import pacman.model.entity.dynamic.physics.Vector2D;

public class Pinky extends GhostImpl {
    public Pinky(Image image, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode, PinkyChaseStrategy chaseStrategy, PinkyScatterStrategy scatterStrategy) {
        super(image, boundingBox, kinematicState, ghostMode, chaseStrategy, scatterStrategy);
    }

    @Override
    public void update(Vector2D playerPosition, Direction playerDirection) {
        super.update(playerPosition, playerDirection);
        // Update chase strategy with the player's current position
        chaseStrategy.setPlayerPosition(playerPosition);
        chaseStrategy.setPlayerDirection(playerDirection);
    }
}
