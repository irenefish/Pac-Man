package pacman.model.entity.dynamic.ghost.state;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.physics.Vector2D;

public interface GhostState {
//    Image getImage();
    Vector2D getTargetLocation();
}
