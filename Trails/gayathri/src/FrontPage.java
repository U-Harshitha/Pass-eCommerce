import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Insets;
import java.awt.GridLayout;

import static java.awt.SystemColor.text;

public class FrontPage extends JFrame {
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        src      button.setMargin(new Insets(100, 15, 10, 15)); // Adjust the values to your preference
        button.setFont(new Font("Arial", Font.PLAIN, 14)); // Adjust font size if needed
        return button;
    }
    public FrontPage() {
        super("Event Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(4000, 2000);
        setLocationRelativeTo(null);




        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Added FlowLayout with gaps
        JLabel greetingLabel = new JLabel("Welcome! Who are you?");
        greetingLabel.setHorizontalAlignment(JLabel.CENTER);
        greetingLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton customerButton = createStyledButton("Customer");
        JButton organizerButton = createStyledButton("Organizer");
        JButton adminButton = createStyledButton("Admin");

        panel.add(greetingLabel);
        panel.add(customerButton);
        panel.add(organizerButton);
        panel.add(adminButton);

        add(panel);

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginPage("Customer");
            }
        });

        organizerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginPage("Organizer");
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginPage("Admin");
            }
        });
    }
    // Update with your MySQL password
    private void openLoginPage(String role) {
        dispose(); // Close the current frame

        // Open the respective login page
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginPage(role, FrontPage.this).setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrontPage().setVisible(true);
            }
        });
    }
}

class LoginPage extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton backButton;
    private FrontPage frontPage;

    public LoginPage(String role, FrontPage frontPage) {
        super(role + " Login");
        this.frontPage = frontPage;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        backButton = new JButton("Back");

        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(backButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement login logic based on the role
                // For simplicity, just print the role and entered credentials
                System.out.println("Role: " + role);
                System.out.println("Username: " + usernameField.getText());
                System.out.println("Password: " + new String(passwordField.getPassword()));

                // After successful login, open the respective dashboard
                if(role =="Customer")
                 openDashboard1(role);
                else {
                    openDashboard2(role, usernameField.getText());
                }
            }
        });

        final String DB_URL = "jdbc:mysql://localhost:3306/mydb";

         final String USERNAME = "root";

        final String PASSWORD = "sfu%gsu46-&#"; // Update with your MySQL password

        registerButton.addActionListener(new ActionListener() {

            @Override

            public void actionPerformed(ActionEvent e) {
                String userrole=role;
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                if (userrole.equals("Customer")) {
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
                else{
                    try (Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
                        String sql = "INSERT INTO Organis (username, password) VALUES (?, ?)";
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


        }});

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate back to the FrontPage
                dispose();
                frontPage.setVisible(true);
            }
        });
    }

    private void openDashboard1(String role) {
        dispose(); // Close the login page

        // Open the respective dashboard
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Dashboard1(role, LoginPage.this).setVisible(true);
            }
        });
    }
    private void openDashboard2(String role, String username) {
        dispose(); // Close the login page

        // Open the respective dashboard with role and username
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Dashboard(role, username, LoginPage.this).setVisible(true);
            }
        });
    }
}

class Dashboard1 extends JFrame {

    private JButton backButton;
    private LoginPage loginPage;

    public Dashboard1(String role, LoginPage loginPage) {
        super(role + " Dashboard");
        this.loginPage = loginPage;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Add dashboard components based on the role
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel to hold the list of event cards
        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));

        // Add sample event cards (you will replace this with your actual data)
        for (int i = 1; i <= 5; i++) {
            EventCard eventCard = new EventCard("Event " + i, "Description of Event " + i, 50); // Replace with actual data
            eventPanel.add(eventCard);
        }

        // Make the event panel scrollable
        JScrollPane scrollPane = new JScrollPane(eventPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        mainPanel.add(backButton, BorderLayout.SOUTH);

        add(mainPanel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Navigate back to the LoginPage
                dispose();
                loginPage.setVisible(true);
            }
        });
    }

    private class EventCard extends JPanel {
        public EventCard(String eventName, String eventDescription, int availableTickets) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createLineBorder(Color.black));

            JLabel nameLabel = new JLabel(eventName);
            add(nameLabel, BorderLayout.NORTH);

            JTextArea descriptionArea = new JTextArea(eventDescription);
            descriptionArea.setEditable(false);
            add(descriptionArea, BorderLayout.CENTER);

            JLabel ticketsLabel = new JLabel("Available Tickets: " + availableTickets);
            add(ticketsLabel, BorderLayout.SOUTH);
        }
    }
}
class Dashboard extends JFrame {

