package Again;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EventCRUDApp extends JFrame {
    private JTextField idField, nameField, locationField, dateField, organizerField, descriptionField;
    private JButton addButton, updateButton, deleteButton, fetchButton;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/javadb";
    private static final String USER = "root";
    private static final String PASSWORD = "harshithA14";

    public EventCRUDApp() {
        // Set up Swing components
        idField = new JTextField(5);
        nameField = new JTextField(20);
        locationField = new JTextField(20);
        dateField = new JTextField(10);
        organizerField = new JTextField(20);
        descriptionField = new JTextField(50);

        addButton = createStyledButton("Add");
        updateButton = createStyledButton("Update");
        deleteButton = createStyledButton("Delete");
        fetchButton = createStyledButton("Fetch");

        // Add action listeners
        addButton.addActionListener(e -> addEvent());
        updateButton.addActionListener(e -> updateEvent());
        deleteButton.addActionListener(e -> deleteEvent());
        fetchButton.addActionListener(e -> fetchEvents());

        // Set up layout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        panel.add(createLabel("ID:"), gbc);
        gbc.gridx++;
        panel.add(idField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(createLabel("Name:"), gbc);
        gbc.gridx++;
        panel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(createLabel("Location:"), gbc);
        gbc.gridx++;
        panel.add(locationField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(createLabel("Date:"), gbc);
        gbc.gridx++;
        panel.add(dateField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(createLabel("Organizer:"), gbc);
        gbc.gridx++;
        panel.add(organizerField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(createLabel("Description:"), gbc);
        gbc.gridx++;
        panel.add(descriptionField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(addButton, gbc);
        gbc.gridy++;
        panel.add(updateButton, gbc);
        gbc.gridy++;
        panel.add(deleteButton, gbc);
        gbc.gridy++;
        panel.add(fetchButton, gbc);

        add(panel);

        // Set frame properties
        setTitle("Event CRUD App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(59, 89, 182));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }
    private void addEvent() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String query = "INSERT INTO Events (id, name, location, date, organizer, description) VALUES (?,?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, idField.getText());
                preparedStatement.setString(2, nameField.getText());
                preparedStatement.setString(3, locationField.getText());
                preparedStatement.setString(4, dateField.getText());
                preparedStatement.setString(5, organizerField.getText());
                preparedStatement.setString(6, descriptionField.getText());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateEvent() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String query = "UPDATE Events SET name=?, location=?, date=?, organizer=?, description=? WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, nameField.getText());
                preparedStatement.setString(2, locationField.getText());
                preparedStatement.setString(3, dateField.getText());
                preparedStatement.setString(4, organizerField.getText());
                preparedStatement.setString(5, descriptionField.getText());
                preparedStatement.setInt(6, Integer.parseInt(idField.getText()));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteEvent() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String query = "DELETE FROM Events WHERE id=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, Integer.parseInt(idField.getText()));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchEvents() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String query = "SELECT * FROM Events";
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(query)) {
                    while (resultSet.next()) {
                        System.out.println("ID: " + resultSet.getInt("id") +
                                ", Name: " + resultSet.getString("name") +
                                ", Location: " + resultSet.getString("location") +
                                ", Date: " + resultSet.getString("date") +
                                ", Organizer: " + resultSet.getString("organizer") +
                                ", Description: " + resultSet.getString("description"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EventCRUDApp());
    }
}
