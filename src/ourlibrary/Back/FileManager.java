package ourlibrary.Back;

import java.io.*;
import java.util.ArrayList;
import ourlibrary.Back.*;
import ourlibrary.Front.*;

public class FileManager {

    private static final String STUDENTS_FILE
            = "students.txt";

    private static final String MANAGERS_FILE
            = "managers.txt";

    public static void saveStudent(Student s) {

        try (BufferedWriter bw
                = new BufferedWriter(
                        new FileWriter(
                                STUDENTS_FILE,
                                true))) {

            StringBuilder books
                    = new StringBuilder();

            for (Book b : s.getBorrowedBooks()) {

                books.append(
                        b.getISBN()
                ).append(";");
            }

            bw.write(
                    s.getId() + ","
                    + s.getName() + ","
                    + s.getPassword() + ","
                    + s.getStatue() + ","
                    + books
            );

            bw.newLine();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static ArrayList<Student> loadStudents() {

        ArrayList<Student> students
                = new ArrayList<>();

        try (BufferedReader br
                = new BufferedReader(
                        new FileReader(
                                STUDENTS_FILE))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data
                        = line.split(",");

                int id
                        = Integer.parseInt(
                                data[0]);

                String name = data[1];

                String password = data[2];

                String statue = data[3];

                Student s
                        = new Student(
                                id,
                                name,
                                password,
                                statue
                        );

                students.add(s);
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        return students;
    }

    public static ArrayList<Manager>
            loadManagers() {

        ArrayList<Manager> managers
                = new ArrayList<>();

        try (BufferedReader br
                = new BufferedReader(
                        new FileReader(
                                MANAGERS_FILE))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data
                        = line.split(",");

                managers.add(
                        new Manager(
                                data[0],
                                data[1]
                        )
                );
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        return managers;
    }

    public static int getNextAvailableId() {

        int maxId = 100;

        try (BufferedReader br
                = new BufferedReader(
                        new FileReader(STUDENTS_FILE))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int id
                        = Integer.parseInt(data[0]);

                if (id > maxId) {

                    maxId = id;
                }
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        return maxId + 1;
    }

}
