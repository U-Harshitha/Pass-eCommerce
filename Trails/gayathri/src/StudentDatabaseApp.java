import java.sql.*;
public class StudentDatabaseApp {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "sfu%gsu46-&#";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            // Create table
            statement.executeUpdate("CREATE TABLE event (event_id INT AUTO_INCREMENT PRIMARY KEY, event_name VARCHAR(255),event_description TEXT,tickets INT,phone_pay_id VARCHAR(55))");

            // Insert values
            statement.executeUpdate("INSERT INTO event (event_name, event_description, tickets, phone_pay_id) VALUES ('a','a',67,'dhgfgf')");

            // Display information
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Organis")) {
                System.out.println("Users:");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
