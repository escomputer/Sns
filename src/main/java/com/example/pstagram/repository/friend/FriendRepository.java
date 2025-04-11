package com.example.pstagram.repository.friend;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.pstagram.domain.friend.Friend;
import com.example.pstagram.domain.friend.FriendStatus;
import com.example.pstagram.domain.user.User;

public interface FriendRepository extends JpaRepository<Friend, Long> {
	Optional<Friend> findByRequesterAndReceiver(User requester, User receiver);

	@Query("""
			SELECT f FROM Friend f
			WHERE 
				(f.requester.id = :userId OR f.receiver.id = :userId)
			AND f.status = com.example.pstagram.domain.friend.FriendStatus.ACCEPTED
		""")
	List<Friend> findFriendList(@Param("userId") Long userId);

	List<Friend> findAllByReceiverAndStatus(User receiver, FriendStatus status);
}
