import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViBookServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/library";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "PHW#84#jeor";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // Establishing connection with MySQL database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            stmt = conn.createStatement();

            // SQL query to fetch all records from the books table
            String sql = "SELECT * FROM books";
            rs = stmt.executeQuery(sql);

            // Displaying fetched records in a HTML table
            out.println("<html><head><title>View Books</title></head><!DOCTYPE html>\r\n"
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
            		+ "<script>\r\n"
                    		+ "    function goBack() {\r\n"
                    		+ "        window.history.back();\r\n"
                    		+ "    }\r\n"
                    		+ "</script>\r\n"
                    		+ ""
            		+"</head>\r\n"
            		+ "<body>\r\n"
            		+ "");
            out.println("<h2>Book Details</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Title</th><th>Author</th><th>Published Year</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("id") + "</td>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getString("author") + "</td>");
                out.println("<td>" + rs.getInt("published_yrar") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<br>");
            out.println("<button style=\"background-color: blue; color: white; display: block; margin: 0 auto;\" onclick=\"goBack()\">Go Back</button>");

            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handling database errors, you can redirect to an error page or display an error message
            out.println("An error occurred while fetching books details.");
        } finally {
            // Closing resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
