package pacman.model.level;

import javafx.scene.image.Image;
import org.json.simple.JSONObject;
import pacman.ConfigurationParseException;
import pacman.model.engine.observer.GameState;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.DynamicEntity;
import pacman.model.entity.dynamic.ghost.Ghost;
import pacman.model.entity.dynamic.ghost.GhostMode;
import pacman.model.entity.dynamic.ghost.decorator.FrightenedGhostDecorator;
import pacman.model.entity.dynamic.physics.PhysicsEngine;
import pacman.model.entity.dynamic.player.Controllable;
import pacman.model.entity.dynamic.player.Pacman;
import pacman.model.entity.staticentity.StaticEntity;
import pacman.model.entity.staticentity.collectable.Collectable;
import pacman.model.entity.staticentity.collectable.PowerPellet;
import pacman.model.level.observer.LevelStateObserver;
import pacman.model.maze.Maze;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Concrete implement of Pac-Man level
 */
public class LevelImpl implements Level {

    private static final int START_LEVEL_TIME = 100;
    private final Maze maze;
    private final List<LevelStateObserver> observers;
    private List<Renderable> renderables;
    private Controllable player;
    private List<Ghost> ghosts;
    private int tickCount;
    private Map<GhostMode, Integer> modeLengths;
    private int numLives;
    private int points;
    private GameState gameState;
    private List<Renderable> collectables;
    private GhostMode currentGhostMode;

    private int consecutiveEaten = 0;

    static final Image FRIGHTENED_IMAGE = new Image("maze/ghosts/frightened.png");

    public LevelImpl(JSONObject levelConfiguration,
                     Maze maze) {
        this.renderables = new ArrayList<>();
        this.maze = maze;
        this.tickCount = 0;
        this.observers = new ArrayList<>();
        this.modeLengths = new HashMap<>();
        this.gameState = GameState.READY;
        this.currentGhostMode = GhostMode.SCATTER;
        this.points = 0;

        initLevel(new LevelConfigurationReader(levelConfiguration));
    }

    private void initLevel(LevelConfigurationReader levelConfigurationReader) {
        // Fetch all renderables for the level
        this.renderables = maze.getRenderables();

        // Set up player
        if (!(maze.getControllable() instanceof Controllable)) {
            throw new ConfigurationParseException("Player entity is not controllable");
        }
        this.player = (Controllable) maze.getControllable();
        this.player.setSpeed(levelConfigurationReader.getPlayerSpeed());
        setNumLives(maze.getNumLives());

        // Set up ghosts
        this.ghosts = maze.getGhosts().stream()
                .map(element -> (Ghost) element)
                .collect(Collectors.toList());
        Map<GhostMode, Double> ghostSpeeds = levelConfigurationReader.getGhostSpeeds();

        for (Ghost ghost : this.ghosts) {
            maze.updatePossibleDirections(ghost);

            player.registerObserver(ghost);
            ghost.setSpeeds(ghostSpeeds);
            ghost.setGhostMode(this.currentGhostMode);

        }
        this.modeLengths = levelConfigurationReader.getGhostModeLengths();

        // Set up collectables
        this.collectables = new ArrayList<>(maze.getPellets());

    }

    @Override
    public List<Renderable> getRenderables() {
        return this.renderables;
    }

    private List<DynamicEntity> getDynamicEntities() {
        return renderables.stream().filter(e -> e instanceof DynamicEntity).map(e -> (DynamicEntity) e).collect(
                Collectors.toList());
    }

    private List<StaticEntity> getStaticEntities() {
        return renderables.stream().filter(e -> e instanceof StaticEntity).map(e -> (StaticEntity) e).collect(
                Collectors.toList());
    }

    @Override
    public void tick() {
        if (this.gameState != GameState.IN_PROGRESS) {
            if (tickCount >= START_LEVEL_TIME) {
                setGameState(GameState.IN_PROGRESS);
                tickCount = 0;
            }
        } else {
            if (tickCount == modeLengths.get(currentGhostMode)) {

                if (currentGhostMode == GhostMode.FRIGHTENED) {
                    // After FRIGHTENED mode, switch back to SCATTER
                    this.currentGhostMode = GhostMode.SCATTER;;
                } else {
                    // Update ghost mode in the normal sequence
                    this.currentGhostMode = GhostMode.getNextGhostMode(currentGhostMode);
                }

                // update ghost mode
                for (Ghost ghost : this.ghosts) {
                    ghost.setGhostMode(this.currentGhostMode);
                }

                tickCount = 0;
            }

            if (tickCount % Pacman.PACMAN_IMAGE_SWAP_TICK_COUNT == 0) {
                this.player.switchImage();
            }

            // Update the dynamic entities
            List<DynamicEntity> dynamicEntities = getDynamicEntities();

            for (DynamicEntity dynamicEntity : dynamicEntities) {
                maze.updatePossibleDirections(dynamicEntity);
                dynamicEntity.update();
            }

            for (int i = 0; i < dynamicEntities.size(); ++i) {
                DynamicEntity dynamicEntityA = dynamicEntities.get(i);

                // handle collisions between dynamic entities
                for (int j = i + 1; j < dynamicEntities.size(); ++j) {
                    DynamicEntity dynamicEntityB = dynamicEntities.get(j);

                    if (dynamicEntityA.collidesWith(dynamicEntityB) ||
                            dynamicEntityB.collidesWith(dynamicEntityA)) {
                        dynamicEntityA.collideWith(this, dynamicEntityB);
                        dynamicEntityB.collideWith(this, dynamicEntityA);
                    }
                }

                // handle collisions between dynamic entities and static entities
                for (StaticEntity staticEntity : getStaticEntities()) {
                    if (dynamicEntityA.collidesWith(staticEntity)) {
                        dynamicEntityA.collideWith(this, staticEntity);
                        PhysicsEngine.resolveCollision(dynamicEntityA, staticEntity);
                    }
                }

            }
        }

        tickCount++;
    }

