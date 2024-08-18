package gudiSpring.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import gudiSpring.admin.dto.AdminDto;
import gudiSpring.board.dto.reviewboard.ReviewBoardDto;
import gudiSpring.user.dto.UserDto;

public class AdminDao {
	private Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// 관리자 존재유무
	public AdminDto adminExist(String id, String pwd) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			String sql = "";
			sql += "SELECT USER_NO, NAME, NICKNAME, ID, PASSWORD, PHONE";
			sql += " , TO_CHAR(CREATED_DATE, 'YYYY\"년\"MM\"월\"DD\"일\" HH24:MI:SS') AS FM_CRE_DATE";
			sql += " , TO_CHAR(UPDATE_DATE, 'YYYY\"년\"MM\"월\"DD\"일\" HH24:MI:SS') AS FM_UP_DATE";
			sql += " , AUTHORITY";
			sql += " FROM USER_INFO";
			sql += " WHERE ID = ?";
			sql += " AND PASSWORD = ?";

			pstmt = connection.prepareStatement(sql);

			int colIndex = 1;

			pstmt.setString(colIndex++, id);
			pstmt.setString(colIndex, pwd);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int userNo = rs.getInt("USER_NO");
				id = rs.getString("ID");
				pwd = rs.getString("PASSWORD");
				String name = rs.getString("NAME");
				String nickName = rs.getString("NICKNAME");
				String phone = rs.getString("PHONE");
				String cerDate = rs.getString("FM_CRE_DATE");
				String upDate = rs.getString("FM_UP_DATE");
				String authority = rs.getString("AUTHORITY");

				AdminDto adminDto = new AdminDto(userNo, id, pwd, name, nickName, phone, cerDate, upDate, authority);

