package svc;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

import static db.JdbcUtil.*;

public class BoardModifyProService {

	// 게시물 번호(board_num)에 해당하는 패스워드(board_pass) 일치 여부 확인
	public boolean isArticleWriter(int board_num, String board_pass) {
//		System.out.println("BoardModifyProService - isArticleWriter()");
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		boolean isArticleWriter = boardDAO.isBoardArticleWriter(board_num, board_pass);
		
		close(con);
		
		return isArticleWriter;
	}
	
	
	// 게시물 수정
	public boolean modifyArticle(BoardBean article) {
//		System.out.println("BoardModifyProService - modifyArticle()");
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		boolean isModifySuccess = false;
		
		// BoardDAO 의 updateArticle() 메서드를 호출하여 BoardBean 객체 전달
		// => 수정 결과를 int 형 updateCount 로 리턴받음
		int updateCount = boardDAO.updateArticle(article);
		
		// updateCount 가 0보다 크면 commit 수행 및 isModifySuccess 를 true 로 변경 
		// 아니면, rollback 수행
		if(updateCount > 0) {
			isModifySuccess = true;
			commit(con);
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isModifySuccess;
	}
	
}
