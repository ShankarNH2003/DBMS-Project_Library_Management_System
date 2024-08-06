import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MyBooksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Fetching user ID from the request
        String userId = request.getParameter("id");

        // Fetching user details from the database
        UserDetails userDetails = getUserDetailsFromDatabase(userId);

        if (userDetails != null) {
        	out.println("<html><head><title>View Users</title></head><!DOCTYPE html>\r\n"
            		+ "<html lang=\"en\">\r\n"
            		+ "<head>\r\n"
            		+ "    <meta charset=\"UTF-8\">\r\n"
            		+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
            		+ "    <title>Library Books</title>\r\n"
            		+ "    <style>\r\n"
            		+ "        body {\r\n"
            		+ "            background-image: url('https://previews.123rf.com/images/djvstock/djvstock2305/djvstock230502260/203756061-a-modern-library-with-a-vast-collection-generated-by-artificial-intelligence.jpg');\r\n"
            		+ "            width: 100%; /* Specify your background image */\r\n"
            		+ "            background-size:cover; /* Cover the entire background / / Center the background image */\r\n"
            		+ "            font-family: Arial, sans-serif; /* Specify font family */\r\n"
            		+ "        }\r\n"
            		+ "\r\n"
            		+ "        table {\r\n"
            		+ "            width: 80%; /* Set table width */\r\n"
            		+ "            margin: 50px auto; /* Center the table horizontally */\r\n"
            		+ "            border-collapse: collapse; /* Collapse borders */\r\n"
            		+ "            background-color: rgba(255, 255, 255, 0.8); /* Background color with opacity */\r\n"
            		+ "        }\r\n"
            		+ "\r\n"
            		+ "        th, td {\r\n"
            		+ "            padding: 10px; /* Set padding for cells */\r\n"
            		+ "            text-align: left; /* Align text to the left */\r\n"
            		+ "            border-bottom: 1px solid #ddd; /* Add bottom border to cells */\r\n"
            		+ "        }\r\n"
            		+ "\r\n"
            		+ "        th {\r\n"
            		+ "            background-color: #f2f2f2; /* Background color for table header */\r\n"
            		+ "        }\r\n"
            		+ "    </style>\r\n"
            		+ "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css\" integrity=\"sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==\" crossorigin=\"anonymous\" referrerpolicy=\"no-referrer\" />\r\n"
            		+"<script>\r\n"
            		+ "    function goBack() {\r\n"
            		+ "        window.history.back();\r\n"
            		+ "    }\r\n"
            		+ "</script>\r\n"
            		+ ""
            		+ "</head>\r\n"
            		+ "<body>\r\n"
            		+ "");
            out.println("<h2>User Details:</h2>");
            out.println("<table border=\"1\">");
            out.println("<tr><th>User ID</th><th>Book Name</th><th>Issued Date</th></tr>");
            out.println("<tr><td>" + userDetails.getUserId() + "</td><td>" + userDetails.getName() + "</td><td>" + userDetails.getEmail() + "</td></tr>");
            out.println("</table>");
            out.println("<br>");
            out.println("<button style=\"background-color: blue; color: white; display: block; margin: 0 auto;\" onclick=\"goBack()\">Go Back</button>");
            out.println("</body></html>");
        } else {
            out.println("<html><head><title>User Not Found</title></head><body>");
            out.println("<h2>User not found!</h2>");
            out.println("</body></html>");
        }
    }

    // Method to fetch user details from the database
    private UserDetails getUserDetailsFromDatabase(String userId) {
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/library";
        String username = "root";
        String password = "PHW#84#jeor";
        

        // SQL query to fetch user details
        String sql = "select bookname ,issueddate from issued_books where userid = ?";

        try (
            Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String bookname = rs.getString("bookname");
                String issueddate = rs.getString("issueddate");
                return new UserDetails(userId, bookname, issueddate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

