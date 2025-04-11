package com.example.pstagram.dto.user;

import lombok.Getter;

/**
 * 회원가입 응답, 로그인 응답, 프로필 조회 응답 등
 * 사용자 정보를 응답할 때마다 쓸 수 있게 이름을
 * SignupRequestDto가 아닌 UserResponseDto로 지정
 */

/**
 * 회원가입 성공 시 클라이언트에게 응답할 사용자 정보 DTO
 * - 비밀번호와 같은 민감 정보는 포함하지 않음
 * - 사용자 ID, 이메일, 닉네임 등 최소한의 정보만 제공
 */

@Getter

/**
 * @AllArgsConstructor
 * UserResponseDto는 보통 생성 후 바로 반환하는 용도라서
 * → new UserResponseDto(id, email, nickname) 형태로 만들기 쉽도록
 *
 * DTO는 엔티티와 달리 주로 불변(read-only) 이므로 setter는 안 씀
 */

public class UserResponseDto {

	/**
	 * 사용자 고유 ID
	 */

	private final Long id;

	/**
	 * 사용자 이메일 주소
	 */

	private final String email;

	/**
	 * 사용자 닉네임
	 */

	private final String nickname;
	private final String bio;

	public UserResponseDto(Long id, String email, String nickname, String bio) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
		this.bio = bio;
	}
}
