package gudiSpring.freeboard.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import gudiSpring.freeboard.dao.BoardDao;
import gudiSpring.freeboard.dto.BoardDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@SuppressWarnings("serial")
//@WebServlet("/freeboard/add")
public class AddBoardController2 extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		//작성폼으로
		RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/freeBoard/newFreeBoardForm.jsp");
        dispatcher.forward(req, res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 // 폼에서 데이터 받아오기
        String subject = req.getParameter("contentSubject");
        String text = req.getParameter("contentText");
        String file = "default-file.txt";  // 파일 업로드 미구현, 기본값으로 처리
        int userNo = 2; // 임시로 사용자 번호 설정

        // BoardDto 객체 생성 및 데이터 설정
        BoardDto boardDto = new BoardDto();
        boardDto.setContentSubject(subject);
        boardDto.setContentText(text);
        boardDto.setContentFile(file);
        boardDto.setUserNo(userNo); // 사용자 번호 설정

        Connection conn = null;
        try {
            ServletContext sc = this.getServletContext();
            conn = (Connection) sc.getAttribute("conn");

            BoardDao boardDao = new BoardDao();
            boardDao.setConnection(conn);
            boardDao.addBoard(boardDto);

            // 게시글 목록 페이지로 리다이렉트
            res.sendRedirect(req.getContextPath() + "/freeboardList");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // 데이터베이스 관련 예외 처리
	            throw new ServletException("게시글 추가 중 데이터베이스 오류 발생", e);
	        } catch (Exception e) {
	            e.printStackTrace();
	            // 일반적인 예외 처리
	            throw new ServletException("게시글 추가 중 오류 발생", e);
	        }
	    }
	}

