package pacman.model.entity.dynamic.ghost.state;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.physics.Vector2D;

public class ScatterState implements GhostState {

    Ghost ghost;
    public ScatterState(Ghost ghost) {
        this.ghost = ghost;
    }

//    @Override
//    public Image getImage() {
//        return ghost.getGhostImage();
//    }

    @Override
    public Vector2D getTargetLocation() {
        return this.ghost.getScatterStrategy().getScatterTargetLocation();
    }
}
