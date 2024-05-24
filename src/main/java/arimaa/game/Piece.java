package src.main.java.arimaa.game;

/**
 * Represents a piece in an Arimaa game.
 * Each piece has a type, color, strength, and a square on the board where it is located.
 */
public class Piece {
    public enum Color {
        GOLD, SILVER
    }

    protected Square square;
    protected final char type;
    protected final Color color;
    protected final int strength;

    /**
     * Constructs a new piece with the given type and color, and places it on the given square.
     *
     * @param square The square on the board where the piece is located.
     * @param type The type of the piece.
     * @param color The color of the piece.
     */
    public Piece(Square square, char type, Color color) {
        this.square = square;
        this.color = color;
        if (color == Color.GOLD) {
            this.type = Character.toUpperCase(type);
        } else {
            this.type = Character.toLowerCase(type);
        }
        switch (type) {
            case 'r':
            case 'R':
                this.strength = 1;
                break;
            case 'c':
            case 'C':
                this.strength = 2;
                break;
            case 'd':
            case 'D':
                this.strength = 3;
                break;
            case 'h':
            case 'H':
                this.strength = 4;
                break;
            case 'm':
            case 'M':
                this.strength = 5;
                break;
            case 'e':
            case 'E':
                this.strength = 6;
                break;
            default:
                throw new IllegalArgumentException("Invalid piece type: " + type);
        }
    }

    /**
     * Sets the square on the board where the piece is located.
     *
     * @param square The square on the board where the piece is located.
     */
    public void setSquare(Square square) {
        this.square = square;
    }

    /**
     * Returns the square on the board where the piece is located.
     *
     * @return The square on the board where the piece is located.
     */
    public Square getSquare() {
        return square;
    }

    /**
     * Returns the type of the piece.
     *
     * @return The type of the piece.
     */
    public char getType() {
        return type;
    }

    /**
     * Returns the color of the piece.
     *
     * @return The color of the piece.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the strength of the piece.
     *
     * @return The strength of the piece.
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Pushes another piece to a new square on the board.
     * The other piece must be weaker and the new square must be adjacent to the current square of the weaker piece.
     * The stronger piece takes the place of the weaker piece.
     *
     * @param other The other piece to push.
     * @param newSquare The new square to push the other piece to.
     * @param board The game board.
     */
    public void push(Piece other, Square newSquare, Board board) {
        if (isStronger(other)) {
            if (other.canMove(newSquare, board)){
                Square currentSquare = other.square;
                board.movePiece(other.square, newSquare);
                board.movePiece(this.square, currentSquare);
            } else {
                System.out.println("Pushed piece must move to an adjacent square.");
            }
        } else {
            System.out.println("Cannot push a piece of equal or greater strength.");
        }
    }

    /**
     * Pulls another piece to a new square on the board.
     * The other piece must be weaker and the new square must be adjacent to the current square of the stronger piece.
     * The weaker piece takes the place of the stronger piece.
     *
     * @param other The other piece to pull.
     * @param newSquare The new square for stronger piece to go to.
     * @param board The game board.
     */
    public void pull(Piece other, Square newSquare, Board board) {
        if (isStronger(other)) {
            if (this.canMove(newSquare, board)){
                Square currentSquare = this.square;
                board.movePiece(this.square, newSquare);
                board.movePiece(other.square, currentSquare);
            } else {
                System.out.println("Your piece must move to an adjacent square when pulling other piece.");
            }
        } else {
            System.out.println("Cannot pull a piece of equal or greater strength.");
        }
    }

    /**
     * Checks if the piece is stronger than another piece.
     *
     * @param other The other piece to compare with.
     * @return true if this piece is stronger than the other piece, false otherwise.
     */
    private boolean isAdjacent(Piece other) {
        int dx = other.getSquare().getColumn() - this.square.getColumn();
        int dy = other.getSquare().getRow() - this.square.getRow();

        return (Math.abs(dx) == 1 && dy == 0) || (dx == 0 && Math.abs(dy) == 1);
    }

    /**
     * Checks if the piece is stronger than another piece.
     *
     * @param other The other piece to compare with.
     * @return true if this piece is stronger than the other piece, false otherwise.
     */
    public boolean isStronger(Piece other) {
        return this.strength > other.strength;
    }

    /**
     * Checks if the piece is frozen.
     * A piece is frozen if it is adjacent to a stronger enemy piece and there are no friendly pieces adjacent to it.
     *
     * @param board The game board.
     * @return true if the piece is frozen, false otherwise.
     */
    public boolean isFrozen(Board board) {
        for (Square adjacentSquare : square.adjacentSquares(board)) {
            Piece adjacentPiece = board.getPieceAt(adjacentSquare);
            if (adjacentPiece != null && adjacentPiece.getColor() != color && adjacentPiece.isStronger(this) && !hasFriendlyAdjacent(board)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the piece has any friendly pieces adjacent to it.
     *
     * @param board The game board.
     * @return true if the piece has any friendly pieces adjacent to it, false otherwise.
     */
    public boolean hasFriendlyAdjacent(Board board) {
        for (Square adjacentSquare : square.adjacentSquares(board)) {
            Piece adjacentPiece = board.getPieceAt(adjacentSquare);
            if (adjacentPiece != null && adjacentPiece.getColor() == color) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the piece can move to a new square.
     * The new square must be adjacent to the current square of the piece and must be empty.
     *
     * @param newSquare The new square to move the piece to.
     * @param board The game board.
     * @return true if the piece can move to the new square, false otherwise.
     */
    public boolean canMove(Square newSquare, Board board) {
        if (this.getSquare().isAdjacent(newSquare) && board.getPieceAt(newSquare) == null) {
            if (type == 'R' && board.game.getCurrentPlayer().getColor() == Color.GOLD) {
                return newSquare.getRow() >= square.getRow();
            }
            if (type == 'r' && board.game.getCurrentPlayer().getColor() == Color.SILVER) {
                return newSquare.getRow() <= square.getRow();
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a string representation of the piece.
     *
     * @return A string representation of the piece.
     */
    @Override
    public String toString() {
        return "Piece{" +
                "type=" + type +
                ", position=" + square +
                '}';
    }
}
