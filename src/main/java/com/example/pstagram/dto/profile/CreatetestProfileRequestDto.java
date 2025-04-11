package com.example.pstagram.dto.profile;

import lombok.Getter;

@Getter
public class CreatetestProfileRequestDto {
	private final String email;
	private final String password;
	private final String nickname;
	private final String bio;

	public CreatetestProfileRequestDto(String email, String password, String nickname, String bio) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.bio = bio;
	}
}
