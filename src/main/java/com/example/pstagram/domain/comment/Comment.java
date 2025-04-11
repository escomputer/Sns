package com.example.pstagram.domain.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import com.example.pstagram.domain.Base;
import com.example.pstagram.domain.post.Post;
import com.example.pstagram.domain.user.User;

/**
 * 게시물에 작성된 댓글을 저장하는 엔티티
 * - 댓글 작성자(User), 대상 게시물(Post)과 연관
 * - 정렬 기준: createdAt DESC (최신순)
 * - 댓글 수정은 작성자만 가능, 삭제는 작성자 또는 게시물 작성자만 가능
 */

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Comment extends Base {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@Column(nullable = false, columnDefinition = "TEXT")
	private String content;

	@Column(nullable = false)
	private boolean deleted =false;

	@Builder
	public Comment(User user, Post post, String content) {
		this.user = user;
		this.post = post;
		this.content = content;
	} // AllArgsConstructor 대신 ! (id를 제외하기 위해서)

	public void updateContent(String content) {
		this.content = content;
	}
}
