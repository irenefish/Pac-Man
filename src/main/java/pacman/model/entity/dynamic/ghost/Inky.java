package pacman.model.entity.dynamic.ghost;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.ghost.strategy.ChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.InkyChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.InkyScatterStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ScatterStrategy;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.KinematicState;
import pacman.model.entity.dynamic.physics.Vector2D;

public class Inky extends GhostImpl {

//    private Blinky blinky;

    public Inky(Image image, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode, InkyChaseStrategy chaseStrategy, InkyScatterStrategy scatterStrategy) {
        super(image, boundingBox, kinematicState, ghostMode, chaseStrategy, scatterStrategy);
    }

    @Override
    public void update(Vector2D playerPosition, Direction playerDirection) {
        super.update(playerPosition, playerDirection);
        // Update chase strategy with the player's current position
//        chaseStrategy.setPlayerPosition(playerPosition);

        Vector2D target = this.chaseStrategy.getChaseTargetLocation();

        // Implement movement towards target
        moveTowards(target);
    }

    private void moveTowards(Vector2D target) {
        // Implement your movement logic here
        // Example: Set velocity towards the target or determine next direction
    }
}
