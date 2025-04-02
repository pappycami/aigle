package com.ainapapy.aigle.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    
    private Long id;
    
    private String title;
    
    private String content;
    
    @JsonProperty(required = false)
    private UserDTO user;
    
    @JsonProperty(required = false)
    private List<CommentDTO> comments;
}
