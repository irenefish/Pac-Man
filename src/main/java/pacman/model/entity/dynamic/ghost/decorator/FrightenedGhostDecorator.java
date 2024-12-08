package pacman.model.entity.dynamic.ghost.decorator;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.ghost.strategy.ChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ScatterStrategy;
import pacman.model.entity.dynamic.physics.BoundingBox;
import pacman.model.entity.dynamic.physics.Direction;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.level.Level;

import java.util.Map;
import java.util.Set;
public class FrightenedGhostDecorator extends GhostDecorator {
    private final Image frightenedImage;

    public FrightenedGhostDecorator(Ghost ghost, Image frightenedImage) {
        super(ghost);
        this.frightenedImage = frightenedImage;
    }

    public Ghost getDecoratedGhost() {
        return this.decoratedGhost;
    }

    @Override
    public Image getImage() {
        if (decoratedGhost.getGhostMode() == GhostMode.FRIGHTENED) {
            return frightenedImage;
        } else {
            return decoratedGhost.getImage();
        }
    }

    @Override
    public double getWidth() {
        return decoratedGhost.getWidth();
    }

    @Override
    public double getHeight() {
        return decoratedGhost.getHeight();
    }

    @Override
    public Layer getLayer() {
        return decoratedGhost.getLayer();
    }

    @Override
    public BoundingBox getBoundingBox() {
        return decoratedGhost.getBoundingBox();
    }

    @Override
    public Vector2D getPositionBeforeLastUpdate() {
        return decoratedGhost.getPositionBeforeLastUpdate();
    }

    @Override
    public boolean collidesWith(Renderable renderable) {
        return decoratedGhost.collidesWith(renderable);
    }

    @Override
    public void setSpeeds(Map<GhostMode, Double> speeds) {
        decoratedGhost.setSpeeds(speeds);
    }

    @Override
    public void setChaseStrategy(ChaseStrategy chaseStrategy) {
        decoratedGhost.setChaseStrategy(chaseStrategy);
    }

    @Override
    public void setScatterStrategy(ScatterStrategy scatterStrategy) {
        decoratedGhost.setScatterStrategy(scatterStrategy);
    }

    @Override
    public ChaseStrategy getChaseStrategy() {
        return decoratedGhost.getChaseStrategy();
    }

    @Override
    public ScatterStrategy getScatterStrategy() {
        return decoratedGhost.getScatterStrategy();
    }

    @Override
    public Image getGhostImage() {
        return null;
    }

    @Override
    public void setPossibleDirections(Set<Direction> possibleDirections) {
        decoratedGhost.setPossibleDirections(possibleDirections);
    }

    @Override
    public Direction getDirection() {
        return decoratedGhost.getDirection();
    }

    @Override
    public Vector2D getCenter() {
        return decoratedGhost.getCenter();
    }

    @Override
    public void update(Vector2D position, Direction direction) {
        decoratedGhost.update(position, direction);
    }

    @Override
    public void reset() {
        decoratedGhost.reset();
    }
}