    @Override
    public boolean isPlayer(Renderable renderable) {
        return renderable == this.player;
    }

    @Override
    public boolean isCollectable(Renderable renderable) {
        return maze.getPellets().contains(renderable) && ((Collectable) renderable).isCollectable();
    }

    @Override
    public void collect(Collectable collectable) {

        if (collectable instanceof PowerPellet) {
            resetConsecutiveEaten();
            tickCount = 0;
            frightened();
        }

        this.points += collectable.getPoints();
        notifyObserversWithScoreChange(collectable.getPoints());

        this.collectables.remove(collectable);
    }

    private void frightened() {
        currentGhostMode = GhostMode.FRIGHTENED;
        System.out.println("Setting ghosts to frightened mode.");

        for (int i = 0; i < ghosts.size(); i++) {
            Ghost ghost = ghosts.get(i);

            if (!(ghost instanceof FrightenedGhostDecorator)) {
                Ghost decoratedGhost = new FrightenedGhostDecorator(ghost, FRIGHTENED_IMAGE);
                ghosts.set(i, decoratedGhost);
                System.out.println("Applying FrightenedGhostDecorator to ghost: " + ghost);

                // Also replace the ghost in the renderables list
                int renderableIndex = renderables.indexOf(ghost);
                if (renderableIndex != -1) {
                    renderables.set(renderableIndex, decoratedGhost);
                }
            }

            ghosts.get(i).setGhostMode(currentGhostMode);
        }
    }

    @Override
    public void handleLoseLife() {
        if (gameState == GameState.IN_PROGRESS) {
            for (DynamicEntity dynamicEntity : getDynamicEntities()) {
                dynamicEntity.reset();
            }
            setNumLives(numLives - 1);
            setGameState(GameState.READY);
            tickCount = 0;
        }
    }

    @Override
    public void moveLeft() {
        player.left();
    }

    @Override
    public void moveRight() {
        player.right();
    }

    @Override
    public void moveUp() {
        player.up();
    }

    @Override
    public void moveDown() {
        player.down();
    }

    @Override
    public boolean isLevelFinished() {
        return collectables.isEmpty();
    }

    @Override
    public void registerObserver(LevelStateObserver observer) {
        this.observers.add(observer);
        observer.updateNumLives(this.numLives);
        observer.updateGameState(this.gameState);
    }

    @Override
    public void removeObserver(LevelStateObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObserversWithNumLives() {
        for (LevelStateObserver observer : observers) {
            observer.updateNumLives(this.numLives);
        }
    }

    private void setGameState(GameState gameState) {
        this.gameState = gameState;
        notifyObserversWithGameState();
    }

    @Override
    public void notifyObserversWithGameState() {
        for (LevelStateObserver observer : observers) {
            observer.updateGameState(gameState);
        }
    }

    /**
     * Notifies observer of change in player's score
     */
    public void notifyObserversWithScoreChange(int scoreChange) {
        for (LevelStateObserver observer : observers) {
            observer.updateScore(scoreChange);
        }
    }

    @Override
    public int getPoints() {
        return this.points;
    }

    @Override
    public int getNumLives() {
        return this.numLives;
    }

    private void setNumLives(int numLives) {
        this.numLives = numLives;
        notifyObserversWithNumLives();
    }

    @Override
    public void handleGameEnd() {
        this.renderables.removeAll(getDynamicEntities());
    }

    @Override
    public void addPoints(int points) {
        this.points += points;
        notifyObserversWithScoreChange(points);
    }

    @Override
    public void handleGhostEaten(Ghost ghost) {
        consecutiveEaten++;

        int pointsAwarded = 200 * (int) Math.pow(2, consecutiveEaten - 1);

        addPoints(pointsAwarded);
    }

    @Override
    public void resetConsecutiveEaten() {
        consecutiveEaten = 0;
    }
}
