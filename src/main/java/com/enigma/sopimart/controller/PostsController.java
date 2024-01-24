package com.enigma.sopimart.controller;

import com.enigma.sopimart.constant.AppPath;
import com.enigma.sopimart.entity.Posts;
import com.enigma.sopimart.service.impl.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.API + AppPath.POSTS)
public class PostsController {
    private final PostsService postsService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Posts>> getAllPosts(){
        return postsService.getAllPosts();
    }
    @GetMapping(value = AppPath.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPostById(@PathVariable Integer id){
        return postsService.getPostById(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createPost (@RequestBody Posts posts){
        return postsService.createPosts(posts);
    }
}
