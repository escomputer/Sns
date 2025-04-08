package com.example.pstagram.service.friend;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import com.example.pstagram.domain.friend.Friend;
import com.example.pstagram.domain.user.User;
import com.example.pstagram.dto.dto.friend.FriendResponseDto;
import com.example.pstagram.repository.friend.FriendRepository;
import com.example.pstagram.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class FriendService {

	private final FriendRepository friendRepository;
	private final UserRepository userRepository;

	private User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당하는 유저가 없습니다."));
	}

	@Transactional
	public void requestFriend(Long requesterId, Long recieverId) {
		User requester = getUser(requesterId);
		User reciever = getUser(recieverId);

		if (friendRepository.findByBoth(requester, reciever).isPresent()) {
			throw new IllegalArgumentException("이미 보낸 요청입니다.");
		}// 중복 방지

		Friend friend = Friend.builder().requester(requester).receiver(reciever).build();

		friendRepository.save(friend);
	}

	@Transactional
	public void cancelRequest(Long requesterId, Long recieverId) {
		User requester = getUser(requesterId);
		User reciever = getUser(recieverId);

		Friend friend = friendRepository.findByBoth(requester, reciever)
			.orElseThrow(() -> new IllegalArgumentException("요청이 존재하지 않습니다."));

		friendRepository.delete(friend);
	}

	@Transactional
	public FriendResponseDto acceptFriend(Long recieverId, Long requesterId) {
		User reciever = getUser(recieverId);
		User requester = getUser(requesterId);

		Friend friend = friendRepository.findByBoth(requester, reciever)
			.orElseThrow(() -> new IllegalArgumentException("요청이 존재하지 않습니다."));
		friend.accept();
		return new FriendResponseDto(friend.getStatus());
	}
}

