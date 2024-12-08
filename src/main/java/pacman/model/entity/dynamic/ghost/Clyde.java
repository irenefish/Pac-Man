package pacman.model.entity.dynamic.ghost;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.ghost.strategy.ChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ClydeChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ClydeScatterStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ScatterStrategy;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.KinematicState;
import pacman.model.entity.dynamic.physics.Vector2D;

public class Clyde extends GhostImpl {
    public Clyde(Image image, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode, ClydeChaseStrategy chaseStrategy, ClydeScatterStrategy scatterStrategy) {
        super(image, boundingBox, kinematicState, ghostMode, chaseStrategy, scatterStrategy);
    }

    @Override
    public void update(Vector2D playerPosition, Direction playerDirection) {
        super.update(playerPosition, playerDirection);
        // Update chase strategy with the player's current position
        chaseStrategy.setPlayerPosition(playerPosition);
    }
}
