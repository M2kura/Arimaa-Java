package src.test.java.arimaa;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.arimaa.game.*;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(Piece.Color.GOLD);
    }

    @Test
    public void testInitialization() {
        assertEquals(Piece.Color.GOLD, player.getColor());
    }

    @Test
    public void testSubmitSetup() {
        player.submitSetup = true;
        assertTrue(player.submitSetup);
    }

    @Test
    public void testTurnMoves() {
        player.currentTurnMoves = 2;
        assertEquals(2, player.currentTurnMoves);
    }
}
