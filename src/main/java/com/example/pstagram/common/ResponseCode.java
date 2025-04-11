package com.example.pstagram.common;

public enum ResponseCode {
	REQUEST_SUCCESS("friend.request.success"),
	REQUEST_CANCEL("friend.request.cancel"),
	REQUEST_ACCEPT("friend.request.accept"),
	REQUEST_REJECT("friend.request.reject"),
	FRIEND_DELTE("friend.list.delete"),
	FRIEND_NOT_FOUND("friend.notfound"),
	FRIEND_ALREADY_REQUEST("friend.already.requested"),
	SELF_REQUEST("friend.self.request"),
	UNAUTHORIZED("user.unauthorized"),

	//user
	SIGNUP_SUCCESS("user.signup.success"),
	LOGIN_SUCCESS("user.login.success"),
	LOGOUT_SUCCESS("user.logout.success"),
	EMAIL_EXISTS("user.email.exists"),
	EMAIL_NOT_FOUND("user.email.not-found"),
	PASSWORD_INVALID("user.password.invalid"),
	USER_ALREADY_DELETED("user.already-deleted"),
	DELETE_SUCCESS("user.delete.success"),
	PASSWORD_SAME("user.password.same"),
	PASSWORD_UPDATED("user.password.updated"),
	USER_NOT_FOUND("user.not-found"),

	//comment
	//댓글
	COMMENT_EMPTY("comment.empty-content"),
	COMMENT_EMPTY_UPDATE("comment.empty-update"),
	COMMENT_NOT_FOUND("comment.not-found"),
	COMMENT_UNAUTHORIZED("comment.unauthorized"),
	COMMENT_EMPTY_LIST("comment.empty-list"),

	//post
	POST_SUCCESS("post.create.success"),
	POST_NOT_FOUND("post.not-found"),
	POST_LIST_SUCCESS("post.list.success"),
	POST_UPDATE_SUCCESS("post.update.success"),
	POST_DELETE_SUCCESS("post.delete.success"),
	POST_UNAUTHORIZED("post.unauthorized");

	private final String messageKey;

	ResponseCode(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}
}
