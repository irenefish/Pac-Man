package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.ghost.Pinky;
import pacman.model.entity.dynamic.ghost.strategy.PinkyChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.PinkyScatterStrategy;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.KinematicState;

public class PinkyFactory extends GhostFactory {

    @Override
    Image getInitialGhostImage() {
        return PINKY_IMAGE;
    }

    @Override
    Ghost createGhost(Image GHOST_IMAGE, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode) {
        return new Pinky(GHOST_IMAGE, boundingBox, kinematicState, ghostMode, new PinkyChaseStrategy(), new PinkyScatterStrategy());
    }
}
