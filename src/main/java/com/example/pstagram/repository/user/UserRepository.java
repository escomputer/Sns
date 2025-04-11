package com.example.pstagram.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.example.pstagram.common.ResponseCode;
import com.example.pstagram.domain.user.User;
import com.example.pstagram.exception.user.EmailNotFoundException;

/**
 * 사용자(User) 엔티티에 대한 데이터베이스 접근을 담당하는 레포지토리
 */

public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * 입력된 이메일이 이미 존재하는지 여부를 확인
	 *
	 * @param email 중복 확인할 이메일 주소
	 * @return 해당 이메일이 존재하면 true, 없으면 false
	 */
	boolean existsByEmail(String email);

	/**
	 * 이메일을 기반으로 사용자 정보를 조회
	 *
	 * @param email 조회할 이메일
	 * @return 해당 이메일의 사용자 정보 (Optional)
	 */
	Optional<User> findByEmail(String email);

	/**
	 * 사용자 탈퇴 처리: deletedAt 컬럼을 현재 시간으로 업데이트
	 *
	 * @param id 탈퇴할 사용자 ID
	 */
	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
	void softDeleteById(@Param("id") Long id);

	// default 메소드
	// 사용예 : User user = userRepository.findByIdOrThrow(userId);
	default User findByIdOrThrow(Long id) {
		return findById(id)
			.orElseThrow(() -> new EmailNotFoundException(ResponseCode.EMAIL_NOT_FOUND));
	}

}
