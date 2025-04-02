package shared;

import java.util.ArrayList;

public class WordGame {

    private final String word;
    private ArrayList<Character> guessWord;

    public WordGame(String newWord) {
        this.word = newWord;
        guessWord = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            guessWord.add(word.charAt(i));
        }
    }

    public ArrayList<Character> getGuessWord() {
        return this.guessWord;
    }

}
