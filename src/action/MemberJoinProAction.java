package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.MemberJoinProService;
import vo.ActionForward;
import vo.MemberBean;

// 회원 가입
public class MemberJoinProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("MemberJoinProAction");
		ActionForward forward = null;
		
		// 파라미터(이름, 성별, 나이, E-Mail, 아이디, 패스워드) 가져와서 MemberBean 에 저장
		MemberBean member = new MemberBean();
		member.setName(request.getParameter("name"));
		member.setGender(request.getParameter("gender"));
		member.setAge(Integer.parseInt(request.getParameter("age")));
		member.setEmail(request.getParameter("email1") + "@" + request.getParameter("email2"));
		member.setId(request.getParameter("id"));
		member.setPasswd(request.getParameter("passwd"));
		
//		System.out.println(member.getName() + ", " + member.getGender() + ", " + member.getAge() + ", " + 
//							member.getEmail() + ", " + member.getId() + ", " + member.getPasswd());
		
		// MemberJoinProService 클래스의 registMember() 메서드를 호출하여 회원 등록 수행
		// => 파라미터 : MemberBean 객체, 리턴타입 : boolean
		MemberJoinProService memberJoinProService = new MemberJoinProService();
		boolean isRegistSuccess = memberJoinProService.registMember(member);
		
		// 회원 등록 결과가 false 일 경우 자바스크립트를 사용하여 "회원 가입 실패!" 출력 후 이전 페이지 이동
		// 아니면 ActionForward 객체를 사용하여 MemberJoinResult.me 로 포워딩 => 리다이렉트 방식
		if(!isRegistSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); // response 객체로부터 PrintWriter 객체 얻어오기
			out.println("<script>");
			out.println("alert('회원 가입 실패!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("MemberJoinResult.me");
		}
		
		return forward;
	}

}



























