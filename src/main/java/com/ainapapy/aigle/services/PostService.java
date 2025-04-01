package com.ainapapy.aigle.services;

import com.ainapapy.aigle.models.Post;
import com.ainapapy.aigle.models.User;
import com.ainapapy.aigle.models.dto.PostDTO;
import com.ainapapy.aigle.repositories.PostRepository;
import com.ainapapy.aigle.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    
    private UserRepository userRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // Convertir Post en PostDTO
    public PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        if (post.getUser() != null) {
            dto.setUserId(post.getUser().getId());
        }
        return dto;
    }

    // Convertir PostDTO en Post
    public Post convertToEntity(PostDTO dto) {
        Post post = new Post();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        if (dto.getUserId() != null) {
            Optional<User> user = userRepository.findById(dto.getUserId());
            user.ifPresent(post::setUser);
        }

        return post;
    }
}
