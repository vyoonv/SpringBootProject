package edu.kh.project.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	Member login(String memberEmail);

	/** 이메일 중복 검사 
	 * @param memberEmail
	 * @return
	 */
	int checkEmail(String memberEmail);
	
	
	
	

}
