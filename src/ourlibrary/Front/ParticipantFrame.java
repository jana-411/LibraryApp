package ourlibrary.Front;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ourlibrary.Back.*;
import ourlibrary.Front.*;
import ourlibrary.Main;

public class ParticipantFrame extends JFrame implements ActionListener {

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Student student;

    private JButton btnBorrow, btnReturn, btnSearch, btnMostBorrowed, btnTopAuthors, btnAvailable, btnLogout;
    private JButton borrowActionBtn, returnActionBtn, searchActionBtn;

    private JTextField borrowIsbnField, returnIsbn, searchIsbnSearch;

    private JTextArea mostBorrowedArea;
    private JTextArea topAuthorsArea;

    private JLabel resTitle, resAuthor;

    private final Color BG_COLOR = new Color(247, 243, 233);
    private final Color SIDEBAR_COLOR = new Color(110, 80, 65);
    private final Color BUTTON_COLOR = new Color(211, 118, 75);
    private final Color TEXT_COLOR = new Color(60, 42, 33);

    public ParticipantFrame(Student s) {
        this.student = s;
        setTitle("Library Portal - Participant Workspace (" + s.getStatue() + ")");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon(
                Main.class.getResource("—Pngtree—open book icon_21432264.png")
        );
        setIconImage(icon.getImage());

        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR_COLOR);
        sidebar.setLayout(new GridLayout(7, 1, 10, 10));
        sidebar.setBorder(BorderFactory.createEmptyBorder(25, 14, 25, 14));

        btnBorrow = createSidebarButton("Borrow Book");
        btnReturn = createSidebarButton("Return Book");
        btnSearch = createSidebarButton("Search Catalog");
        btnMostBorrowed = createSidebarButton("Top Books");
        btnTopAuthors = createSidebarButton("Top Authors");
        btnAvailable = createSidebarButton("Available Stocks");
        btnLogout = createSidebarButton("Logout Client");
        btnLogout.setBackground(new Color(150, 60, 50));

        btnBorrow.addActionListener(this);
        btnReturn.addActionListener(this);
        btnSearch.addActionListener(this);
        btnMostBorrowed.addActionListener(this);
        btnTopAuthors.addActionListener(this);
        btnAvailable.addActionListener(this);
        btnLogout.addActionListener(this);

