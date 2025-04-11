package com.example.pstagram.dto.comment;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

	private final String content;

	public CommentUpdateRequestDto(String content) {
		this.content = content;
	}
}
