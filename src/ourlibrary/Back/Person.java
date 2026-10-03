package ourlibrary.Back;

import ourlibrary.Back.*;
import ourlibrary.Front.*;

public class Person {
    private String name;
    private String password;


    public Person(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Person(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
