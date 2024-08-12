package gudiSpring.comment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gudiSpring.comment.dto.CommentDto;

public class CommentDao {

	private Connection connection;

	public void setConnection(Connection conn) {
		this.connection = conn;
	}

	// 댓글 추가
	public void addComment(CommentDto commentDto) throws SQLException {
		 String sql = "INSERT INTO BOARD_CONTENT_COMMENT "
		 		+ "(COMMENT_NO, CONTENT_NO, CONTENT_COMMENT, "
		 		+ "COMMENT_CRE_DATE, "
		 		+ "COMMENT_UPDATE_DATE) "
	            + "VALUES (COMMENT_NO_SEQ.NEXTVAL, "
	            + "?, ?, SYSDATE, SYSDATE)";
	    try (
	    	PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, commentDto.getContentNo());
	        pstmt.setString(2, commentDto.getContentComment());
	       
	        pstmt.executeUpdate();
	    }
	}

	// 댓글 삭제
	public void deleteComment(int commentNo) throws SQLException {
		String sql = "DELETE FROM "
				+ "BOARD_CONTENT_COMMENT "
				+ "WHERE COMMENT_NO = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
			pstmt.setInt(1, commentNo);
			pstmt.executeUpdate();
		}
	}

	//댓글수정해야함
	public void updateComment(CommentDto commentDto) throws SQLException {
	    String sql = "UPDATE BOARD_CONTENT_COMMENT "
	               + "SET CONTENT_COMMENT = ?, COMMENT_UPDATE_DATE = SYSDATE "
	               + "WHERE COMMENT_NO = ?";

	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setString(1, commentDto.getContentComment());
	        pstmt.setInt(2, commentDto.getCommentNo());
	        pstmt.executeUpdate();
	    }
	}
	// 댓글조회완료
	public List<CommentDto> getCommentsByContentNo(int contentNo) 
			throws SQLException {
		String sql = "SELECT COMMENT_NO, CONTENT_NO, CONTENT_COMMENT, COMMENT_CRE_DATE, COMMENT_UPDATE_DATE " +
                "FROM BOARD_CONTENT_COMMENT " +
                "WHERE CONTENT_NO = ? " +
                "ORDER BY COMMENT_CRE_DATE ASC";
			List<CommentDto> comments = new ArrayList<>();
			PreparedStatement pstmt = connection.prepareStatement(sql);
			ResultSet rs = null;
		try {
			
				
			pstmt.setInt(1, contentNo);
			
			
			rs = pstmt.executeQuery();

			
			 while (rs.next()) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentNo(rs.getInt("COMMENT_NO"));
        commentDto.setContentNo(rs.getInt("CONTENT_NO"));
        commentDto.setContentComment(rs.getString("CONTENT_COMMENT"));
        commentDto.setCommentCreDate(rs.getDate("COMMENT_CRE_DATE"));
        commentDto.setCommentUpdateDate(rs.getDate("COMMENT_UPDATE_DATE"));
       
                comments.add(commentDto);
		 }//while  
		
			
		

			return comments;
		}finally {
			// 자원 해제
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
		}//finally해제
	}
	  
	public List<CommentDto> getCommentsByContentNo(int contentNo, int startRow, int pageSize) throws SQLException {
	    String sql = "SELECT * FROM ( "
	            + "   SELECT ROWNUM AS rnum, a.* FROM ( "
	            + "       SELECT COMMENT_NO, CONTENT_NO, CONTENT_COMMENT, COMMENT_CRE_DATE, COMMENT_UPDATE_DATE "
	            + "       FROM BOARD_CONTENT_COMMENT "
	            + "       WHERE CONTENT_NO = ? "
	            + "       ORDER BY COMMENT_CRE_DATE ASC "
	            + "   ) a "
	            + "   WHERE ROWNUM <= ? "  // endRow
	            + ") "
	            + "WHERE rnum >= ?";  // startRow

	    List<CommentDto> comments = new ArrayList<>();
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, contentNo);
	        pstmt.setInt(2, startRow + pageSize - 1);  // endRow 계산
	        pstmt.setInt(3, startRow);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                CommentDto commentDto = new CommentDto();
	                commentDto.setCommentNo(rs.getInt("COMMENT_NO"));
	                commentDto.setContentNo(rs.getInt("CONTENT_NO"));
	                commentDto.setContentComment(rs.getString("CONTENT_COMMENT"));
	                commentDto.setCommentCreDate(rs.getDate("COMMENT_CRE_DATE"));
	                commentDto.setCommentUpdateDate(rs.getDate("COMMENT_UPDATE_DATE"));
	                comments.add(commentDto);
	            }
	        }
	    }
	    return comments;
	}
	public int getTotalComments(int contentNo) throws SQLException {
	    String sql = "SELECT COUNT(*) FROM BOARD_CONTENT_COMMENT WHERE CONTENT_NO = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	        pstmt.setInt(1, contentNo);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1);  // 총 댓글 수 반환
	            }
	        }
	    }
	    return 0;  // 댓글이 없는 경우 0을 반환
	}
	
	// 게시글에 연관된 모든 댓글 삭제
		public void deleteCommentsByContentNo(int contentNo) throws SQLException {
			String sql = "DELETE FROM BOARD_CONTENT_COMMENT WHERE CONTENT_NO = ?";
			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				pstmt.setInt(1, contentNo);
				pstmt.executeUpdate();
			}
		}

   
}//dao
