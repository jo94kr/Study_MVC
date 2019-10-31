package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.BoardListService;
import vo.ActionForward;
import vo.BoardBean;
import vo.PageInfo;

public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// BoardListService 클래스를 통해 게시물 목록을 조회하여
		// 조회된 결과를 request 객체에 저장하고, qna_board_list.jsp 페이지로 이동
		// => 이 때, request 객체를 유지한 채 포워딩이 되어야 하므로 포워딩 방식은 dispatch 방식을 사용
//		System.out.println("BoardListAction");
		
		// 페이징 처리를 위한 변수 선언
		int page = 1; // 현재 페이지 번호를 저장할 변수
		int limit = 10; // 한 페이지에 표시할 게시물 갯수를 지정하는 변수
		
		// 전달된 파라미터 중 "page" 파라미터가 null 이 아닐 경우 page 변수에 해당 파라미터값 저장
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page")); // String -> int 형변환 필수
		}
		
		// BoardListService 클래스 인스턴스 생성 후 getListCount() 메서드를 호출하여 총 게시물 수 가져오기
		// => 변수 listCount 에 총 게시물 수 저장
		BoardListService boardListService = new BoardListService();
		
		int listCount = boardListService.getListCount();
//		System.out.println("총 게시물 수 : " + listCount);
		
		// BoardListService 클래스의 getArticleList() 메서드를 호출하여 전체 게시물 목록 가져오기
		// => 파라미터로 page, limit 전달
		// 게시물 목록을 저장할 ArrayList 객체 생성(제네릭 타입으로 BoardBean 타입 지정)
		ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
		articleList = boardListService.getArticleList(page, limit); // 읽어올 페이지 번호와 게시물 갯수 전달
		
		
		// 페이지 계산 작업 수행
		// 1. 전체 페이지 수 계산(총 게시물 수 / 페이지 당 게시물 수 + 0.95 결과를 정수화시킴)
		int maxPage = (int)((double)listCount / limit + 0.95); // 0.95 는 올림처리를 위한 덧셈
		
		// 2. 현재 페이지에서 보여줄 시작 페이지 수(1, 11, 21 페이지 등)
		int startPage = ((int)((double)page / 10 + 0.9) - 1) * 10 + 1;
		
		// 3. 현재 페이지에서 보여줄 마지막 페이지 수(10, 20, 30 페이지 등)
		int endPage = startPage + 10 - 1;
		
		// 4. 마지막 페이지가 현재 페이지에서 표시할 최대 페이지(전체 페이지 수)보다 클 경우
		//    마지막 페이지 번호를 전체 페이지 번호로 대체
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		// 계산된 페이지 정보를 PageInfo 객체에 저장
		PageInfo pageInfo = new PageInfo(page, maxPage, startPage, endPage, listCount);
		
		
		// request 객체에 jsp 페이지로 전달할 데이터 저장(request.setAttribute() 메서드 사용)
		request.setAttribute("pageInfo", pageInfo); // 페이지 정보
		request.setAttribute("articleList", articleList); // 게시물 목록
		
		
		// ActionForward 객체 생성 후 이동할 페이지(board 폴더 내의 qna_board_list.jsp 파일) 지정
		// => request 객체를 유지한 채 포워딩해야하므로 dispatch 방식으로 포워딩
		ActionForward forward = new ActionForward();
		forward.setPath("/board/qna_board_list.jsp");
//		forward.setRedirect(false); // 생략 가능(true 일 때만 지정 필수)
		
		// ActionForward 객체 리턴
		return forward;
	}

}
























