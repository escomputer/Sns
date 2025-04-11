package com.example.pstagram.exception.comment;

import com.example.pstagram.common.ResponseCode;

public class CommentNotFoundException extends RuntimeException {
	private final ResponseCode code;

	public CommentNotFoundException(ResponseCode code) {
		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
