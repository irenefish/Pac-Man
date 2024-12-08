package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.ConfigurationParseException;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostImpl;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.*;

import java.util.Arrays;
import java.util.List;

/**
 * Concrete renderable factory for Ghost objects
 */
public abstract class GhostFactory implements RenderableFactory {

    private static final int RIGHT_X_POSITION_OF_MAP = 448;
    private static final int TOP_Y_POSITION_OF_MAP = 16 * 3;
    private static final int BOTTOM_Y_POSITION_OF_MAP = 16 * 34;

    static final Image BLINKY_IMAGE = new Image("maze/ghosts/blinky.png");
    static final Image INKY_IMAGE = new Image("maze/ghosts/inky.png");
    static final Image CLYDE_IMAGE = new Image("maze/ghosts/clyde.png");
    static final Image PINKY_IMAGE = new Image("maze/ghosts/pinky.png");
//    private static final Image GHOST_IMAGE = BLINKY_IMAGE;

    abstract Image getInitialGhostImage();

    List<Vector2D> targetCorners = Arrays.asList(
            new Vector2D(0, TOP_Y_POSITION_OF_MAP),
            new Vector2D(RIGHT_X_POSITION_OF_MAP, TOP_Y_POSITION_OF_MAP),
            new Vector2D(0, BOTTOM_Y_POSITION_OF_MAP),
            new Vector2D(RIGHT_X_POSITION_OF_MAP, BOTTOM_Y_POSITION_OF_MAP)
    );

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Ghost createRenderable(
            Vector2D position
    ) {
        try {
            position = position.add(new Vector2D(4, -4));

            BoundingBox boundingBox = new BoundingBoxImpl(
                    position,
                    getInitialGhostImage().getHeight(),
                    getInitialGhostImage().getWidth()
            );

            KinematicState kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                    .setPosition(position)
                    .build();

            return createGhost(
                    getInitialGhostImage(),
                    boundingBox,
                    kinematicState,
                    GhostMode.SCATTER
//                    ,targetCorners.get(getRandomNumber(0, targetCorners.size() - 1))
            );
        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid ghost configuration | %s ", e));
        }
    }

    abstract Ghost createGhost(Image GHOST_IMAGE, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode);


}
