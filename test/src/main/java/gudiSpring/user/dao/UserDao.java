package gudiSpring.user.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import gudiSpring.user.dto.UserDto;

public class UserDao {
	private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    //회원등록
    public int userInsert(UserDto userDto) throws Exception {
		int result = 0;
		PreparedStatement pstmt = null;
		
		try {
			String email = userDto.getEmail();
			String pwd = userDto.getPassword();
			String memberName = userDto.getMemberName();
			
			String sql = "";
			
			sql = "INSERT INTO MEMBER";
			sql += "(MEMBER_NO, EMAIL, PWD, MEMBER_NAME, CRE_DATE, MOD_DATE)";
			sql += "VALUES(MEMBER_NO_SEQ.NEXTVAL, ?, ?, ?, SYSDATE, SYSDATE)";
			
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, email);
			pstmt.setString(2, pwd);
			pstmt.setString(3, memberName);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("UserDto UserDtoinsert() 예외 발생");
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} // finally 종료
		
		return result;
	}
    
    
    
    
}//dao종료
