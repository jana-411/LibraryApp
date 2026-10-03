package ourlibrary.Back;

import java.time.LocalDate;
import ourlibrary.Back.*;
import ourlibrary.Front.*;

public class Borrow {

    private Book book;
    private Student student;
    private LocalDate begin;
    private LocalDate end;                  // Expected return date (fixed)
    private LocalDate actualReturnDate;     // Actual return date (null if not returned)

    public Borrow(Student student, Book book, LocalDate begin, LocalDate end) {
        this.student = student;
        this.book = book;
        this.begin = begin;
        this.end = end;
        this.actualReturnDate = null;       // Not yet returned
    }

    // Mark the book as returned
    public void setActualReturnDate(LocalDate date) {
        this.actualReturnDate = date;
    }

    // --- Getters ---
    public Book getBook() {
        return book;
    }

    public Student getStudent() {
        return student;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public LocalDate getEnd() {
        return end;
    }                   // Expected return date

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    // Helper to check if this borrow is still active (not returned)
    public boolean isActive() {
        return actualReturnDate == null;
    }
}
