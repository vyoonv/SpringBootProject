package edu.kh.project.myPage.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.dto.UploadFile;
import edu.kh.project.myPage.model.service.MyPageService;
import lombok.RequiredArgsConstructor;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
public class MyPageController {
	
	private final MyPageService service;
	
	/** 내 정보 조회/수정 화면으로 전환
	 * @param loginMember : 세션에 존재하는 loginMember를 얻어와 매개변수에 대입
	 * @param model : 데이터 전달용 객체(기본 request scope)
	 * @return myPage/myPage-info로 요청위임
	 */
	@GetMapping("info") //    /myPage/info  (GET)
	public String info( 
				@SessionAttribute("loginMember") Member loginMember,
				Model model
			) {
		
		// 주소만 꺼내옴
		String memberAddress = loginMember.getMemberAddress();
		
		// 주소가 있을 경우에만 동작
		if(memberAddress != null) {
			
			// 구분자 "^^^"를 기준으로
			// memberAddress 값을 쪼개어 String[]로 반환
			String[] arr = memberAddress.split("\\^\\^\\^");
			
			// "04540^^^서울 중구 남대문로 120^^^3층 E 강의장"
			// --> ["04540", "서울 중구 남대문로 120", "3층 E 강의장"]
			//			0			1						2
			model.addAttribute("postcode",      arr[0]);
			model.addAttribute("address" ,      arr[1]);
			model.addAttribute("detailAddress", arr[2]);
			
		}
		
		// /templates/myPage/myPage-info.html로 forward
		return "myPage/myPage-info";
	}
	
	/** 프로필 이미지 변경 화면 이동
	 * @return
	 */
	@GetMapping("profile")
	public String profile() {
		return "myPage/myPage-profile";
	}
	
	
	/** 비밀번호 변경 화면 이동
	 * @return
	 */
	@GetMapping("changePw")
	public String changePw() {
		return "myPage/myPage-changePw";
	}
	
	/** 회원 탈퇴 화면 이동
	 * @return
	 */
	@GetMapping("secession")
	public String secession() {
		return "myPage/myPage-secession";
	}
	
	
	/** 회원 정보 수정
	 * @param inputMember : 제출된 회원 닉네임, 전화번호, 주소(,,)
	 * @param loginMember : 로그인한 회원 정보(회원 번호 사용할 예정)
	 * @param memberAddress : 주소만 따로 받은 String[]
	 * @param ra : 리다이렉트 시 request scope로 데이터 전달
	 * @return redirect:info
	 */
	@PostMapping("info")
	public String updateInfo(Member inputMember, 
					@SessionAttribute("loginMember") Member loginMember,
					@RequestParam("memberAddress") String[] memberAddress,
					RedirectAttributes ra
			) {
		
		// inputMember에 로그인한 회원번호 추가
		int memberNo = loginMember.getMemberNo();
		inputMember.setMemberNo(memberNo);
		
		
		// 회원 정보 수정 서비스 호출
		int result = service.updateInfo(inputMember, memberAddress);
		
		String message = null;
		
		if(result > 0) {
			message = "회원 정보 수정 성공!!";
			
			// loginMember는
			// 세션에 저장된 로그인한 회원 정보가 저장된 객체를 참조하고 있다!!
			
			// -> loginMember를 수정하면
			//    세션에 저장된 로그인한 회원 정보가 수정된다!
			
			// == 세션 데이터와 DB 데이터를 맞춤
			
			loginMember.setMemberNickname( inputMember.getMemberNickname() );
			
			loginMember.setMemberTel( inputMember.getMemberTel() );
			
			loginMember.setMemberAddress( inputMember.getMemberAddress() );
			
			
		} else {
			message = "회원 정보 수정 실패..";
		}
		
		ra.addFlashAttribute("message", message);
				
		
		return "redirect:info";
	}
	
