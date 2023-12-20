package com.enigma.sopimart.controller;


import com.enigma.sopimart.constant.AppPath;
import com.enigma.sopimart.service.impl.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.FILES)
public class FileController {
    private final FileStorageService fileStorageService;

    @PostMapping(value = AppPath.UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestPart(name = "file") MultipartFile file){
        String result = fileStorageService.storageFile(file);
        return "Success Upload File : " + result;
    }

    @GetMapping(AppPath.DOWNLOAD + AppPath.FILENAME)
    public ResponseEntity<Resource> downloadFile (@PathVariable String fileName) {
       Resource resource;
        try {
            resource = fileStorageService.downloadFile(fileName);
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
