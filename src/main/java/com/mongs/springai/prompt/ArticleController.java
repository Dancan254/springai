package com.mongs.springai.prompt;

import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final AzureOpenAiChatModel chatModel;

    public ArticleController(AzureOpenAiChatModel chatModel){
        this.chatModel = chatModel;
    }

    @GetMapping("/generate")
    public String generateArticle(@RequestParam String topic){

        String systemPrompt = """
                # ARTICLE WRITER ASSISTANT
                
                ## YOUR ROLE
                You are an expert content writer specializing in creating informative and engaging articles.
                
                ## GUIDELINES
                - Write in a clear, concise, and professional style
                - Structure content with logical flow and appropriate headings
                - Include an introduction that hooks the reader
                - Provide substantive content with relevant insights
                - End with a thoughtful conclusion that summarizes key points
                - Keep articles around 800 words in length
                - Use Markdown formatting for structure
                
                ## ARTICLE STRUCTURE
                1. Title: Descriptive and engaging
                2. Introduction: Context and importance of the topic
                3. Main sections: Well-organized with clear headings
                4. Conclusion: Summary and closing thoughts
                
                ## PROHIBITED CONTENT
                - Do not include fabricated statistics or citations
                - Do not write promotional or marketing content
                - Do not include political opinions or controversial statements
                - Do not write about medical advice or legal guidance
                """;
        var prompt = new Prompt(
                new SystemMessage(systemPrompt),
                new UserMessage("Write an informative article about: " + topic)
        );

        String articleContent = chatModel.call(prompt).getResult().getOutput().getText();
        Map<String, Object> response = new HashMap<>();
        response.put("topic", topic);
        response.put("content", articleContent);
        assert articleContent != null;
        response.put("wordCount", countWords(articleContent));
        return response.toString();
    }

    private int countWords(String text){
        return text.split("\\s+").length;
    }
}
