import java.util.ArrayList;

public class User {
    private String name; // name of user
    private String email;
    private String studentID;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.studentID = name + email;
    }

    // Accessors
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStudentID() {
        return studentID;
    }

    // Mutators
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // toString
    public String toString() {
        return "Name: " + this.getName() + "/n" +
                "Email: " + this.getEmail();
    }
}