    private JButton backButton;
    private JButton addEventButton;
    private LoginPage loginPage;
    private String username;

    public Dashboard(String role, String username, LoginPage loginPage) {
        super(role + " Dashboard");
        this.username = username;
        this.loginPage = loginPage;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel eventPanel = new JPanel();
        eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));

        // Sample event cards
        for (int i = 1; i <= 5; i++) {
            EventCard eventCard = new EventCard("Event " + i, "Description of Event " + i, 50);
            eventPanel.add(eventCard);
        }

        JScrollPane scrollPane = new JScrollPane(eventPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        addEventButton = new JButton("Add Event");

        panel.add(backButton, BorderLayout.SOUTH);
        panel.add(addEventButton, BorderLayout.SOUTH);

        add(panel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                loginPage.setVisible(true);
            }
        });

        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddEventPage();
            }
        });
    }

    private void openAddEventPage() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddEventPage(Dashboard.this).setVisible(true);
            }
        });
    }


    public class EventCard extends JPanel implements MouseListener {

        private static final Color CARD_BACKGROUND = new Color(255, 255, 255);
        private static final Color BORDER_COLOR = new Color(200, 200, 200);
        private static final Font EVENT_NAME_FONT = new Font("Arial", Font.BOLD, 16);
        private static final Font DESCRIPTION_FONT = new Font("Arial", Font.PLAIN, 14);
        private static final Font TICKETS_FONT = new Font("Arial", Font.ITALIC, 12);

        public EventCard(String eventName, String eventDescription, int availableTickets) {
            setLayout(new BorderLayout());
            setBackground(CARD_BACKGROUND);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));

            JLabel nameLabel = new JLabel(eventName);
            nameLabel.setFont(EVENT_NAME_FONT);
            add(nameLabel, BorderLayout.NORTH);

            JTextArea descriptionArea = new JTextArea(eventDescription);
            descriptionArea.setEditable(false);
            descriptionArea.setFont(DESCRIPTION_FONT);
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(descriptionArea);
            add(scrollPane, BorderLayout.CENTER);

            JLabel ticketsLabel = new JLabel("Available Tickets: " + availableTickets);
            ticketsLabel.setFont(TICKETS_FONT);
            add(ticketsLabel, BorderLayout.SOUTH);

            addMouseListener(this); // Register the listener
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // Handle mouse click event if needed
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // Handle mouse press event if needed
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // Handle mouse release event if needed
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            setBackground(BORDER_COLOR);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setBackground(CARD_BACKGROUND);
        }
    }
}

class AddEventPage extends JFrame {

    private JTextField eventNameField;
    private JTextArea eventDescriptionArea;
    private JTextField ticketsField;
    private JTextField phonePayIdField;
    private JButton addButton;
    private JButton cancelButton;
    private Dashboard dashboard;

    public AddEventPage(Dashboard dashboard) {
        super("Add Event");
        this.dashboard = dashboard;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2));

        panel.add(new JLabel("Event Name:"));
        eventNameField = new JTextField();
        panel.add(eventNameField);

        panel.add(new JLabel("Event Description:"));
        eventDescriptionArea = new JTextArea();
        panel.add(new JScrollPane(eventDescriptionArea));

        panel.add(new JLabel("Tickets:"));
        ticketsField = new JTextField();
        panel.add(ticketsField);

        panel.add(new JLabel("Phone Pay ID:"));
        phonePayIdField = new JTextField();
        panel.add(phonePayIdField);

        addButton = new JButton("Add Event");
        cancelButton = new JButton("Cancel");

        panel.add(addButton);
        panel.add(cancelButton);

        add(panel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEventToDatabase();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void addEventToDatabase() {
        String eventName = eventNameField.getText();
        String eventDescription = eventDescriptionArea.getText();
        String tickets = ticketsField.getText();
        String phonePayId = phonePayIdField.getText();

        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String dbPassword = "sfu%gsu46-&#";

        try {
            // Establish the database connection
            Connection connection = DriverManager.getConnection(url, user, dbPassword);

            // SQL query to insert data into the 'events' table
            String insertQuery = "INSERT INTO event (event_name, event_description, tickets, phone_pay_id) VALUES (?, ?, ?, ?)";

            // Create a prepared statement to safely insert data
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, eventName);
                preparedStatement.setString(2, eventDescription);
                preparedStatement.setString(3, tickets);
                preparedStatement.setString(4, phonePayId);

                // Execute the query to insert data
                preparedStatement.executeUpdate();

                System.out.println("Event details added to the 'events' table in the database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any SQLExceptions or database connection issues here
        }

        // Close the AddEventPage after adding the event
        dispose();
    }
}
