package com.example.pstagram.exception.post;

import com.example.pstagram.common.ResponseCode;

/**
 * 게시물이 존재하지 않을 때 발생하는 예외
 */
public class PostNotFoundException extends RuntimeException {
	private final ResponseCode code;

	/**
	 * 메시지를 받아 부모 생성자에 전달하는 생성자입니다.
	 *
	 * @param code message.properties의 키값을 가져옴)
	 */
	public PostNotFoundException(ResponseCode code) {

		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
