package com.example.pstagram.service.comment;

import java.util.List;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import com.example.pstagram.common.ResponseCode;
import com.example.pstagram.domain.comment.Comment;
import com.example.pstagram.domain.post.Post;
import com.example.pstagram.domain.user.User;
import com.example.pstagram.dto.comment.CommentRequestDto;
import com.example.pstagram.dto.comment.CommentResponseDto;
import com.example.pstagram.exception.comment.CommentListEmptyException;
import com.example.pstagram.exception.comment.CommentNotFoundException;
import com.example.pstagram.exception.comment.EmptyCommentContentException;
import com.example.pstagram.exception.comment.EmptyUpdateContentException;
import com.example.pstagram.exception.comment.UnauthorizedCommentAccessException;
import com.example.pstagram.exception.post.PostNotFoundException;
import com.example.pstagram.exception.user.UserNotFoundException;
import com.example.pstagram.repository.comment.CommentRepository;
import com.example.pstagram.repository.post.PostRepository;
import com.example.pstagram.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Transactional
	public CommentResponseDto save(Long userId, Long postId, CommentRequestDto commentRequestDto) {
		if (commentRequestDto.getContent() == null || commentRequestDto.getContent().trim().isEmpty()) {
			throw new EmptyCommentContentException(ResponseCode.COMMENT_EMPTY);
		}

		User user = userRepository.findById(userId)

			.orElseThrow(() -> new UserNotFoundException(ResponseCode.USER_NOT_FOUND));

		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException(ResponseCode.POST_NOT_FOUND));

		Comment comment = Comment.builder()
			.user(user)
			.post(post)
			.content(commentRequestDto.getContent())
			.build();

		Comment savedComment = commentRepository.save(comment);

		return new CommentResponseDto(
			post.getId(),
			savedComment.getId(),
			user.getNickname(),
			savedComment.getContent(),
			savedComment.getCreatedAt(),
			savedComment.getUpdatedAt(),
			savedComment.getDeletedAt()
		);
	}

	@Transactional
	public List<CommentResponseDto> getCommentsByPost(Long postId) {
		Post post = postRepository.findById(postId)
			.orElseThrow(() -> new PostNotFoundException(ResponseCode.POST_NOT_FOUND));
		List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAtDesc(postId);

		if (comments.isEmpty()) {
			throw new CommentListEmptyException(ResponseCode.COMMENT_EMPTY_LIST);
		}
		return comments.stream()
			.map(comment -> new CommentResponseDto(
				comment.getPost().getId(),
				comment.getId(),
				comment.getUser().getNickname(),
				comment.getContent(),
				comment.getCreatedAt(),
				comment.getUpdatedAt(),
				comment.getDeletedAt()
			))
			.toList();

	}

	@Transactional
	public CommentResponseDto updateComment(Long commentId, Long userId, CommentRequestDto commentRequestDto) {
		if (commentRequestDto.getContent() == null || commentRequestDto.getContent().trim().isEmpty()) {
			throw new EmptyUpdateContentException(ResponseCode.COMMENT_EMPTY_UPDATE);
		}

		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentNotFoundException(ResponseCode.COMMENT_NOT_FOUND));

		if (!comment.getUser().getId().equals(userId)) {
			throw new UnauthorizedCommentAccessException(ResponseCode.COMMENT_UNAUTHORIZED);
		}

		comment.updateContent(commentRequestDto.getContent());

		return new CommentResponseDto(
			comment.getPost().getId(),
			comment.getId(),
			comment.getUser().getNickname(),
			comment.getContent(),
			comment.getCreatedAt(),
			comment.getUpdatedAt(),
			comment.getDeletedAt()
		);
	}

	public void deleteComment(Long userId, Long commentId) {

		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentNotFoundException(ResponseCode.COMMENT_NOT_FOUND));

		if (!comment.getUser().getId().equals(userId)) {
			throw new UnauthorizedCommentAccessException(ResponseCode.COMMENT_UNAUTHORIZED);
		}
		commentRepository.deleteById(commentId);

	}
}
