package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import vo.MemberBean;

import static db.JdbcUtil.*;

public class MemberDAO {
	private static MemberDAO instance = new MemberDAO();
	private Connection con = null;
	
	private MemberDAO() {}

	public static MemberDAO getInstance() {
		return instance;
	}

	public void setConnection(Connection con) {
		this.con = con;
	}

	public int selectMemberLogin(String id, String passwd) {
		/*
		 * 전달받은 id 와 passwd 를 사용하여 일치 여부 확인
		 * 1. id 가 존재하는지 확인 
		 *    => 존재하지 않을 경우 loginResult = 0 으로 변경
		 * 2. id 가 존재할 경우 passwd 일치 여부 확인
		 *    => id 에 해당하는 passwd 를 조회하여 일치하지 않을 경우 loginResult = -1 로 변경
		 *       아니면 loginResult = 1 로 변경
		 * 3. loginResult 리턴
		 */
		int loginResult = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			// 1. ID 조회
			String sql = "SELECT id FROM member WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // ID 가 존재할 경우
				// 2. 패스워드 조회
				sql = "SELECT passwd FROM member WHERE id=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				
				// 조회된 패스워드 있을 경우
				// 패스워드가 전달받은 패스워드와 동일하면 loginResult = 1, 아니면 -1
				if(rs.next()) {
					if(passwd.equals(rs.getString("passwd"))) {
						loginResult = 1;
					} else {
						loginResult = -1;
					}
				}
				
			}
			
			// ID가 존재하지 않을 경우 else 문을 통해 loginResult = 0 수정해야하지만, 기본값이 0 이므로 생략
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pstmt);
		}
		
		return loginResult;
	}

	// 회원 추가
	public int insertMember(MemberBean member) {
		int insertCount = 0;
		
		PreparedStatement pstmt = null;
		
		// MemberBean 에 저장된 이름, 성별, 나이, E-Mail, 아이디, 패스워드를 member 테이블에 추가
		// => 결과값을 insertCount 에 저장 후 리턴
		String sql = "INSERT INTO member VALUES (null,?,?,?,?,?,?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getGender());
			pstmt.setInt(3, member.getAge());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getId());
			pstmt.setString(6, member.getPasswd());
			
			insertCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return insertCount;
	}
	
	
	
	
}























