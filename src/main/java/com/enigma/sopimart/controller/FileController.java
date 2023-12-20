package com.enigma.sopimart.controller;


import com.enigma.sopimart.service.impl.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final FileStorageService fileStorageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestPart(name = "file") MultipartFile file){
        String result = fileStorageService.storageFile(file);
        return "Success Upload File : " + result;
    }
}
