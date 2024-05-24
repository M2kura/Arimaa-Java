package src.main.java.arimaa.game;


/**
 * Represents the game board in an Arimaa game.
 * The board is an 8x8 grid of squares, and each square can hold a piece.
 */
public class Board {
    public Square[][] grid = new Square[8][8];
    public Game game;

    /**
     * Constructs a new board for the given game.
     * The board is initialized with a default setup of pieces.
     *
     * @param game The game that this board is part of.
     */
    public Board(Game game) {
        this.game = game;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean isTrap = (col == 2 || col == 5) && (row == 2 || row == 5);
                grid[col][row] = new Square((char) ('a' + col), row + 1, isTrap);
            }
        }
        defaultSetUp(Piece.Color.GOLD);
        defaultSetUp(Piece.Color.SILVER);
    }

    /**
     * Sets up the pieces for a player in their default starting positions.
     *
     * @param color The color of the player to set up.
     */
    public void defaultSetUp(Piece.Color color) {
        int row = (color == Piece.Color.GOLD) ? 0 : 7;
        this.placePiece(new Piece(this.grid[0][row], 'C', color), this.grid[0][row]);
        this.placePiece(new Piece(this.grid[7][row], 'C', color), this.grid[7][row]);
        this.placePiece(new Piece(this.grid[1][row], 'D', color), this.grid[1][row]);
        this.placePiece(new Piece(this.grid[6][row], 'D', color), this.grid[6][row]);
        this.placePiece(new Piece(this.grid[2][row], 'H', color), this.grid[2][row]);
        this.placePiece(new Piece(this.grid[5][row], 'H', color), this.grid[5][row]);
        this.placePiece(new Piece(this.grid[3][row], 'M', color), this.grid[3][row]);
        this.placePiece(new Piece(this.grid[4][row], 'E', color), this.grid[4][row]);
        if (row == 0) for (int i = 0; i < 8; i++) {
            this.placePiece(new Piece(this.grid[i][1], 'R', color), this.grid[i][1]);
        }
        else for (int i = 0; i < 8; i++) {
            this.placePiece(new Piece(this.grid[i][6], 'R', color), this.grid[i][6]);
        }
    }

    /**
     * Switches the positions of two pieces on the board.
     *
     * @param square1 The square of the first piece.
     * @param square2 The square of the second piece.
     */
    public void switchPieces(Square square1, Square square2) {
        Piece piece1 = getPieceAt(square1);
        Piece piece2 = getPieceAt(square2);
        if (piece1 != null && piece2 != null && piece1.getColor() == piece2.getColor()) {
            placePiece(piece1, square2);
            placePiece(piece2, square1);
        } else {
            System.out.println("Cannot switch pieces. Make sure both squares contain a piece with the same color.");
        }
    }

    /**
     * Places a piece on a square of the board.
     *
     * @param piece The piece to place.
     * @param square The square to place the piece on.
     */
    public void placePiece(Piece piece, Square square) {
        grid[square.getColumn() - 'a'][square.getRow() - 1].setPiece(piece);
    }

    /**
     * Returns the piece at a given square.
     *
     * @param square The square to check.
     * @return The piece at the given square, or null if the square is empty.
     */
    public Piece getPieceAt(Square square) {
        return grid[square.getColumn() - 'a'][square.getRow() - 1].getPiece();
    }

    /**
     * Moves a piece from one square to another.
     *
     * @param from The square the piece is moving from.
     * @param to The square the piece is moving to.
     */
    public void movePiece(Square from, Square to) {
        Piece piece = getPieceAt(from);
        if (piece.canMove(to, this)){
            this.game.movePiece(piece, from, to);
            grid[to.getColumn() - 'a'][to.getRow() - 1].setPiece(piece);
            grid[from.getColumn() - 'a'][from.getRow() - 1].setPiece(null);
            checkTraps();
        } else {
            System.out.println("Pieces can only move to adjacent squares.");
        }
    }

    /**
     * Checks all the trap squares on the board and removes any trapped pieces.
     */
    public void checkTraps() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = grid[col][row];
                if (square.isTrap()) {
                    Piece piece = getPieceAt(square);
                    if (piece != null && !piece.hasFriendlyAdjacent(this)) {
                        this.game.removePiece(piece, square);
                        square.setPiece(null);
                    }
                }
            }
        }
    }

    /**
     * Returns a text print of the board.
     *
     * @return A string representation of the board.
     */
    public String printBoard() {
        StringBuilder boardString = new StringBuilder();
        boardString.append("  +-----------------+\n");

        for (int row = 7; row > -1; row--) {
            boardString.append((row + 1) + " | ");
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[col][row].getPiece();

                if (piece != null) {
                    boardString.append(piece.getType() + " ");
                } else if (grid[col][row].isTrap()) {
                    boardString.append("x ");
                } else {
                    boardString.append("  ");
                }
            }
            boardString.append("|\n");
        }

        boardString.append("  +-----------------+\n");
        boardString.append("    a b c d e f g h\n");
        return boardString.toString();
    }
}
