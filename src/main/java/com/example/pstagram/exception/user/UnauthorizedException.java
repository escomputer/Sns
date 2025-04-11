package com.example.pstagram.exception.user;

import com.example.pstagram.common.ResponseCode;

/**
 * 인증되지 않은 사용자가 접근 시 발생하는 예외입니다.
 */

public class UnauthorizedException extends RuntimeException {
	private final ResponseCode code;

	/**
	 * 메시지를 받아 부모 생성자에 전달하는 생성자입니다.
	 *
	 * @param code message.properties의 키값을 가져옴)
	 */
	public UnauthorizedException(ResponseCode code) {

		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
