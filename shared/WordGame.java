package shared;

import java.util.ArrayList;

public class WordGame {

    private ArrayList<Character> guessWord;
    private ArrayList<Character> lettersGuessed = new ArrayList<>();
    private int mistakesLeft = 7;

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
        mistakesLeft = 7;
    }

    public void mistake() {
        mistakesLeft--;
    }

    public int getMistakesLeft() {
        return mistakesLeft;
    }
}
