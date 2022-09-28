import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public abstract class User {
    int classId;
    String username;
    String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getClassId() {
        return classId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    void saveToFile() throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter("Users\\" + username + ".txt");
        printWriter.println(classId);
        printWriter.println(username);
        printWriter.println(password);
        printWriter.close();
    }

    void getFromFile() throws FileNotFoundException {
        File file = new File("Users\\" + username + ".txt");
        Scanner scanner = new Scanner(file);
        classId = scanner.nextInt();
        username = scanner.next();
        password = scanner.next();
        scanner.close();
    }
}
