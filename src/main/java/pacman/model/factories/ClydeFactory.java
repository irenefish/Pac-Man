package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.Clyde;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.ghost.strategy.ClydeChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ClydeScatterStrategy;
import pacman.model.entity.dynamic.ghost.strategy.InkyChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.InkyScatterStrategy;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.KinematicState;
import pacman.model.entity.dynamic.physics.Vector2D;

public class ClydeFactory extends GhostFactory{

    @Override
    Image getInitialGhostImage() {
        return CLYDE_IMAGE;
    }

    @Override
    Ghost createGhost(Image GHOST_IMAGE, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode) {
        return new Clyde(GHOST_IMAGE, boundingBox, kinematicState, ghostMode, new ClydeChaseStrategy(), new ClydeScatterStrategy());
    }


}
