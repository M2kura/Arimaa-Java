package src.main.java.arimaa.pieces;

import src.main.java.arimaa.game.*;

public class Rabbit extends Piece {
    public Rabbit(Square square, Color color) {
        super(square, 'R', color, 1);
    }

    @Override
    public boolean canMove(Square newSquare, Board board) {
        if (this.getSquare().isAdjacent(newSquare)) {
            if (board.getPieceAt(newSquare) == null) {
                int dy = newSquare.getRow() - this.getSquare().getRow();
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
