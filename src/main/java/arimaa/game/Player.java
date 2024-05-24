package src.main.java.arimaa.game;

/**
 * Represents a player in an Arimaa game.
 * Each player has a color, and can submit their setup and moves.
 */
public class Player {
    private Piece.Color color;
    public boolean submitSetup = false;
    public boolean submitMove = false;
    public int currentTurnMoves = 0;
    private Square[] squaresForSwitch = new Square[2];
    private int squareIndex = 0;
    private Piece pieceForPP;
    private Piece pieceToMove;

    /**
     * Constructs a new player with the given color.
     *
     * @param color The color of the player's pieces.
     */
    public Player(Piece.Color color) {
        this.color = color;
    }

    /**
     * Returns the color of the player's pieces.
     *
     * @return The color of the player's pieces.
     */
    public Piece.Color getColor() {
        return color;
    }

    /**
     * Moves a piece to a square on the board.
     * The piece must not be frozen and the player must not have made more than 4 moves in the current turn.
     *
     * @param square The square to move the piece to.
     * @param board The game board.
     */
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

    /**
     * Sets the piece for push/pull.
     *
     * @param piece The piece for push/pull.
     */
    public void setPieceForPP (Piece piece) {
        pieceForPP = piece;
    }

    /**
     * Sets the piece to move.
     *
     * @param piece The piece to move.
     */
    public void setPieceToMove (Piece piece) {
        pieceToMove = piece;
    }

    /**
     * Empties the piece selection.
     */
    public void emptyPieceSelection() {
        pieceForPP = null;
        pieceToMove = null;
    }

    /**
     * Adds a square for switching pieces.
     * The square must contain a piece of the player's color.
     *
     * @param square The square to add.
     * @param board The game board.
     */
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

    /**
     * Returns whether the player has submitted their setup.
     *
     * @return true if the player has submitted their setup, false otherwise.
     */
    public boolean submittedSetup() {
        return submitSetup;
    }
}
