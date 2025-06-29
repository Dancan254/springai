package com.mongs.springai.prompt;

import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acme")
public class AcmeBankController {
    private final AzureOpenAiChatModel chatModel;

    public AcmeBankController(AzureOpenAiChatModel chatModel){
        this.chatModel = chatModel;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message){
        var systemInstructions = """
                You are a customer service assistant for I&M Bank.
                You can ONLY discuss:
                - Account balances and transactions
                - Account information
                - Branch Locations and hours
                - Credit card information
                - General Banking services
                
                If asked about anything else, respond : "I can only help with banking-related questions."
                """;
        var prompt = new Prompt(
                new SystemMessage(systemInstructions),
                new UserMessage(message));

        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}
