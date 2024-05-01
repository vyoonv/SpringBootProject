package com.bebegiboo.project.member.model.service;

import com.bebegiboo.project.member.model.dto.Member;


public interface MemberService {

	/** 아이디 중복 체크 
	 * @param memberId
	 * @return result 
	 */
	int checkId(String memberId);


	/** 이메일 중복 검사 
	 * @param email
	 * @return
	 */
	int checkEmail(String email);

	/**회원 로그인
	 * @param inputMember
	 * @return
	 */
	Member login(Member inputMember);


	/** 회원가입 
	 * @param inputMember
	 * @param memberAddress
	 * @return 
	 */
	int signup(Member inputMember, String[] memberAddress);


}
