import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdBookServlet extends HttpServlet {
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
        String bookid = request.getParameter("bookid");
        String title = request.getParameter("Title");
        String author = request.getParameter("Author");
        int publishedYear = Integer.parseInt(request.getParameter("Published"));

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Establishing connection with MySQL database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // SQL query to insert book information into the table
            String sql = "INSERT INTO books (id, title, author, published_yrar) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookid);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setInt(4, publishedYear);
            pstmt.executeUpdate();

            // Redirecting user to a success page
            response.sendRedirect("add-book-success.html");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handling database errors, you can redirect to an error page or display an error message
            response.sendRedirect("add-book-error.html");
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
