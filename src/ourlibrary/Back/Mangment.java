package ourlibrary.Back;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ourlibrary.Back.*;
import ourlibrary.Front.*;

import static java.lang.Math.max;

public abstract class Mangment {
    //all books and all students on whole system

    public static ArrayList<Book> allBooks = new ArrayList<>();
    public static BooksTree libraryBooks = new BooksTree();
    //hashmap that links the object of all students in the system with the id of each student now the complexity of load student from the file is o(1)
    public static HashMap<Integer, Student> allStudents = new HashMap<>();

    //inserts a book for the library
    public static void insertBookToLibrary(Book book) throws LibraryExceptions {
        // Check for duplicate ISBN
        if (libraryBooks.search(book.getISBN()) != null) {
            throw new LibraryExceptions("A book with ISBN " + book.getISBN() + " already exists.");
        }
        allBooks.add(book);
        libraryBooks.insert(book);
    }

    //removes a book from the library
    public static void removeBookFromLibrary(int ISBN) {
        Book book = libraryBooks.search(ISBN);
        allBooks.remove(book);//remove from arraylist
        libraryBooks.deleteBook(ISBN);//remove from bst
    }

    //search for a book in the library by it's ISBN
    public static Book searchBookInLibrary(int ISBN) { //takes the isbn from the swing and returns the Book that carries that isbn
        return libraryBooks.search(ISBN);
    }

    //Maximum (reports)
    //<editor-fold defaultstate="collapsed" desc="maximumBorrowedBook">
    public static Book maximumBorrowedBook() {
        int max = 0;
        Book maxBook = null;
        System.out.println(
                "Number of books = "
                + allBooks.size()
        );
        for (Book b : allBooks) {
            if (max <= b.countOfeachBorrow) {
                max = b.countOfeachBorrow;
                maxBook = b;
            }
        }
        return maxBook;
    }
//</editor-fold>

    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="maximumAuthersReaders">
    public static String maximumAuthersReaders() {

        HashMap<String, Integer> authors = new HashMap<>();

        for (Book b : allBooks) {

            String author = b.getAuther();

            if (authors.containsKey(author)) {

                authors.put(author,
                        authors.get(author) + b.countOfeachBorrow);

            } else {

                authors.put(author,
                        b.countOfeachBorrow);
            }
        }

        String maxAuthor = "";
        int maxReads = 0;

        for (String author : authors.keySet()) {

            if (authors.get(author) > maxReads) {

                maxReads = authors.get(author);
                maxAuthor = author;
            }
        }

        return maxAuthor;
    }
//</editor-fold>

    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------
    //<editor-fold defaultstate="collapsed" desc="AvailableEachBoook">
    public int AvailableEachBook(Book book) {
        return book.getAvailables();
    }

    public static String AvailablesAllBooks() {
        return libraryBooks.AvailablesAllBooksHelper(libraryBooks.root);
    }
//</editor-fold>

    //----------------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------------------------
    /*enables the student to borrow a new book and add it to his borrowed books array
    list
     */
    //just to search for the book by isbn then borrow it
    //the actual method
    public static void Request(Student student, Book book) throws LibraryExceptions {

        if (student.getBorrowedBooks().size() >= 3) {  //if the student reaches the limit of borrow
            throw new LibraryExceptions("you can't borrow more than three books");
        }
        if (book.getAvailables() == 0) {
            // In Request method, inside the if (book.getAvailables() == 0) block
            book.studentsQueue.add(new WaitlistedStudent(student));
            System.out.println(
                    student.getName()
                    + " added to waiting queue"
            );

        } else {//if the book is available
            System.out.println("you borrowed this book on " + LocalDate.now());//start date
            book.countOfeachBorrow++;// register the borrow
            book.setAvailables(book.getAvailables() - 1); //avilable book -1
            student.getBorrowedBooks().add(book); //add the book to the borrowed book array list to the student
            LocalDate after = LocalDate.now().plusDays(10);//the return date exeptable
            System.out.println("you should return the book" + after);
            Borrow b1 = new Borrow(student, book, LocalDate.now(), after);//add to the  borrow registers
            student.getListOfBorrowedBooks().add(b1);
            book.arrayOfStudents.add(student);//add the student to the students who borrowed this book

        }
    }

