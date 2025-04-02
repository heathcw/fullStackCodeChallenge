package shared;

import java.util.ArrayList;

public class WordGame {

    private ArrayList<Character> guessWord;
    private ArrayList<Character> lettersGuessed = new ArrayList<>();

    public WordGame(String newWord) {
        guessWord = new ArrayList<>();
        for (int i = 0; i < newWord.length(); i++) {
            guessWord.add(newWord.charAt(i));
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

    public void newWord(String newWord) {
        guessWord = new ArrayList<>();
        for (int i = 0; i < newWord.length(); i++) {
            guessWord.add(newWord.charAt(i));
        }
        lettersGuessed = new ArrayList<>();
    }
}
