package com.example.pstagram.config;

import java.util.Locale;

import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import com.example.pstagram.common.ResponseCode;

/**
 * messages.properties에서 메시지를 가져오는 유틸 클래스
 */
@Component
@RequiredArgsConstructor
public class MessageUtil {

	private final MessageSource messageSource;

	public String getMessage(String code) {
		return messageSource.getMessage(code, null, Locale.KOREA);
	}

	public String getMessage(ResponseCode code) {
		return getMessage(code.getMessageKey());
	}
}