        sidebar.add(btnBorrow);
        sidebar.add(btnReturn);
        sidebar.add(btnSearch);
        sidebar.add(btnMostBorrowed);
        sidebar.add(btnTopAuthors);
        sidebar.add(btnAvailable);
        sidebar.add(btnLogout);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createBorrowPanel(), "Borrow");
        cardPanel.add(createReturnPanel(), "Return");
        cardPanel.add(createSearchPanel(), "Search");

        mostBorrowedArea = new JTextArea();

        topAuthorsArea = new JTextArea();

        cardPanel.add(
                createAnalyticsPanel(
                        "Most Borrowed Summary",
                        mostBorrowedArea
                ),
                "MostBorrowed"
        );
        String author = Mangment.maximumAuthersReaders();
        if (author.isEmpty()) {
            topAuthorsArea.setText("No data yet.");
        } else {
            topAuthorsArea.setText("Most Read Author:\n\n" + author);
        }

        Book b = Mangment.maximumBorrowedBook();
        if (b != null) {
            mostBorrowedArea.setText(
                    "Name : " + b.getName()
                    + "\nAuthor : " + b.getAuther()
                    + "\nBorrow Count : " + b.getCountOfeachBorrow()
            );
        } else {
            mostBorrowedArea.setText("No books in the library yet.");
        }

        cardPanel.add(
                createAnalyticsPanel(
                        "Most Popular Authors",
                        topAuthorsArea
                ),
                "TopAuthors"
        );
        cardLayout.show(cardPanel, "TopAuthors");
        cardLayout.show(cardPanel, "MostBorrowed");
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sidebar, cardPanel);
        splitPane.setDividerLocation(240);
        splitPane.setEnabled(false);
        add(splitPane);

        // التحقق من وجود كتب متأخرة بعد إنشاء الواجهة
        if (Mangment.hasOverdueBooks(s)) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(
                        this,
                        "Warning: You have overdue books! Please return them immediately.",
                        "Overdue Notice",
                        JOptionPane.WARNING_MESSAGE
                );
            });
        }

        setVisible(true);
    }

    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        return button;
    }

    private JPanel createBorrowPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Submit Borrow Request");
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(createStyledLabel(" Enter ISBN:"), gbc);
        gbc.gridx = 1;
        borrowIsbnField = new JTextField(15);
        borrowIsbnField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(borrowIsbnField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(24, 14, 14, 14);
        borrowActionBtn = new JButton("Borrow");
        borrowActionBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        borrowActionBtn.setBackground(BUTTON_COLOR);
        borrowActionBtn.setForeground(Color.WHITE);
        borrowActionBtn.addActionListener(this);
        panel.add(borrowActionBtn, gbc);

        return panel;
    }

    private JPanel createReturnPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Process Book Return");
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
        returnIsbn = new JTextField(15);
        returnIsbn.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(returnIsbn, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(24, 14, 14, 14);
        returnActionBtn = new JButton("Return");
        returnActionBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        returnActionBtn.setBackground(BUTTON_COLOR);
        returnActionBtn.setForeground(Color.WHITE);
        returnActionBtn.addActionListener(this);
        panel.add(returnActionBtn, gbc);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Catalog Identity Finder");
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(TEXT_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(createStyledLabel("Search ISBN Code:"), gbc);
        gbc.gridx = 1;
        searchIsbnSearch = new JTextField(15);
        searchIsbnSearch.setFont(new Font("SansSerif", Font.PLAIN, 15));
        panel.add(searchIsbnSearch, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(createStyledLabel("Identified Title:"), gbc);
        gbc.gridx = 1;
        resTitle = new JLabel("---");
        resTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
        panel.add(resTitle, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(createStyledLabel("Identified Author:"), gbc);
        gbc.gridx = 1;
        resAuthor = new JLabel("---");
        resAuthor.setFont(new Font("SansSerif", Font.BOLD, 15));
        panel.add(resAuthor, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(24, 14, 14, 14);
        searchActionBtn = new JButton("Search");
        searchActionBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        searchActionBtn.setBackground(BUTTON_COLOR);
        searchActionBtn.setForeground(Color.WHITE);
        searchActionBtn.addActionListener(this);
        panel.add(searchActionBtn, gbc);

        return panel;
    }

    private JPanel createAnalyticsPanel(
            String header,
            JTextArea area) {

        JPanel panel = new JPanel(new BorderLayout(18, 18));

        panel.setBackground(BG_COLOR);
        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 30, 30, 30
                ));

        JLabel title = new JLabel(header);

        title.setFont(
                new Font(
                        "Serif",
                        Font.BOLD,
                        24
                )
        );

        panel.add(title, BorderLayout.NORTH);

        area.setEditable(false);

        panel.add(
                new JScrollPane(area),
                BorderLayout.CENTER
        );

        return panel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lbl.setForeground(TEXT_COLOR);
        return lbl;
    }

    private void showAvailableBooksWindow(String text) {
        JFrame smallFrame = new JFrame("Stock Audit");
        smallFrame.setSize(420, 260);
        smallFrame.setLocationRelativeTo(this);
        smallFrame.setLayout(new GridBagLayout());
        smallFrame.getContentPane().setBackground(BG_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(14, 14, 14, 14);

        text = text.replace("\n", "<br>");
        JLabel dataLabel
                = new JLabel("<html>" + text + "</html>");
        dataLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        dataLabel.setForeground(TEXT_COLOR);
        smallFrame.add(dataLabel, gbc);

        smallFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBorrow) {
            cardLayout.show(cardPanel, "Borrow");
        } else if (e.getSource() == btnReturn) {
            cardLayout.show(cardPanel, "Return");
        } else if (e.getSource() == btnSearch) {
            cardLayout.show(cardPanel, "Search");
        } else if (e.getSource() == btnMostBorrowed) {
            Book b = Mangment.maximumBorrowedBook();
            if (b != null) {
                mostBorrowedArea.setText(
                        "Name : " + b.getName()
                        + "\nAuthor : " + b.getAuther()
                        + "\nBorrow Count : " + b.getCountOfeachBorrow()
                );
            } else {
                mostBorrowedArea.setText("No books in the library yet.");
            }
            cardLayout.show(cardPanel, "MostBorrowed");
        } else if (e.getSource() == btnTopAuthors) {
            String author = Mangment.maximumAuthersReaders();
            topAuthorsArea.setText(
                    "Most Read Author:\n\n"
                    + author
            );
            cardLayout.show(cardPanel, "TopAuthors");
        } else if (e.getSource() == btnAvailable) {
            String text = Mangment.AvailablesAllBooks();
            showAvailableBooksWindow(text);
        } else if (e.getSource() == btnLogout) {
            this.dispose();
            new LoginFrame().setVisible(true);
        } else if (e.getSource() == borrowActionBtn) {

            try {
                Book book
                        = Mangment.searchBookInLibrary(Integer.parseInt(
                                borrowIsbnField.getText()));

                if (book == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book not found!"
                    );

                    return;
                }

                String message
                        = "Book Name : " + book.getName()
                        + "\nAuthor : " + book.getAuther()
                        + "\nISBN : " + book.getISBN()
                        + "\nCopies : " + book.getCoppies()
                        + "\nAvailable : " + book.getAvailables()
                        + "\n\nConfirm Borrow Request?";

                int choice
                        = JOptionPane.showConfirmDialog(
                                this,
                                message,
                                "Confirm Borrow",
                                JOptionPane.YES_NO_OPTION
                        );

                if (choice == JOptionPane.YES_OPTION) {

                    Mangment.Request(
                            student,
                            book
                    );

                    JOptionPane.showMessageDialog(
                            this,
                            "Borrow Request Completed Successfully"
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid ISBN"
                );

            } catch (LibraryExceptions ex) {

                JOptionPane.showMessageDialog(
                        this,
                        ex.getMessage()
                );
            }
        } else if (e.getSource() == returnActionBtn) {
            try {

                Book book = Mangment.searchBookInLibrary(Integer.parseInt(returnIsbn.getText()));
                if (book == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book not found!"
                    );
                    return;
                }

                boolean borrowed = false;

                for (Book b : student.getBorrowedBooks()) {

                    if (b.getISBN() == Integer.parseInt(returnIsbn.getText())) {
                        borrowed = true;
                        break;
                    }
                }

                if (!borrowed) {
                    JOptionPane.showMessageDialog(
                            this,
                            "You didn't borrow this book!"
                    );

                    return;
                }

                String message
                        = "Book Name : " + book.getName()
                        + "\nAuthor : " + book.getAuther()
                        + "\nISBN : " + book.getISBN()
                        + "\n\nConfirm Return?";

                int choice
                        = JOptionPane.showConfirmDialog(
                                this,
                                message,
                                "Confirm Return",
                                JOptionPane.YES_NO_OPTION
                        );

                if (choice == JOptionPane.YES_OPTION) {

                    Mangment.returnBorrowedBook(
                            student,
                            book
                    );

                    JOptionPane.showMessageDialog(
                            this,
                            "Book returned successfully."
                    );
                }

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid ISBN"
                );
            }
        } else if (e.getSource() == searchActionBtn) {

            try {

                int isbn
                        = Integer.parseInt(
                                searchIsbnSearch.getText()
                        );

                Book b
                        = Mangment.searchBookInLibrary(isbn);

                if (b == null) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Book not found!",
                            "Search Result",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                resTitle.setText(b.getName());
                resAuthor.setText(b.getAuther());

                JOptionPane.showMessageDialog(
                        this,
                        "Book Name : " + b.getName()
                        + "\nAuthor : " + b.getAuther()
                        + "\nISBN : " + b.getISBN()
                        + "\nCopies : " + b.getCoppies()
                        + "\nAvailable : " + b.getAvailables(),
                        "Book Information",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid ISBN!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
