package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.ghost.Inky;
import pacman.model.entity.dynamic.ghost.strategy.ChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.InkyChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.InkyScatterStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ScatterStrategy;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.KinematicState;

public class InkyFactory extends GhostFactory {

    @Override
    Image getInitialGhostImage() {
        return INKY_IMAGE;
    }

    @Override
    Ghost createGhost(Image GHOST_IMAGE, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode) {
        return new Inky(GHOST_IMAGE, boundingBox, kinematicState, ghostMode, new InkyChaseStrategy(), new InkyScatterStrategy());
    }
}
