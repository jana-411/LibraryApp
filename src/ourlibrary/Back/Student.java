package ourlibrary.Back;

import java.util.ArrayList;
import ourlibrary.Back.*;
import ourlibrary.Front.*;

public class Student extends Person
        implements Comparable<Student> {

    private int id;

    private static int nextId;

    private String statue;

    private ArrayList<Book> borrowedBooks =
            new ArrayList<>();

    private ArrayList<Borrow> listOfBorrowedBooks =
            new ArrayList<>();


    // عند إنشاء طالب جديد
    public Student(String name,
                   String pass,
                   String statue) {

        super(name, pass);

        this.id = nextId++;

        this.statue = statue;
    }


    // عند تحميل طالب من الملف
    public Student(int id,
                   String name,
                   String pass,
                   String statue) {

        super(name, pass);

        this.id = id;

        this.statue = statue;
    }


    public int getId() {
        return id;
    }


    public static void setNextId(int nextId) {
        Student.nextId = nextId;
    }


    public String getStatue() {
        return statue;
    }


    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }


    public ArrayList<Borrow> getListOfBorrowedBooks() {
        return listOfBorrowedBooks;
    }


    public void setStatue(String statue) {
        this.statue = statue;
    }


    @Override
    public int compareTo(Student other) {

        if (this.statue.equalsIgnoreCase("graduate")
                && !other.statue.equalsIgnoreCase("graduate")) {

            return -1;
        }

        if (!this.statue.equalsIgnoreCase("graduate")
                && other.statue.equalsIgnoreCase("graduate")) {

            return 1;
        }

        return 0;
    }
}