				return adminDto;
			}

		} catch (Exception e) {
			// TODO: handle exception
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

		return null;
	}

	// admin count
	public int adminTotalCount() {
		int totalCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "";
			sql += "SELECT COUNT(USER_NO) AS TOTAL_COUNT";
			sql += " FROM USER_INFO";
			sql += " WHERE AUTHORITY = 'admin'";
			
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
	
	// footer 가이드라인 조회
	public List<String> selectFooterList(String id, String pwd) {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			String sql = "";
			sql += "";
			sql += " ";
			sql += " ";

			pstmt = connection.prepareStatement(sql);

			rs = pstmt.executeQuery();

			if (rs.next()) {

			} else {

			}

		} catch (Exception e) {
			// TODO: handle exception

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally 종료

		return null;
	}
	
	 // 전체 조회 (페이징 적용)
    public List<ReviewBoardDto> selectList(int startRow, int pageSize) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "";
            sql += "SELECT rnum, CONTENT_NO, CONTENT_SUBJECT, CONTENT_TEXT, ";
            sql += "CONTENT_BOARD_INFO_NO, CONTENT_CRE_DATE, CONTENT_UPDATE_DATE, USER_NO, NICKNAME, BOARD_INFO_NAME FROM ( ";
            sql += "   SELECT ROWNUM AS rnum, a.CONTENT_NO, a.CONTENT_SUBJECT, a.CONTENT_TEXT, ";
            sql += "   a.CONTENT_BOARD_INFO_NO, a.CONTENT_CRE_DATE, a.CONTENT_UPDATE_DATE, a.USER_NO, u.NICKNAME, b.BOARD_INFO_NAME FROM ( ";
            sql += "       SELECT CONTENT_NO, CONTENT_SUBJECT, CONTENT_TEXT, ";
            sql += "       CONTENT_BOARD_INFO_NO, CONTENT_CRE_DATE, CONTENT_UPDATE_DATE, USER_NO ";
            sql += "       FROM BOARD_CONTENT ";
            sql += "       ORDER BY CONTENT_NO DESC ";
            sql += "   ) a ";
            sql += "   LEFT JOIN USER_INFO u ON a.USER_NO = u.USER_NO ";
            sql += "   LEFT JOIN BOARD_INFO b ON a.CONTENT_BOARD_INFO_NO = b.BOARD_INFO_NO ";  // 추가된 JOIN
            sql += "   WHERE ROWNUM <= ? ";  // endRow
            sql += ") ";
            sql += "WHERE rnum >= ?";  // startRow

            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, startRow + pageSize - 1);  // endRow 계산
            pstmt.setInt(2, startRow);  // startRow 지정
            
            rs = pstmt.executeQuery();

            List<ReviewBoardDto> boardList = new ArrayList<>();
            while (rs.next()) {
                int contentNo = rs.getInt("CONTENT_NO");
                String contentSubject = rs.getString("CONTENT_SUBJECT");
                String contentText = rs.getString("CONTENT_TEXT");
                int contentBoardInfoNo = rs.getInt("CONTENT_BOARD_INFO_NO");
                Date contentCreDate = rs.getDate("CONTENT_CRE_DATE");
                Date contentUpdateDate = rs.getDate("CONTENT_UPDATE_DATE");
                int userNo = rs.getInt("USER_NO");
                String nickname = rs.getString("NICKNAME");  // USER_INFO 테이블에서 가져온 닉네임
                String boardInfoName = rs.getString("BOARD_INFO_NAME");
                
                ReviewBoardDto boardDto = new ReviewBoardDto(
                    contentNo, contentSubject, contentText,
                    contentBoardInfoNo, contentCreDate, contentUpdateDate, userNo, nickname, boardInfoName
                );
                boardList.add(boardDto);
            }

            return boardList;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 게시글 총 개수 조회 (페이징을 위해 필요)
    public int getTotalCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM BOARD_CONTENT";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public ReviewBoardDto selectDetail(int contentNo) 
    		throws SQLException {
    	String sql = "SELECT bc.CONTENT_NO, bc.CONTENT_SUBJECT, bc.CONTENT_TEXT, bc.CONTENT_FILE, " +
                "bc.CONTENT_BOARD_INFO_NO, bc.CONTENT_CRE_DATE, bc.CONTENT_UPDATE_DATE, bc.USER_NO, " +
                "ui.NICKNAME, bi.BOARD_INFO_NAME " +
                "FROM BOARD_CONTENT bc " +
                "JOIN USER_INFO ui ON bc.USER_NO = ui.USER_NO " +
                "JOIN BOARD_INFO bi ON bc.CONTENT_BOARD_INFO_NO = bi.BOARD_INFO_NO " +
                "WHERE bc.CONTENT_NO = ?";
   PreparedStatement pstmt = null;
   ResultSet rs = null;
   ReviewBoardDto boardDetail = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, contentNo);
            rs = pstmt.executeQuery();

            if (rs.next()) {
            	 String contentText = rs.getString("CONTENT_TEXT");
                int contentBoardInfoNo = rs.getInt("CONTENT_BOARD_INFO_NO");
                String contentSubject = rs.getString("CONTENT_SUBJECT");
                String contentFile = rs.getString("CONTENT_FILE");  // 파일 정보 추가
                String nickname = rs.getString("NICKNAME");
                List<String> contentFiles = contentFile != null ? Arrays.asList(contentFile.split(",")) : null; // 파일 리스트로 변환
                String boardInfoName = rs.getString("BOARD_INFO_NAME");
                Date contentCreDate = rs.getDate("CONTENT_CRE_DATE");
                Date contentUpdateDate = rs.getDate("CONTENT_UPDATE_DATE");
                int userNo = rs.getInt("USER_NO");

                boardDetail= new ReviewBoardDto(contentNo, 
                		contentSubject, contentText, contentFiles,
                contentBoardInfoNo, contentCreDate, contentUpdateDate, userNo, nickname, boardInfoName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } 
        return boardDetail;
    }
    public void deletePost(int contentNo) throws SQLException {
        // 1. BOARD_CONTENT_IMG 테이블에서 해당 게시글 번호에 연결된 이미지를 삭제
        String deleteImagesSql = "DELETE FROM BOARD_CONTENT_IMG WHERE CONTENT_NO = ?";
        
        // 2. BOARD_CONTENT 테이블에서 게시글을 삭제
        String deletePostSql = "DELETE FROM BOARD_CONTENT WHERE CONTENT_NO = ?";

        try (
            PreparedStatement imgPstmt = connection.prepareStatement(deleteImagesSql);
            PreparedStatement postPstmt = connection.prepareStatement(deletePostSql)) {
            
            // 이미지 삭제
            imgPstmt.setInt(1, contentNo);
            imgPstmt.executeUpdate();

            // 게시글 삭제
            postPstmt.setInt(1, contentNo);
            postPstmt.executeUpdate();
        }
    }
 // 게시글에 첨부된 파일 경로를 가져오는 메서드
 	public List<String> getFilePathsByContentNo(int contentNo) throws SQLException {
 	    String sql = "SELECT CONTENT_FILE FROM BOARD_CONTENT WHERE CONTENT_NO = ?";
 	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
 	        pstmt.setInt(1, contentNo);
 	        try (ResultSet rs = pstmt.executeQuery()) {
 	            if (rs.next()) {
 	                String contentFile = rs.getString("CONTENT_FILE");
 	                return contentFile != null ? Arrays.asList(contentFile.split(",")) : new ArrayList<>();
 	            }
 	        }
 	    }
 	    return new ArrayList<>();
 	}
}
