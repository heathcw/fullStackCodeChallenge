package shared.test;

import org.junit.Test;
import shared.WordGame;

import java.util.ArrayList;

public class GameTest {

    @Test
    public void newWordTest() {
        WordGame myGame = new WordGame("word");
        ArrayList<Character> check = new ArrayList<>();
        check.add('w');
        check.add('o');
        check.add('r');
        check.add('d');
        assert check.equals(myGame.getGuessWord());
    }
}
