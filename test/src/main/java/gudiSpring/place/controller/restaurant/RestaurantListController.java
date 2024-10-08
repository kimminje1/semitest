package gudiSpring.place.controller.restaurant;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import gudiSpring.place.dao.restaurant.RestaurantDao;
import gudiSpring.place.dto.restaurant.RestaurantDto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(value = "/place/restaurant")
public class RestaurantListController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 식당 리스트 출력
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;

		try {
			ServletContext sc = this.getServletContext();

			conn = (Connection) sc.getAttribute("conn");

			String areaNoParam = req.getParameter("areaNo");
			int areaNo = 1;

			if (areaNoParam != null && !areaNoParam.isEmpty()) {
				try {
					areaNo = Integer.parseInt(areaNoParam);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}

			RestaurantDao restaurantDao = new RestaurantDao();
			restaurantDao.setConnection(conn);

			ArrayList<RestaurantDto> restaurantList = (ArrayList<RestaurantDto>) restaurantDao.selectRestaurantList(areaNo);
			req.setAttribute("restaurantList", restaurantList);

			RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/place/restaurant/restaurantListView.jsp");
			dispatcher.forward(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, res);
	}

}
