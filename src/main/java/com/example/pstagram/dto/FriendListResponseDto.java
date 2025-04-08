package com.example.pstagram.dto;

import java.time.LocalDateTime;

import com.example.pstagram.domain.friend.FriendStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 친구 목록 반환해주는 dto

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendListResponseDto {
	private Long friendId;
	private Long requesterId;
	private String requesterName;
	private Long receiverId;
	private String receiverName;
	private FriendStatus status;
	private LocalDateTime requestedAt;
	private LocalDateTime respondedAt;
}