package com.example.pstagram.exception.user;

import com.example.pstagram.common.ResponseCode;

/**
 * 로그인 시, 입력한 이메일에 해당하는 사용자가 존재하지 않을 경우 발생하는 예외
 */

public class EmailNotFoundException extends RuntimeException {

	private final ResponseCode code;

	/**
	 * 메시지를 받아 부모 생성자에 전달하는 생성자입니다.
	 *
	 * @param code message.properties의 키값을 가져옴)
	 */
	public EmailNotFoundException(ResponseCode code) {

		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
