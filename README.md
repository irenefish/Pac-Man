# SOFT2201 - Assignment 3

## Index 
- [How To Run](#how-to-run)
- [Design Patterns](#Design-Patterns)

## How To Run
There are two steps to take when running the program:
### 1. To build the program:
```
gradle build
```
### 2. To run the program:
```
gradle run
```

## Design Patterns
### 1. Factory Pattern:
- Product: Renderable
- ConcreteProduct: GhostImpl, Pacman, PowerPellet, etc.
- Creator: RenderableFactory
- ConcreteCreator: Specific factories like PacmanFactory, PelletFactory, BlinkyFactory, WallFactory, etc.

### 2. Strategy Pattern:
- Context: GhostImpl
- Strategy Interface: ChaseStrategy, ScatterStrategy
- Concrete Strategies: BlinkyChaseStrategy, InkyChaseStrategy, etc.

### 3. State Pattern:
- Context: GhostImpl
- State Interface: GhostState
- Concrete States: ChaseState, ScatterState, FrightenedState

### 4. Decorator Pattern
- Component: Ghost
- ConcreteComponent: GhostImpl
- Decorator: GhostDecorator
- ConcreteDecorator: FrightenedGhostDecorator
