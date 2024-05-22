package src.main.java.arimaa.game;

public class Player {
    private Piece.Color color;
    public boolean submitSetup = false;
    public boolean submitMove = false;
    public int currentTurnMoves = 0;
    private Square[] squaresForSwitch = new Square[2];
    private int squareIndex = 0;
    private Piece pieceForPP;
    private Piece pieceToMove;

    public Player(Piece.Color color) {
        this.color = color;
    }

    public Piece.Color getColor() {
        return color;
    }

    public void movePiece(Square square, Board board) {
        if (pieceToMove != null) {
            if (currentTurnMoves < 4){
                if (!pieceToMove.isFrozen(board)) {
                    if (pieceForPP != null) {
                        if (pieceForPP.getSquare().isAdjacent(pieceToMove.getSquare())) {
                            if (currentTurnMoves < 3) {
                                if (square.isAdjacent(pieceForPP.getSquare())) {
                                    pieceToMove.push(pieceForPP, square, board);
                                } else {
                                    pieceToMove.pull(pieceForPP, square, board);
                                }
                                currentTurnMoves += 2;
                            } else {
                                System.out.println("You must have at least 2 moves left for push/pull.");
                            }
                        } else {
                            System.out.println("The opponents piece you selected to push/pull is not adjacent to your piece.");
                        }
                    } else {
                        board.movePiece(pieceToMove.getSquare(), square);
                        currentTurnMoves++;
                    }
                } else {
                    System.out.println("The piece is frozen and cannot move.");
                }
            } else {
                System.out.println("You cannot make more then 4 moves per turn.");
            }
        }
        emptyPieceSelection();
    }

    public void setPieceForPP (Piece piece) {
        pieceForPP = piece;
    }

    public void setPieceToMove (Piece piece) {
        pieceToMove = piece;
    }

    private void emptyPieceSelection() {
        pieceForPP = null;
        pieceToMove = null;
    }

    public void addSquareForSwitch(Square square, Board board) {
        Piece piece = square.getPiece();
        if (piece != null && piece.getColor() == this.color) {
            squaresForSwitch[squareIndex] = square;
            squareIndex++;
            if (squareIndex == 2) {
                board.switchPieces(squaresForSwitch[0], squaresForSwitch[1]);
                squareIndex = 0;
                squaresForSwitch = new Square[2];
            }
        } else {
            System.out.println("The square does not contain a player's piece.");
        }
    }

    public boolean submittedSetup() {
        return submitSetup;
    }
}
