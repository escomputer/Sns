package com.example.pstagram.dto.profile;

import lombok.Getter;

@Getter
public class ViewProfileResponseDto {
	private final Long id;
	private final String nickname;
	private final String bio;

	public ViewProfileResponseDto(Long id, String nickname, String bio) {
		this.id = id;
		this.nickname = nickname;
		this.bio = bio;
	}
}
