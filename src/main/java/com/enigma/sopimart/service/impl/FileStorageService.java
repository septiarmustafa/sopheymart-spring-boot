package com.enigma.sopimart.service.impl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
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
        String mimeType = file.getContentType();
        // check file type just image
        if (mimeType == null || (!mimeType.startsWith("image/"))){
            throw new RuntimeException("Invalid upload, only upload image");
        }
        try{
            Path targetLocation = this.fileStorageLocation.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return file.getOriginalFilename();
        } catch (Exception e) {
            throw new RuntimeException("Could not store " + file.getOriginalFilename() + " please check again " + e);
        }
    }

    public Resource downloadFile (String nameFile) throws FileNotFoundException {
        try {
            Path targetLocation = this.fileStorageLocation.resolve(nameFile).normalize();
            Resource resource = new UrlResource(targetLocation.toUri());
            if (resource.exists()){
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + nameFile);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("File not found " + nameFile + " : " + e);
        }
    }
}
