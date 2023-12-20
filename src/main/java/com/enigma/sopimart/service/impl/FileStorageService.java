package com.enigma.sopimart.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation = Paths.get("/home/user/IdeaProjects/sopimart/src/main/java/com/enigma/sopimart/file");

    public FileStorageService (){
        try{
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new RuntimeException("Could not create the directory where the upload file to storage");
        }
    }

    public String storageFile (MultipartFile file){
        try{
            Path targetLocation = this.fileStorageLocation.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return file.getOriginalFilename();
        } catch (Exception e) {
            throw new RuntimeException("Could not store " + file.getOriginalFilename() + " please check again " + e);
        }
    }
}
