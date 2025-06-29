package com.mongs.springai;

import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final AzureOpenAiChatModel model;

    public RecipeService(AzureOpenAiChatModel model){
        this.model = model;
    }

    public String createRecipe(String ingredients, String cuisine, String dietaryRestrictions){

        var promptTemplate = """
                 You are a professional chef. Create a detailed recipe using the following information:

                Ingredients: %s
                Cuisine: %s
                Dietary Restrictions: %s
        
                Please provide:
                - A creative recipe title
                - A list of ingredients with quantities
                - Step-by-step cooking instructions
        
                Make sure the recipe fits the specified cuisine and dietary restrictions.
                """.formatted(ingredients, cuisine, dietaryRestrictions);

        return model.call(promptTemplate);
    }
}
