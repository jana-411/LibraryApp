package ourlibrary.Front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.util.HashMap;
import ourlibrary.Back.*;
import ourlibrary.Front.*;
import ourlibrary.Main;

public class LoginFrame extends JFrame implements ActionListener, ItemListener {

    private JTextField loginIdField, loginUsernameField, signupUsernameField;
    private JPasswordField loginPasswordField, signupPasswordField;
    private JComboBox<String> roleCombo;
    private JComboBox<String> signupStatusCombo; // Removed loginStatusCombo

    private JButton loginButton;
    private JButton switchSignupButton;
    private JButton signupBtn;
    private JButton backLogin;

    private JPanel containerPanel;
    private CardLayout cardLayout;

    // Cozy Library Cozy Palette (Larger Dimensions)
    private final Color BG_COLOR = new Color(247, 243, 233);      // Soft Cream
    private final Color ACCENT_COLOR = new Color(139, 94, 60);   // Warm Brown
    private final Color BUTTON_COLOR = new Color(211, 118, 75);  // Muted Terracotta Orange
    private final Color TEXT_COLOR = new Color(60, 42, 33);      // Dark Espresso

    public LoginFrame() {
        setTitle("Digital Library Workspace - Portal");
        setSize(560, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon icon = new ImageIcon(
                Main.class.getResource("—Pngtree—open book icon_21432264.png")
        );
        setIconImage(icon.getImage());

        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        containerPanel.add(createLoginPanel(), "LoginCard");
        containerPanel.add(createSignUpPanel(), "SignUpCard");

        add(containerPanel);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Digital Library Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(ACCENT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // ID Field Row
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        idLabel.setForeground(TEXT_COLOR);
        panel.add(idLabel, gbc);

        gbc.gridx = 1;
        loginIdField = new JTextField(15);
        loginIdField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.add(loginIdField, gbc);

        // Username Row
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        userLabel.setForeground(TEXT_COLOR);
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        loginUsernameField = new JTextField(15);
        loginUsernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.add(loginUsernameField, gbc);

        // Password Row
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passLabel.setForeground(TEXT_COLOR);
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        loginPasswordField = new JPasswordField(15);
        loginPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.add(loginPasswordField, gbc);

        // Role Row
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        roleLabel.setForeground(TEXT_COLOR);
        panel.add(roleLabel, gbc);

        gbc.gridx = 1;
        roleCombo = new JComboBox<>(new String[]{"Manager", "Participant"});
        roleCombo.setBackground(Color.WHITE);
        roleCombo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        roleCombo.addItemListener(this);
        panel.add(roleCombo, gbc);

        // --- Education Status selection row completely removed from here ---
        // Login Button Row
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 14, 12, 14);
        loginButton = new JButton("Login to System");
        loginButton.setBackground(BUTTON_COLOR);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(14, 26, 14, 26));
        loginButton.addActionListener(this);
        panel.add(loginButton, gbc);

        // Toggle to Signup Row
        gbc.gridy = 6;
        gbc.insets = new Insets(12, 14, 14, 14);
        switchSignupButton = new JButton("If you don't have account, sign up!");
        switchSignupButton.setFont(new Font("SansSerif", Font.ITALIC, 15));
        switchSignupButton.setForeground(ACCENT_COLOR);
        switchSignupButton.setContentAreaFilled(false);
        switchSignupButton.setBorderPainted(false);
        switchSignupButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        switchSignupButton.addActionListener(this);
        panel.add(switchSignupButton, gbc);

        return panel;
    }

    private JPanel createSignUpPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Participant Sign Up", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setForeground(ACCENT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel userLabel = new JLabel("Desired Username:");
        userLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        userLabel.setForeground(TEXT_COLOR);
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        signupUsernameField = new JTextField(15);
        signupUsernameField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.add(signupUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passLabel = new JLabel("Choose Password:");
        passLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        passLabel.setForeground(TEXT_COLOR);
        panel.add(passLabel, gbc);

        gbc.gridx = 1;
        signupPasswordField = new JPasswordField(15);
        signupPasswordField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.add(signupPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel statusLabel = new JLabel("Education Status:");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        statusLabel.setForeground(TEXT_COLOR);
        panel.add(statusLabel, gbc);

        gbc.gridx = 1;
        signupStatusCombo = new JComboBox<>(new String[]{"Student", "Graduate"});
        signupStatusCombo.setBackground(Color.WHITE);
        signupStatusCombo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.add(signupStatusCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 14, 12, 14);
        signupBtn = new JButton("Register Profile");
        signupBtn.setBackground(BUTTON_COLOR);
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFont(new Font("SansSerif", Font.BOLD, 18));
        signupBtn.setFocusPainted(false);
        signupBtn.setBorder(BorderFactory.createEmptyBorder(14, 26, 14, 26));
        signupBtn.addActionListener(this);
        panel.add(signupBtn, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(12, 14, 14, 14);
        backLogin = new JButton("Already registered? Return to Login");
        backLogin.setFont(new Font("SansSerif", Font.ITALIC, 15));
        backLogin.setForeground(ACCENT_COLOR);
        backLogin.setContentAreaFilled(false);
        backLogin.setBorderPainted(false);
        backLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backLogin.addActionListener(this);
        panel.add(backLogin, gbc);

        return panel;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // No UI adjustments required dynamically on login screen anymore since status dropdown was removed
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {

            String role = (String) roleCombo.getSelectedItem();

            if ("Manager".equals(role)) {
                boolean loginSuccess
                        = Mangment.checkLoggedManager(
                                loginUsernameField.getText(),
                                loginPasswordField.getText()
                        );
                if (loginSuccess) {
                    this.dispose();
                    new ManagerFrame()
                            .setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Wrong manager name or password!"
                    );
                }
            } else {
                try {
                    int id
                            = Integer.parseInt(
                                    loginIdField.getText()
                            );
                    Student student = Mangment.checkLoggedStudent(
                            Integer.parseInt(loginIdField.getText()),
                            loginUsernameField.getText(),
                            loginPasswordField.getText());

                    if (student != null) {

                        this.dispose();

                        new ParticipantFrame(student)
                                .setVisible(true);

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Wrong ID or password!"
                        );
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a valid ID!"
                    );
                }
            }
        } else if (e.getSource() == switchSignupButton) {
            cardLayout.show(containerPanel, "SignUpCard");
        } else if (e.getSource() == signupBtn) {
            Mangment.addStudent(signupUsernameField.getText(),
                    signupStatusCombo.getSelectedItem().toString(),
                    signupPasswordField.getText());

            //test......
            for (HashMap.Entry<Integer, Student> entry
                    : Mangment.allStudents.entrySet()) {

                System.out.println("ID = " + entry.getKey());
                System.out.println("Name = " + entry.getValue().getName());
                System.out.println("Status = " + entry.getValue().getStatue());
                System.out.println("----------------");
            }

            JOptionPane.showMessageDialog(this, "Profile Registration Success!");
            cardLayout.show(containerPanel, "LoginCard");
        } else if (e.getSource() == backLogin) {
            cardLayout.show(containerPanel, "LoginCard");
        }
    }

    public static void main(String[] args) {
        Student.setNextId(FileManager.getNextAvailableId());
        Mangment.loadStudentsMap();
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
