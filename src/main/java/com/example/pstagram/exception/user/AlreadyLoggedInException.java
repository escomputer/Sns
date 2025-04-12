package com.example.pstagram.exception.user;

import com.example.pstagram.common.ResponseCode;

public class AlreadyLoggedInException extends RuntimeException {
	private final ResponseCode code;

	public AlreadyLoggedInException(String message) {
		super(message);
		this.code = ResponseCode.ALREADY_LOGGED_IN;
	}

	public ResponseCode getCode() {
		return code;
	}
}
