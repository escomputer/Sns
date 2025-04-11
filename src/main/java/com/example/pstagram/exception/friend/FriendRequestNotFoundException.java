package com.example.pstagram.exception.friend;

import com.example.pstagram.common.ResponseCode;

public class FriendRequestNotFoundException extends RuntimeException {
	private final ResponseCode code;

	public FriendRequestNotFoundException(ResponseCode code) {
		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