    // Returns a list of all students who have at least one overdue book
    public static List<Student> getOverdueStudents() {
        List<Student> overdueStudents = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Student s : allStudents.values()) {
            if (hasOverdueBooks(s)) {
                overdueStudents.add(s);
            }
        }
        return overdueStudents;
    }

// Checks if a specific student has any overdue books
    public static boolean hasOverdueBooks(Student s) {
        LocalDate today = LocalDate.now();
        for (Borrow b : s.getListOfBorrowedBooks()) {
            if (b.isActive() && b.getEnd().isBefore(today)) {
                return true;
            }
        }
        return false;
    }

    //enables the student to return his borrowed book
    public static void returnBorrowedBook(Student student, Book book) {
        // Remove book from returning student's borrowed list
        student.getBorrowedBooks().remove(book);

        // Mark the active borrow record as returned
        for (Borrow b : student.getListOfBorrowedBooks()) {
            if (b.getBook().equals(book) && b.isActive()) {
                b.setActualReturnDate(LocalDate.now());
                break;
            }
        }

        WaitlistedStudent wrapper = book.studentsQueue.poll();

        if (wrapper == null) {
            // Queue empty → the copy becomes available
            book.setAvailables(book.getAvailables() + 1);
        } else {
            // Queue not empty → transfer to next student
            Student nextStudent = wrapper.getStudent();
            // Temporarily increase available copies so Request can process
            book.setAvailables(book.getAvailables() + 1);
            try {
                Request(nextStudent, book);
            } catch (LibraryExceptions e) {
                System.err.println("Transfer to " + nextStudent.getName()
                        + " failed: " + e.getMessage());
            }
        }
    }

    //enables the manager to change the number of copies
    public static void editNumOfCoppies(Book b, int newTotalCopies) {
        int oldTotal = b.getCoppies();
        int oldAvailables = b.getAvailables();
        int currentlyBorrowed = oldTotal - oldAvailables; // copies out with students

        if (newTotalCopies < currentlyBorrowed) {
            // Cannot reduce total below number of copies currently out
            // In a real system you'd warn the manager, but we'll just cap it.
            newTotalCopies = currentlyBorrowed;
        }

        int newAvailables = newTotalCopies - currentlyBorrowed;

        b.setCoppies(newTotalCopies);
        b.setAvailables(newAvailables);

        // If new copies became available and there's a queue, fulfill requests
        while (b.getAvailables() > 0 && !b.studentsQueue.isEmpty()) {
            WaitlistedStudent wrapper = b.studentsQueue.poll();
            Student nextStudent = wrapper.getStudent();
            try {
                Request(nextStudent, b);
            } catch (LibraryExceptions e) {
                // If request fails, we need to put the copy back and stop
                // (The request already decremented availables, but if it failed,
                // we manually increment it back. However, Request currently
                // decrements availables only in the success path; if it throws,
                // availables isn't decremented, so we're safe.)
                System.err.println("Cannot fulfill queue request for "
                        + nextStudent.getName() + ": " + e.getMessage());
                // Put the wrapper back? Better to just break and leave available copy
                // so someone else can borrow it later.
                break;
            }
        }
    }

    //=======================================================================================
    //add student to the arrayList of loggedIn student
    public static void addStudent(
            String name,
            String statue,
            String pass) {

        Student s
                = new Student(
                        name,
                        pass,
                        statue
                );

        allStudents.put(
                s.getId(),
                s
        );

        FileManager.saveStudent(s);
    }

    //check if the logged in student matches his password
    public static Student checkLoggedStudent(
            int id,
            String name,
            String pass) {

        Student s = allStudents.get(id);

        if (s == null) {
            return null;
        }

        if (s.getName().equalsIgnoreCase(name)
                && s.getPassword().equals(pass)) {

            return s;
        }

        return null;
    }

    public static boolean checkLoggedManager(
            String name,
            String pass) {

        ArrayList<Manager> managers
                = FileManager.loadManagers();

        for (Manager m : managers) {

            if (m.getName().equalsIgnoreCase(name)
                    && m.getPassword().equals(pass)) {

                return true;
            }
        }

        return false;
    }

    public static void loadStudentsMap() {

        ArrayList<Student> students
                = FileManager.loadStudents();

        for (Student s : students) {

            allStudents.put(
                    s.getId(),
                    s
            );
        }
    }

}
