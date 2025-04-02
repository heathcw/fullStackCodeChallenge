package shared;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.io.IOException;

public class WordRequest {

    private static final String API_URL = "https://random-word-api.herokuapp.com/word";

    public WordRequest() {}

    public String getRandomWord() {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String jsonResponse = response.body();

                int wordIndex = jsonResponse.indexOf("\"word\":\"") + 8;
                int endIndex = jsonResponse.indexOf("\"", wordIndex);

                return jsonResponse.substring(wordIndex, endIndex);
            } else {
                System.out.println("Error: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            return null;
        }
    }
}
