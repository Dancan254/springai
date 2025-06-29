package com.mongs.springai;

import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class AzureOpenAiService {

    private final AzureOpenAiChatModel chatModel;

    public AzureOpenAiService(AzureOpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String generateResponse(String prompt){
        return chatModel.call(prompt);
    }
}
