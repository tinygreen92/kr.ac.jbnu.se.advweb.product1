package kr.ac.jbnu.se.advweb.product.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;

import kr.ac.jbnu.se.advweb.product.model.UserAccount;
import kr.ac.jbnu.se.advweb.product.utils.DBUtils;
import kr.ac.jbnu.se.advweb.product.utils.MyUtils;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet(urlPatterns = { "/createUserAccount" })
public class CreateUserAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateUserAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher = request.getServletContext()
				.getRequestDispatcher("/WEB-INF/views/registrationView.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn = (Connection) MyUtils.getStoredConnection(request);
		
		String userName = (String) request.getParameter("userName");
		String password = (String) request.getParameter("password");
		
		UserAccount useraccount = new UserAccount(userName, password);
		
		String errorString = null;
		
		String regex = "\\w+";
		
		if (userName == null || !userName.matches(regex)) {
			errorString = "UserAccount Code invalid!";
		}else if (password == null || !password.matches(regex)) {
			errorString = "UserAccount Code invalid!";
		}
		
		if (errorString == null) {
			try {
				DBUtils.insertUser(conn, useraccount);
			} catch (SQLException e) {
				e.printStackTrace();
				errorString = e.getMessage();
			}
		}
		// Store infomation to request attribute, before forward to views.
		request.setAttribute("errorString", errorString);
		request.setAttribute("useraccount", useraccount);
		
		// If error, forward to Edit page.
		if (errorString != null) {
			RequestDispatcher dispatcher = request.getServletContext()
					.getRequestDispatcher("/WEB-INF/views/registrationView.jsp");
			dispatcher.forward(request, response);
		}
		// If everything nice.
		// Redirect to the product listing page.
		else {
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

}