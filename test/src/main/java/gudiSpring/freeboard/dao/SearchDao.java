package gudiSpring.freeboard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gudiSpring.freeboard.dto.FreeBoardDto;
import gudiSpring.freeboard.dto.MemberFreeBoardDto;
import gudiSpring.user.dto.MemberDto;


public class SearchDao {
	private Connection connection;
	public SearchDao() {
		// 기본 생성자 구현
	}

		// Connection을 매개변수로 받는 생성자
		public SearchDao(Connection connection) {
			this.connection = connection;
		}

	private ResultSet rs;//정보를 담을 수 있는 객체	
	

	public void setConnection(Connection conn) {
		this.connection = conn;
	}
	public ArrayList<FreeBoardDto> getSearch(String searchField, String searchText){//특정한 리스트를 받아서 반환
		
		ArrayList<FreeBoardDto> list = new ArrayList<FreeBoardDto>();
		// 매핑 처리
		 String column = "";
	    switch (searchField) {
	        case "FREE_BOARD_TITLE":
	            column = "FREE_BOARD_TITLE";
	            break;
	        case "FREE_BOARD_WRITER":
	            column = "FREE_BOARD_WRITER";
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid search field");
	        
	    }
		String SQL = "SELECT * FROM Free_Board";
		 // 검색 조건이 있을 때 쿼리 수정
	    if (searchField != null && !searchField.trim().isEmpty() && searchText != null && !searchText.trim().isEmpty()) {
	        // WHERE 조건과 LIKE 연산자 추가
	        SQL += " WHERE " + column  + " LIKE ? ORDER BY FREE_BOARD_ID DESC";
	    } else {
	        // ORDER BY만 있는 경우
	        SQL += " ORDER BY FREE_BOARD_ID DESC";
	    }

	    try (PreparedStatement pstmt = connection.prepareStatement(SQL)) {
	        if (searchField != null && !searchField.trim().isEmpty() && searchText != null && !searchText.trim().isEmpty()) {
	            // 검색 텍스트 설정
	            pstmt.setString(1, "%" + searchText.trim() + "%");
	        }
	       
	        ResultSet rs = pstmt.executeQuery();
		    // ResultSet 처리
				
				while(rs.next()) {
	            FreeBoardDto freeBoardDto = new FreeBoardDto();
	           
	            freeBoardDto.setFreeBoardId(rs.getInt("FREE_BOARD_ID")); 
	            // Assuming column names match
	            freeBoardDto.setMemberNo(rs.getInt("MEMBER_NO"));
	            freeBoardDto.setFreeBoardTitle(rs.getString("FREE_BOARD_TITLE"));
	            freeBoardDto.setFreeBoardContent(rs.getString("FREE_BOARD_CONTENT"));
	            freeBoardDto.setFreeBoardWriter(rs.getString("FREE_BOARD_WRITER"));
	            freeBoardDto.setCreateDate(rs.getDate("CREATE_DATE"));
	            // Use rs.getDate() for Date type
	            list.add(freeBoardDto);//list에 해당 인스턴스를 담는다.
	         }         
	      } catch(SQLException e) {
	         e.printStackTrace();
	      }
	      return list;//ㄱㅔ시글 리스트 반환
	   }
}
