import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            boolean logged = false;
            int classId = 0;
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your username(Type \"exit\" to exit): ");
            String username = scanner.next();
            if (username.equals("exit")) {
                System.exit(0);
            }
            System.out.print("Enter your password: ");
            String password = scanner.next();
            try {
                File file = new File("Users\\" + username + ".txt");
                Scanner fileSc = new Scanner(file);
                classId = fileSc.nextInt();
                fileSc.next();
                if (fileSc.next().equals(password)) {
                    logged = true;
                } else {
                    System.out.println("Wrong password");
                    logged = false;
                    Waiter.waiter();
                }
            } catch (FileNotFoundException e) {
                System.out.println("User not found");
            }
            if (logged) {
                while (true) {
                    try {
                        if (classId == 1) {
                            Student student = new Student(username, password);
                            student.getFromFile();
                            System.out.println("Welcome, student " + student.getUsername());
                            if (student.getApplication() == 0) {
                                System.out.println("Your application is not submitted");
                            } else if (student.getApplication() == 1) {
                                System.out.println("Your application is submitted, please wait for the administrator to view it");
                            } else if (student.getApplication() == 2) {
                                System.out.println("Your application is accepted");
                            } else if (student.getApplication() == 3) {
                                System.out.println("Your application is rejected");
                            }
                            System.out.println("Main Menu:");
                            System.out.println("1. Submit Application");
                            System.out.println("2. Withdrawal Application");
                            System.out.println("3. Change Password");
                            System.out.println("4. Exit");
                            System.out.print("Enter your choice: ");
                            int choice = scanner.nextInt();
                            if (choice == 1) {
                                if (student.getApplication() == 0) {
                                    student.setApplication(1);
                                    student.saveToFile();
                                    System.out.println("Your application is submitted");
                                    Waiter.waiter();
                                } else if (student.getApplication() == 1) {
                                    System.out.println("You have already submitted your application");
                                    Waiter.waiter();
                                } else if (student.getApplication() == 2) {
                                    System.out.println("Your application is already accepted");
                                    Waiter.waiter();
                                } else if (student.getApplication() == 3) {
                                    System.out.println("Your application is already rejected");
                                    Waiter.waiter();
                                }
                            } else if (choice == 2) {
                                if (student.getApplication() == 1) {
                                    student.setApplication(0);
                                    student.saveToFile();
                                    System.out.println("Your application is withdrawn");
                                    Waiter.waiter();
                                } else if (student.getApplication() == 0) {
                                    System.out.println("You have not submitted your application");
                                    Waiter.waiter();
                                } else if (student.getApplication() == 2) {
                                    System.out.println("Your application is already accepted");
                                    Waiter.waiter();
                                } else if (student.getApplication() == 3) {
                                    System.out.println("Your application is already rejected");
                                    Waiter.waiter();
                                }
                            } else if (choice == 3) {
                                System.out.print("Enter your new password: ");
                                String newPassword = scanner.next();
                                student.setPassword(newPassword);
                                student.saveToFile();
                                System.out.println("Your password is changed");
                                Waiter.waiter();
                            } else if (choice == 4) {
                                logged = false;
                                break;
                            } else {
                                System.out.println("Invalid choice");
                                Waiter.waiter();
                            }
                        } else if (classId == 2) {
                            Administrator administrator = new Administrator(username, password);
                            administrator.getFromFile();
                            int num = 0;
                            System.out.println("Welcome, administrator " + administrator.getUsername());
                            File users = new File("Users");
                            File[] usersList = users.listFiles();
                            ArrayList<Student> students = new ArrayList<>();
                            ArrayList<Student> needView = new ArrayList<>();
                            ArrayList<Administrator> administrators = new ArrayList<>();
                            ArrayList<Guard> guards = new ArrayList<>();
                            for (File userFile : usersList) {
                                Scanner sc = new Scanner(userFile);
                                int thisClassId = sc.nextInt();
                                if (thisClassId == 1) {
                                    students.add(new Student(sc.next(), sc.next()));
                                    students.get(students.size() - 1).getFromFile();
                                    if (students.get(students.size() - 1).getApplication() == 1) {
                                        needView.add(students.get(students.size() - 1));
                                        num++;
                                    }
                                } else if (thisClassId == 2) {
                                    administrators.add(new Administrator(sc.next(), sc.next()));
                                    administrators.get(administrators.size() - 1).getFromFile();
                                } else if (thisClassId == 3) {
                                    guards.add(new Guard(sc.next(), sc.next()));
                                    guards.get(guards.size() - 1).getFromFile();
                                }
                            }
                            System.out.println(num + " students have submitted their applications but not viewed");
                            System.out.println("Main Menu:");
                            System.out.println("1. View Applications");
                            System.out.println("2. Reset Application Status");
                            System.out.println("3. Manage Users");
                            System.out.println("4. Change Password");
                            System.out.println("5. Exit");
                            System.out.print("Enter your choice: ");
                            int choice = scanner.nextInt();
                            if (choice == 1) {
                                while (true) {
                                    if (num == 0) {
                                        System.out.println("No student has submitted their application but not viewed");
                                        Waiter.waiter();
                                        break;
                                    } else {
                                        System.out.println("The following students have submitted their applications:");
                                        int n = 1;
                                        for (Student student : needView) {
                                            System.out.println(n + ": " + student.getUsername());
                                            n++;
                                        }
                                        System.out.print("Enter the number of the student you want to view or ”0“ to exit: ");
                                        int index = scanner.nextInt();
                                        if (index == 0) {
                                            break;
                                        } else {
                                            System.out.println("Name: " + needView.get(index - 1).getUsername());
                                            System.out.println("1. Accept");
                                            System.out.println("2. Reject");
                                            System.out.print("Enter your choice: ");
                                            int choice2 = scanner.nextInt();
                                            if (choice2 == 1) {
                                                needView.get(index - 1).setApplication(2);
                                                needView.get(index - 1).saveToFile();
                                                needView.remove(index - 1);
                                                num--;
                                                System.out.println("The application is accepted");
                                                Waiter.waiter();
                                            } else if (choice2 == 2) {
                                                needView.get(index - 1).setApplication(3);
                                                needView.get(index - 1).saveToFile();
                                                System.out.println("The application is rejected");
                                                needView.remove(index - 1);
                                                num--;
                                                Waiter.waiter();
                                            }
                                        }
                                    }
                                }
                            } else if (choice == 2) {
                                for (Student student : students) {
                                    student.setApplication(0);
                                    student.saveToFile();
                                }
                                System.out.println("All applications are reset");
                                Waiter.waiter();
                            } else if (choice == 3) {
                                System.out.println("1. Add User");
                                System.out.println("2. Delete User");
                                System.out.println("3. Edit User");
                                System.out.print("Enter your choice: ");
                                int choice2 = scanner.nextInt();
                                if (choice2 == 1) {
                                    System.out.print("Enter the username: ");
                                    String newUsername = scanner.next();
                                    System.out.print("Enter the password: ");
                                    String newPassword = scanner.next();
                                    System.out.print("Enter the class (1=Student 2=Administrator 3=Guard): ");
                                    int newClass = scanner.nextInt();
                                    if (newClass == 1) {
                                        Student student = new Student(newUsername, newPassword);
                                        student.saveToFile();
                                    } else if (newClass == 2) {
                                        Administrator administrator1 = new Administrator(newUsername, newPassword);
                                        administrator1.saveToFile();

                                    } else if (newClass == 3) {
                                        Guard guard = new Guard(newUsername, newPassword);
                                        guard.saveToFile();
                                    }
                                    System.out.println("The user is added");
                                    Waiter.waiter();
                                } else if (choice2 == 2) {
                                    System.out.println("The following users exist:");
                                    int n = 1;
                                    System.out.println("----------------------------------------------------------------------");
                                    System.out.println("No.  Username               Password          Class    ");
                                    for (Student student : students) {
                                        System.out.printf("%-5d%-23s%-18sStudents\n", n, student.getUsername(), student.getPassword());
                                        n++;
                                    }
                                    for (Administrator administrator1 : administrators) {
                                        System.out.printf("%-5d%-23s%-18sAdministrators\n", n, administrator1.getUsername(), administrator1.getPassword());
                                        n++;
                                    }
                                    for (Guard guard : guards) {
                                        System.out.printf("%-5d%-23s%-18sGuards\n", n, guard.getUsername(), guard.getPassword());
                                        n++;
                                    }
                                    System.out.println("----------------------------------------------------------------------");
                                    System.out.print("Enter the username that you want to delete: ");
                                    String newUsername = scanner.next();
                                    File userFile = new File("Users/" + newUsername + ".txt");
                                    if (userFile.exists()) {
                                        if (userFile.delete()) {
                                            System.out.println("The user is deleted");
                                            Waiter.waiter();
                                        } else {
                                            System.out.println("The user is not deleted");
                                            Waiter.waiter();
                                        }
                                    } else {
                                        System.out.println("The user does not exist");
                                        Waiter.waiter();
                                    }
                                } else if (choice2 == 3) {
                                    System.out.println("The following users exist:");
                                    int n = 1;
                                    System.out.println("----------------------------------------------------------------------");
                                    System.out.println("No.  Username               Password          Class              ");
                                    for (Student student : students) {
                                        System.out.printf("%-5d%-23s%-18sStudents\n", n, student.getUsername(), student.getPassword());
                                        n++;
                                    }
                                    for (Administrator administrator1 : administrators) {
                                        System.out.printf("%-5d%-23s%-18sAdministrators\n", n, administrator1.getUsername(), administrator1.getPassword());
                                        n++;
                                    }
                                    for (Guard guard : guards) {
                                        System.out.printf("%-5d%-23s%-18sGuards\n", n, guard.getUsername(), guard.getPassword());
                                        n++;
                                    }
                                    System.out.println("----------------------------------------------------------------------");
                                    System.out.print("Enter the username that you want to edit: ");
                                    String editUsername = scanner.next();
                                    File userFile = new File("Users/" + editUsername + ".txt");
                                    if (userFile.exists()) {
                                        System.out.print("Enter the new password: ");
                                        String newPassword = scanner.next();
                                        System.out.print("Enter the new class (1=Student 2=Administrator 3=Guard): ");
                                        int newClassId = scanner.nextInt();
                                        if (newClassId == 1) {
                                            Student student = new Student(editUsername, newPassword);
                                            student.saveToFile();
                                        } else if (newClassId == 2) {
                                            Administrator administrator1 = new Administrator(editUsername, newPassword);
                                            administrator1.saveToFile();
                                        } else if (newClassId == 3) {
                                            Guard guard = new Guard(editUsername, newPassword);
                                            guard.saveToFile();
                                        }
                                        System.out.println("The user is edited");
                                        Waiter.waiter();
                                    } else {
                                        System.out.println("The user does not exist");
                                        Waiter.waiter();
                                    }
                                } else {
                                    System.out.println("Invalid choice");
                                    Waiter.waiter();
                                }
                            } else if (choice == 4) {
                                System.out.print("Enter your new password: ");
                                String newPassword = scanner.next();
                                administrator.setPassword(newPassword);
                                administrator.saveToFile();
                                System.out.println("Your password is changed");
                                Waiter.waiter();
                            } else if (choice == 5) {
                                logged = false;
                                break;
                            }
                        } else if (classId == 3) {
                            Guard guard = new Guard(username, password);
                            guard.getFromFile();
                            System.out.println("Welcome, guard " + guard.getUsername());
                            System.out.println("Type \"exit\" to log out.");
                            System.out.print("Enter a name: ");
                            String name = scanner.next();
                            if (new File("Users\\" + name + ".txt").exists() && new Scanner(new File("Users\\" + name + ".txt")).next().equals("1")) {
                                Student student = new Student(name);
                                student.getFromFile();
                                if (student.getApplication() == 2) {
                                    System.out.println("Approved");
                                    Waiter.waiter();
                                } else if (student.getApplication() == 3 || student.getApplication() == 0 || student.getApplication() == 1) {
                                    System.out.println("Rejected");
                                    Waiter.waiter();
                                }
                            } else if (name.equals("exit")) {
                                logged = false;
                                break;
                            } else {
                                System.out.println("Wrong name or not student");
                                Waiter.waiter();
                            }
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("[SYSTEM ERROR] File lost!");
                        Waiter.waiter();
                    }
                }
            }
        }
    }
}
