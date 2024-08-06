import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class IssuedBooksServlet extends HttpServlet {
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
        		+ "</head>\r\n"
        		+ "<body>\r\n"
        		+ "");
        out.println("<script>\r\n"
        		+ "    function goBack() {\r\n"
        		+ "        window.history.back();\r\n"
        		+ "    }\r\n"
        		+ "</script>\r\n"
        		+ "");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2 align:center>Issued Books</h2>");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            // Establishing connection with MySQL database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            
            // Creating SQL statement
            stmt = conn.createStatement();
            String sql = "SELECT * FROM issued_books";
            
            // Executing SQL query
            rs = stmt.executeQuery(sql);

            // Displaying issued book information
            out.println("<table border='1'>");
            out.println("<tr><th>User ID</th><th>Book ID</th><th>Book Name</th><th>Period</th><th>Issued Date</th></tr>");
            while (rs.next()) {
                String userid = rs.getString("userid");
                String bookid = rs.getString("bookid");
                String bookname = rs.getString("bookname");
                int period = rs.getInt("period");
                String issueddate = rs.getString("issueddate");
                out.println("<tr><td>" + userid + "</td><td>" + bookid + "</td><td>" + bookname + "</td><td>" + period + "</td><td>" + issueddate + "</td></tr>");
            }
            out.println("</table>");
            out.println("<br>");
            out.println("<button style=\"background-color: blue; color: white; display: block; margin: 0 auto;\" onclick=\"goBack()\">Go Back</button>");


            // Closing the HTML document
            out.println("</body>");
            out.println("</html>");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handling database errors
            out.println("<p>Error occurred while retrieving issued book information.</p>");
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
