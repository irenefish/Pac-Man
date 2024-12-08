package pacman.model.entity.dynamic.ghost.decorator;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.physics.Vector2D;
import pacman.model.level.Level;

public abstract class GhostDecorator implements Ghost {
    protected Ghost decoratedGhost;

    public GhostDecorator(Ghost ghost) {
        this.decoratedGhost = ghost;
    }

    @Override
    public void update() {
        decoratedGhost.update();
    }

    @Override
    public void setGhostMode(GhostMode ghostMode) {
        decoratedGhost.setGhostMode(ghostMode);
    }

    @Override
    public void collideWith(Level level, Renderable renderable) {
        decoratedGhost.collideWith(level, renderable);
    }

    @Override
    public Image getImage() {
        return decoratedGhost.getImage();
    }

    @Override
    public Vector2D getPosition() {
        return decoratedGhost.getPosition();
    }

    @Override
    public void setPosition(Vector2D position) {
        decoratedGhost.setPosition(position);
    }

    @Override
    public GhostMode getGhostMode() {
        return decoratedGhost.getGhostMode();
    }
}

