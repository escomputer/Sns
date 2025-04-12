package com.example.pstagram.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.pstagram.common.ResponseCode;
import com.example.pstagram.config.MessageUtil;
import com.example.pstagram.dto.common.ApiResponse;
import com.example.pstagram.dto.user.DeleteUserRequestDto;
import com.example.pstagram.dto.user.LoginRequestDto;
import com.example.pstagram.dto.user.SignUpRequestDto;
import com.example.pstagram.dto.user.UpdatePasswordRequestDto;
import com.example.pstagram.dto.user.UserResponseDto;
import com.example.pstagram.exception.user.AlreadyLoggedInException;
import com.example.pstagram.exception.user.UnauthorizedException;
import com.example.pstagram.service.user.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
	 *
	 * @param requestDto 클라이언트가 전송한 회원가입 정보
	 * @return 가입된 사용자 정보 응답 (공통 응답 구조)
	 */
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse<UserResponseDto>> signup(@Valid @RequestBody SignUpRequestDto requestDto) {
		UserResponseDto responseDto = userService.signup(requestDto);
		String message = messageUtil.getMessage(ResponseCode.SIGNUP_SUCCESS);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(200, message, responseDto));
	}

	/**
	 * 로그인 API
	 *
	 * @param requestDto 로그인 요청 정보
	 * @param session    HttpSession 객체 (자동 주입)
	 * @return 로그인 성공 시 사용자 정보 응답 (공통 응답 구조)
	 */
	@PostMapping("/login")

	public ResponseEntity<ApiResponse<UserResponseDto>> login(@Valid @RequestBody LoginRequestDto requestDto,
		HttpSession session) {

		// 이미 로그인 되어 있다면 로그인 불가 처리
		if (session.getAttribute("userId") != null) {
			throw new AlreadyLoggedInException(messageUtil.getMessage(ResponseCode.ALREADY_LOGGED_IN.getMessageKey()));
		}

		UserResponseDto user = userService.login(requestDto);

		// 세션에 사용자 ID 저장
		session.setAttribute("userId", user.getId());

		String message = messageUtil.getMessage(ResponseCode.LOGIN_SUCCESS.getMessageKey());
		return ResponseEntity.ok(new ApiResponse<>(200, message, user));
	}

	/**
	 * 로그아웃 API
	 *
	 * @param session 현재 사용자 세션
	 * @return 로그아웃 완료 메시지
	 */
	@PostMapping("/logout")
	public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {
		session.invalidate(); // 세션 무효화
		String message = messageUtil.getMessage(ResponseCode.LOGOUT_SUCCESS);
		return ResponseEntity.ok(new ApiResponse<>(200, message, null));
	}

	/**
	 * 회원 탈퇴 API (세션 인증 확인 포함)
	 *
	 * @param requestDto 이메일 + 현재 비밀번호
	 * @userId 로그인 사용자 세션
	 * @return 탈퇴 성공 메시지
	 */

	@DeleteMapping("/delete")
	public ResponseEntity<ApiResponse<Void>> deleteUser(@Valid @RequestBody DeleteUserRequestDto requestDto,
		@SessionAttribute(value = "userId", required = false) Long userId) {

		if (userId == null) {
			throw new UnauthorizedException(ResponseCode.UNAUTHORIZED);
		}

		userService.deleteUser(requestDto);

		// 탈퇴 완료 메시지 반환
		String message = messageUtil.getMessage(ResponseCode.DELETE_SUCCESS);
		return ResponseEntity.ok(new ApiResponse<>(200, message, null));
	}

	/**
	 * 비밀번호 변경 API
	 */
	@PutMapping("/password")
	public ResponseEntity<ApiResponse<Void>> updatePassword(
		@Valid @RequestBody UpdatePasswordRequestDto requestDto,
		@SessionAttribute(value = "userId", required = false) Long userId) {
		userService.updatePassword(requestDto, userId);
		String message = messageUtil.getMessage(ResponseCode.PASSWORD_UPDATED); //
		return ResponseEntity.ok(new ApiResponse<>(200, message, null));
	}

}
