package com.email.writer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class EmailGeneratorService {

    private static final Logger log = LoggerFactory.getLogger(EmailGeneratorService.class);

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public EmailGeneratorService(WebClient.Builder builder) {

        this.webClient = builder.build();
    }

    public String generate(EmailRequest req) {
        String prompt = buildPrompt(req);

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                )
        );

        try {
            String response = webClient.post()
                    .uri(geminiApiUrl + "?key=" + geminiApiKey)   // ✅ full URL
                    .header("Content-Type", "application/json")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Gemini raw response: {}", response); // optional
            return extractResponseContent(response);

        } catch (Exception ex) {

            log.error("Gemini call failed: {}", ex.getMessage(), ex);
            return "Error calling Gemini: " + ex.getMessage();
        }
    }

    private String extractResponseContent(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            return root.path("candidates").get(0)
                    .path("content").path("parts").get(0)
                    .path("text").asText("");
        } catch (Exception e) {
            log.error("Failed to parse Gemini response: {}", e.getMessage(), e);
            return "Error processing response: " + e.getMessage();
        }
    }

    private String buildPrompt(EmailRequest req) {
        StringBuilder sb = new StringBuilder();
        sb.append("Generate a professional email reply for the following email content. ")
                .append("Please don't generate a subject line. ");
        if (req != null && req.getTone() != null && !req.getTone().isEmpty()) {
            sb.append("Use a ").append(req.getTone()).append(" tone. ");
        }
        if (req != null && req.getPrompt() != null && !req.getPrompt().isEmpty()) {
            sb.append("Content: ").append(req.getPrompt());
        }
        return sb.toString();
    }
}