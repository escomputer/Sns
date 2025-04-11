package com.example.pstagram.controller.user;

import com.example.pstagram.config.MessageUtil;
import com.example.pstagram.dto.common.ApiResponse;
import com.example.pstagram.dto.user.DeleteUserRequestDto;
import com.example.pstagram.dto.user.LoginRequestDto;
import com.example.pstagram.dto.user.SignUpRequestDto;
import com.example.pstagram.dto.user.UpdatePasswordRequestDto;
import com.example.pstagram.dto.user.UserResponseDto;
import com.example.pstagram.exception.user.UnauthorizedException;
import com.example.pstagram.service.user.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 관련 API 요청을 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;
	private final MessageUtil messageUtil;

	/**
	 * 회원가입 API
	 */
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<UserResponseDto>> signup(@Valid @RequestBody SignUpRequestDto requestDto) {
		UserResponseDto responseDto = userService.signup(requestDto);
		String message = messageUtil.getMessage("user.signup.success");
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(message, responseDto));
	}

	/**
	 * 로그인 API - 세션에 userId 저장
	 */
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<UserResponseDto>> login(@Valid @RequestBody LoginRequestDto requestDto,
		HttpSession session) {
		UserResponseDto user = userService.login(requestDto);
		session.setAttribute("userId", user.getId());
		String message = messageUtil.getMessage("user.login.success");
		return ResponseEntity.ok(new ApiResponse<>(message, user));
	}

	/**
	 * 로그아웃 API - 세션 무효화
	 */
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
		session.invalidate();
		String message = messageUtil.getMessage("user.logout.success");
		return ResponseEntity.ok(new ApiResponse<>(message, null));
	}

	/**
	 * 회원 탈퇴 API (세션 기반 인증)
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse<Void>> deleteUser(
		@Valid @RequestBody DeleteUserRequestDto requestDto,
		@SessionAttribute(name = "userId", required = false) Long userId
	) {
		if (userId == null) {
			throw new UnauthorizedException(messageUtil.getMessage("user.unauthorized"));
		}

		userService.deleteUser(requestDto);
		String message = messageUtil.getMessage("user.delete.success");
		return ResponseEntity.ok(new ApiResponse<>(message, null));
	}

	/**
	 * 비밀번호 변경 API (세션 기반 인증)
	 */
	@PutMapping("/password")
	public ResponseEntity<ApiResponse<Void>> updatePassword(
		@Valid @RequestBody UpdatePasswordRequestDto requestDto,
		@SessionAttribute(name = "userId", required = false) Long userId
	) {
		if (userId == null) {
			throw new UnauthorizedException(messageUtil.getMessage("user.unauthorized"));
		}

		userService.updatePassword(requestDto, userId);
		String message = messageUtil.getMessage("user.password.updated");
		return ResponseEntity.ok(new ApiResponse<>(message, null));
	}
}
