import java.util.ArrayList;

public class User {
    private String name; // name of user

    public User(String name) {
        this.name = name;
    }

    // Accessors
    public String getName() {
        return name;
    }

    // Mutators
    public void setName(String name) {
        this.name = name;
    }

    // toString
    public String toString() {
        return "Name: " + getName();
    }
}
