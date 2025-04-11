package com.example.pstagram.exception.user;

import com.example.pstagram.common.ResponseCode;

/**
 * 현재 비밀번호와 동일한 비밀번호로 변경하려는 경우 발생
 */
public class SamePasswordException extends RuntimeException {
	private final ResponseCode code;

	/**
	 * 메시지를 받아 부모 생성자에 전달하는 생성자입니다.
	 *
	 * @param code message.properties의 키값을 가져옴)
	 */
	public SamePasswordException(ResponseCode code) {

		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
