package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardReplyProService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardReplyProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardReplyProAction");
		
		ActionForward forward = null;
		
		/*
		 * 답변 글을 등록
		 * 
		 * 1. BoardReplyPro.bo => BoardReplyProAction 으로 이동
		 *    => 전달받은 파라미터(답글 작성에 필요한 모든 파라미터) 가져오기
		 *    모든 파라미터를 BoardBean 객체에 저장
		 * 
		 * 2. BoardReplyProService 클래스의 replyArticle() 메서드 호출
		 *    => BoardBean 객체 전달, boolean 타입 리턴(isReplySuccess)
		 * 
		 * 3. isReplySuccess 가 false 일 경우 "답글 등록 실패" 출력
		 *    아니면 BoardList.bo?page=x 페이지로 포워딩 => 페이지번호 함께 전달
		 *    => Redirect 방식 포워딩
		 */
		String nowPage = request.getParameter("page");
		
		BoardBean article = new BoardBean();
		article.setBoard_num(Integer.parseInt(request.getParameter("board_num")));
		article.setBoard_name(request.getParameter("board_name"));
		article.setBoard_pass(request.getParameter("board_pass"));
		article.setBoard_subject(request.getParameter("board_subject"));
		article.setBoard_content(request.getParameter("board_content"));
		article.setBoard_re_ref(Integer.parseInt(request.getParameter("board_re_ref")));
		article.setBoard_re_lev(Integer.parseInt(request.getParameter("board_re_lev")));
		article.setBoard_re_seq(Integer.parseInt(request.getParameter("board_re_seq")));
		
		BoardReplyProService boardReplyProService = new BoardReplyProService();
		boolean isReplySuccess = boardReplyProService.replyArticle(article);
		
		if(!isReplySuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter(); // response 객체로부터 PrintWriter 객체 얻어오기
			out.println("<script>");
			out.println("alert('답글 등록 실패!')");
			out.println("history.back()");
			out.println("</script>");
		} else {
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("BoardList.bo?page=" + nowPage);
		}
		
		return forward;
	}

}














