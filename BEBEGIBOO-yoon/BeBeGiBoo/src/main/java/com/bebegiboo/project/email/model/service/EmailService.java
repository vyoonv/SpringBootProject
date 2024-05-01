package com.bebegiboo.project.email.model.service;

import java.util.Map;

public interface EmailService {

	/** 인증 메일 보내기 서비스 
	 * @param string
	 * @param email
	 * @return
	 */
	String sendEmail(String string, String email);

	/** 인증 번호 확인 
	 * @param map
	 * @return
	 */
	int checkAuthKey(Map<String, Object> map);

}
