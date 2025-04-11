package com.example.pstagram.exception.user;

import com.example.pstagram.common.ResponseCode;

/**
 * 이미 탈퇴한 사용자일 경우 발생하는 예외 클래스
 *
 * deletedAt != null 인 사용자에게 탈퇴 요청이 들어올 경우 사용됩니다
 */
public class AlreadyDeletedUserException extends RuntimeException {
	private final ResponseCode code;

	/**
	 * 메시지를 받아 부모 생성자에 전달하는 생성자입니다.
	 *
	 * @param code message.properties의 키값을 가져옴)
	 */
	public AlreadyDeletedUserException(ResponseCode code) {

		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
