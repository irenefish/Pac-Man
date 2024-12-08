package pacman.model.entity.dynamic.ghost;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.ghost.strategy.BlinkyChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.BlinkyScatterStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ScatterStrategy;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.KinematicState;
import pacman.model.entity.dynamic.physics.Vector2D;

public class Blinky extends GhostImpl {
    public Blinky(Image image, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode, BlinkyChaseStrategy chaseStrategy, BlinkyScatterStrategy scatterStrategy) {
        super(image, boundingBox, kinematicState, ghostMode, chaseStrategy, scatterStrategy);
//        super(image, boundingBox, kinematicState, ghostMode, new BlinkyScatterStrategy(), new BlinkyChaseStrategy());
    }

    @Override
    public void update(Vector2D playerPosition, Direction playerDirection) {
        super.update(playerPosition, playerDirection);
        // Update chase strategy with the player's current position
        chaseStrategy.setPlayerPosition(playerPosition);
    }

}
