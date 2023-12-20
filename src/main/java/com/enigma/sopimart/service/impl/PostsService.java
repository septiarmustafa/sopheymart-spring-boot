package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.entity.Posts;
import com.enigma.sopimart.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final RestTemplate restTemplate;
    private final PostsRepository postsRepository;


    @Value("${api.endpoint.url.post}")
    private String BASE_URL;

    public ResponseEntity<List<Posts>> getAllPosts (){
        ResponseEntity<Posts[]> apiResponse = restTemplate.getForEntity(BASE_URL, Posts[].class);
        List<Posts> externalPosts = List.of(apiResponse.getBody());

        List<Posts> dbPosts = postsRepository.findAll();

        dbPosts.addAll(externalPosts);

        return ResponseEntity.ok(dbPosts);
    }

    public ResponseEntity<String> getPostById (Integer id){
        return responseMethod(restTemplate.getForEntity(BASE_URL +"/"+ id,String.class), "Failed to load data");
    }

    public ResponseEntity<String> createPosts (Posts posts){
        // mengatur header permintaan
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        postsRepository.save(posts);

        // membungkus data permintaan dalam http entity
        HttpEntity<Posts> requestEntity = new HttpEntity<>(posts, headers);
        // response
        return responseMethod(restTemplate.postForEntity(BASE_URL,requestEntity,String.class), "Failed to create data");
    }

    private ResponseEntity<String> responseMethod(ResponseEntity<String> resTemplate, String message) {
        if (resTemplate.getStatusCode().is2xxSuccessful()){
            String responseBody = resTemplate.getBody();
            return ResponseEntity.ok(responseBody);
        }
        return ResponseEntity.status(resTemplate.getStatusCode()).body(message);
    }
}
