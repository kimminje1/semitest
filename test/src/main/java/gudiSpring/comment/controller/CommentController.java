package gudiSpring.comment.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import gudiSpring.comment.dao.CommentDao;
import gudiSpring.comment.dto.CommentDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/comment/list")
public class CommentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int contentNo = Integer.parseInt(req.getParameter("contentNo"));
        int recordsPerPage = 10;  // 한 페이지에 표시할 댓글 수
        int page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;
        int startRow = (page - 1) * recordsPerPage + 1;

        Connection conn = null;
        try {
            ServletContext sc = this.getServletContext();
            conn = (Connection) sc.getAttribute("conn");

            CommentDao commentDao = new CommentDao();
            commentDao.setConnection(conn);
            
            List<CommentDto> commentList = commentDao.getCommentsByContentNo(contentNo, startRow, recordsPerPage);
            int totalRecords = commentDao.getTotalCommentsByContentNo(contentNo);
            int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

            req.setAttribute("commentList", commentList);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("currentPage", page);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/board/reviewboard/commentList.jsp");
            dispatcher.forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("댓글 조회 중 오류 발생", e);
        }
    }

    // 다른 POST 관련 메소드 (addComment, deleteComment 등)
}
