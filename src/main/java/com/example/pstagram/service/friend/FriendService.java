package com.example.pstagram.service.friend;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.pstagram.common.ResponseCode;
import com.example.pstagram.domain.friend.Friend;
import com.example.pstagram.domain.friend.FriendStatus;
import com.example.pstagram.domain.user.User;
import com.example.pstagram.dto.friend.FriendListResponseDto;
import com.example.pstagram.dto.friend.FriendResponseDto;
import com.example.pstagram.dto.friend.FriendWaitingResponseDto;
import com.example.pstagram.exception.friend.DuplicateFriendRequestException;
import com.example.pstagram.exception.friend.FriendNotFoundException;
import com.example.pstagram.exception.friend.FriendRequestNotFoundException;
import com.example.pstagram.exception.friend.SelfRequestException;
import com.example.pstagram.exception.user.UnauthorizedException;
import com.example.pstagram.repository.friend.FriendRepository;
import com.example.pstagram.service.user.UserService;

@Service
@RequiredArgsConstructor
public class FriendService {

	private final FriendRepository friendRepository;
	private final UserService userService;

	@Transactional
	public void requestFriend(Long requesterId, Long receiverId) {
		User requester = userService.getUser(requesterId);
		User receiver = userService.getUser(receiverId);

		if (friendRepository.findByRequesterAndReceiver(requester, receiver).isPresent()) {
			throw new DuplicateFriendRequestException(ResponseCode.FRIEND_ALREADY_REQUEST);
		}// 중복 방지

		if (requesterId.equals(receiverId)) {
			throw new SelfRequestException(ResponseCode.SELF_REQUEST);
		}
		if (requesterId == null) {
			throw new UnauthorizedException(ResponseCode.UNAUTHORIZED);
		}
		Friend friend = Friend.builder().requester(requester).receiver(receiver).build();

		friendRepository.save(friend);
	}

	@Transactional
	public void cancelRequest(Long requesterId, Long receiverId) {
		User requester = userService.getUser(requesterId);
		User receiver = userService.getUser(receiverId);

		if (requesterId == null) {
			throw new UnauthorizedException(ResponseCode.UNAUTHORIZED);
		}

		Friend friend = friendRepository.findByRequesterAndReceiver(requester, receiver)
			.orElseThrow(() -> new FriendRequestNotFoundException(ResponseCode.FRIEND_NOT_FOUND));

		friendRepository.delete(friend);
	}

	@Transactional
	public FriendResponseDto acceptFriend(Long requesterId, Long receiverId) {
		User receiver = userService.getUser(receiverId);
		User requester = userService.getUser(requesterId);

		if (receiverId == null) {
			throw new UnauthorizedException(ResponseCode.UNAUTHORIZED);
		}

		Friend friend = friendRepository.findByRequesterAndReceiver(requester, receiver)
			.orElseThrow(() -> new FriendNotFoundException(ResponseCode.FRIEND_NOT_FOUND));
		friend.accept();
		return new FriendResponseDto(friend.getStatus());
	}

	@Transactional
	public FriendResponseDto rejectFriend(Long requesterId, Long receiverId) {
		User requester = userService.getUser(requesterId);
		User receiver = userService.getUser(receiverId);

		if (receiverId == null) {
			throw new UnauthorizedException(ResponseCode.UNAUTHORIZED);
		}

		Friend friend = friendRepository.findByRequesterAndReceiver(requester, receiver)
			.orElseThrow(() -> new FriendNotFoundException(ResponseCode.FRIEND_NOT_FOUND));
		friend.reject();
		return new FriendResponseDto(friend.getStatus());

	}

	@Transactional(readOnly = true)
	public List<FriendListResponseDto> getFriendList(Long currentUserId) {
		User currentUser = userService.getUser(currentUserId);

		return friendRepository.findFriendList(currentUser.getId())
			.stream()
			.map(friend -> FriendListResponseDto.fromFriend(friend, currentUserId))
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<FriendWaitingResponseDto> getWaitingList(Long currentUserId) {
		User currentUser = userService.getUser(currentUserId);

		if (currentUserId == null) {
			throw new UnauthorizedException(ResponseCode.UNAUTHORIZED);
		}

		return friendRepository.findAllByReceiverAndStatus(currentUser, FriendStatus.WAITING).stream()
			.map(FriendWaitingResponseDto::fromFriend)
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteFriend(Long currentUserId, Long friendId) {

		if (currentUserId == null) {
			throw new UnauthorizedException(ResponseCode.UNAUTHORIZED);
		}

		friendRepository.deleteById(friendId);

	}
}

