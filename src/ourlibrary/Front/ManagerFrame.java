package ourlibrary.Front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import ourlibrary.Back.*;
import ourlibrary.Front.*;
import ourlibrary.Main;

import static java.lang.Integer.parseInt;

public class ManagerFrame extends JFrame implements ActionListener {

    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JButton btnAdd;
    private JButton btnRemove;
    private JButton btnEdit;
    private JButton btnLogout;

    private JButton btnOverdue;

    private JTextArea overdueArea;

    private JButton addActionBtn;
    private JButton removeActionBtn;
    private JButton editActionBtn;
    private JButton refreshBtn; // New Refresh Button

    private JTextField addIsbn, addName, addAuthor;
    private JTextField removeIsbn;
    private JTextField editIsbn, editName, editAuthor, editCopies;

    private final Color BG_COLOR = new Color(247, 243, 233);
    private final Color SIDEBAR_COLOR = new Color(92, 64, 51);
    private final Color BUTTON_COLOR = new Color(211, 118, 75);
    private final Color TEXT_COLOR = new Color(60, 42, 33);
    private final Color ACCENT_COLOR = new Color(139, 94, 60);

    public ManagerFrame() {
        setTitle("Library Management System - Manager Workspace");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(
                Main.class.getResource("—Pngtree—open book icon_21432264.png")
        );
        setIconImage(icon.getImage());

        // Sidebar
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setLayout(new GridLayout(4, 1, 18, 18));
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 18, 30, 18));

        btnAdd = createSidebarButton("Add New Book");
        btnRemove = createSidebarButton("Remove Book");
        btnEdit = createSidebarButton("Edit Copies");
        btnLogout = createSidebarButton("Logout Workspace");
        btnLogout.setBackground(new Color(150, 60, 50));

        btnOverdue = createSidebarButton("Overdue Members");
        btnOverdue.addActionListener(this);
        sidebar.add(btnOverdue);

        btnAdd.addActionListener(this);
        btnRemove.addActionListener(this);
        btnEdit.addActionListener(this);
        btnLogout.addActionListener(this);

        sidebar.add(btnAdd);
        sidebar.add(btnRemove);
        sidebar.add(btnEdit);
        sidebar.add(btnLogout);

        // Initialize card panel FIRST
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Now add the overdue panel
        JPanel overduePanel = new JPanel(new BorderLayout());
        overduePanel.setBackground(BG_COLOR);
        overdueArea = new JTextArea();
        overdueArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(overdueArea);
        overduePanel.add(scroll, BorderLayout.CENTER);
        cardPanel.add(overduePanel, "Overdue");

        // Add other panels
        cardPanel.add(createAddBookPanel(), "AddBook");
        cardPanel.add(createRemoveBookPanel(), "RemoveBook");
        cardPanel.add(createEditCopiesPanel(), "EditCopies");

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, cardPanel);
        splitPane.setDividerLocation(240);
        splitPane.setEnabled(false);
        add(splitPane);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        return button;
    }

    private JPanel createAddBookPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Register New Item");
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(createStyledLabel("ISBN Code:"), gbc);
        gbc.gridx = 1;
        addIsbn = new JTextField(15);
        addIsbn.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(addIsbn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createStyledLabel("Book Title:"), gbc);
        gbc.gridx = 1;
        addName = new JTextField(15);
        addName.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(addName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(createStyledLabel("Author Name:"), gbc);
        gbc.gridx = 1;
        addAuthor = new JTextField(15);
        addAuthor.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(addAuthor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(24, 14, 14, 14);
        addActionBtn = new JButton("Add");
        addActionBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        addActionBtn.setBackground(BUTTON_COLOR);
        addActionBtn.setForeground(Color.WHITE);
        addActionBtn.addActionListener(this);
        panel.add(addActionBtn, gbc);

        return panel;
    }

    private JPanel createRemoveBookPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Discard Catalog Record");
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(createStyledLabel("Enter ISBN to Remove:"), gbc);
        gbc.gridx = 1;
        removeIsbn = new JTextField(15);
        removeIsbn.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(removeIsbn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(24, 14, 14, 14);
        removeActionBtn = new JButton("Remove");
        removeActionBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        removeActionBtn.setBackground(BUTTON_COLOR);
        removeActionBtn.setForeground(Color.WHITE);
        removeActionBtn.addActionListener(this);
        panel.add(removeActionBtn, gbc);

        return panel;
    }

    private JPanel createEditCopiesPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Modify Stock Values");
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(createStyledLabel("ISBN Code:"), gbc);

        // Horizontal sub-panel to pair the editIsbn field with the new Refresh button inline
        gbc.gridx = 1;
        JPanel isbnSearchFieldPanel = new JPanel(new BorderLayout(8, 0));
        isbnSearchFieldPanel.setBackground(BG_COLOR);

        editIsbn = new JTextField(10);
        editIsbn.setFont(new Font("SansSerif", Font.PLAIN, 15));
        isbnSearchFieldPanel.add(editIsbn, BorderLayout.CENTER);

        refreshBtn = new JButton("Search");
        refreshBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        refreshBtn.setBackground(ACCENT_COLOR == null ? new Color(139, 94, 60) : new Color(139, 94, 60));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.addActionListener(this);
        isbnSearchFieldPanel.add(refreshBtn, BorderLayout.EAST);

        panel.add(isbnSearchFieldPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createStyledLabel("Book Name:"), gbc);
        gbc.gridx = 1;
        editName = new JTextField(15);
        editName.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(editName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(createStyledLabel("Author:"), gbc);
        gbc.gridx = 1;
        editAuthor = new JTextField(15);
        editAuthor.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(editAuthor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(createStyledLabel("New Quantity / Copies:"), gbc);
        gbc.gridx = 1;
        editCopies = new JTextField(15);
        editCopies.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(editCopies, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(24, 12, 12, 12);
        editActionBtn = new JButton("Change");
        editActionBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        editActionBtn.setBackground(BUTTON_COLOR);
        editActionBtn.setForeground(Color.WHITE);
        editActionBtn.addActionListener(this);
        panel.add(editActionBtn, gbc);

        return panel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lbl.setForeground(TEXT_COLOR);
        return lbl;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnOverdue) {
            List<Student> overdue = Mangment.getOverdueStudents();
            StringBuilder sb = new StringBuilder();
            if (overdue.isEmpty()) {
                sb.append("No members with overdue books.");
            } else {
                for (Student s : overdue) {
                    sb.append("ID: ").append(s.getId())
                            .append(", Name: ").append(s.getName())
                            .append(", Status: ").append(s.getStatue())
                            .append("\n");
                }
            }
            overdueArea.setText(sb.toString());
            cardLayout.show(cardPanel, "Overdue");
        }
        if (e.getSource() == btnAdd) {
            cardLayout.show(cardPanel, "AddBook");
        } else if (e.getSource() == btnRemove) {
            cardLayout.show(cardPanel, "RemoveBook");
        } else if (e.getSource() == btnEdit) {
            cardLayout.show(cardPanel, "EditCopies");
        } else if (e.getSource() == btnLogout) {
            this.dispose();
            new LoginFrame().setVisible(true);
        } else if (e.getSource() == addActionBtn) {
            try {
                String name = addName.getText().trim();
                String author = addAuthor.getText().trim();
                String isbnText = addIsbn.getText().trim();
// التحقق من الحقول الفارغة
                if (name.isEmpty() || author.isEmpty() || isbnText.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a valid text in all fields!",
                            "Invalid Input",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                int ISBN = Integer.parseInt(isbnText);
                Book insertedBook = new Book(ISBN, name, author);
                try {
                    Mangment.insertBookToLibrary(insertedBook);
                    JOptionPane.showMessageDialog(this, "Book successfully recorded in the catalog!");  // first
                } catch (LibraryExceptions ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Duplicate ISBN", JOptionPane.ERROR_MESSAGE);
                }

                Mangment.libraryBooks.display();
                System.out.println("=========");

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "ISBN must be a valid number!",
                        "Invalid ISBN",
                        JOptionPane.ERROR_MESSAGE
                );

            }

        } else if (e.getSource() == removeActionBtn) {
            int ISBN = parseInt(removeIsbn.getText());
            Mangment.removeBookFromLibrary(ISBN);
            Mangment.libraryBooks.display();
            System.out.println("=========");

            JOptionPane.showMessageDialog(this, "The requested volume has been discarded.");
        } else if (e.getSource() == refreshBtn) {
            try {
                int ISBN = parseInt(editIsbn.getText());
                Book b = Mangment.libraryBooks.search(ISBN);
                if (b != null) {
                    editName.setText(b.getName());
                    editAuthor.setText(b.getAuther());
                } else {
                    JOptionPane.showMessageDialog(this, "Book with this ISBN code not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric ISBN sequence.", "Invalid Format", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == editActionBtn) {
            try {
                int ISBN = Integer.parseInt(editIsbn.getText());
                Book b = Mangment.libraryBooks.search(ISBN);
                if (b != null) {
                    int newCopies = Integer.parseInt(editCopies.getText());
                    int currentlyBorrowed = b.getCoppies() - b.getAvailables();

                    // If the manager tries to set copies below the number of borrowed books
                    if (newCopies < currentlyBorrowed) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Cannot set total copies below the number of books currently borrowed ("
                                + currentlyBorrowed + "). The total has been set to "
                                + currentlyBorrowed + ".",
                                "Adjustment Applied",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }

                    Mangment.editNumOfCoppies(b, newCopies);
                    JOptionPane.showMessageDialog(this, "Stock configurations revised.");
                } else {
                    JOptionPane.showMessageDialog(this, "Cannot modify. Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please check input value formats.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
