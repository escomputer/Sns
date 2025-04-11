package com.example.pstagram.dto.friend;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.pstagram.domain.friend.Friend;
import com.example.pstagram.domain.friend.FriendStatus;

// 친구 목록 반환해주는 dto

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendListResponseDto {
	private Long friendId;
	private String friendName;
	private FriendStatus status;
	private LocalDateTime respondedAt;

	/**
	 *
	 * if문 - 내가 친구요청을 보내고 친구가 수락했을때 친구관계
	 * else문 - 내가 친구요청을 받은 친구 관계
	 * @param friend
	 * @param currentUserId 현재 로그인한 사람의 아이디
	 */

	public FriendListResponseDto(Friend friend, Long currentUserId) {
		this.status = friend.getStatus();
		this.respondedAt = friend.getRespondedAt();

		if (friend.getRequester().getId().equals(currentUserId)) {
			this.friendId = friend.getReceiver().getId();
			this.friendName = friend.getReceiver().getNickname();

		} else {
			this.friendId = friend.getRequester().getId();
			this.friendName = friend.getRequester().getNickname();
		}
	}

	public static FriendListResponseDto fromFriend(Friend friend, Long currentUserId) {
		return new FriendListResponseDto(friend, currentUserId);
	}
}
