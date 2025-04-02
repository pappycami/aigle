package com.ainapapy.aigle.models.convertors;

import com.ainapapy.aigle.models.Post;
import com.ainapapy.aigle.models.User;
import com.ainapapy.aigle.models.dto.PostDTO;
import com.ainapapy.aigle.repositories.UserRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class PostConvertor {
    
    private UserRepository userRepository;
    
    // Convertir Post en PostDTO
    public PostDTO convertToDTO(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        if (post.getUser() != null) {
            //dto.setUser(post.getUser());
        }
        return dto;
    }

    // Convertir PostDTO en Post
    public Post convertToEntity(PostDTO dto) {
        Post post = new Post();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        if (dto.getUser() != null) {
            Optional<User> user = userRepository.findById(dto.getUser().getId());
            user.ifPresent(post::setUser);
        }
        return post;
    }
    
}
