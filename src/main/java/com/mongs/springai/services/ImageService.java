package com.mongs.springai.services;

import org.springframework.ai.azure.openai.AzureOpenAiImageModel;
import org.springframework.ai.azure.openai.AzureOpenAiImageOptions;

import org.springframework.ai.image.Image;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final AzureOpenAiImageModel imageModel;

    public ImageService(AzureOpenAiImageModel imageModel){
        this.imageModel = imageModel;
    }

    public Image generateImage(String prompt){
        ImageResponse response = imageModel.call(
                new ImagePrompt(prompt,
                        AzureOpenAiImageOptions.builder()
                                .style("natural")
                                .N(4)
                                .height(1024)
                                .width(1024).build())
        );
        return response.getResult().getOutput();
    }

    public String createImage(String imagePrompt) {
        try {
            ImagePrompt prompt = new ImagePrompt(imagePrompt);
            ImageResponse response = imageModel.call(prompt);

            // Return the URL of the first generated image
            return response.getResult().getOutput().getUrl();
        } catch (Exception e) {
            return "Error generating image: " + e.getMessage();
        }
    }

    public ImageResponse generateImageResponse(String prompt){
        return imageModel.call(
                new ImagePrompt(prompt,
                        AzureOpenAiImageOptions.builder()
                                .style("natural")
                                .N(1)
                                .height(1024)
                                .width(1024).build())
        );
    }
}
