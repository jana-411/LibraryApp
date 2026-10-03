package ourlibrary;

import javax.swing.SwingUtilities;
import ourlibrary.Back.*;
import ourlibrary.Front.*;

public class Main {

    public static void main(String[] args) {
        // Run the GUI application safely on the Event Dispatch Thread
//        SwingUtilities.invokeLater(() -> {
//            new LoginFrame().setVisible(true);
//        });
        Book b = new Book(1, "test", "hello");
        BooksTree allBooks = new BooksTree();
        b.setAvailables(15);
        allBooks.insert(b);
        Book b1 = new Book(2, "test2", "hi");
        b1.setAvailables(12);
        allBooks.insert(b1);
        Book b3 = new Book(3, "fk", "sfjl");
        b3.setAvailables(25);
        allBooks.insert(b3);
        allBooks.insert(new Book(12, "fof", "htp"));
        allBooks.display();
        System.out.println("======");
        System.out.println(allBooks.AvailablesAllBooksHelper(allBooks.getRoot()));

//        System.out.println(
//                System.getProperty("user.dir")
//        );
    }
}
