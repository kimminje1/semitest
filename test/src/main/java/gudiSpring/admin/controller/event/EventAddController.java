package gudiSpring.admin.controller.event;

import java.io.IOException;
import java.sql.Connection;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "/admin/event/add")
public class EventAddController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/admin/event/eventAddView.jsp");

		dispatcher.forward(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Connection conn = null;
		
		try {
			
			String eventName = req.getParameter("eventName");
			String openDate = req.getParameter("openDate");
			String closeDate = req.getParameter("closeDate");
			
			System.out.println(eventName);
			System.out.println(openDate);
			System.out.println(closeDate);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/error/e500.jsp");
			
			dispatcher.forward(req, res);
		}
	}

}
