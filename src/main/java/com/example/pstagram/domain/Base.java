package com.example.pstagram.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import lombok.Getter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Base {

	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@LastModifiedDate
	private LocalDateTime updatedAt = LocalDateTime.now();

	//timeWhenDeleted() 메서드에서만 deletedAt = LocalDateTime.now() 실행되도록 해야함
	private LocalDateTime deletedAt; // 기본값 없이 null 상태 유지!

	public void timeWhenDeleted() {
		this.deletedAt = LocalDateTime.now();
	} //삭제 시간 설정용 메서드입니당

}
