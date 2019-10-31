package svc;

import java.sql.Connection;

import dao.MemberDAO;

import static db.JdbcUtil.*;

public class MemberLoginProService {

	public int memberLogin(String id, String passwd) {
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);
		
		// MemberDAO 의 selectMemberLogin() 메서드를 호출하여 로그인 수행
		// 파라미터 : id, passwd    리턴타입 : int(loginResult)
		int loginResult = memberDAO.selectMemberLogin(id, passwd);
		
		close(con);
		
		return loginResult;
	}

}
