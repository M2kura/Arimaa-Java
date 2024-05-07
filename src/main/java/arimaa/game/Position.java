package src.main.java.arimaa.game;

public class Position {
    private char column;
    private int row;

    public Position(String pos) {
        if (pos.length() != 2) {
            throw new IllegalArgumentException("Invalid board position format");
        }
        char col = pos.charAt(0);
        int rw = Character.getNumericValue(pos.charAt(1));
        if (col < 'a' || col > 'h' || rw < 1 || rw > 8) {
            throw new IllegalArgumentException("Invalid board position values: " + pos);
        }
        this.column = col;
        this.row = rw;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public boolean isValid() {
        return column >= 'a' && column <= 'h' && row >= 1 && row <= 8;
    }

    public boolean equals(Position other) {
        return this.column == other.column && this.row == other.row;
    }

    @Override
    public String toString() {
        return "" + column + row;
    }
}

