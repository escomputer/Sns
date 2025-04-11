package com.example.pstagram.exception.user;

import com.example.pstagram.common.ResponseCode;

/**
 * 이미 사용 중인 이메일로 회원가입을 시도할 경우 발생하는 예외
 */

public class EmailAlreadyExistsException extends RuntimeException {

	private final ResponseCode code;

	/**
	 * 메시지를 받아 부모 생성자에 전달하는 생성자입니다.
	 *
	 * @param code message.properties의 키값을 가져옴)
	 */
	public EmailAlreadyExistsException(ResponseCode code) {

		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
