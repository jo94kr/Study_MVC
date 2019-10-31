package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDeleteProService;
import vo.ActionForward;

public class BoardDeleteProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("BoardDeleteProAction");
		
		ActionForward forward = null;
		
		// board_num, page 파라미터 가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String nowPage = request.getParameter("page");
		
		BoardDeleteProService boardDeleteProService = new BoardDeleteProService();
		// BoardDeleteProService 클래스의 isArticleWriter() 메서드를 호출하여 패스워드 일치 여부 판별
		// => 파라미터 : 글 번호(board_num), 패스워드(board_pass)
		boolean isRightUser = boardDeleteProService.isArticleWriter(
													board_num, request.getParameter("board_pass"));
		
		// 만약, 게시물 삭제 권한이 없는 경우(= 패스워드가 틀린 경우)
		// 자바스크립트를 사용하여 "삭제 권한이 없습니다!" 출력
		if(!isRightUser) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); // response 객체로부터 PrintWriter 객체 얻어오기
			out.println("<script>");
			out.println("alert('삭제 권한이 없습니다!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			// 삭제 권한이 있는 경우(= 패스워드가 일치하는 경우)
			// 글 번호(board_num)를 사용하여 글 삭제 => Service 클래스의 removeArticle() 메서드 호출
			// 파라미터 : board_num, 리턴타입 : boolean(isDeleteSuccess)
			boolean isDeleteSuccess = boardDeleteProService.removeArticle(board_num);
			
			// isDeleteSuccess 가 false 일 경우 자바스크립트를 사용하여 "삭제 실패!" 출력
			// 아니면, 새로운 주소로 포워딩
			if(!isDeleteSuccess) {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter(); // response 객체로부터 PrintWriter 객체 얻어오기
				out.println("<script>");
				out.println("alert('삭제 실패!')");
				out.println("history.back()");
				out.println("</script>");
			} else {
				// ActionForward 객체 생성
				// BoardList.bo 주소로 새로운 요청이 발생해야하므로 Redirect 방식으로 포워딩 설정
				// => "BoardList.bo" URL 요청 뒤에 파라미터값으로 page 전달
				forward = new ActionForward();
				forward.setRedirect(true); // Redirect 방식
				forward.setPath("BoardList.bo?page=" + nowPage);
			}
			
		}
		
		return forward;
	}

}














