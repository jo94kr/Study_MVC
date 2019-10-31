package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.BoardDAO;
import vo.BoardBean;

public class BoardReplyProService {

	// 답글 등록하기
	public boolean replyArticle(BoardBean article) {
		Connection con = getConnection();
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		boolean isReplySuccess = false;
		
		// BoardDAO 객체의 insertReplyArticle() 메서드를 호출하여 답변글 등록 작업 수행
		// => 파라미터 : BoardBean 객체, 리턴타입 : int(insertCount)
		int insertCount = boardDAO.insertReplyArticle(article);
		
		// insertCount 가 0보다 크면 commit 수행, isReplySuccess 를 true 로 변경
		// 아니면 rollback 수행
		if(insertCount > 0) {
			commit(con);
			isReplySuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isReplySuccess;
	}
	
}












