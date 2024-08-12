package gudiSpring.reviewboard.controller;

import java.io.IOException;
import java.sql.Connection;

import gudiSpring.reviewboard.dao.ReviewBoardDao;
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

	            ReviewBoardDao boardDao = new ReviewBoardDao();
	            boardDao.setConnection(conn);
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
