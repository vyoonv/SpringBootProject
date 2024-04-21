package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor=Exception.class)
@Service
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt; 

	@Override
	public Member login(Member inputMember) {
		
		// memebrEmail : user01@kh.or.kr
		// memberPw : pass01!
		
		// ** TEST
		String bcryptPassword = bcrypt.encode(inputMember.getMemberPw());
		log.debug( "bcryptPassword : " + bcryptPassword);
		//------------------------------------------
		
		//boolean result = bcrypt.matches(inputMember.getMemberPw(), bcryptPassword); 
		//log.debug("result", result);
		
		Member loginMember = mapper.login(inputMember.getMemberEmail()); 
		
		if(loginMember == null ) return null;
		
		if( !bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
			return null ;
			
		}
		
		loginMember.setMemberPw(null);	
		
		return loginMember;
	}

	@Override
	public int checkEmail(String memberEmail) {
		
		return mapper.checkEmail(memberEmail);
	} 
	
	
	

}
