import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/library";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "PHW#84#jeor";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Retrieve form parameters
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String usn = request.getParameter("usn");
        String phoneno = request.getParameter("phoneno");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Establishing connection with MySQL database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // SQL query to insert user information into the table
            String sql = "INSERT INTO library (username, email, usn, phoneno, password) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, usn);
            pstmt.setString(4, phoneno);
            pstmt.setString(5, password);
            pstmt.executeUpdate();

            // Redirecting user to a success page
            response.sendRedirect("login1.html");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handling database errors
            response.getWriter().println("Error occurred: " + e.getMessage());
        } finally {
            // Closing resources
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}