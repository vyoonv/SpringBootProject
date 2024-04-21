package edu.kh.project.member.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.status.Status;
import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@SessionAttributes({"loginMember"})
@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberService service; 
	
	
	/** 로그
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
						@RequestParam(value="saveId", required = false) String saveId,
						HttpServletResponse resp ) {
		
		
		Member loginMember = service.login(inputMember);
		
		if(loginMember == null) {
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다"); 			
		}
		
		if(loginMember != null) {
			model.addAttribute("loginMember", loginMember); 
			
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail()); 
			// saveId=user01@kh.or.kr
			
			cookie.setPath("/");
			
			// 만료 기간 
			if(saveId != null) {
				cookie.setMaxAge(60*60*24*30);
				
			} else {
				cookie.setMaxAge(0); 
			}
			
			resp.addCookie(cookie); 
			
		} 
		
		
		
		return "redirect:/"; 
	}
	
	/** 로그아
	 * @param status (session status) : 세션을 완료시키는 역할의 객체 1
	 * @return
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		
		status.setComplete(); 
		return "redirect:/"; 
	}
	
	
	@GetMapping("signup")
	public String signupPage() {
		
		return "member/signup"; 
		
		
	}
	
	@ResponseBody
	@GetMapping("checkEmail")
	public int checkEmail( @RequestParam("memberEmail") String memberEmail ) {
		
		return service.checkEmail(memberEmail); 
		
	}
	
	
	

}
