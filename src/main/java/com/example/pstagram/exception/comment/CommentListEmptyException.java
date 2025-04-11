package com.example.pstagram.exception.comment;

import com.example.pstagram.common.ResponseCode;

public class CommentListEmptyException extends RuntimeException {

	private final ResponseCode code;

	public CommentListEmptyException(ResponseCode code) {
		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
