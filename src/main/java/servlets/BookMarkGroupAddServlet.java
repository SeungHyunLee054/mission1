package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/bookmark-group-add")
public class BookMarkGroupAddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String dbURL = "jdbc:SQLite:C:\\Users\\LSH\\IdeaProjects\\mission1\\src\\main\\webapp\\WEB-INF\\db\\wifi.db";

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String insertSql = "insert into bookmark_group " +
                "(bookmark_group_name, \"order\", register_date) " +
                "VALUES (?,?,datetime('now'));";

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = DriverManager.getConnection(dbURL);
            pstmt = connection.prepareStatement(insertSql);
            pstmt.setString(1, req.getParameter("bookmarkGroupName"));
            pstmt.setInt(2, Integer.parseInt(req.getParameter("order")));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        req.getRequestDispatcher("bookmark-group-add-submit.jsp").forward(req, resp);
    }
}
