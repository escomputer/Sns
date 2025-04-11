package com.example.pstagram.dto.profile;

import lombok.Getter;

@Getter
public class UpdateProfileRequestDto {
	private final String password;
	private final String nickname;
	private final String bio;

	public UpdateProfileRequestDto(String password, String nickname, String bio) {
		this.password = password;
		this.nickname = nickname;
		this.bio = bio;
	}
}
