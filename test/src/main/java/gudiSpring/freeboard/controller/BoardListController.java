package gudiSpring.freeboard.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gudiSpring.freeboard.dao.BoardDao;
import gudiSpring.freeboard.dto.BoardDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/freeboardList")
public class BoardListController extends HttpServlet {
   
    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
    		throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	   Connection conn = null;
    	   
    	   try {ServletContext sc = this.getServletContext();
   		
			// 미리 준비된 DB 객체 불러오기
			conn = (Connection)sc.getAttribute("conn");
			
			BoardDao boardDao = new BoardDao();
			boardDao.setConnection(conn);
			
			  // 게시글 목록 조회
            ArrayList<BoardDto> boardList = (ArrayList<BoardDto>) boardDao.selectList();
            req.setAttribute("boardList", boardList);
			
            // JSP 페이지로 포워딩
            res.setContentType("text/html");
            res.setCharacterEncoding("UTF-8");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/board/boardListView.jsp");
            dispatcher.forward(req, res);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
    	   
    	   
    }
    

  
    
    
    
    
    
    
    
    
  }
