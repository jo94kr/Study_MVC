package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardReplyFormAction!");
		
		// 답글을 달기 위해 필요한 원본 글에 대한 정보 가져오기
		ActionForward forward = null;

		// 전달받은 파라미터 가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String nowPage = request.getParameter("page");
//		System.out.println("board_num = " + board_num + ", page = " + nowPage);
		
		// BoardDetailService 클래스의 getArticle() 메서드를 호출하여 답글을 위한 게시물 1개 정보 가져오기
		// => 파라미터 : board_num, 리턴타입 : BoardBean
		BoardDetailService boardDetailService = new BoardDetailService();
		BoardBean article = boardDetailService.getArticle(board_num);
		
//		System.out.println(article.getBoard_subject());
		
		// request 객체에 전달할 객체 및 값 저장 => BoardBean 객체, page 번호
		request.setAttribute("article", article);
		request.setAttribute("page", nowPage);
		
		// 페이지 포워딩
		forward = new ActionForward();
//		forward.setRedirect(false); // Dispatch 방식 포워딩
		forward.setPath("/board/qna_board_reply.jsp");
		
		return forward;
	}

}
