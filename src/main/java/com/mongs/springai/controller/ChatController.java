package com.mongs.springai.controller;

import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {
    private final AzureOpenAiChatModel chatModel;

    public ChatController(AzureOpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "message", defaultValue = "A random dad joke") String prompt) {
        return chatModel.call(prompt);
    }

    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam (value = "message", defaultValue = "A random dad joke") String prompt) {
        return chatModel.stream(prompt);
    }
}
