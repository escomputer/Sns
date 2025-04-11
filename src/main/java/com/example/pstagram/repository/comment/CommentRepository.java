package com.example.pstagram.repository.comment;

import java.util.List;

import com.example.pstagram.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByPostIdOrderByCreatedAtDesc(Long postId);
}
