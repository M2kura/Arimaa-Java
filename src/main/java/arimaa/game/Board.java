package src.main.java.arimaa.game;

public class Board {
    public Square[][] grid = new Square[8][8];
    public Game game;

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

    public void placePiece(Piece piece, Square square) {
        grid[square.getColumn() - 'a'][square.getRow() - 1].setPiece(piece);
    }

    public Piece getPieceAt(Square square) {
        return grid[square.getColumn() - 'a'][square.getRow() - 1].getPiece();
    }

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
