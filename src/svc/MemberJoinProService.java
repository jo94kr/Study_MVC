package svc;

import static db.JdbcUtil.*;

import java.sql.Connection;

import dao.MemberDAO;
import vo.MemberBean;

public class MemberJoinProService {

	public boolean registMember(MemberBean member) {
		boolean isRegistSuccess = false; 
		
		Connection con = getConnection();
		MemberDAO memberDAO = MemberDAO.getInstance();
		memberDAO.setConnection(con);

		int insertCount = memberDAO.insertMember(member);
		
		if(insertCount > 0) {
			commit(con);
			isRegistSuccess = true;
		} else {
			rollback(con);
		}
		
		close(con);
		
		return isRegistSuccess;
	}
	
}
















