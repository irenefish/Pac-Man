package pacman.model.factories;

import javafx.scene.image.Image;
import pacman.ConfigurationParseException;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.BoundingBoxImpl;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.entity.staticentity.collectable.PowerPellet;

/**
 * Concrete renderable factory for PowerPellet objects
 */
public class PowerPelletFactory extends PelletFactory {
    private static final Image POWER_PELLET_IMAGE = new Image("maze/pellet.png");
    private static final int POWER_PELLET_POINTS = 50;
    private final Renderable.Layer layer = Renderable.Layer.BACKGROUND;

    @Override
    public Renderable createRenderable(
            Vector2D position
    ) {
        try {

            Vector2D adjustedPosition = position.add(new Vector2D(-8, -8));

            double boundingBoxHeight = POWER_PELLET_IMAGE.getHeight() * 2;
            double boundingBoxWidth = POWER_PELLET_IMAGE.getWidth() * 2;

            BoundingBox boundingBox = new BoundingBoxImpl(
                    adjustedPosition,
                    boundingBoxHeight,
                    boundingBoxWidth
            );

            return new PowerPellet(
                    boundingBox,
                    layer,
                    POWER_PELLET_IMAGE,
                    POWER_PELLET_POINTS
            );

        } catch (Exception e) {
            throw new ConfigurationParseException(
                    String.format("Invalid power pellet configuration | %s", e));
        }
    }
}
