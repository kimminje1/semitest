package gudiSpring.user.controller;

import java.io.IOException;
import java.sql.Connection;

import gudiSpring.user.dao.UserDao;
import gudiSpring.user.dto.UserDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(value = "/auth/signin")
public class UserSigninController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/auth/signinView.jsp");

		dispatcher.forward(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		Connection conn = null;

		try {

			ServletContext sc = this.getServletContext();

			conn = (Connection) sc.getAttribute("conn");
			UserDao userDao = new UserDao();
			userDao.setConnection(conn);

			String id = req.getParameter("id");
			String password = req.getParameter("password");

			UserDto userDto;

			userDto = userDao.userLogin(id, password);

			if (userDto == null) {
				  // 로그인 실패 시 처리 임시코드
                req.setAttribute("errorMessage", "Invalid username or password.");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/auth/signinView.jsp");
                dispatcher.forward(req, res);
                return; // 더 이상 실행하지 않음
			} else {
				System.out.println("성공");
			}

			HttpSession session = req.getSession();
			if (session == null) {
                throw new ServletException("세션을 생성할 수 없습니다.");
            } //임시코드
			session.setAttribute("userDto", userDto);

//			RequestDispatcher dispatcher = req.getRequestDispatcher("./index.jsp");
//			
//			dispatcher.forward(req, res);
			res.sendRedirect(req.getContextPath()+"/board/reviewboard/list");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
