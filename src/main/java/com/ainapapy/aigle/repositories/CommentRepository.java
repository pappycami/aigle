package com.ainapapy.aigle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ainapapy.aigle.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}