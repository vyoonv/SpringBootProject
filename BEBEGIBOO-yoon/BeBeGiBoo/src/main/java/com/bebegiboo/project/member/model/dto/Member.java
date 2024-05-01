package com.bebegiboo.project.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	private int memberNo; 
	private String memberId; 
	private String memberPw;
	private String memberName; 
	private String memberBirth; 
	private String email; 
	private String phone; 
	private String address; 
	private String enrollDate; 
	private String memberDelFl;
	private int authority; 
	 
}
