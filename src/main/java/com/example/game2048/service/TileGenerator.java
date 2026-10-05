package com.example.game2048.service;

import com.example.game2048.grid.Grid;
import com.example.game2048.grid.Tile;

import java.util.Random;

public class TileGenerator {

    private final Random random;
    private static final int COMMON_VALUE = 2;
    private static final int RARE_VALUE = 4;
    private static final int COMMON_TILE_PERCENT = 90;
    private static final int PERCENT_BASE = 100;

    public TileGenerator() {
        this(new Random());
    }

    public TileGenerator(Random random) {
        this.random = random;
    }

    public Tile createTile() {
        // TODO: Generate 2 with 90% probability and 4 with 10% probability.
        int value = random.nextInt(PERCENT_BASE);
        return ((value < COMMON_TILE_PERCENT) ? new Tile(COMMON_VALUE) : new Tile(RARE_VALUE));
    }

    public void spawnTile(Grid grid) {
        if (grid.isFull()) {
            return;
        }
        int row;
        int column;
        do {
            row = random.nextInt(grid.getSize());
            column = random.nextInt(grid.getSize());
        } while (!grid.isEmpty(row, column));
        grid.setTile(row, column, createTile());
    }
}
