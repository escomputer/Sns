package com.example.pstagram.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * API 응답을 감싸는 공통 응답 포맷입니다.
 *
 * @param <T> 실제 응답 데이터의 타입
 */
@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  //Null인 경우는 제외됨
public class ApiResponse<T> {

	private int status;
	/**
	 * 사용자에게 보여줄 응답 메시지
	 */
	private String message;

	/**
	 * 응답 데이터 (nullable)
	 */
	private T data;

}
