package gudiSpring.comment.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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

//무한스크롤구현
@WebServlet("/loadComments")
public class LoadCommentsServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException {
        int contentNo = Integer.parseInt(req.getParameter("contentNo"));
        int page = Integer.parseInt(req.getParameter("page"));
        int commentsPerPage = 10; // 한 번에 불러올 댓글 수

        int startRow = (page - 1) * commentsPerPage + 1;
        int endRow = page * commentsPerPage;

        Connection conn = null;
        List<CommentDto> commentList = new ArrayList<>();
        try {
            ServletContext sc = this.getServletContext();
            conn = (Connection) sc.getAttribute("conn");

            CommentDao commentDao = new CommentDao();
            commentDao.setConnection(conn);
            commentList = commentDao.getCommentsByContentNo(contentNo, startRow, endRow);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } 

        req.setAttribute("commentList", commentList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/commentPartial.jsp");
        dispatcher.forward(req, res);
    }
}
