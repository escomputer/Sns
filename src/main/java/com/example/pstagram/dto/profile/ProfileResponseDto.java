package com.example.pstagram.dto.profile;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ProfileResponseDto {
	private final Long id;
	private final String email;
	private final String password;
	private final String nickname;
	private final String bio;
	private final LocalDateTime created_at;
	private final LocalDateTime updated_at;

	public ProfileResponseDto(Long id, String email, String password, String nickname, String bio,
		LocalDateTime created_at, LocalDateTime updated_at) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.bio = bio;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
}


