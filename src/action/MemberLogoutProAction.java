package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vo.ActionForward;

public class MemberLogoutProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("LogoutProAction");
		
		// request 객체로부터 현재 세션 객체(HttpSession) 가져오기
		HttpSession session = request.getSession();
		// 세션 초기화 => invalidate() 메서드 호출
		session.invalidate(); // 사용자 세션 전체 초기화
//		session.removeAttribute("sId"); // 선택적 세션 삭제
		
		// BoardMain.bo 페이지로 포워딩 => Redirect 방식
		ActionForward forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("BoardMain.bo");
		
		return forward;
	}

}
