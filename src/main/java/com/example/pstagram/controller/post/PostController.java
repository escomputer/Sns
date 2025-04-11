package com.example.pstagram.controller.post;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import com.example.pstagram.common.ResponseCode;
import com.example.pstagram.config.MessageUtil;
import com.example.pstagram.dto.common.ApiResponse;
import com.example.pstagram.dto.post.PostListResponseDto;
import com.example.pstagram.dto.post.PostRequestDto;
import com.example.pstagram.dto.post.PostResponseDto;
import com.example.pstagram.service.post.PostService;

/**
 * 게시물 관련 요청을 처리하는 컨트롤러 클래스
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;
	private final MessageUtil messageUtil;

	/**
	 * 게시물 등록 API
	 *
	 * @param requestDto 게시물 내용
	 * @param userId 로그인한 사용자 세션
	 * @return 생성된 게시물 응답 DTO
	 */
	@PostMapping
	public ApiResponse<PostResponseDto> createPost(
		@Valid @RequestBody PostRequestDto requestDto,
		@SessionAttribute("userId") Long userId
	) {
		PostResponseDto response = postService.createPost(requestDto, userId);
		String message = messageUtil.getMessage(ResponseCode.POST_SUCCESS);
		return new ApiResponse<>(200, message, response);
	}

	/**
	 * 게시물 목록 조회 (최신순 정렬 + 페이징)
	 *
	 * @param page 페이지 번호 (기본: 0)
	 * @param size 페이지 크기 (기본: 10)
	 * @return 게시물 목록 + 페이징 정보
	 */
	@GetMapping
	public ApiResponse<PostListResponseDto> getPosts(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size
	) {
		PostListResponseDto response = postService.getPostsSortedByCreatedAt(page, size);
		String message = messageUtil.getMessage(ResponseCode.POST_LIST_SUCCESS);
		return new ApiResponse<>(200, message, response);
	}

	/**
	 * 게시물 수정 API
	 *
	 * @param postId 수정할 게시물 ID
	 * @param requestDto 수정할 내용
	 * @param userId 로그인한 사용자 세션
	 * @return 수정된 게시물 응답 DTO
	 */
	@PutMapping("/{postId}")
	public ApiResponse<PostResponseDto> updatePost(
		@PathVariable Long postId,
		@Valid @RequestBody PostRequestDto requestDto,
		@SessionAttribute("userId") Long userId
	) {
		PostResponseDto response = postService.updatePost(postId, requestDto, userId);
		String message = messageUtil.getMessage(ResponseCode.POST_UPDATE_SUCCESS);
		return new ApiResponse<>(200, message, response);
	}

	/**
	 * 게시물 삭제 API
	 *
	 * @param postId 삭제할 게시물 ID
	 * @param userId 로그인한 사용자 세션
	 * @return 삭제 성공 메시지
	 */
	@DeleteMapping("/{postId}")
	public ApiResponse<Void> deletePost(
		@PathVariable Long postId,
		@SessionAttribute("userId") Long userId
	) {
		postService.deletePost(postId, userId);
		String message = messageUtil.getMessage(ResponseCode.POST_DELETE_SUCCESS);
		return new ApiResponse<>(200, message, null);
	}
}
