package com.example.pstagram.service.friend;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.pstagram.domain.friend.Friend;
import com.example.pstagram.domain.user.User;
import com.example.pstagram.dto.dto.friend.FriendListResponseDto;
import com.example.pstagram.dto.dto.friend.FriendResponseDto;
import com.example.pstagram.dto.dto.friend.FriendWaitingResponseDto;
import com.example.pstagram.exception.friend.DuplicateFriendRequestException;
import com.example.pstagram.exception.friend.FriendNotFoundException;
import com.example.pstagram.exception.friend.FriendRequestNotFoundException;
import com.example.pstagram.exception.friend.SelfRequestException;
import com.example.pstagram.repository.friend.FriendRepository;
import com.example.pstagram.service.user.UserService;

@Service
@RequiredArgsConstructor
public class FriendService {

	private final FriendRepository friendRepository;
	private final UserService userService;

	@Transactional
	public void requestFriend(Long requesterId, Long recieverId) {
		User requester = userService.getUser(requesterId);
		User reciever = userService.getUser(recieverId);

		if (friendRepository.findByRequesterAndReciever(requester, reciever).isPresent()) {
			throw new DuplicateFriendRequestException("이미 보낸 요청입니다.");
		}// 중복 방지

		if (requesterId.equals(recieverId)) {
			throw new SelfRequestException("자기 자신에게 친구 요청을 보낼 수 없습니다.");
		}

		Friend friend = Friend.builder().requester(requester).receiver(reciever).build();

		friendRepository.save(friend);
	}

	@Transactional
	public void cancelRequest(Long requesterId, Long recieverId) {
		User requester = userService.getUser(requesterId);
		User reciever = userService.getUser(recieverId);

		Friend friend = friendRepository.findByRequesterAndReciever(requester, reciever)
			.orElseThrow(() -> new FriendRequestNotFoundException("요청이 존재하지 않습니다."));

		friendRepository.delete(friend);
	}

	@Transactional
	public FriendResponseDto acceptFriend(Long requesterId, Long recieverId) {
		User reciever = userService.getUser(recieverId);
		User requester = userService.getUser(requesterId);

		Friend friend = friendRepository.findByRequesterAndReciever(requester, reciever)
			.orElseThrow(() -> new FriendNotFoundException("요청이 존재하지 않습니다."));
		friend.accept();
		return new FriendResponseDto(friend.getStatus());
	}

	@Transactional
	public FriendResponseDto rejectFriend(Long requesterId, Long currentUserId) {
		User requester = userService.getUser(requesterId);
		User reciever = userService.getUser(currentUserId);

		Friend friend = friendRepository.findByRequesterAndReciever(requester, reciever)
			.orElseThrow(() -> new FriendNotFoundException("요청이 존재하지 않습니다."));
		friend.reject();
		return new FriendResponseDto(friend.getStatus());

	}

	@Transactional(readOnly = true)
	public List<FriendListResponseDto> getFriendList(Long currentUserId) {
		User currentUser = userService.getUser(currentUserId);

		//친구가 한명도 없을때 ! , 로그인한 사용자가 아닐때
		return friendRepository.findFriendList(currentUser.getId())
			.stream()
			.map(friend -> FriendListResponseDto.fromFriend(friend, currentUserId))
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<FriendWaitingResponseDto> getWaitingList(Long currentUserId) {
		User currentUser = userService.getUser(currentUserId);

		//로그인한 사용자가 아닐때, 친구요청이 1명도 없을때
		return friendRepository.findAllByReceiverAndStatus_Waiting(currentUser).stream()
			.map(FriendWaitingResponseDto::fromFriend)
			.collect(Collectors.toList());
	}

	@Transactional
	public void deleteFriend(Long friendId, Long currentUserId) {
		friendRepository.deleteById(friendId);

		//로그인한 사용자가 아닐떄
	}
}

