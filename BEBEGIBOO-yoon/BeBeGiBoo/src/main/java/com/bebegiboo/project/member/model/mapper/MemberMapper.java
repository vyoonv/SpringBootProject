package com.bebegiboo.project.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.bebegiboo.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	/** 아이디 중복 체크 
	 * @param memberId
	 * @return result 
	 */
	int checkId(String memberId);

	/** 이메일 중복 체크 
	 * @param email
	 * @return result 
	 */
	int checkEmail(String email);

	/** 회원 로그인
	 * @param memberId
	 * @return
	 */
	Member login(String memberId);

	/** 회원 가입 
	 * @param inputMember
	 * @return
	 */
	int signup(Member inputMember);


}
