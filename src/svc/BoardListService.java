package svc;

import java.sql.Connection;
import java.util.ArrayList;

import dao.BoardDAO;
import vo.BoardBean;

import static db.JdbcUtil.*;

public class BoardListService {

	// 현재 게시물 총 갯수를 가져와서 리턴하는 getListCount() 메서드 => 리턴타입 : int
	public int getListCount() {
//		System.out.println("BoardListService - getListCount()");
		// 공통작업-1. Connection 객체 가져오기
		Connection con = getConnection();
		// 공통작업-2. DAO 객체 가져와서 Connection 객체 전달하기
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		// BoardDAO 객체의 selectArticleList() 메서드를 호출하여 게시물 총 갯수를 가져오기
		int listCount = boardDAO.selectListCount();
		
//		System.out.println("총 게시물 수 : " + listCount);
		
		// 공통작업-3. Connection 객체 반환하기
		close(con);
		
		return listCount;
	}
	
	// 전체 게시물 목록을 가져와서 리턴하는 getArticleList() 메서드 
	// => 리턴타입 : ArrayList<BoardBean>, 파라미터 : int형 page, limit
	public ArrayList<BoardBean> getArticleList(int page, int limit) {
//		System.out.println("BoardListService - getArticleList()");
		// 공통작업-1. Connection 객체 가져오기
		Connection con = getConnection();
		// 공통작업-2. DAO 객체 가져와서 Connection 객체 전달하기
		BoardDAO boardDAO = BoardDAO.getInstance();
		boardDAO.setConnection(con);
		
		ArrayList<BoardBean> articleList = null;
		articleList = boardDAO.selectArticleList(page, limit);
		
		
		// 공통작업-3. Connection 객체 반환하기
		close(con);
		
		return articleList;
	}
	
	

}























