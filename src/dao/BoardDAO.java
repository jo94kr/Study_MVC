package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vo.BoardBean;

import static db.JdbcUtil.*;

public class BoardDAO {
	// ------------ 싱글톤 디자인 패턴을 활용한 BoardDAO 인스턴스 작업 ---------------
	// 1. 인스턴스 생성 불가능하도록 생성자 private 선언
	// 2. 직접 인스턴스를 생성하여 저장(3번에서 인스턴스가 없을 때 직접 생성해도 됨)
	// 3. Getter 사용하여 생성한 인스턴스를 외부로 리턴
	// 4. 3번 메서드를 인스턴스 생성없이 호출해야하므로 static 으로 선언
	//    => 이 때, 2번의 변수도 static 으로 선언해야함
	// 5. 2번의 변수도 외부에서 접근이 불가능하도록 private 선언
	private BoardDAO() {}
	
	private static BoardDAO instance;

	public static BoardDAO getInstance() {
		// 기존의 인스턴스가 instance 변수에 저장되어 있지 않을 경우(null 일 경우) 생성하여 리턴
		if(instance == null) {
			instance = new BoardDAO();
		}
		
		return instance;
	}
	// -------------------------------------------------------------------------------

	Connection con;
	
	public void setConnection(Connection con) {
		// Service 클래스로부터 Connection 객체를 전달받아 멤버변수에 저장
		this.con = con;
	}

	public int insertArticle(BoardBean boardBean) {
		// Service 클래스로부터 BoardBean 객체를 전달받아 DB 에 INSERT 작업을 수행한 후 결과(int타입) 리턴
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		int num = 0;
		int insertCount = 0;
		
		try {
			// 현재 게시물의 최대 번호를 조회하여 새로운 글번호를 결정(+1)
			String sql = "SELECT MAX(board_num) FROM board"; // 가장 큰 번호 조회
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 등록된 게시물이 하나라도 존재할 경우
				num = rs.getInt(1) + 1; // 새 글 번호 = 현재 게시물 가장 큰 번호 + 1
			} else { // 등록된 게시물이 하나도 없을 경우
				num = 1; // 새 글 번호 = 1
			}
			
			// 전달받은 데이터를 사용하여 INSERT 작업 수행
			// => 마지막 필드인 board_date(게시물 작성일) 는 데이터베이스 now() 함수 사용하여 현재 시각 사용
			sql = "INSERT INTO board VALUES(?,?,?,?,?,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, boardBean.getBoard_name());
			pstmt.setString(3, boardBean.getBoard_pass());
			pstmt.setString(4, boardBean.getBoard_subject());
			pstmt.setString(5, boardBean.getBoard_content());
			pstmt.setString(6, boardBean.getBoard_file());
			pstmt.setInt(7, num);
			pstmt.setInt(8, boardBean.getBoard_re_lev());
			pstmt.setInt(9, boardBean.getBoard_re_seq());
			pstmt.setInt(10, boardBean.getBoard_readcount());
			
			insertCount = pstmt.executeUpdate(); // INSERT 실행 결과 값을 int형 변수로 저장
			
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("insertArticle() 오류 - " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
//			close(con); // 주의!! DAO 내에서 Connection 객체를 닫지 않도록 주의할 것!!!!
			// => Service 클래스에서 commit, rollback 여부를 결정한 후 Connection 에 접근해야하기 때문에
		}
		
		return insertCount;
	}

