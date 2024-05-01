package com.bebegiboo.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bebegiboo.project.member.model.dto.Member;
import com.bebegiboo.project.member.model.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;



@SessionAttributes({"loginMember"})
@Slf4j
@Controller
@RequestMapping("member")
public class MemberController {
	
	
	
	@Autowired
	private MemberService service; 
	

	
	/** 회원가입 화면 이동 
	 * @return
	 */
	@GetMapping("signup/signupMain")
	public String signupMain() {

		
		return "/member/signup/signupMain"; 
	}
	


	/** 회원가입 약관 동의 화면 이동 
	 * @return
	 */
	@GetMapping("signup/signupTerm")
	public String signupTerm() {
		return "/member/signup/signupTerm"; 

	}
	
	
	/** 회원가입 폼 화면 이동 
	 * @return
	 */
	@GetMapping("signup/signupForm")
	public String signupForm() {
		return "/member/signup/signupForm"; 
	}
	

	@PostMapping("signup/signupForm")
	public String signup(	Member inputMember, 
							@RequestParam("address") String[] memberAddress, 
							RedirectAttributes ra	) {
		
		int result = service.signup(inputMember, memberAddress); 
		
		String path = null; 
		String message = null;
		
		if(result > 0) {
			message = inputMember.getMemberName()+"님의 가입을 환영합니다 :)"; 
			path = "/"; 
		} else {
			message = "회원가입 실패.."; 
			path = "signup/signupForm"; 
		}
		
		ra.addFlashAttribute("message", message); 
		
		return "redirect:" + path; 
		
	}
	

	
	/** 로그인 화면 이동 
	 * @return
	 */
	@GetMapping("login")
	public String login() {
		
		return "/member/login/login"; 
		
	}
	
	
	/** 아이디 중복 검사 
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@GetMapping("checkId")
	public int checkId(@RequestParam("memberId") String memberId) {
	    return service.checkId(memberId);
	}
	
	
	/**로그인
	 * @param inputMember
	 * @param ra
	 * @param model
	 * @param saveId
	 * @param resp
	 * @return
	 */
	@PostMapping("login")
	public String login(Member inputMember,
						RedirectAttributes ra,
						Model model,
						@RequestParam(value="saveId", required=false) String saveId,
						HttpServletResponse resp
						) {
		

		Member loginMember = service.login(inputMember);
		String message=null;
		String path= null;
		
		log.debug("loginMember:"+loginMember);
		if(loginMember == null) {
			
			message="아이디 또는 비밀번호가 일치하지 않습니다";
			path ="redirect:/member/login";
		}
		if(loginMember != null){
			
			log.debug("test" + loginMember.getMemberId());
			model.addAttribute("loginMember", loginMember);
			
			//쿠키 만들기
			Cookie cookie = new Cookie("saveId", loginMember.getMemberId());
			
			cookie.setPath("/"); 
			
			if(saveId != null) {
				cookie.setMaxAge(60*60*24*30);
				
			}else {
				cookie.setMaxAge(0); //기존 쿠키 삭제
			}
			
			resp.addCookie(cookie);
			
			path ="redirect:/";
			
		}
		
		ra.addFlashAttribute("message", message);
		return path;
	}

	

	/** 이메일 중복 검사 
	 * @param email
	 * @return 
	 */
	@ResponseBody
	@GetMapping("checkEmail")
	public int checkEmail(@RequestParam("email") String email) {
		return service.checkEmail(email); 
	}
	
	

	@GetMapping("inquiry/idInquiry")
	public String idInquiry() {
		return "member/inquiry/idInquiry";
	}
	
	@GetMapping("inquiry/pwInquiry")
	public String pwInquiry() {
		return "member/inquiry/pwInquiry";
	}
	
}
