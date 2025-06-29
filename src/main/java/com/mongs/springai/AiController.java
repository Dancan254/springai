package com.mongs.springai;

import org.springframework.ai.image.ImageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AiController {

    private final AzureOpenAiService azureOpenAiService;
    private final ImageService imageService;
    private final RecipeService recipeService;

    public AiController(AzureOpenAiService azureOpenAiService, ImageService imageService, RecipeService recipeService) {
        this.azureOpenAiService = azureOpenAiService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }
    @PostMapping("/generate")
    public String generateResponse(String prompt){
        return azureOpenAiService.generateResponse(prompt);
    }

    @PostMapping("/image")
    public ResponseEntity<Map<String, String>> generateImage(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Prompt is required"));
        }

        String imageUrl = imageService.createImage(prompt);
        return ResponseEntity.ok(Map.of("imageUrl", imageUrl));
    }

    @PostMapping("/image/response")
    public ImageResponse generateImageResponse(@RequestBody Map<String, String> request){
        String prompt = request.get("prompt");
        return imageService.generateImageResponse(prompt);
    }


    @PostMapping("/recipe")
    public ResponseEntity< String> createRecipe(@RequestBody Map<String, String> request) {
        String ingredients = request.get("ingredients");
        String cuisine = request.get("cuisine");
        String dietaryRestrictions = request.get("dietaryRestrictions");
        return new ResponseEntity<>(recipeService.createRecipe(ingredients, cuisine, dietaryRestrictions), HttpStatus.OK);
    }
}
