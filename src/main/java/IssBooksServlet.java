import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class IssBooksServlet extends HttpServlet {
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

        // Retrieve  form parameters
        String userid = request.getParameter("userid");
        String bookid = request.getParameter("bookid");
        String bookname = request.getParameter("bookname");
        int period = Integer.parseInt(request.getParameter("period"));
        String issueddate = request.getParameter("issueddate");

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Establishing connection with MySQL database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // SQL query to insert book issuance information into the table
            String sql = "INSERT INTO issued_books (userid, bookid, bookname, period, issueddate) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid);
            pstmt.setString(2, bookid);
            pstmt.setString(3, bookname);
            pstmt.setInt(4, period);
            pstmt.setString(5, issueddate);
            pstmt.executeUpdate();

            // Redirecting  user to a success page with JavaScript pop-up
            response.sendRedirect("issue-book-success.html");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handling database errors, you can redirect to an error page or display an error message
            response.sendRedirect("issue-book-error.html");
        } finally {
            // Closing resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
