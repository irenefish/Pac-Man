package pacman.model.entity.dynamic.ghost.state;

import javafx.scene.image.Image;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.maze.Maze;

public class FrightenedState implements GhostState {

    Ghost ghost;

    public FrightenedState(Ghost ghost) {
        this.ghost = ghost;
    }

//    @Override
//    public Image getImage() {
//        return ghost.getFrightenedImage();
//    }

    @Override
    public Vector2D getTargetLocation() {
        return null;
    }
}
