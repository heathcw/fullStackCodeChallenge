package shared;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.io.IOException;
import com.google.gson.Gson;

public class WordRequest {

    private String API_URL;
    private Difficulty difficulty;

    public WordRequest(Difficulty d) {
        difficulty = d;
        String wordLength = null;
        if (d == Difficulty.EASY) {
            wordLength = "?length=4";
        } else if (d == Difficulty.MEDIUM) {
            wordLength = "?length=6";
        } else if (d == Difficulty.HARD) {
            wordLength = "?length=10";
        }
        API_URL = "https://random-word-api.herokuapp.com/word" + wordLength;
    }

    public String getRandomWord() {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();

                Gson serializer = new Gson();

                return serializer.fromJson(jsonResponse, String.class);
            } else {
                System.out.println("Error: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }

    public void updateDifficulty(Difficulty d) {
        difficulty = d;
        String wordLength = null;
        if (d == Difficulty.EASY) {
            wordLength = "?length=4";
        } else if (d == Difficulty.MEDIUM) {
            wordLength = "?length=6";
        } else if (d == Difficulty.HARD) {
            wordLength = "?length=10";
        }
        API_URL = "https://random-word-api.herokuapp.com/word" + wordLength;
    }
}
