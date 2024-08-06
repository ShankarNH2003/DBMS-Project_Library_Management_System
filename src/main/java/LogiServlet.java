import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogiServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// JDBC URL, username, and password of MySQL server
	 private static final String JDBC_URL = "jdbc:mysql://localhost:3306/library";
	    private static final String JDBC_USER = "root";
	    private static final String JDBC_PASSWORD = "PHW#84#jeor";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // Establishing connection with MySQL database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // SQL query to fetch user information from the table based on username
            String sql = "SELECT * FROM library WHERE username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Retrieve password from the result set
                String dbPassword = rs.getString("password");

                // Check if the provided password matches the stored password
                if (password.equals(dbPassword)) {
                    // Authentication successful, redirect to a success page or perform further actions
                    response.sendRedirect("studenthome.html");
                    return; // End the method execution
                }
            }

            // Authentication failed, redirect back to the login page with an error message
            response.sendRedirect("login1.html?error=1"); // You can customize the error parameter as needed
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handling database errors, you can redirect to an error page or display an error message
            response.sendRedirect("login-error.html");
        } finally {
            // Closing resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
