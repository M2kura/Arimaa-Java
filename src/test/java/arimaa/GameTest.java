package src.test.java.arimaa;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.arimaa.game.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameTest {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testSwitchTurns() {
        Player initialPlayer = game.getCurrentPlayer();
        game.switchTurns();
        assertNotEquals(initialPlayer, game.getCurrentPlayer());
        game.switchTurns();
        assertEquals(initialPlayer, game.getCurrentPlayer());
    }

    @Test
    public void testLoadGame() throws IOException {
        File tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("1g Ca1 Ra2 Db1 Rb2 Rc1 Mc2 Rd1 Hd2 Ee1 Re2 Hf1 Rf2 Dg1 Rg2 Ch1 Rh2");
            writer.newLine();
            writer.write("1s ra7 ca8 rb7 db8 hc7 rc8 rd7 md8 re7 ee8 rf7 hf8 rg7 dg8 rh7 ch8");
            writer.newLine();
            writer.write("2g Hd2n Hd3e");
            writer.newLine();
            writer.write("2s re7s rf7s re6s rf6x");
            writer.newLine();
            writer.write("3g ");
            writer.newLine();
            writer.write("  +-----------------+");
            writer.newLine();
            writer.write("8 | c d r m e h d c |");
            writer.newLine();
            writer.write("7 | r r h r     r r |");
            writer.newLine();
            writer.write("6 |     x     x     |");
            writer.newLine();
            writer.write("5 |         r       |");
            writer.newLine();
            writer.write("4 |                 |");
            writer.newLine();
            writer.write("3 |     x   H x     |");
            writer.newLine();
            writer.write("2 | R R M   R R R R |");
            writer.newLine();
            writer.write("1 | C D R R E H D C |");
            writer.newLine();
            writer.write("  +-----------------+");
            writer.newLine();
            writer.write("    a b c d e f g h");
            writer.newLine();
        }

        game.loadGame(tempFile);

        assertFalse(game.isGameOver());
        assertEquals(3, game.getTurnCount());
        assertEquals(Piece.Color.GOLD, game.getCurrentPlayer().getColor());
    }
}
