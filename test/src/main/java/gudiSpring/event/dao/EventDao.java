package gudiSpring.event.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gudiSpring.event.dto.EventDto;

public class EventDao {
	Connection connection;
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	// Event 전체 조회
	public List<EventDto> selectEventList() {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<EventDto> eventList = new ArrayList<>();
		
		try {
			String sql = "";
			sql += "SELECT E.EVENT_NO, E.EVENT_NAME";
			sql += " , TO_CHAR(E.OPEN_DATE, 'YYYY\"년 \"MM\"월 \"DD\"일\" HH24:MI:SS') AS FM_OPEN_DATE";
			sql += " , TO_CHAR(E.CLOSE_DATE, 'YYYY\"년 \"MM\"월 \"DD\"일\" HH24:MI:SS') AS FM_CLOSE_DATE";
			sql += " , U.NAME, U.NICKNAME";
			sql += " FROM EVENT E, USER_INFO U";
			sql += " WHERE E.USER_NO = U.USER_NO";
			
			pstmt = connection.prepareStatement(sql);

			// db에 sql문 전달, 실행
			rs = pstmt.executeQuery();
			
			int eventNo = 0;
			String eventName = "";
			String openDate = "";
			String closeDate = "";
			String userName = "";
			String userNickname = "";	
			
			EventDto eventDto = null;
			
			while(rs.next()) {
				eventNo = rs.getInt("EVENT_NO"); 
				eventName = rs.getString("EVENT_NAME");
				openDate = rs.getString("FM_OPEN_DATE");
				closeDate = rs.getString("FM_CLOSE_DATE");
				userName = rs.getString("NAME");
				userNickname = rs.getString("NICKNAME");
				
				eventDto = new EventDto(eventNo, eventName, openDate, closeDate, userName, userNickname);
				
				eventList.add(eventDto);
			}
			
			return eventList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally 종료
		
		return eventList;
	}
	
	// event count
	public int eventTotalCount() {
		int totalCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "";
			sql += "SELECT COUNT(FROM EVENT_NO) AS TOTAL_COUNT";
			sql += " FROM FROM EVENT";
			
			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();
			
			totalCount = rs.getInt("TOTAL_COUNT");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally 종료
		
		return totalCount;
	}
	
	// Event search
	public List<EventDto> searchEventList(String param) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<EventDto> eventList = new ArrayList<>();
		
		try {
			String sql = "";
			sql += "SELECT E.EVENT_NO, E.EVENT_NAME";
			sql += " , TO_CHAR(E.OPEN_DATE, 'YYYY\"년 \"MM\"월 \"DD\"일\" HH24:MI:SS') AS FM_OPEN_DATE";
			sql += " , TO_CHAR(E.CLOSE_DATE, 'YYYY\"년 \"MM\"월 \"DD\"일\" HH24:MI:SS') AS FM_CLOSE_DATE";
			sql += " , U.NAME, U.NICKNAME";
			sql += " FROM EVENT E, USER_INFO U";
			sql += " WHERE E.USER_NO = U.USER_NO";
			sql += " AND EVENT_NAME = ?";
			
			pstmt = connection.prepareStatement(sql);
			
			int colIndex = 1;
			
			pstmt.setString(colIndex, param);
			
			rs = pstmt.executeQuery();
			
			int eventNo = 0;
			String eventName = "";
			String openDate = "";
			String closeDate = "";
			String userName = "";
			String userNickname = "";	
			
			EventDto eventDto = null;
			
			while(rs.next()) {
				eventNo = rs.getInt("EVENT_NO"); 
				eventName = rs.getString("EVENT_NAME");
				openDate = rs.getString("FM_OPEN_DATE");
				closeDate = rs.getString("FM_CLOSE_DATE");
				userName = rs.getString("NAME");
				userNickname = rs.getString("NICKNAME");
				
				eventDto = new EventDto(eventNo, eventName, openDate, closeDate, userName, userNickname);
				
				eventList.add(eventDto);
			}
			
			return eventList;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally 종료
		
		return eventList;
	}
	
	// 이벤트 추가
	public int eventInsert(EventDto eventDto) {
		int result = 0;
		
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	// 이벤트 삭제 => 여러 개
	public int deleteEvents(ArrayList<Integer> removeEventNoList) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		try {
			String sql = "";
			
			for (int i = 0; i < removeEventNoList.size(); i++) {
				sql += "DELETE FROM EVENT";
				sql += " WHERE EVENT_NO = ?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setInt(1, removeEventNoList.get(i));
			}
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally 종료
		
		
		return result;
	}
}
