package shared;

import java.util.ArrayList;

public class WordGame {

    private final String word;
    private ArrayList<Character> guessWord;
    private ArrayList<Character> lettersGuessed = new ArrayList<>();

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

    public void addLetter(Character c) {
        lettersGuessed.add(c);
    }

    public ArrayList<Character> getLettersGuessed() {
        return this.lettersGuessed;
    }

}
