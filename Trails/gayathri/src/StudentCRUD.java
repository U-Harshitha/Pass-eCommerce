import java.sql.*;

public class StudentCRUD {

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "sfu%gsu46-&#";

    // JDBC variables for opening, closing, and managing connection
    private static Connection connection;

    public static void main(String[] args) {
        try {
            // Step 1: Connect to the database
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            // Step 2: Perform CRUD operations
            createStudent("John Doe", 25, "Computer Science");
            readStudents();
            updateStudent(1, "Updated Name", 30, "Updated Major");
            readStudents();
            deleteStudent(1);
            readStudents();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Step 3: Close the connection
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createStudent(String name, int age, String major) throws SQLException {
        String sql = "INSERT INTO students (name, age, major) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, major);
            preparedStatement.executeUpdate();
            System.out.println("Student created successfully.");
        }
    }

    private static void readStudents() throws SQLException {
        String sql = "SELECT * FROM students";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            System.out.println("Students:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String major = resultSet.getString("major");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age + ", Major: " + major);
            }
            System.out.println();
        }
    }

    private static void updateStudent(int id, String name, int age, String major) throws SQLException {
        String sql = "UPDATE student SET name=?, age=?, major=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, major);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
            System.out.println("Student updated successfully.");
        }
    }

    private static void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Student deleted successfully.");
        }
    }
}
