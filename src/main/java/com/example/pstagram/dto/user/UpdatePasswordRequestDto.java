package com.example.pstagram.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

/**
 * 비밀번호 변경 요청 DTO
 */
@Getter
public class UpdatePasswordRequestDto {

	@NotBlank(message = "현재 비밀번호는 필수입니다.")
	private final String currentPassword;



	@NotBlank(message = "새 비밀번호는 필수입니다.")
	@Pattern(
		regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$",
		message = "비밀번호는 8자 이상, 영문/숫자/특수문자를 포함해야 합니다."
	)
	private final String newPassword;

	public UpdatePasswordRequestDto(String currentPassword, String newPassword) {
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
	}

	// fix : ㅂㅂㄱㅊ

}

