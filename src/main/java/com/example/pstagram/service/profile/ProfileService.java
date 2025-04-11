package com.example.pstagram.service.profile;

import java.util.Optional;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import com.example.pstagram.common.ResponseCode;
import com.example.pstagram.domain.user.User;
import com.example.pstagram.dto.profile.UpdateProfileRequestDto;
import com.example.pstagram.dto.profile.ViewProfileResponseDto;
import com.example.pstagram.exception.user.UnauthorizedException;
import com.example.pstagram.repository.user.UserRepository;

/**
 * 프로필 관련 비즈니스 로직을 처리하는 서비스
 */
@Service
@RequiredArgsConstructor
public class ProfileService {
	private final UserRepository userRepository;

	/**
	 * ID로 사용자 조회
	 * @param id 조회할 사용자 ID
	 * @return 사용자 정보 DTO
	 */
	public ViewProfileResponseDto findById(Long id) {

		Optional<User> optionalUser = userRepository.findById(id); // ID로 User 엔티티 조회

		User findProfile = optionalUser.get(); // User 엔티티 가져오기 (Optional 에서)

		return new ViewProfileResponseDto(
			findProfile.getId(),
			findProfile.getNickname(),
			findProfile.getBio()
		);

	}

	/**
	 * 사용자 정보 수정
	 * @param id 수정할 사용자 ID
	 * @param requestDto 수정할 사용자 정보 DTO
	 */
	@Transactional
	public void update(Long id, UpdateProfileRequestDto requestDto) {

		User user = userRepository.findById(id)
			.orElseThrow(() -> new UnauthorizedException(ResponseCode.UNAUTHORIZED));

		user.updateProfile(requestDto.getNickname(), requestDto.getBio()); // User 엔티티 업데이트 (nickname, bio 수정)
	}

}
