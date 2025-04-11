package com.example.pstagram.controller.profile;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import com.example.pstagram.config.MessageUtil;
import com.example.pstagram.dto.common.ApiResponse;
import com.example.pstagram.dto.profile.UpdateProfileRequestDto;
import com.example.pstagram.dto.profile.ViewProfileResponseDto;
import com.example.pstagram.service.profile.ProfileService;

/**
 * 프로필 관련 API를 제공하는 컨트롤러
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class ProfileController {
	private final ProfileService profileService;
	private final MessageUtil messageUtil;

	/**
	 * ID로 사용자 조회
	 * @param id 조회할 사용자 ID
	 * @return 사용자 정보 DTO와 OK 응답
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ViewProfileResponseDto> findById(@PathVariable Long id) {

		ViewProfileResponseDto viewProfileResponseDto = profileService.findById(id);

		return new ResponseEntity<>(viewProfileResponseDto, HttpStatus.OK);
	}

	/**
	 * 사용자 정보 수정
	 * @param id 수정할 사용자 ID (세션에서 가져옴)
	 * @param requestDto 수정할 사용자 정보
	 * @return 성공시 OK 응답
	 */
	//현재 주어진 id에 해당하는 유저가 password를 통해 인증하여 수정할 수 있음
	//sesson을 통해 정보를 불러 오도록 수정 예정

	/**
	 * 사용자 정보 수정
	 *
	 * @param id         로그인된 사용자 ID (Session에서 추출)
	 * @param requestDto 수정할 닉네임/소개 정보
	 * @return 메시지를 담은 공통 응답
	 */
	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> update(
		@SessionAttribute(name = "userId") Long id,
		@RequestBody UpdateProfileRequestDto requestDto
	) {
		profileService.update(id, requestDto);

		String message = messageUtil.getMessage("profile.update.success");
		return ResponseEntity.ok(new ApiResponse<>(200, message, null));
	}

}
