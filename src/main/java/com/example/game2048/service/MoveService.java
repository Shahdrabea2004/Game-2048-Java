package com.example.game2048.service;

import com.example.game2048.Direction;
import com.example.game2048.MoveResult;
import com.example.game2048.Position;
import com.example.game2048.grid.Grid;
import com.example.game2048.grid.Tile;

import java.util.ArrayList;
import java.util.List;

public class MoveService {

    private final Grid grid;


    public MoveService(Grid grid) {
        this.grid = grid;
    }

    private Position positionAt(Direction direction, int k, int i) {
        int size = grid.getSize() - 1;
        return switch (direction) {
            case LEFT -> new Position(k, i);

            case RIGHT -> new Position(k, size - i);

            case UP -> new Position(i, k);

            case DOWN -> new Position(size - i, k);
        };
    }

    private List<Tile> readLine(Direction direction, int k) {
        List<Tile> currentLine = new ArrayList<>();
        for (int i = 0; i < grid.getSize(); i++) {
            Position position = positionAt(direction, k, i);
            currentLine.add(grid.getTile(position.row(), position.column()));
        }

        return currentLine;
    }

    private void writeLine(Direction direction, int k, List<Tile> line) {
        for (int i = 0; i < grid.getSize(); i++) {
            Position position = positionAt(direction, k, i);
            Tile tile = line.get(i);
            grid.setTile(position.row(), position.column(), tile);
        }
    }

    private int processLine(List<Tile> line) {
        int scoreGained = 0;

        line.removeIf(tile -> tile == null);

        for (int i = 0; i < line.size() - 1; i++) {
            if (line.get(i).getValue() == line.get(i + 1).getValue()) {
                Tile merge = new Tile(line.get(i).getValue());
                merge.merge();
                line.set(i, merge);
                scoreGained += merge.getValue();
                line.remove(i + 1);
            }
        }

        while (line.size() < grid.getSize()) {
            line.add(null);
        }

        return scoreGained;
    }

    public MoveResult execute(Direction direction) {
        boolean changed = false;
        int scoreGained = 0;

        for (int i = 0; i < grid.getSize(); i++) {
            List<Tile> before = readLine(direction, i);
            List<Tile> after = readLine(direction, i);
            scoreGained += processLine(after);
            if (!after.equals(before)) {
                changed = true;
                writeLine(direction, i, after);
            }
        }

        return new MoveResult(changed, scoreGained);
    }

    /**
     * Moves the board in the requested direction.
     *
     * @return true when the board changed, false for a no-op move.
     */
    public boolean move(Direction direction) {
        // TODO: Implement movement, shifting and merging.
        return execute(direction).changed();
    }

}
