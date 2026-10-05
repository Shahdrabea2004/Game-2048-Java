package com.example.game2048;

import com.example.game2048.grid.Grid;
import com.example.game2048.grid.Tile;
import com.example.game2048.service.MoveService;
import com.example.game2048.service.TileGenerator;

public class Game {

    private final Grid grid;
    private final MoveService moveService;
    private final TileGenerator tileGenerator;

    private GameState state;
    private int score;

    public Game() {
        this(Grid.DEFAULT_SIZE, new TileGenerator());
    }

    public Game(int gridSize) {
        this(gridSize, new TileGenerator());
    }

    public Game(int gridSize, TileGenerator tileGenerator) {
        this.grid = new Grid(gridSize);
        this.moveService = new MoveService(grid);
        this.tileGenerator = tileGenerator;
        this.state = GameState.NEW;
    }

    public void start() {
        // TODO: Start a fresh game with two tiles.
        this.state = GameState.RUNNING;
        this.score = 0;
        grid.clear();
        for (int i = 0; i < 2; i++) {
            tileGenerator.spawnTile(grid);
        }
    }

    public boolean move(Direction direction) {
        // TODO: Reject moves when appropriate, move the board, update score,
        // spawn a tile after a successful move and update game state.
        if (state != GameState.RUNNING) {
            return false;
        }

        MoveResult moveResult = moveService.execute(direction);

        if (!moveResult.changed()) {
            return false;
        }

        score += moveResult.scoreGained();
        tileGenerator.spawnTile(grid);

        if (isGameOver()) {
            state = GameState.GAME_OVER;
        }
        return true;
    }

    public void restart() {
        // TODO
        start();
    }

    public GameState getState() {
        return state;
    }

    public int getScore() {
        return score;
    }

    public Grid getGrid() {
        return grid;
    }

    // TODO: The implementation should determine whether the game has ended.
    private boolean isGameOver() {

        if (!grid.isFull()) {
            return false;
        }

        int size = grid.getSize();

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {

                Tile current = grid.getTile(row, column);

                // Check right neighbor
                if (column < size - 1) {
                    Tile right = grid.getTile(row, column + 1);

                    if (current.getValue() == right.getValue()) {
                        return false;
                    }
                }

                // Check down neighbor
                if (row < size - 1) {
                    Tile down = grid.getTile(row + 1, column);

                    if (current.getValue() == down.getValue()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
