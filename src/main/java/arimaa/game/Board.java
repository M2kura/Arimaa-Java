package src.main.java.arimaa.game;

import src.main.java.arimaa.pieces.*;

public class Board {
    public Square[][] grid = new Square[8][8];

    public Board() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boolean isTrap = (col == 2 || col == 5) && (row == 2 || row == 5);
                grid[col][row] = new Square((char) ('a' + col), row + 1, isTrap);
            }
        }
    }

    public void defaultSetUp(Piece.Color color) {
        int row = (color == Piece.Color.GOLD) ? 0 : 7;
        this.placePiece(new Cat(this.grid[0][row], color), this.grid[0][row]);
        this.placePiece(new Cat(this.grid[7][row], color), this.grid[7][row]);
        this.placePiece(new Dog(this.grid[1][row], color), this.grid[1][row]);
        this.placePiece(new Dog(this.grid[6][row], color), this.grid[6][row]);
        this.placePiece(new Horse(this.grid[2][row], color), this.grid[2][row]);
        this.placePiece(new Horse(this.grid[5][row], color), this.grid[5][row]);
        this.placePiece(new Camel(this.grid[3][row], color), this.grid[3][row]);
        this.placePiece(new Elephant(this.grid[4][row], color), this.grid[4][row]);
        if (row == 0) for (int i = 0; i < 8; i++) {
            this.placePiece(new Rabbit(this.grid[i][1], color), this.grid[i][1]);
        }
        else for (int i = 0; i < 8; i++) {
            this.placePiece(new Rabbit(this.grid[i][6], color), this.grid[i][6]);
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

    public boolean movePiece(Square from, Square to) {
        Piece piece = getPieceAt(from);
        if (piece != null && piece.canMove(to, this)) {
            grid[to.getColumn() - 'a'][to.getRow() - 1].setPiece(piece);
            grid[from.getColumn() - 'a'][from.getRow() - 1].setPiece(null);
            piece.move(to, this);

            // Check if the piece landed on a trap and if there are no friendly pieces adjacent
            if (grid[to.getColumn() - 'a'][to.getRow() - 1].isTrap() && !hasFriendlyAdjacent(to, piece.getColor())) {
                // Remove the piece from the game
                grid[to.getColumn() - 'a'][to.getRow() - 1].setPiece(null);
            }

            return true;
        }
        return false;
    }

    private boolean hasFriendlyAdjacent(Square square, Piece.Color color) {
        for (Square adjacentSquare : square.adjacentSquares(this)) {
            Piece adjacentPiece = getPieceAt(adjacentSquare);
            if (adjacentPiece != null && adjacentPiece.getColor() == color) {
                return true;
            }
        }
        return false;
    }

    public void printBoard() {
        System.out.println("  +-----------------+");

        for (int row = 7; row > 0; row--) {
            System.out.print((row + 1) + " | ");
            for (int col = 0; col < 8; col++) {
                Piece piece = grid[col][row].getPiece();

                if (piece != null) {
                    System.out.print(piece.getType() + " ");
                } else if (grid[col][row].isTrap()) {
                    System.out.print("x ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println("|");
        }

        System.out.println("  +-----------------+");
        System.out.println("    a b c d e f g h");
    }
}
