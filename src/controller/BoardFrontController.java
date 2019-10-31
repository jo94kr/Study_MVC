package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.BoardDeleteProAction;
import action.BoardDetailAction;
import action.BoardListAction;
import action.BoardModifyFormAction;
import action.BoardModifyProAction;
import action.BoardReplyFormAction;
import action.BoardReplyProAction;
import action.BoardWriteProAction;
import vo.ActionForward;

// 서블릿 주소가 XXX.bo 일 경우 BoardFrontController 로 요청이 넘어감
@WebServlet("*.bo")
public class BoardFrontController extends HttpServlet { // HttpServlet 클래스 상속받아 정의
	
	// doGet(), doPost() 메서드가 호출되면 공통으로 작업을 처리하기 위해 doProcess() 메서드 정의
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // POST 방식에 대한 한글 처리
		
		String command = request.getServletPath(); // 서블릿 주소 추출
//		System.out.println(command); // 서블릿 주소 확인
		
		
		Action action = null;
		ActionForward forward = null;
		
		// 입력받은 서블릿 주소 매핑
		if(command.equals("/BoardMain.bo")) {
			// 포워딩 대상이 뷰페이지(*.jsp) 일 경우 ActionForward 객체 생성하여 URL 전달
			forward = new ActionForward();
			forward.setPath("/index.jsp");
		} else if(command.equals("/BoardWriteForm.bo")) {
		    // WebContent 폴더의 서브 폴더를 지정할 경우 "/서브폴더명/파일명" 으로 지정	
			forward = new ActionForward();
			forward.setPath("/board/qna_board_write.jsp");
		} else if(command.equals("/BoardWritePro.bo")) {
			// Action 클래스(Controller)에 대한 접근이 필요하므로 인스턴스 생성 후 execute() 메서드 호출
			action = new BoardWriteProAction(); // 다형성 활용하기 위해 Action 타입으로 참조
			try {
				// 공통 메서드인 execute() 메서드를 호출하여 request, response 객체 전달한 뒤
				// 작업 처리 후 리턴되는 ActionForward 객체 리턴받음
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardList.bo")) {
			// BoardListAction 클래스로 이동
			action = new BoardListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardDetail.bo")) {
			// BoardDetailAction 클래스로 이동
			action = new BoardDetailAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardModifyForm.bo")) {
			// BoardModifyFormAction 클래스로 이동
			action = new BoardModifyFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardModifyPro.bo")) {
			// BoardModifyProAction 클래스로 이동
			action = new BoardModifyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardDeleteForm.bo")) {
			forward = new ActionForward();
			forward.setPath("/board/qna_board_delete.jsp");
		} else if(command.equals("/BoardDeletePro.bo")) {
			// BoardDeleteProAction 클래스로 이동
			action = new BoardDeleteProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardReplyForm.bo")) {
			// BoardReplyFormAction 클래스로 이동
			action = new BoardReplyFormAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(command.equals("/BoardReplyPro.bo")) {
			// BoardReplyProAction 클래스로 이동
			action = new BoardReplyProAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}  
		
		
		
		// ActionForward 객체 내용에 따라 각각 다른 포워딩 수행
		if(forward != null) { // ActionForward 객체가 null 이 아닐 경우
			if(forward.isRedirect()) { // Redirect 방식으로 포워딩할 경우
				// response 객체의 sendRedirect() 메서드를 호출하여 ActionForward 객체의 URL 정보 전달
				response.sendRedirect(forward.getPath());
			} else { // Dispatch 방식으로 포워딩할 경우
				// request 객체의 getRequestDispatcher() 메서드를 호출하여 RequestDispatcher 객체 리턴
				// => 메서드 파라미터로 ActionForward 객체의 URL 정보 전달
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				// RequestDispatcher 객체의 forward() 메서드를 호출하여 포워딩(request, response 객체 전달)
				dispatcher.forward(request, response);
			}
		}
		
		
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
}
