package com.example.pstagram.controller.friend;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import com.example.pstagram.common.ResponseCode;
import com.example.pstagram.config.MessageUtil;
import com.example.pstagram.dto.common.ApiResponse;
import com.example.pstagram.dto.friend.FriendListResponseDto;
import com.example.pstagram.dto.friend.FriendResponseDto;
import com.example.pstagram.dto.friend.FriendWaitingResponseDto;
import com.example.pstagram.service.friend.FriendService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friends")
public class FriendController {
	private final FriendService friendService;
	private final MessageUtil messageUtil;

	@PostMapping("/request/{userId}")
	public ResponseEntity<ApiResponse<Void>> requestFriend(@SessionAttribute(name = "userId", required = false) Long id,
		@PathVariable Long userId) {
		friendService.requestFriend(id, userId);
		String message = messageUtil.getMessage(ResponseCode.REQUEST_SUCCESS.getMessageKey());
		return ResponseEntity.ok(new ApiResponse<>(200, message, null));
	}

	@DeleteMapping("/request/{userId}")
	public ResponseEntity<ApiResponse<Void>> cancelRequest(@SessionAttribute(name = "userId", required = false) Long id,
		@PathVariable Long userId) {
		friendService.cancelRequest(id, userId);
		String message = messageUtil.getMessage(ResponseCode.REQUEST_CANCEL.getMessageKey());
		return ResponseEntity.ok(new ApiResponse<>(200, message, null));
	}

	@PostMapping("/status/{requesterId}/accept")
	public ResponseEntity<ApiResponse<FriendResponseDto>> acceptFriend(@PathVariable Long requesterId,
		@SessionAttribute(name = "userId", required = false) Long id) {
		FriendResponseDto response = friendService.acceptFriend(requesterId, id);
		String message = messageUtil.getMessage(ResponseCode.REQUEST_ACCEPT.getMessageKey());
		return ResponseEntity.ok(new ApiResponse<>(200, message, response));
	}

	@PostMapping("status/{requesterId}/reject")
	public ResponseEntity<ApiResponse<FriendResponseDto>> rejectFriend(@PathVariable Long requesterId,
		@SessionAttribute(name = "userId", required = false) Long id) {
		FriendResponseDto response = friendService.rejectFriend(requesterId, id);
		String message = messageUtil.getMessage(ResponseCode.REQUEST_REJECT.getMessageKey());
		return ResponseEntity.ok(new ApiResponse<>(200, message, response));

	}

	@GetMapping("/friend/accept")
	public ResponseEntity<ApiResponse<List<FriendListResponseDto>>> getFriends(
		@SessionAttribute(name = "userId", required = false) Long id) {
		List<FriendListResponseDto> friends = friendService.getFriendList(id);
		return ResponseEntity.ok(new ApiResponse<>(200, null, friends));
	}

	@GetMapping("/friend/waiting")
	public ResponseEntity<ApiResponse<List<FriendWaitingResponseDto>>> getWaitingList(
		@SessionAttribute(name = "userId", required = false) Long id) {
		List<FriendWaitingResponseDto> friends = friendService.getWaitingList(id);
		return ResponseEntity.ok(new ApiResponse<>(200, null, friends));

	}

	@DeleteMapping("/friend/{friendId}")
	public ResponseEntity<ApiResponse<Void>> deleteFriend(@SessionAttribute(name = "userId", required = false) Long id,
		@PathVariable Long friendId) {
		friendService.deleteFriend(friendId, id);
		String message = messageUtil.getMessage(ResponseCode.FRIEND_DELTE.getMessageKey());
		return ResponseEntity.ok(new ApiResponse<>(200, message, null));
	}

}
