package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.ghost.Blinky;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.ghost.strategy.BlinkyChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.BlinkyScatterStrategy;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.KinematicState;

public class BlinkyFactory extends GhostFactory{

    @Override
    Image getInitialGhostImage() {
        return BLINKY_IMAGE;
    }

    @Override
    Ghost createGhost(Image GHOST_IMAGE, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode) {
        return new Blinky(GHOST_IMAGE, boundingBox, kinematicState, ghostMode, new BlinkyChaseStrategy(), new BlinkyScatterStrategy());
    }
}
