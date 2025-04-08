package com.example.pstagram.repository.friend;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.pstagram.domain.friend.Friend;
import com.example.pstagram.domain.friend.FriendStatus;
import com.example.pstagram.domain.user.User;
import com.example.pstagram.dto.dto.friend.FriendListResponseDto;

public interface FriendRepository extends JpaRepository {
	Optional<Friend> findByRequesterAndReciever(User requester, User reciever);

	@Query("""
			SELECT new com.example.pstagram.dto.dto.friend.FriendListResponseDto(
				f.id,
				f.requester.id,
				f.requester.nickname,
				f.receiver.id,
				f.receiver.nickname,
				f.status,
				f.requestedAt,
				f.respondedAt
			)
			FROM Friend f
			WHERE 
				(f.requester.id = :userId OR f.receiver.id = :userId)
			AND f.status = :status
		""")
	List<FriendListResponseDto> findFriendList(@Param("userId") Long userId,
		@Param("status") FriendStatus status);
}
