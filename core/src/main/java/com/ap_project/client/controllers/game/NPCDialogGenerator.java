package com.ap_project.client.controllers.game;

import com.ap_project.common.models.enums.environment.Season;
import com.ap_project.common.models.enums.environment.Weather;
import com.ap_project.common.models.enums.types.NPCType;

import java.net.http.*;
import java.net.URI;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

public class NPCDialogGenerator {
    private static final String API_URL = "http://localhost:11434/api/generate";

    private static String callLLMApi(String prompt) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = "{\"prompt\":\"" + prompt.replace("\"", "\\\"") + "\", \"max_tokens\":100}";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer ")
            .POST(BodyPublishers.ofString(jsonBody))
            .build();

        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            // parse response JSON and extract dialogue text (depends on API response format)
            String responseBody = response.body();
            // Assuming response has {"text":"generated dialog"}
            // You can use a JSON parser like Jackson or Gson here.
            // For simplicity, assume responseBody contains the dialogue directly:
            return parseDialogueFromResponse(responseBody);
        } else {
            throw new Exception("Failed to get response from LLM API: " + response.statusCode());
        }
    }

    public static String getDynamicDialogue(NPCType npc, int hour, Season season, Weather weather, List<String> pastDialogs) {
        String prompt = buildPrompt(npc, hour, season, weather, pastDialogs);
        try {
            return callOllamaApi(prompt);
        } catch (Exception e) {
            e.printStackTrace();
            return getFallbackDialog(npc, hour, season, weather);
        }
    }

    private static String buildPrompt(NPCType npc, int hour, Season season, Weather weather, List<String> pastDialogs) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("You are an NPC named ").append(npc.name()).append(".\n").append(npc.getDescription());
        prompt.append("Current time: ").append(hour).append(":00\n");
        prompt.append("Season: ").append(season).append("\n");
        prompt.append("Weather: ").append(weather).append("\n");
        prompt.append("Previous dialogues:\n");
        for (String d : pastDialogs) {
            prompt.append("- ").append(d).append("\n");
        }
        prompt.append("Generate a natural dialogue in less than 3 sentences from the NPC.");
        return prompt.toString();
    }

    private static String getFallbackDialog(NPCType npc, int hour, Season season, Weather weather) {
        return "Hello, it's a nice day, isn't it?";
    }

    private static String callOllamaApi(String prompt) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String safePrompt = prompt
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");

        String jsonBody = "{"
            + "\"model\": \"llama2\","
            + "\"prompt\": \"" + safePrompt + "\","
            + "\"max_tokens\": 150"
            + "}";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:11434/api/generate"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return parseDialogueFromResponse(response.body());
        } else {
            throw new Exception("Ollama API error: " + response.statusCode() + " " + response.body());
        }
    }

    private static String parseDialogueFromResponse(String responseBody) {
        return responseBody;
    }
}
