package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 수정할 게시물의 상세 내역을 가져와서 리턴하여 뷰 페이지에서 폼에 상제 내역 출력
//		System.out.println("BoardModifyFormAction");
		
		// request 객체를 통해 전달받은 파라미터(board_num) 가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		// BoardDetailService 클래스의 getArticle() 메서드를 호출하여 게시물 상세 내용 가져오기
		// => 파라미터 : board_num, 리턴타입 : BoardBean
		BoardDetailService boardDetailService = new BoardDetailService();
		BoardBean article = boardDetailService.getArticle(board_num);
		
		// request 객체에 BoardBean 객체 저장
		request.setAttribute("article", article);
		
		ActionForward forward = new ActionForward();
//		forward.setRedirect(false);
		forward.setPath("/board/qna_board_modify.jsp");
		
		return forward;
	}

}
