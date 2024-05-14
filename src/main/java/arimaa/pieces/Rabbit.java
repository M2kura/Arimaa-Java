package src.main.java.arimaa.pieces;

import src.main.java.arimaa.game.*;

public class Rabbit extends Piece {
    public Rabbit(Square square, Color color) {
        super(square, 'R', color, 1);
    }

    @Override
    public boolean canMove(Square newSquare, Board board) {
        int dx = newSquare.getColumn() - square.getColumn();
        int dy = newSquare.getRow() - square.getRow();

        // Check if the move is one square in any direction (not diagonal)
        if ((Math.abs(dx) == 1 && dy == 0) || (dx == 0 && Math.abs(dy) == 1)) {
            if (board.getPieceAt(newSquare) == null) {
                // Check if the rabbit is not moving backwards
                return (color == Color.GOLD && dy >= 0) || (color == Color.SILVER && dy <= 0);
            }
        }

        return false;
    }

    @Override
    public boolean push(Piece other, Square newSquare, Board board) {
        return false;
    }

    @Override
    public boolean pull(Piece other, Square newSquare, Board board) {
        return false;
    }
}
