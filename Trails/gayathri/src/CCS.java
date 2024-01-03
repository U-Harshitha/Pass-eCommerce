
import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.sql.*;



public class CCS {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydb";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "sfu%gsu46-&#"; // Update with your MySQL password



    public static void main(String[] args) {

        JFrame frame = new JFrame("Login or Register");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(300, 200);

        frame.setLayout(new GridLayout(3, 1));



        JPanel loginPanel = new JPanel(new GridLayout(2, 2));

        JLabel usernameLabel = new JLabel("Username:");

        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");

        JPasswordField passwordField = new JPasswordField();

        loginPanel.add(usernameLabel);

        loginPanel.add(usernameField);

        loginPanel.add(passwordLabel);

        loginPanel.add(passwordField);



        JButton loginButton = new JButton("Login");

        JButton registerButton = new JButton("Register");



        // Login button action

        loginButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();

                String password = String.valueOf(passwordField.getPassword());



                try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {

                    String sql = "SELECT * FROM register WHERE username = ? AND password = ?";

                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setString(1, username);

                    statement.setString(2, password);

                    ResultSet resultSet = statement.executeQuery();



                    if (resultSet.next()) {

                        JOptionPane.showMessageDialog(null, "Login successful");



                        // Open Page Two upon successful login

                        JFrame pageTwoFrame = new JFrame("Page Two");

                        pageTwoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                        pageTwoFrame.setSize(300, 200);



                        JLabel helloLabel = new JLabel("Hello, " + username + "!");

                        pageTwoFrame.add(helloLabel);

                        pageTwoFrame.setVisible(true);

                    } else {

                        JOptionPane.showMessageDialog(null, "Invalid username or password");

                    }

                } catch (SQLException ex) {

                    ex.printStackTrace();

                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());

                }

            }

        });



        // Register button action

        registerButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {

                String username = usernameField.getText();

                String password = String.valueOf(passwordField.getPassword());



                try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {

                    String sql = "INSERT INTO register (username, password) VALUES (?, ?)";

                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setString(1, username);

                    statement.setString(2, password);

                    int rowsInserted = statement.executeUpdate();



                    if (rowsInserted > 0) {

                        JOptionPane.showMessageDialog(null, "Registration successful");

                    } else {

                        JOptionPane.showMessageDialog(null, "Registration failed");

                    }

                } catch (SQLException ex) {

                    ex.printStackTrace();

                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());

                }

            }

        });



        frame.add(loginPanel);

        frame.add(loginButton);

        frame.add(registerButton);

        frame.setVisible(true);

    }

}