package com.ainapapy.aigle.controllers;

import com.ainapapy.aigle.models.Post;
import com.ainapapy.aigle.models.convertors.PostConvertor;
import com.ainapapy.aigle.models.dto.PostDTO;
import com.ainapapy.aigle.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;
    
    @Autowired
    private PostConvertor postConvertor; 

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts().stream()
                .map(postConvertor::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(p -> ResponseEntity.ok(postConvertor.convertToDTO(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PostDTO createPost(@RequestBody Post post) {
        return postConvertor.convertToDTO(postService.savePost(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        Optional<Post> postOptional = postService.getPostById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setTitle(updatedPost.getTitle());
            post.setContent(updatedPost.getContent());
            return ResponseEntity.ok(postConvertor.convertToDTO(postService.savePost(post)));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
