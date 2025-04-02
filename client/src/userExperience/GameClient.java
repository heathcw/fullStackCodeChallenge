package userExperience;

import exception.ResponseException;
import shared.Difficulty;
import shared.WordGame;
import shared.WordRequest;

import java.util.ArrayList;
import java.util.Arrays;

public class GameClient {

    private final WordRequest randomWord = new WordRequest(Difficulty.EASY);
    private final WordGame game;

    public GameClient() {
        String word = randomWord.getRandomWord();
        game = new WordGame(word);
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "guess" -> guess(params);
                case "retry" -> retry();
                case "debug" -> game.getGuessWord().toString();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String guess(String... params) throws ResponseException{
        if (params.length == 1 && params[0].length() == 1) {
            game.addLetter(params[0].charAt(0));
            return "You Guessed: " + params[0];
        }

        throw new ResponseException("Expected: <LETTER>");
    }

    public String retry(String... params) throws ResponseException{
        if (params.length == 1) {
            switch (params[0]) {
                case "easy" -> randomWord.updateDifficulty(Difficulty.EASY);
                case "medium" -> randomWord.updateDifficulty(Difficulty.MEDIUM);
                case "hard" -> randomWord.updateDifficulty(Difficulty.HARD);
                default -> throw new ResponseException("Expected: <EASY|MEDIUM|HARD>");
            }
            String word = randomWord.getRandomWord();
            game.newWord(word);
            return "New Word";
        }

        throw new ResponseException("Expected: <EASY|MEDIUM|HARD>");
    }

    public String help() {
        return """
               - guess <LETTER>
               - retry <EASY|MEDIUM|HARD>
               - quit
               - help
               """;
    }

    public String printWord() {
        ArrayList<Character> word = game.getGuessWord();
        ArrayList<Character> guessed = game.getLettersGuessed();
        ArrayList<Character> combined = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (Character c : word) {
            if (guessed.contains(c)) {
                combined.add(c);
            }
            else {
                combined.add('*');
            }
        }
        for (Character c : combined) {
            builder.append(c).append(" ");
        }
        return builder.toString();
    }
}