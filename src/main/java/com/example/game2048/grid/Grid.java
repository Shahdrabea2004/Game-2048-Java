package com.example.game2048.grid;

public class Grid {

    public static final int DEFAULT_SIZE = 4;

    private final int size;
    private final Tile[][] tiles;

    public Grid() {
        this(DEFAULT_SIZE);
    }

    public Grid(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("Grid size must be at least 2");
        }
        this.size = size;
        this.tiles = new Tile[size][size];
    }

    public int getSize() {
        return size;
    }

    private void validateCoordinates(int row, int column) {
        if ((row < 0 || row >= this.getSize()) || (column < 0 || column >= this.getSize())) {
            throw new IndexOutOfBoundsException("Coordinates must be between 0 and " + (this.getSize() - 1));
        }
    }

    public Tile getTile(int row, int column) {
        // TODO: Validate coordinates and return the tile.
        validateCoordinates(row, column);
        return this.tiles[row][column];
    }

    public void setTile(int row, int column, Tile tile) {
        // TODO: Validate coordinates and update the cell.
        validateCoordinates(row, column);
        this.tiles[row][column] = tile;
    }

    public boolean isEmpty(int row, int column) {
        // TODO
        return getTile(row, column) == null;
    }

    public boolean isFull() {
        // TODO
        for (int row = 0; row < size; row++) {
            for (int colum = 0; colum < size; colum++) {
                if (isEmpty(row, colum)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void clear() {
        // TODO
        for (int row = 0; row < size; row++) {
            for (int colum = 0; colum < size; colum++) {
                tiles[row][colum] = null;
            }
        }

    }
}
