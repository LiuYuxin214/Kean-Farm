import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Student extends User {

    int application;


    public Student(String username) {
        classId = 1;
        this.username = username;
        application = 0;
    }

    public Student(String username, String password) {
        classId = 1;
        this.username = username;
        this.password = password;
        application = 0;
    }

    int getApplication() {
        return application;
    }

    void setApplication(int application) {
        this.application = application;
    }

    void saveToFile() throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter("Users\\" + username + ".txt");
        printWriter.println(classId);
        printWriter.println(username);
        printWriter.println(password);
        printWriter.println(application);
        printWriter.close();
    }

    void getFromFile() throws FileNotFoundException {
        File file = new File("Users\\" + username + ".txt");
        Scanner scanner = new Scanner(file);
        classId = scanner.nextInt();
        username = scanner.next();
        password = scanner.next();
        application = scanner.nextInt();
        scanner.close();
    }
}
