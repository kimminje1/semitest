package gudiSpring.board.controller.reviewboard;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import gudiSpring.board.dao.reviewboard.ReviewBoardDao;
import gudiSpring.comment.dao.CommentDao;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/board/reviewboard/delete")
public class ReviewDeletePostController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse res) 
					throws ServletException, IOException {
		  int contentNo = Integer.parseInt(req.getParameter("contentNo"));

	        Connection conn = null;
	        try {
	            ServletContext sc = this.getServletContext();
	            conn = (Connection) sc.getAttribute("conn");
	            
	            
	            // 댓글 먼저 삭제
				CommentDao commentDao = new CommentDao();
				commentDao.setConnection(conn);
				commentDao.deleteCommentsByContentNo(contentNo);

	            // 파일 경로 가져오기
	            ReviewBoardDao boardDao = new ReviewBoardDao();
	            boardDao.setConnection(conn);
	            List<String> filePaths = boardDao.getFilePathsByContentNo(contentNo);
	            System.out.println("filePaths"+filePaths);
	            
	            // 파일 삭제
	            for (String filePath : filePaths) {
	                File file = new File("D:/GudiSpring/img/" + filePath); // 경로 수정 필요
	                if (file.exists() && file.isFile()) {
	                    file.delete();
	                }
	            }
	           
	            //게시글삭제+이미지삭제

	            boardDao.deletePost(contentNo);

	            res.sendRedirect(req.getContextPath() + "/board/reviewboard/list");
	}catch (Exception e) {
        throw new ServletException("게시글 삭제 중 오류 발생", e);
    }
	        
	

}//doget
    
@Override
protected void doPost(HttpServletRequest req, 
		HttpServletResponse res) throws ServletException, IOException {
	// TODO Auto-generated method stub
	super.doPost(req, res);
}
}//전체종료
