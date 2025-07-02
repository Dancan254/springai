package com.mongs.springai.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.azure.openai.AzureOpenAiChatOptions;
import org.springframework.ai.azure.openai.AzureOpenAiResponseFormat;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VacationPlanController {

    private final AzureOpenAiChatModel chatModel;

    public VacationPlanController(AzureOpenAiChatModel chatModel){
        this.chatModel = chatModel;
    }

    @GetMapping("/vacation/unstructured")
    public String unstructured(){
        return chatModel.call(" I want to go on a vacation to Maldives. Give me a list of things to do.");
    }

    @GetMapping("/vacation/structured")
    public Itinerary structured() throws Exception {
        String prompt = """
                Return only a JSON object with this structure:
                {
                  "activities": [
                    {"activityName": "...", "location": "...", "day": "...", "time": "..."}
                  ]
                }
                I want to go on a vacation to Maldives. Give me a list of things to do.
                """;
        AzureOpenAiResponseFormat responseFormat = AzureOpenAiResponseFormat.builder()
                .type(AzureOpenAiResponseFormat.Type.JSON_OBJECT)
                .build();
        AzureOpenAiChatOptions options = AzureOpenAiChatOptions.builder()
                .responseFormat(responseFormat)
                .build();

        String response = chatModel.call(new Prompt(prompt, options))
                .getResult()
                .getOutput()
                .getText();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, Itinerary.class);
    }
}
