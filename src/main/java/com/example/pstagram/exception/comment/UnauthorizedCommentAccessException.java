package com.example.pstagram.exception.comment;

import com.example.pstagram.common.ResponseCode;

/**
 * 작성자가 일치 하지 않는 경우 예외
 */

public class UnauthorizedCommentAccessException extends RuntimeException {

	private final ResponseCode code;

	public UnauthorizedCommentAccessException(ResponseCode code) {
		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
