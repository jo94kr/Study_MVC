package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardDetailService;
import vo.ActionForward;
import vo.BoardBean;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		System.out.println("BoardDetailAction");
		
		// request 객체를 통해 전달받은 파라미터(board_num, page) 가져오기
		int board_num = Integer.parseInt(request.getParameter("board_num"));
		String page = request.getParameter("page"); 
		// => "page" 파라미터는 현재 클래스에서 사용되지 않고 다음 페이지로 포워딩을 위해 전달만 하므로
		//    정수형태로 변환할 필요없이 String 타입 그대로 리턴받아 전달
//		System.out.println("board_num : " + board_num + ", page : " + page);
		
		
		// BoardDetailService 클래스 인스턴스 생성 후 getArticle() 메서드를 호출하여 게시글 내용 리턴받음
		// => 파라미터 : board_num, 리턴타입 : BoardBean
		BoardDetailService boardDetailService = new BoardDetailService();
		BoardBean article = boardDetailService.getArticle(board_num);
		
		
		// request 객체에 전달할 파라미터(page, BoardBean 객체)를 저장
		request.setAttribute("page", page);
		request.setAttribute("article", article);
		
		
		// ActionForward 객체에 값 저장
		ActionForward forward = new ActionForward();
		// setRedirect() : request 객체를 유지한 채 현재 요청 주소 그대로 포워딩 = dispatch 방식 
		//                 => 방식 지정 생략 가능
		// setPath() : board 폴더 내의 qna_board_view.jsp 페이지 지정
//		forward.setRedirect(false);
		forward.setPath("/board/qna_board_view.jsp");
		
		return forward;
	}

}
