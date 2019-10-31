package svc;

import static db.JdbcUtil.close;
import static db.JdbcUtil.commit;
import static db.JdbcUtil.getConnection;
import static db.JdbcUtil.rollback;

import java.sql.Connection;

import dao.BoardDAO;

public class BoardDeleteProService {

	// 삭제 권한 확인을 위해 패스워드 일치 여부 판별
	public boolean isArticleWriter(int board_num, String board_pass) {
//		System.out.println("BoardDeleteProService - isArticleWriter()");
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		boolean isArticleWriter = boardDAO.isBoardArticleWriter(board_num, board_pass);
		
		close(con);
		
		return isArticleWriter;
	}

	// 게시물 삭제
	public boolean removeArticle(int board_num) {
//		System.out.println("BoardDeleteProService - removeArticle()");
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		boolean isDeleteSuccess = false;
		
		// BoardDAO 객체의 deleteArticle() 메서드를 호출하여 게시물 삭제
		// 파라미터 : board_num, 리턴타입 : int
		int deleteCount = boardDAO.deleteArticle(board_num);
		
		// deleteCount 가 0보다 크면 commit 수행 및 isDeleteSuccess 를 true 로 변경 
		// 아니면, rollback 수행
		if(deleteCount > 0) {
			isDeleteSuccess = true;
			commit(con);
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isDeleteSuccess;
	}
	
	
	
}
