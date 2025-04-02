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
    private State gameState = State.WAITING;
    private boolean allowHint = true;

    public GameClient() {
        game = new WordGame("word");
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "start" -> start(params);
                case "guess" -> guess(params);
                case "retry" -> retry(params);
                case "hint" -> hint();
                case "quit" -> "quit";
                default -> help();
            };
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String start(String... params) throws ResponseException {
        if (assertStarted()) {
            throw new ResponseException("You already started");
        }
        if (params.length == 1) {
            switch (params[0]) {
                case "easy" -> randomWord.updateDifficulty(Difficulty.EASY);
                case "medium" -> randomWord.updateDifficulty(Difficulty.MEDIUM);
                case "hard" -> randomWord.updateDifficulty(Difficulty.HARD);
                default -> throw new ResponseException("Expected: <EASY|MEDIUM|HARD>");
            }
            String word = randomWord.getRandomWord();
            game.newWord(word);
            gameState = State.STARTED;
            allowHint = true;
            return printWord();
        }

        throw new ResponseException("Expected: <EASY|MEDIUM|HARD>");
    }

    public String guess(String... params) throws ResponseException {
        if (!assertStarted()) {
            throw new ResponseException("You must start a game");
        }
        if (params.length == 1 && params[0].length() == 1 && params[0].matches("[a-zA-Z]+")) {
            Character c = params[0].charAt(0);
            if (game.getLettersGuessed().contains(c)) {
                return "You already guessed that letter!";
            }
            game.addLetter(c);
            return update(c);
        }

        throw new ResponseException("Expected: <LETTER>");
    }

    public String retry(String... params) throws ResponseException{
        if (!assertStarted()) {
            throw new ResponseException("You must start a game");
        }
        if (params.length == 1) {
            switch (params[0]) {
                case "easy" -> randomWord.updateDifficulty(Difficulty.EASY);
                case "medium" -> randomWord.updateDifficulty(Difficulty.MEDIUM);
                case "hard" -> randomWord.updateDifficulty(Difficulty.HARD);
                default -> throw new ResponseException("Expected: <EASY|MEDIUM|HARD>");
            }
            String word = randomWord.getRandomWord();
            game.newWord(word);
            allowHint = true;
            return "New Word\n" + printWord();
        }

        throw new ResponseException("Expected: <EASY|MEDIUM|HARD>");
    }

    public String hint() throws ResponseException {
        if (!assertStarted()) {
            throw new ResponseException("You must start a game");
        }
        if (allowHint) {
            for (Character c : game.getGuessWord()) {
                if (!game.getLettersGuessed().contains(c)) {
                    allowHint = false;
                    return "Try: " + c;
                }
            }
        }
        return "No hint";
    }

    public String help() {
        if (gameState.equals(State.STARTED)) {
            return """
               - guess <LETTER>
               - retry <EASY|MEDIUM|HARD>
               - hint
               - quit
               - help
               """;
        }
        return """
               - start <EASY|MEDIUM|HARD>
               - quit
               - help
               """;
    }

    private String printWord() {
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

    private String update(Character c) {
        StringBuilder builder = new StringBuilder();
        builder.append("You Guessed: ").append(c).append('\n');
        if (game.getGuessWord().contains(c)) {
            builder.append("Correct!");
        } else {
            builder.append("Incorrect!");
            game.mistake();
            if (game.getMistakesLeft() == 0) {
                builder.append('\n').append(lose());
                return builder.toString();
            }
        }
        builder.append('\n').append("Mistakes Left: ").append(game.getMistakesLeft());
        builder.append('\n').append(printWord());

        if (!printWord().contains("*")) {
            builder.append('\n').append(win());
        }

        return builder.toString();
    }

    private String lose() {
        gameState = State.WAITING;
        return "You Lose!\n" + help();
    }

    private String win() {
        gameState = State.WAITING;
        return "You Win!\n" + help();
    }

    private boolean assertStarted() {
        return gameState.equals(State.STARTED);
    }
}