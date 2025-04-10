package com.example.pstagram.exception;

import com.example.pstagram.config.MessageUtil;
import com.example.pstagram.dto.common.ApiResponse;
import com.example.pstagram.exception.user.AlreadyDeletedUserException;
import com.example.pstagram.exception.user.EmailAlreadyExistsException;
import com.example.pstagram.exception.user.EmailNotFoundException;
import com.example.pstagram.exception.user.InvalidPasswordException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
//접근 권한 예외
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.pstagram.config.MessageUtil;
import com.example.pstagram.dto.common.ApiResponse;
import com.example.pstagram.exception.friend.DuplicateFriendRequestException;
import com.example.pstagram.exception.friend.FriendNotFoundException;
import com.example.pstagram.exception.friend.FriendRequestNotFoundException;
import com.example.pstagram.exception.friend.SelfRequestException;
import com.example.pstagram.exception.friend.UnauthorizedException;
import com.example.pstagram.exception.friend.UserNotFoundException;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
/**
 * 프로젝트 전역에서 발생하는 예외를 처리하는 핸들러
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);


	private final MessageUtil messageUtil;

	/**
	 * 이메일 중복 예외 처리
	 */
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ApiResponse<Void>> handleEmailExists(EmailAlreadyExistsException ex) {
		String message = messageUtil.getMessage("user.email.exists");
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse<>(message, null));
	}

	/**
	 * 이메일 없음 예외 처리
	 */
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleEmailNotFound(EmailNotFoundException ex) {
		String message = messageUtil.getMessage("user.email.not-found");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(message, null));
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UserNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), messageUtil.getMessage(ex.getCode().getMessageKey()),
				null));
	/**
	 * 비밀번호 불일치 예외 처리
	 */
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ApiResponse<Void>> handleInvalidPassword(InvalidPasswordException ex) {
		String message = messageUtil.getMessage("user.password.invalid");
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(message, null));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
		return new ResponseEntity<>("@Validated failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
	@ExceptionHandler(DuplicateFriendRequestException.class)
	public ResponseEntity<ApiResponse<Void>> handleDuplicateRequest(DuplicateFriendRequestException ex) {
	/**
	 * 회원탈퇴 예외 처리
	 */
	@ExceptionHandler(AlreadyDeletedUserException.class)
	public ResponseEntity<ApiResponse<Void>> handleAlreadyDeletedUserException(AlreadyDeletedUserException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
			.body(
				new ApiResponse<>(HttpStatus.CONFLICT.value(), messageUtil.getMessage(ex.getCode().getMessageKey()),
					null));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
		return new ResponseEntity<>("@Valid failed: " + ex.getBindingResult().getFieldError().getDefaultMessage(),
			HttpStatus.BAD_REQUEST);
	@ExceptionHandler(FriendRequestNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleFriendRequestNotFound(FriendRequestNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), messageUtil.getMessage(ex.getCode().getMessageKey()),
				null));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다.");
	@ExceptionHandler(FriendNotFoundException.class)
	public ResponseEntity<ApiResponse<Void>> handleFriendNotFound(FriendNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), messageUtil.getMessage(ex.getCode().getMessageKey()),
				null));
	}

	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<ApiResponse<Void>> handleUnauthorized(UnauthorizedException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			.body(
				new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), messageUtil.getMessage(ex.getCode().getMessageKey()),
					null));
			.body(new ApiResponse<>(ex.getMessage(), null));
	}

	@ExceptionHandler(SelfRequestException.class)
	public ResponseEntity<ApiResponse<Void>> handleSelfRequest(SelfRequestException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(
				new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), messageUtil.getMessage(ex.getCode().getMessageKey()),
					null));
	}

}
