package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.MemberLoginProService;
import vo.ActionForward;

public class MemberLoginProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("MemberLoginProAction");
		
		ActionForward forward = null;
		
		// 전달받은 파라미터(id, passwd) 가져오기
		String id = request.getParameter("id");
		String passwd = request.getParameter("passwd");
		System.out.println(id + ", " + passwd);
		
		// MemberLoginProService 클래스의 memberLogin() 메서드 호출
		// => 파라미터 : id, passwd, 리턴타입 : int(loginResult)
		MemberLoginProService memberLoginProService = new MemberLoginProService();
		int loginResult = memberLoginProService.memberLogin(id, passwd);
		
		// loginResult 가 0일 경우 자바스크립트를 사용하여 "ID 가 존재하지 않습니다" 출력
		// loginResult 가 -1일 경우 자바스크립트를 사용하여 "패스워드가 틀렸습니다" 출력
		// loginResult 가 1일 경우 ActionForward 객체를 사용하여 포워딩
		// => 포워딩 주소 : BoardMain.bo, 포워딩 방식 : Redirect
		if(loginResult == 0) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); // response 객체로부터 PrintWriter 객체 얻어오기
			out.println("<script>");
			out.println("alert('ID 가 존재하지 않습니다!')");
			out.println("history.back()");
			out.println("</script>");
		} else if(loginResult == -1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); // response 객체로부터 PrintWriter 객체 얻어오기
			out.println("<script>");
			out.println("alert('패스워드가 틀렸습니다!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			// request 객체로부터 현재 세션 객체를 HttpSession 인터페이스 타입으로 리턴받아 저장
			// ID 와 패스워드가 모두 일치하는 경우 ID 값을 세션에 저장
			HttpSession session = request.getSession();
			session.setAttribute("sId", id);
			
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("BoardMain.bo");
		}
		
		return forward;
	}

}



