	/** 비밀번호 변경
	 * @param paramMap : 모든 파라미터를 맵으로 저장
	 * @param loginMember : 세션 로그인한 회원 정보
	 * @param ra
	 * @return
	 */
	@PostMapping("changePw")
	public String changePw(
				@RequestParam Map<String, Object> paramMap,
				@SessionAttribute("loginMember") Member loginMember,
				RedirectAttributes ra
			) {
		
		// 로그인한 회원 번호
		int memberNo = loginMember.getMemberNo();
		
		// 현재 + 새 + 회원번호를 서비스로 전달
		int result = service.changePw(paramMap, memberNo);
		
		String path = null;
		String message = null;
		
		if(result > 0) {
			path = "/myPage/info";
			message = "비밀번호가 변경 되었습니다";
		} else {
			path = "/myPage/changePw";
			message = "현재 비밀번호가 일치하지 않습니다";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
		
	}
	
	
	/** 회원 탈퇴
	 * @param memberPw : 입력 받은 비밀번호
	 * @param loginMember : 로그인한 회원 정보(세션)
	 * @param status : 세션 완료 용도의 객체 
	 *			-> @SessionAttributes 로 등록된 세션을 완료
	 * @param ra
	 * @return
	 */
	@PostMapping("secession")
	public String secession(
				@RequestParam("memberPw") String memberPw,
				@SessionAttribute("loginMember") Member loginMember,
				SessionStatus status,
				RedirectAttributes ra
			) {
		
		// 서비스 호출
		int memberNo = loginMember.getMemberNo();
		
		int result = service.secession(memberPw, memberNo);
		
		String message = null;
		String path = null;
		
		if(result > 0) {
			message = "탈퇴 되었습니다.";
			path = "/";
			
			status.setComplete(); // 세션 완료시킴
			
		} else {
			message = "비밀번호가 일치하지 않습니다.";
			path = "secession";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
		
	}
	
	// -------------------------------------------------------------------------
	
	/* 파일 업로드 테스트 */
	
	@GetMapping("fileTest")
	public String fileTest() {
		return "myPage/myPage-fileTest";
	}
	
	
	/*
	 * Spring에서 파일 업로드를 처리하는 방법
	 * 
	 * - enctype="multipart/form-data" 로 클라이언트 요청을 받으면
	 *   (문자, 숫자, 파일 등이 섞여있는 요청)
	 *   
	 *   이를 MultipartResolver(FileConfig에 정의)를 이용해서 
	 *   섞여있는 파라미터를 분리
	 * 
	 * 	 문자열, 숫자 -> String
	 *   파일         -> MultipartFile 
	 * 
	 * */
	
	/**
	 * @param uploadFile : 업로드한 파일 + 파일에 대한 내용 및 설정 내용
	 * @return
	 */
	@PostMapping("file/test1")
	public String fileUpload1(
				/*@RequestParam("memberName") String memberName,*/
				@RequestParam("uploadFile") MultipartFile uploadFile,
				RedirectAttributes ra
			) throws Exception {
		
		String path = service.fileUpload1(uploadFile);
		
		// 파일이 저장되어 웹에서 접근할 수 있는 경로가 반환 되었을 때
		if(path != null) {
			ra.addFlashAttribute("path", path);
		}
		
		return "redirect:/myPage/fileTest";
	}
	
	
	/** 
	 * @param uploadFile
	 * @param loginMember
	 * @param ra
	 * @return
	 */
	@PostMapping("file/test2")
	public String fileUpload2( @RequestParam("uploadFile") MultipartFile uploadFile,
							   @SessionAttribute("loginMember") Member loginMember,
							   RedirectAttributes ra 	) throws IOException {
		
		// 로그인한 회원의 번호(누가 업로드 했는지) 
		int memberNo = loginMember.getMemberNo(); 
		
		// 업로드된 파일 정보를 DB에 INSERT한 후 결과 행의 개수 반환받을 예정 
		int result = service.fileUpload2(uploadFile, memberNo); 
		
		String message = null; 
		
		if(result >0) {
			message = "파일 업로드 성공"; 
		} else {
			message = "파일 업로드 실패"; 
		}
		
		ra.addFlashAttribute("message",message);
		
		
		return "redirect:/myPage/fileTest"; 
		
	}
	
	/** 파일 목록 조회
	 * @param model  
	 * @return 
	 */
	@GetMapping("fileList")
	public String fileList(Model model) {
		
		// 파일 목록 조회 서비스 호출 
		List<UploadFile> list = service.fileList();  
		
		// model list 담아서 
		model.addAttribute("list", list); 
				
		// myPage/myPage-fileList.html 로 forward
		return "myPage/myPage-fileList"; 
		
	}
	
	
	@PostMapping("file/test3")
	public String fileUpload3(
								@RequestParam("aaa") List<MultipartFile> aaaList,
								@RequestParam("bbb") List<MultipartFile> bbbList,
								@SessionAttribute("loginMember") Member loginMember, 
								RedirectAttributes ra   ) throws Exception {
		
		// aaa 파일 미제출시 
		// -> 0번, 1번 인덱스 파일이 모두 비어있음 
		
		// bbb(multiple) 파일 미제출시 
		// -> 0번 인덱스 파일이 비어있음 
		
		int memberNo = loginMember.getMemberNo(); 
		
		// result == 업로드 파일 개수 
		int result = service.fileUpload3(aaaList, bbbList, memberNo); 
		
		String message = null; 
		
		if(result == 0) {
			message = "업로드된 파일이 없습니다"; 
		} else {
			message = result + "개의 파일이 업로드되었습니다"; 
		}
		
		ra.addFlashAttribute("message", message); 
				
		return "redirect:/myPage/fileTest"; 
		
	}
	
	
	/**
	 * @param profileImg
	 * @param loginMember
	 * @param ra
	 * @return
	 */
	@PostMapping("profile")
	public String profile(	@RequestParam("profileImg") MultipartFile profileImg, 
							@SessionAttribute("loginMember") Member loginMember,
							RedirectAttributes ra ) throws Exception {
		
		// 서비스 호출 
		// /myPage/profile/변경된 파일명 형태의 문자열
		// 현재 로그인한 회원의 PROFILE_IMG 컬럼값으로 수정(UPDATE) 
		int result = service.profile(profileImg, loginMember); 
		
		String message = null; 
		
		if(result >0) message = "변경 성공!"; 
		else 		  message = "변경 실패!"; 
		
		ra.addFlashAttribute("message", message); 
		//message 실을때 꼭 "" key 지정해서 
		
		return "redirect:profile"; // 리다이렉트 - /myPage/profile(상대경로) 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
