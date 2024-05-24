package src.test.java.arimaa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import src.main.java.arimaa.game.*;

import static org.junit.Assert.assertTrue;

public class PieceTest {

    @Test
    public void testIsFrozen() {
        Game game = new Game();
        game.setupGame();
        game.getBoard().placePiece(game.getBoard().grid[0][1].getPiece(), game.getBoard().grid[3][3]);
        game.getBoard().placePiece(game.getBoard().grid[7][7].getPiece(), game.getBoard().grid[4][3]);
        Assertions.assertTrue(game.getBoard().grid[3][3].getPiece().isFrozen(game.getBoard()));
        assertFalse(game.getBoard().grid[4][3].getPiece().isFrozen(game.getBoard()));
    }
}
