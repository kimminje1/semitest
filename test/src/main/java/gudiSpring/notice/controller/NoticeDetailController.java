package gudiSpring.notice.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import gudiSpring.notice.dao.NoticeDao;
import gudiSpring.notice.dto.NoticeDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/noticeDetail")
public class NoticeDetailController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		  String contentNoParam = req.getParameter("	");
		    System.out.println("Received contentNoParam: '" + contentNoParam + "'"); // 디버깅을 위해 출력
		    // 나머지 코드  
		
		int contentNo = Integer.parseInt(req.getParameter("contentNo"));
		   	System.out.println(contentNo);
	        Connection conn = (Connection) getServletContext().getAttribute("conn");
	        NoticeDao noticeDao = new NoticeDao();
	        noticeDao.setConnection(conn);

	        NoticeDto notice = noticeDao.getNoticeById(contentNo);

	        req.setAttribute("notice", notice);

	    req.getRequestDispatcher("/jsp/notice/noticeDetail.jsp").forward(req, res);
	    }
	

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}



}



