package svc;

import java.sql.Connection;

import dao.BoardDAO;

import static db.JdbcUtil.*;
import vo.BoardBean;

public class BoardDetailService {

	public BoardBean getArticle(int board_num) {
//		System.out.println("BoardDetailService - getArticle()");
		
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		
		// BoardDAO 의 selectArticle() 메서드를 호출하여 게시물 상세 정보 가져오기
		// => 파라미터 : board_num, 리턴타입 : BoardBean
		BoardBean article = boardDAO.selectArticle(board_num);
		
//		System.out.println(article.getBoard_subject());
		
		// BoardDAO 의 updateReadcount() 메서드를 호출하여 조회수 증가
		// => 파라미터 : board_num, 리턴타입 : 
		int updateCount = boardDAO.updateReadcount(board_num);
		
		// 조회 수 증가 후 리턴받은 updateCount 가 0보다 크면 commit, 아니면 rollback 수행
		if(updateCount > 0) {
			commit(con);
		} else {
			rollback(con);
		}
		
		close(con);
		
		return article;
	}
	
}




















