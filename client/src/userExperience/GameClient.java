package userExperience;

import exception.ResponseException;
import shared.WordGame;

import java.util.ArrayList;
import java.util.Arrays;

public class GameClient {

    private final WordGame game;

    public GameClient() {
        game = new WordGame("test");
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "guess" -> guess(params);
                case "retry" -> retry();
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

    public String retry(){
        game.newWord("random");
        return "New Word";
    }

    public String help() {
        return """
               - guess <LETTER>
               - retry
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