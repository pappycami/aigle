package com.ainapapy.aigle.models.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    
    private Long id;
    
    private String title;
    
    private String content;
    
    private Long userId;
}
