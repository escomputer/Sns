package com.example.pstagram.exception.friend;

import com.example.pstagram.common.ResponseCode;

public class DuplicateFriendRequestException extends RuntimeException {
	private final ResponseCode code;

	public DuplicateFriendRequestException(ResponseCode code) {
		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
