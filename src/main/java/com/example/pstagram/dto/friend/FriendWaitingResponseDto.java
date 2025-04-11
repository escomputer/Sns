package com.example.pstagram.dto.friend;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.example.pstagram.domain.friend.Friend;

@Getter
@AllArgsConstructor
public class FriendWaitingResponseDto {
	private Long friendId;
	private Long requesterId;
	private String requesterName;
	private LocalDateTime requestedAt;

	public static FriendWaitingResponseDto fromFriend(Friend friend) {
		return new FriendWaitingResponseDto(
			friend.getId(),
			friend.getRequester().getId(),
			friend.getRequester().getNickname(),
			friend.getRequestedAt()
		);
	}
}
