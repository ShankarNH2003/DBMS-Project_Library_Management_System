import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ReturnBookServlet")
public class ReturnBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userid");
        String bookId = request.getParameter("bookid");

        if (userId != null && !userId.isEmpty() && bookId != null && !bookId.isEmpty()) {
            // Database connection parameters
            String url = "jdbc:mysql://localhost:3306/library";
            String username = "root";
            String password = "PHW#84#jeor";

            // SQL query to delete user
            String sql = "DELETE FROM issued_books WHERE userid = ? AND bookid = ?";

            try (
                Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
                stmt.setString(1, userId);
                stmt.setString(2, bookId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    // User successfully deleted
                    String message = "User with ID " + userId + " returned book with ID " + bookId + " successfully.";
                    sendResponse(response, message, "home1.html");
                } else {
                    // User not found
                    String message = "User with ID " + userId + " and book with ID " + bookId + " not found.";
                    sendResponse(response, message, "home1.html");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Error deleting user
                String message = "Error deleting user.";
                sendResponse(response, message, "home1.html");
            }
        } else {
            // Invalid input
            String message = "Invalid user ID or book ID.";
            sendResponse(response, message, "home1.html");
        }
    }

    // Method to send response with JavaScript alert and redirection
    private void sendResponse(HttpServletResponse response, String message, String redirectUrl) throws IOException {
        String script = "<script>alert('" + message + "');window.location.href='" + redirectUrl + "';</script>";
        response.setContentType("text/html");
        response.getWriter().println(script);
    }
}
