package gudiSpring.freeboard.controller;

import java.io.IOException;
import java.sql.Connection;

import gudiSpring.freeboard.dao.BoardDao;
import gudiSpring.freeboard.dto.BoardDto;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/freeboard/edit")
public class EditBoardController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        int contentNo = Integer.parseInt(req.getParameter("contentNo"));
        
        Connection conn = null;
        try {
            ServletContext sc = this.getServletContext();
            conn = (Connection) sc.getAttribute("conn");

            BoardDao boardDao = new BoardDao();
            boardDao.setConnection(conn);
            BoardDto boardDto = boardDao.selectOne(contentNo);

            req.setAttribute("boardDto", boardDto);
            req.getRequestDispatcher("/jsp/freeBoard/editFreeBoardForm.jsp").forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("게시글 수정 중 오류 발생", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        
    	  // 파일 업로드 기능이 아직 구현되지 않은 경우 기본값 설정
        String contentFile = "default_file"; // 또는 미리 정의된 파일명이나 경로
    	
    	int contentNo = Integer.parseInt(req.getParameter("contentNo"));
        String contentSubject = req.getParameter("contentSubject");
        String contentText = req.getParameter("contentText");
//       String contentFile = req.getParameter("contentFile"); // 파일 업로드 기능을 추가할 수 있습니다.
        	
        
        
        BoardDto boardDto = new BoardDto();
        boardDto.setContentNo(contentNo);
        boardDto.setContentSubject(contentSubject);
        boardDto.setContentText(contentText);
        boardDto.setContentFile(contentFile);  // 파일 정보 처리

        Connection conn = null;
        try {
            ServletContext sc = this.getServletContext();
            conn = (Connection) sc.getAttribute("conn");

            BoardDao boardDao = new BoardDao();
            boardDao.setConnection(conn);
            boardDao.updateBoard(boardDto);

            res.sendRedirect(req.getContextPath() + "/freeboardList");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("게시글 수정 중 오류 발생", e);
        }
    }
}
