package ourlibrary.Back;

import java.util.*;
import ourlibrary.Back.*;
import ourlibrary.Front.*;

public class Book {

    private int ISBN;
    private String name;
    public PriorityQueue<WaitlistedStudent> studentsQueue = new PriorityQueue<>();
    public ArrayList<Student> arrayOfStudents = new ArrayList<>();
    private String auther;
    private int Coppies = 0;//لنسخ الكلية
    private int availables = 0;// المتوفرين
    Book right, left;
    int height;
    int countOfeachBorrow = 0; //بتحسبلي عدد المرات يلي استعير فيها هذا الكتاب

    //---------------------------------------------------------------------
    public Book(int ISBN, String name, String auther) {
        this.ISBN = ISBN;
        this.name = name;
        this.auther = auther;
        left = right = null;
        Author.map.put(name, auther);
    }

    public PriorityQueue<WaitlistedStudent> getStudentsQueue() {
        return studentsQueue;
    }

    public void setStudentsQueue(PriorityQueue<WaitlistedStudent> studentsQueue) {
        this.studentsQueue = studentsQueue;
    }

    public Book getRight() {
        return right;
    }

    public void setRight(Book right) {
        this.right = right;
    }

    public Book getLeft() {
        return left;
    }

    public void setLeft(Book left) {
        this.left = left;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getCountOfeachBorrow() {
        return countOfeachBorrow;
    }

    public void setCountOfeachBorrow(int countOfeachBorrow) {
        this.countOfeachBorrow = countOfeachBorrow;
    }

    public Book(int ISBN) {
        this.ISBN = ISBN;
    }

    public int getISBN() {
        return ISBN;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Student> getArrayOfStudents() {
        return arrayOfStudents;
    }

    public String getAuther() {
        return auther;
    }

    public int getCoppies() {
        return Coppies;
    }

    public int getAvailables() {
        return availables;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArrayOfStudents(ArrayList<Student> arrayOfStudents) {
        this.arrayOfStudents = arrayOfStudents;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }

    public void setCoppies(int coppies) {
        Coppies = coppies;
    }

    public void setAvailables(int availables) {
        this.availables = availables;
    }

    public void copyBookData(Book other) {
        this.ISBN = other.ISBN;
        this.name = other.name;
        this.auther = other.auther;
        this.Coppies = other.Coppies;
        this.availables = other.availables;
        this.countOfeachBorrow = other.countOfeachBorrow;
        this.studentsQueue = other.studentsQueue;
        this.arrayOfStudents = other.arrayOfStudents;
    }

}
