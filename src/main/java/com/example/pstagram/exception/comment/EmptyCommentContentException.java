package com.example.pstagram.exception.comment;

import com.example.pstagram.common.ResponseCode;

public class EmptyCommentContentException extends RuntimeException {
	/**
	 * 댓글 내용이 비어있는 경우 예외
	 */

	private final ResponseCode code;

	public EmptyCommentContentException(ResponseCode code) {
		super(code.getMessageKey());
		this.code = code;
	}

	public ResponseCode getCode() {
		return code;
	}
}
