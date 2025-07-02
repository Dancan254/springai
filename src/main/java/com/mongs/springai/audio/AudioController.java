package com.mongs.springai.audio;

import org.springframework.ai.azure.openai.AzureOpenAiAudioTranscriptionModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class AudioController {

    private final AzureOpenAiAudioTranscriptionModel audioModel;

    public AudioController(AzureOpenAiAudioTranscriptionModel audioModel){
        this.audioModel = audioModel;
    }

    @PostMapping("/audio/transcription")
    public ResponseEntity<String> audioTranscription(@RequestParam("file") MultipartFile file) throws IOException {

        ByteArrayResource audioResource = new ByteArrayResource(file.getBytes());
        String transcription = audioModel.call(audioResource);
        return new ResponseEntity<>(transcription, HttpStatus.OK);
    }
}
