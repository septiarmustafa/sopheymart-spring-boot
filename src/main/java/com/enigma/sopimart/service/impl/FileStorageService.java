package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.entity.FileStorage;
import org.springframework.beans.factory.annotation.Value;
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
import java.time.LocalDateTime;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService (@Value("${app.sopimart.directory-image-path}") String path){
        fileStorageLocation = Paths.get(path);
        try{
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new RuntimeException("Could not create the directory where the upload file to storage");
        }
    }

    // contoh return String

//    public String storageFile (MultipartFile file){
//        String mimeType = file.getContentType();
//        // check file type just image
//        if (mimeType == null || (!mimeType.startsWith("image/"))){
//            throw new RuntimeException("Invalid upload, only upload image");
//        }
//        try{
//            Path targetLocation = this.fileStorageLocation.resolve(file.getOriginalFilename());
//            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
//            return file.getOriginalFilename();
//        } catch (Exception e) {
//            throw new RuntimeException("Could not store " + file.getOriginalFilename() + " please check again " + e);
//        }
//    }

    // contoh return FileStorage
    public FileStorage storageFile (MultipartFile file){
        String mimeType = file.getContentType();
        // check file type just image
        if (mimeType == null || (!mimeType.startsWith("image/"))){
            throw new RuntimeException("Invalid upload, only upload image");
        }
        String fileName = file.getOriginalFilename();
        try{
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return FileStorage.builder()
                    .fileName(file.getOriginalFilename())
                    .dateTime(LocalDateTime.now())
                    .build();
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
