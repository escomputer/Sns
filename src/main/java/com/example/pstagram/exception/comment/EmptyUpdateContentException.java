package com.example.pstagram.exception.comment;

import com.example.pstagram.common.ResponseCode;

public class EmptyUpdateContentException extends RuntimeException {
	private final ResponseCode code;

	public EmptyUpdateContentException(ResponseCode code) {
		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