	public int selectListCount() {
		// board 테이블의 총 게시물 수 조회하여 리턴 => count(*) 함수 사용
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT count(*) FROM board";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// 조회 결과가 있을 경우(rs.next() 가 true 일 경우)
			if(rs.next()) {
				listCount = rs.getInt(1);
			}
		} catch (SQLException e) {
//			e.printStackTrace();
			System.out.println("selectListCount() 오류 - " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return listCount;
	}

	
	public ArrayList<BoardBean> selectArticleList(int page, int limit) {
		// 지정된 갯수 만큼의 게시물 조회하여 ArrayList 객체에 저장한 뒤 리턴
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<BoardBean> articleList = new ArrayList<BoardBean>();
		
		// 조회 시작 게시물 번호(행 번호) 계산
		int startRow = (page - 1) * 10;
		
		try {
			// 게시물 조회
			// 참조글번호(ref)를 기준으로 내림차순, 순차번호(seq)를 기준으로 오름차순 정렬
			// 조회 시작 게시물 번호(startRow) 부터 10개(limit)만큼 조회
			String sql = "SELECT * FROM board ORDER BY board_re_ref desc,board_re_seq asc LIMIT ?,?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, limit);
			rs = pstmt.executeQuery();
			
			// 읽어올 게시물이 존재할 경우
			while(rs.next()) {
				// BoardBean 객체에 읽어온 게시물 1개씩 저장(패스워드 제외) => ArrayList 객체에 추가
				BoardBean boardBean = new BoardBean();
				boardBean.setBoard_num(rs.getInt("board_num"));
				boardBean.setBoard_name(rs.getString("board_name"));
				boardBean.setBoard_subject(rs.getString("board_subject"));
				boardBean.setBoard_content(rs.getString("board_content"));
				boardBean.setBoard_file(rs.getString("board_file"));
				boardBean.setBoard_re_ref(rs.getInt("board_re_ref"));
				boardBean.setBoard_re_lev(rs.getInt("board_re_lev"));
				boardBean.setBoard_re_seq(rs.getInt("board_re_seq"));
				boardBean.setBoard_readcount(rs.getInt("board_readcount"));
				boardBean.setBoard_date(rs.getDate("board_date"));
				
				articleList.add(boardBean);
			}
			
		} catch (SQLException e) {
			System.out.println("selectArticleList() 오류 - " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return articleList;
	}

	public BoardBean selectArticle(int board_num) {
		// 게시물 하나의 상세 정보 조회 => board_num 에 해당하는 레코드 SELECT
		// => 조회 결과가 있을 경우 BoardBean 객체에 모든 데이터 저장 후 리턴
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		BoardBean boardBean = null;
		
		try {
			String sql = "SELECT * FROM board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			// 게시물이 존재할 경우 BoardBean 객체에 모든 데이터 저장
			if(rs.next()) {
				boardBean = new BoardBean();
				boardBean.setBoard_num(rs.getInt("board_num"));
				boardBean.setBoard_name(rs.getString("board_name"));
				boardBean.setBoard_subject(rs.getString("board_subject"));
				boardBean.setBoard_content(rs.getString("board_content"));
				boardBean.setBoard_file(rs.getString("board_file"));
				boardBean.setBoard_re_ref(rs.getInt("board_re_ref"));
				boardBean.setBoard_re_lev(rs.getInt("board_re_lev"));
				boardBean.setBoard_re_seq(rs.getInt("board_re_seq"));
				boardBean.setBoard_readcount(rs.getInt("board_readcount"));
				boardBean.setBoard_date(rs.getDate("board_date"));
				System.out.println(rs.getString("board_content"));
			}
			
		} catch (SQLException e) {
			System.out.println("selectArticle() 오류 - " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return boardBean;
	}

	public int updateReadcount(int board_num) {
		// board_num 에 해당하는 게시물 조회수(readcount) 1 증가
		PreparedStatement pstmt = null;
		
		int updateCount = 0;
		
		try {
			// 조회수를 증가시킬 게시물 번호(board_num)를 SQL 구문 작성 시 바로 결합해도 되고
//			String sql = "UPDATE board SET board_readcount=board_readcount+1 WHERE board_num=" + board_num;
			
			// 만능문자(?)를 사용하여 파라미터 값 지정해도 됨(setXXX() 필수)
			String sql = "UPDATE board SET board_readcount=board_readcount+1 WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("updateReadcount() 오류 - " + e.getMessage());
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	// 게시물 패스워드 일치 여부 확인
	public boolean isBoardArticleWriter(int board_num, String board_pass) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isArticleWriter = false;
		
		try {
			// board_num 에 해당하는 레코드의 board_pass 가 전달받은 값과 일치하는지 여부 판별
			String sql = "SELECT board_pass FROM board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(board_pass.equals(rs.getString("board_pass"))) {
					// 패스워드가 일치할 경우 isArticleWriter 를 true 로 변경
					isArticleWriter = true;
				}
			}
			
		} catch (SQLException e) {
			System.out.println("isBoardArticleWriter() 오류 - " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return isArticleWriter;
	}

	// 게시물 수정
	public int updateArticle(BoardBean article) {
		PreparedStatement pstmt = null;
		
		int updateCount = 0;
		
		try {
			// BoardBean 객체에 저장되어 있는 게시물 수정 정보(작성자, 제목, 내용)를 사용하여
			// BoardBean 객체에 저장되어 있는 게시물 번호에 해당하는 레코드를 수정
			String sql = "UPDATE board SET board_name=?,board_subject=?,board_content=? WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, article.getBoard_name());
			pstmt.setString(2, article.getBoard_subject());
			pstmt.setString(3, article.getBoard_content());
			pstmt.setInt(4, article.getBoard_num());
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("updateArticle() 오류 - " + e.getMessage());
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	// 게시물 삭제
	public int deleteArticle(int board_num) {
		PreparedStatement pstmt = null;
		
		int deleteCount = 0;
		
		try {
			// board_num 에 해당하는 레코드 삭제
			String sql = "DELETE FROM board WHERE board_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_num);
			deleteCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("deleteArticle() 오류 - " + e.getMessage());
		} finally {
			close(pstmt);
		}
		
		return deleteCount;
		
	}

	// 답글 등록하기
	public int insertReplyArticle(BoardBean article) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int num = 1; // 새로 등록할 게시물 번호
		int insertCount = 0;
		
		int re_ref = article.getBoard_re_ref(); // 참조글 번호
		int re_lev = article.getBoard_re_lev(); // 들여쓰기 레벨
		int re_seq = article.getBoard_re_seq(); // 답글 순서 번호
		
		
		try {
			// 현재 게시물의 최대 번호를 조회하여 새로운 글번호를 결정(+1)
			String sql = "SELECT MAX(board_num) FROM board"; // 가장 큰 번호 조회
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 등록된 게시물이 하나라도 존재할 경우
				num = rs.getInt(1) + 1; // 새 글 번호 = 현재 게시물 가장 큰 번호 + 1
			} 
			
			/*
			 * 답변글 등록을 위한 계산
			 * ----------------------------------------------------------------------------------
			 * 원본글번호(board_num)    새 글 번호(num)    참조글번호(re_ref)    순서번호(re_seq)
			 * ----------------------------------------------------------------------------------
			 *         없음                    1                   1                     0  
			 *           
			 * ex) 1번 게시물에 대한 답변 글 등록 => 등록될 새 글 번호가 100번이라고 가정
			 * => 1번 글에 대한 답글이므로 1번 글 번호를 참조글번호(re_ref)로 사용
			 *    답글이 아닌 본 글이므로 순서번호는 첫 번호인 0번을 사용
			 * ------------------------------------------------------------------------------------------------
			 * 원본글번호(board_num)  새 글 번호(num)    참조글번호(re_ref)  순서번호(re_seq)  들여쓰기(re_lev)
			 * ------------------------------------------------------------------------------------------------
			 *         없음                 1                  1                    0                  0
			 *           1                 100                 1                    1                  1                
			 *    
			 * ex2) 1번 게시물에 대한 답변 글 등록 => 등록될 새 글 번호가 110번이라고 가정
			 * => 1번 글에 대한 답글이므로 1번 글 번호를 참조글번호(re_ref)로 사용
			 *    기존에 답글이 존재하기 때문에 기존 답글의 순서 번호들을 전부 +1 시킴
			 *    => 그리고 새로 추가되는 답글의 순서 번호를 다시 0번으로 지정
			 * ------------------------------------------------------------------------------------------------
			 * 원본글번호(board_num)  새 글 번호(num)    참조글번호(re_ref)  순서번호(re_seq)  들여쓰기(re_lev)
			 * ------------------------------------------------------------------------------------------------
			 *         없음                 1                  1                    0                  0
			 *           1                 100                 1                    2                  1   
			 *           1                 110                 1                    1                  1
			 *           
			 * ex3) 1번 게시물에 대한 답변 글 등록 => 등록될 새 글 번호가 199번이라고 가정
			 * => 1번 글에 대한 답글이므로 1번 글 번호를 참조글번호(re_ref)로 사용
			 *    기존에 답글이 존재하기 때문에 기존 답글의 순서 번호들을 전부 +1 시킴
			 *    => 그리고 새로 추가되는 답글의 순서 번호를 다시 0번으로 지정
			 * ------------------------------------------------------------------------------------------------
			 * 원본글번호(board_num)  새 글 번호(num)    참조글번호(re_ref)  순서번호(re_seq)  들여쓰기(re_lev)
			 * ------------------------------------------------------------------------------------------------
			 *         없음                 1                  1                    0                  0
			 *           1                 100                 1                    3                  1   
			 *           1                 110                 1                    2                  1  
			 *           1                 199                 1                    1                  1 
			 *           
			 * ex4) 110번 게시물에 대한 답변 글 등록 => 등록될 새 글 번호가 200번이라고 가정
			 * => 110번 글에 대한 답글이므로 110번 글 번호를 참조글번호(re_ref)로 사용
			 *    기존에 답글이 존재하기 때문에 기존 답글의 순서 번호들을 전부 +1 시킴
			 *    => 그리고 새로 추가되는 답글의 순서 번호를 다시 0번으로 지정
			 * ------------------------------------------------------------------------------------------------
			 * 원본글번호(board_num)  새 글 번호(num)    참조글번호(re_ref)  순서번호(re_seq)  들여쓰기(re_lev)
			 * ------------------------------------------------------------------------------------------------
			 *         없음                 1                  1                    0                  0
			 *           1                 100                 1                    4                  1   
			 *           1                 110                 1                    2                  1  
			 *           1                 199                 1                    1                  1   
			 *         110                 200               110                    3                  2
			 */
			
			// 기존 답글들에 대한 순서 번호 증가
			sql = "UPDATE board SET board_re_seq=board_re_seq+1 WHERE board_re_ref=? AND board_re_seq>?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, re_ref);
			pstmt.setInt(2, re_seq);
			int updateCount = pstmt.executeUpdate();
			
			// 답글 순서 번호에 대한 업데이트가 성공할 경우(updateCount > 0) commit 수행
			if(updateCount > 0) {
				commit(con);
			} else {
				rollback(con);
			}
			
			re_seq = re_seq + 1; // 새 답글의 순서 번호를 기존 순서번호 + 1 로 지정
			re_lev = re_lev + 1; // 새 답글의 들여쓰기 레벨을 기존 들여쓰기 레벨 + 1 로 지정
			
			// 답변글 INSERT 작업 수행
			sql = "INSERT INTO board VALUES(?,?,?,?,?,?,?,?,?,?,now())";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, article.getBoard_name());
			pstmt.setString(3, article.getBoard_pass());
			pstmt.setString(4, article.getBoard_subject());
			pstmt.setString(5, article.getBoard_content());
			pstmt.setString(6, "");
			pstmt.setInt(7, re_ref);
			pstmt.setInt(8, re_lev);
			pstmt.setInt(9, re_seq);
			pstmt.setInt(10, 0);
			
			insertCount = pstmt.executeUpdate(); // INSERT 실행 결과 값을 int형 변수로 저장
			
		} catch (SQLException e) {
			System.out.println("insertReplyArticle() 오류 - " + e.getMessage());
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return insertCount;
	}
	
	
}





















