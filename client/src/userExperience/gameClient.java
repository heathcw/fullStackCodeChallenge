package userExperience;

import exception.ResponseException;
import shared.WordGame;

import java.util.Arrays;

public class gameClient {

    private WordGame game;

    public gameClient() {
        game = new WordGame("test");
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "guess" -> guess(params);
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

    public String help() {
        return """
               - guess <LETTER>
               - quit
               - help
               """;
    }
}