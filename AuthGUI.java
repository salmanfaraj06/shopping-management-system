package ShoppingManagementSystem;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class AuthGUI extends JFrame {
    private List<User> users = new ArrayList<>();

    public AuthGUI() {
        loadUsers();
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(3, 2, 5, 5));


        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20,20,20));
        add(panel);



            loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticateUser(username, password)) {

                ShopGUI frame = new ShopGUI();
                frame.setTitle("Shopping Management System");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            }

        });

            registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            // Check if the username already exists
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    JOptionPane.showMessageDialog(this, "Username already exists. Please choose a different one.");
                    return;
                }
            }


            User newUser = new User(username, password);
            users.add(newUser);
            saveUsers();

            JOptionPane.showMessageDialog(null, "Account created successfully!");

        });


    }

    private boolean authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public void saveUsers() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"));

            for (User user : users) {
                writer.write(user.getUsername());
                writer.newLine();
                writer.write(user.getPassword());
                writer.newLine();
            }

            writer.close();
        } catch (IOException i) {
            System.out.println("An error occurred while saving the file.");
        }
    }
    public void loadUsers() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));

            while (reader.ready()) {
                String username = reader.readLine();
                String password = reader.readLine();
                User newUser = new User(username, password);
                users.add(newUser);
            }

            reader.close();
        } catch (IOException i) {
            System.out.println("An error occurred trying to load to file ");
        }
    }

    public void showAuth() {
        AuthGUI Auth = new AuthGUI();
        Auth.setSize(350, 210);
        Auth.setLocationRelativeTo(null);
        Auth.setVisible(true);
        Auth.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}