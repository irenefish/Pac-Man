package pacman.model.entity.dynamic.ghost;

import javafx.scene.image.Image;
import pacman.model.entity.Renderable;
import pacman.model.entity.dynamic.ghost.state.ChaseState;
import pacman.model.entity.dynamic.ghost.state.FrightenedState;
import pacman.model.entity.dynamic.ghost.state.GhostState;
import pacman.model.entity.dynamic.ghost.state.ScatterState;
import pacman.model.entity.dynamic.ghost.strategy.ChaseStrategy;
import pacman.model.entity.dynamic.ghost.strategy.ScatterStrategy;
import pacman.model.entity.dynamic.physics.*;
import pacman.model.level.Level;
import pacman.model.maze.Maze;

import java.util.*;

/**
 * Concrete implementation of Ghost entity in Pac-Man Game
 */
public class GhostImpl implements Ghost {

    private static final int minimumDirectionCount = 8;
    private final Layer layer = Layer.FOREGROUND;
    private final Image ghostImage;
    private final BoundingBox boundingBox;
    private final Vector2D startingPosition;
//    private final Vector2D targetCorner;
    private KinematicState kinematicState;
    GhostMode ghostMode;
    private Vector2D targetLocation;
    private Vector2D playerPosition;
    private Direction currentDirection;
    private Set<Direction> possibleDirections;
    private Map<GhostMode, Double> speeds;
    private int currentDirectionCount = 0;

    GhostState currentState;
    GhostState chaseState;
    GhostState scatterState;
    GhostState frightenedState;

    ChaseStrategy chaseStrategy;
    ScatterStrategy scatterStrategy;

    Random random;

//    private final Image FRIGHTENED_IMAGE = new Image("maze/ghosts/frightened.png");

    public GhostImpl(Image image, BoundingBox boundingBox, KinematicState kinematicState, GhostMode ghostMode, ChaseStrategy chaseStrategy, ScatterStrategy scatterStrategy) {
        this.ghostImage = image;
        this.boundingBox = boundingBox;
        this.kinematicState = kinematicState;
        this.startingPosition = kinematicState.getPosition();
        this.ghostMode = ghostMode;
        this.possibleDirections = new HashSet<>();
//        this.targetCorner = targetCorner;
        this.currentDirection = null;

        this.random = new Random();


        this.chaseState = new ChaseState(this);
        this.scatterState = new ScatterState(this);
        this.frightenedState = new FrightenedState(this);

        this.scatterStrategy = scatterStrategy;
        this.chaseStrategy = chaseStrategy;

        switch (ghostMode) {
            case CHASE -> this.currentState = chaseState;
            case SCATTER -> this.currentState = scatterState;
            case FRIGHTENED -> this.currentState = frightenedState;
            default -> this.currentState = scatterState; // Default to SCATTER mode
        }

        this.targetLocation = currentState.getTargetLocation();
    }

    @Override
    public void setSpeeds(Map<GhostMode, Double> speeds) {
        this.speeds = speeds;
    }

    @Override
    public Image getImage() {
        return ghostImage;
//        return ghostMode == GhostMode.FRIGHTENED ? FRIGHTENED_IMAGE : ghostImage;
    }

    public Image getGhostImage() {
        return ghostImage;
    }

    @Override
    public GhostMode getGhostMode() {
        return ghostMode;
    }

    public ChaseStrategy getChaseStrategy() {
        return chaseStrategy;
    }

    public ScatterStrategy getScatterStrategy() {
        return scatterStrategy;
    }

    @Override
    public void update() {
        if (currentState != frightenedState){
            this.targetLocation = currentState.getTargetLocation();
        } else {
            this.targetLocation = null;
        }
        this.updateDirection();
        this.kinematicState.update();
        this.boundingBox.setTopLeft(this.kinematicState.getPosition());
    }

    private void updateDirection() {
        // Ghosts update their target location when they reach an intersection
        if (Maze.isAtIntersection(this.possibleDirections)) {
//            System.out.println("isAtIntersection");
            if (currentState != frightenedState) {
                this.targetLocation = getTargetLocation();
            } else {
                this.targetLocation = null;
            }
        }

        Direction newDirection;

        if (this.ghostMode == GhostMode.FRIGHTENED) {
            // Use random direction selection for FRIGHTENED mode
            newDirection = determineNextDirection(this.possibleDirections);
        } else {
            // Use standard direction selection for CHASE and SCATTER modes
            newDirection = selectDirection(this.possibleDirections);
        }

        // Ghosts have to continue in a direction for a minimum time before changing direction
        if (this.currentDirection != newDirection) {
            this.currentDirectionCount = 0;
        }
        this.currentDirection = newDirection;

        switch (currentDirection) {
            case LEFT -> this.kinematicState.left();
            case RIGHT -> this.kinematicState.right();
            case UP -> this.kinematicState.up();
            case DOWN -> this.kinematicState.down();
        }
    }

    private Vector2D getTargetLocation() {
        return currentState.getTargetLocation();
    }

