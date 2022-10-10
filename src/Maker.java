import java.io.FileNotFoundException;
import java.util.Scanner;

public class Maker {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.print("The number of students: ");
        int numberOfStudents = sc.nextInt();
        System.out.print("The number of administrators: ");
        int numberOfAdministrators = sc.nextInt();
        System.out.print("The number of guards: ");
        int numberOfGuards = sc.nextInt();
        System.out.print("Password: ");
        String password = sc.next();
        for (int i = 1; i <= numberOfStudents; i++) {
            new Student("stu" + i, password).saveToFile();
        }
        for (int i = 1; i <= numberOfAdministrators; i++) {
            new Administrator("admin" + i, password).saveToFile();
        }
        for (int i = 1; i <= numberOfGuards; i++) {
            new Guard("guard" + i, password).saveToFile();
        }
        System.out.println("Done!");
    }
}