    private Direction selectDirection(Set<Direction> possibleDirections) {
        if (possibleDirections.isEmpty()) {
            return currentDirection;
        }

        // ghosts have to continue in a direction for a minimum time before changing direction
        if (currentDirection != null && currentDirectionCount < minimumDirectionCount) {
            currentDirectionCount++;
            return currentDirection;
        }

        Map<Direction, Double> distances = new HashMap<>();

        for (Direction direction : possibleDirections) {
            // ghosts never choose to reverse travel
            if (currentDirection == null || direction != currentDirection.opposite()) {
                distances.put(direction, Vector2D.calculateEuclideanDistance(this.kinematicState.getPotentialPosition(direction), this.targetLocation));
            }
        }

        // only go the opposite way if trapped
        if (distances.isEmpty()) {
            return currentDirection.opposite();
        }

        // select the direction that will reach the target location fastest
        return Collections.min(distances.entrySet(), Map.Entry.comparingByValue()).getKey();
    }


    public Direction determineNextDirection(Set<Direction> possibleDirections) {
        // ghosts have to continue in a direction for a minimum time before changing direction
        if (currentDirection != null && currentDirectionCount < minimumDirectionCount) {
            currentDirectionCount++;
            return currentDirection;
        }

        // Remove the opposite direction to prevent immediate reversal
        Direction currentDir = this.currentDirection;
        if (currentDir != null) {
            possibleDirections.remove(currentDir.opposite());
        }

        // Handle cases where possibleDirections might be empty after removal
        if (possibleDirections.isEmpty()) {
            if (currentDir != null) {
                // Reverse direction to prevent getting stuck
                return currentDir.opposite();
            } else {
                return getRandomDirection();
            }
        }

        int index = random.nextInt(possibleDirections.size());
        return (Direction) possibleDirections.toArray()[index];
    }

    private Direction getRandomDirection() {
        List<Direction> allDirections = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT);
        return allDirections.get(random.nextInt(allDirections.size()));
    }


    @Override
    public void setGhostMode(GhostMode ghostMode) {
        this.ghostMode = ghostMode;
        this.kinematicState.setSpeed(speeds.get(ghostMode));
        // ensure direction is switched
        this.currentDirectionCount = minimumDirectionCount;

        // change state, not sure
        this.currentState = switch (ghostMode) {
            case CHASE -> chaseState;
            case SCATTER -> scatterState;
            case FRIGHTENED -> frightenedState;
        };

        if (currentState != frightenedState) {
            this.targetLocation = currentState.getTargetLocation();
            if (this.targetLocation == null) {
                System.err.println("Warning: Target location is null after setting ghost mode to " + ghostMode + ". Assigning default target.");
            }
        } else {
            this.targetLocation = null;
        }

        System.out.println("Ghost mode set to: " + ghostMode);
    }

    @Override
    public boolean collidesWith(Renderable renderable) {
        return boundingBox.collidesWith(kinematicState.getSpeed(), kinematicState.getDirection(), renderable.getBoundingBox());
    }

    @Override
    public void collideWith(Level level, Renderable renderable) {
        if (level.isPlayer(renderable)) {
            if (this.ghostMode == GhostMode.FRIGHTENED) {
                level.handleGhostEaten(this);
                this.reset();

            } else {
                level.resetConsecutiveEaten();
                level.handleLoseLife();
            }
        }
    }

    @Override
    public void update(Vector2D playerPosition, Direction playerDirection) {
        this.playerPosition = playerPosition;

        if (chaseStrategy != null) {
            chaseStrategy.setPlayerPosition(playerPosition);
            chaseStrategy.setPlayerDirection(playerDirection);
            chaseStrategy.setGhostPosition(this.kinematicState.getPosition());
        }
        this.update();
    }

    @Override
    public Vector2D getPositionBeforeLastUpdate() {
        return this.kinematicState.getPreviousPosition();
    }

    @Override
    public double getHeight() {
        return this.boundingBox.getHeight();
    }

    @Override
    public double getWidth() {
        return this.boundingBox.getWidth();
    }

    @Override
    public Vector2D getPosition() {
        return this.kinematicState.getPosition();
    }

    @Override
    public void setPosition(Vector2D position) {
        this.kinematicState.setPosition(position);
    }

    @Override
    public Layer getLayer() {
        return this.layer;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public void reset() {
        // return ghost to starting position
        this.kinematicState = new KinematicStateImpl.KinematicStateBuilder()
                .setPosition(startingPosition)
                .build();
        this.boundingBox.setTopLeft(startingPosition);
        this.setGhostMode(GhostMode.SCATTER);
        this.currentDirectionCount = minimumDirectionCount;
    }

    @Override
    public void setPossibleDirections(Set<Direction> possibleDirections) {
        this.possibleDirections = possibleDirections;
    }

    @Override
    public Direction getDirection() {
        return this.kinematicState.getDirection();
    }

    @Override
    public Vector2D getCenter() {
        return new Vector2D(boundingBox.getMiddleX(), boundingBox.getMiddleY());
    }

    public void setChaseStrategy(ChaseStrategy chaseStrategy) {
        this.chaseStrategy = chaseStrategy;
    }

    public void setScatterStrategy(ScatterStrategy scatterStrategy) {
        this.scatterStrategy = scatterStrategy;
    }
}
