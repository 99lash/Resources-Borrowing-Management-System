import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import custom.utils.Clrscr;
import custom.utils.Title;

public class StudentController {
  private Map<Integer, Student> students;

  public StudentController() {
    this.students = new HashMap<>();
  }

  public void clearStudents() {
    students.clear();
  }

  public boolean addStudentRecord() {
    new Clrscr();
    new Title();
    Scanner in = new Scanner(System.in);
    System.out.println("---------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Students (Creating)");
    System.out.println("---------------------------------------------------\n");

    System.out.print("Student No: ");
    String studentNo = in.nextLine();
    Student student = searchStudent(studentNo);
    if (student != null) {
      System.out.println("Student No. is already exist!");
      return false;
    }

    System.out.print("First Name: ");
    String firstName = in.nextLine().toUpperCase();
    System.out.print("Middle Initial: ");
    String middleInitial = in.nextLine().toUpperCase().charAt(0) + ".";
    System.out.print("Last Name: ");
    String lastName = in.nextLine().toUpperCase();
    System.out.print("Course: ");
    String course = in.nextLine().toUpperCase();
    course = course.contains("BS") ? course : "BS" + course;
    System.out.print("Year: ");
    int year = in.nextInt();
    in.nextLine();
    System.out.print("Section: ");
    char section = in.nextLine().toUpperCase().charAt(0);
    System.out.print("Department: ");
    String department = in.nextLine().toUpperCase();

    student = new Student(studentNo, lastName, firstName, middleInitial, course, year, section, department);
    int rollId = students.size() + 1;
    students.put(rollId, student);
    System.out.println("\nSuccesfully add a new record of student!");
    return true;
  }

  public boolean updateStudentRecord() {
    new Clrscr();
    new Title();
    Scanner in = new Scanner(System.in);
    System.out.println("---------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Students (Updating)");
    System.out.println("---------------------------------------------------\n");

    System.out.print("Find student by ID: ");
    String studentId = in.nextLine();

    Student student = searchStudent(studentId);
    if (student == null) {
      System.out.println("Student doesn't exist.");
      return false;
    }
    System.out.println("[1] First Name");
    System.out.println("[2] Middle Initial");
    System.out.println("[3] Last Name");
    System.out.println("[4] Student No");
    System.out.println("[5] Course");
    System.out.println("[6] Year");
    System.out.println("[7] Section");
    System.out.println("[8] Department");
    System.out.println("[9] Cancel");
    System.out.print("Select account information to update: ");
    int ch = in.nextInt();
    in.nextLine();

    switch (ch) {
      case 1:
        System.out.print("Enter new first name: ");
        String newFirstName = in.nextLine();
        if (!student.getFirstName().equals(newFirstName)) {
          System.out.printf("First name of #%s set to %s -> %s", studentId, student.getFirstName(), newFirstName);
          student.setFirstName(newFirstName);
          break;
        }
        System.out.println("You cannot replace your old first name with the same first name. ");
        return false;

      case 2:
        System.out.print("Enter new middle initial: ");
        String newMiddleInitial = String.valueOf(in.nextLine().toUpperCase().charAt(0) + ".");
        if (!student.getMiddleInitial().equals(newMiddleInitial)) {
          System.out.printf("Middle initial of #%s set from %s -> %s", studentId, student.getMiddleInitial(),
              newMiddleInitial.toUpperCase());
          student.setMiddleInitial(newMiddleInitial);
          break;
        }
        System.out.println("You cannot replace your old middle initial with the same middle initial. ");
        return false;

      case 3:
        System.out.print("Enter new last name: ");
        String newLastName = in.nextLine();
        if (!student.getLastName().equals(newLastName)) {
          System.out.printf("Last name of #%s set from %s -> %s", studentId, student.getLastName(),
              newLastName.toUpperCase());
          student.setLastName(newLastName);
          break;
        }
        System.out.println("You cannot replace your old last name with the same last name. ");
        return false;

      case 4:
        System.out.print("Enter new student no.: ");
        String newStudentNo = in.nextLine();
        if (!student.getStudentId().equals(newStudentNo)) {
          System.out.printf("Student no of #%s set from %s -> %s", studentId, student.getStudentId(),
              newStudentNo.toUpperCase());
          student.setStudentId(newStudentNo);
          break;
        }
        System.out.println("You cannot replace your old student no with the same student no. ");
        return false;

      case 5:
        System.out.print("Enter new course : ");
        String newCourse = in.nextLine();
        if (!student.getCourse().equals(newCourse)) {
          System.out.printf("Course of #%s set from %s -> %s", studentId, student.getCourse(), newCourse.toUpperCase());
          student.setCourse(newCourse);
          break;
        }
        System.out.println("You cannot replace your old course with the same course. ");
        return false;

      case 6:
        System.out.print("Enter new year : ");
        int newYear = in.nextInt();
        in.nextLine();
        if ((student.getYear() != newYear)) {
          System.out.printf("Year of #%s set from %d -> %d", studentId, student.getYear(), newYear);
          student.setYear(newYear);
          break;
        }
        System.out.println("You cannot replace your old year with the same year. ");
        return false;

      case 7:
        System.out.print("Enter new section : ");
        char newSection = in.nextLine().toUpperCase().charAt(0);
        in.nextLine();
        if ((student.getSection() != newSection)) {
          System.out.printf("Year of #%s set from %c -> %c", studentId, student.getSection(), newSection);
          student.setSection(newSection);
          break;
        }
        System.out.println("You cannot replace your old section with the same section. ");
        return false;

      case 8:
        System.out.print("Enter new department : ");
        String newDepartment = in.nextLine();
        System.out.printf("Department of #%s set from %s -> %s", studentId, student.getDepartment(),
            newDepartment.toUpperCase());
        if (!student.getDepartment().equals(newDepartment)) {
          student.setDepartment(newDepartment);
          break;
        }
        System.out.println("You cannot replace your old deparment with the same deparment. ");
        return false;

      case 9:
        return false;

      default:
        System.out.println("Invalid input! Please select between 1-9.");
        break;
    }
    return true;
  }

  public Student searchStudent(String studentNo) {
    studentNo = studentNo.contains("-S") || studentNo.contains("-s") ? studentNo.toUpperCase() : studentNo + "-S";
    for (Student student : students.values()) {
      if (student.getStudentId().equalsIgnoreCase(studentNo)) {
        return student;
      }
    }
    return null;
  }

  public boolean displayStudentRecord() {
    new Clrscr();
    new Title();
    Scanner in = new Scanner(System.in);
    System.out.println("---------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Students (Searching)");
    System.out.println("---------------------------------------------------\n");

    System.out.print("Find student by ID: ");
    String studentNo = in.nextLine();

    Student student = searchStudent(studentNo);

    if (student == null) {
      System.out.println("Student doesn't exist.");
      return false;
    }
    System.out.println(student.getStudentInfo());
    return true;
  }

  public void fetchStudentsFromDatabase() {
    String filepath = "./res/data/masterlist/student_masterlist.csv";
    try {
      Scanner reader = new Scanner(new File(filepath));

      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] fields = line.split(",");

        boolean header = fields[0].equalsIgnoreCase("id") ? true : false;
        if (header == false) {
          Integer id = Integer.parseInt(fields[0]);
          String lastName = fields[1];
          String firstName = fields[2];
          String middleInitial = fields[3];
          String studentId = fields[4];
          String course = fields[5];
          int year = Integer.parseInt(fields[6]);
          char section = fields[7].charAt(0);
          String department = fields[8];
          students.put(id,
              new Student(studentId, lastName, firstName, middleInitial, course, year, section, department));
        }
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void displayAllStudentsRecord() {
    Scanner in = new Scanner(System.in);
    final int displayCountPerPage = 10;
    int totalRecords = students.size();
    int totalPages = (int) Math.ceil((double) totalRecords / displayCountPerPage);
    int currentPage = 1;

    List<Map.Entry<Integer, Student>> studentEntries = new ArrayList<>(students.entrySet());

    while (true) {
      new Clrscr();
      new Title();
      int start = (currentPage - 1) * displayCountPerPage;
      int end = Math.min(start + displayCountPerPage, totalRecords);

      System.out.printf("Student Masterlist\t\t\t\t\t\tShowing %d - %d entries  Page %d of %d\n", start+1, end, currentPage, totalPages);
      System.out.println(
          "-----------------------------------------------------------------------------------------------------------");
      System.out.printf("| %-20s | %-40s | %-20s | %-15s|\n", "Student Number", "Full Name", "Course Year-Section",
          "Department");
      System.out.println(
          "-----------------------------------------------------------------------------------------------------------");


      for (int i = start; i < end; i++) {
        Map.Entry<Integer, Student> entry = studentEntries.get(i);
        Student student = entry.getValue();

        String courseYearSection = String.format("%s %d-%c", student.getCourse(), student.getYear(),
            student.getSection());
        System.out.printf("| %-20s | %-40s | %-20s | %-15s|\n",
            student.getStudentId(),
            student.getFullName(),
            courseYearSection,
            student.getDepartment());
        System.out.println(
            "-----------------------------------------------------------------------------------------------------------");
      }

      if (currentPage == 1) {
        System.out.println("[N] Next Page | [Q] Quit");
      } else if (currentPage == totalPages) {
        System.out.println("[P] Previous Page | [Q] Quit");
      } else {
        System.out.println("[N] Next Page | [P] Previous Page | [Q] Quit");
      }

      String ch = in.nextLine().trim().toUpperCase();

      if (ch.equals("N")) {
        if (currentPage < totalPages) {
          currentPage++;
        }
      } else if (ch.equals("P")) {
        if (currentPage > 1) {
          currentPage--;
        }
      } else if (ch.equals("Q")) {
        break;
      }
    }
  }

  public boolean deleteStudentRecord() {
    new Clrscr();
    new Title();
    Scanner in = new Scanner(System.in);
    System.out.println("---------------------------------------------------");
    System.out.println("Administrative Panel -> Manage Account (Deleting)");
    System.out.println("---------------------------------------------------\n");

    System.out.print("Find student by ID: ");
    String studentId = in.nextLine();

    Student student = searchStudent(studentId);
    if (student == null) {
      System.out.println("Student does not exist.");
      return false;
    }

    while (true) {
      System.out.println("\nReview account details to be deleted");
      System.out.println("---------------------------------------------------");
      System.out.println(student.getStudentInfo());
      System.out.print("Are you sure you want to delete this student record? (Y/N): ");
      char ch = in.next().toUpperCase().charAt(0);
      if (ch == 'Y') {
        Iterator<Map.Entry<Integer, Student>> iterator = students.entrySet().iterator();
        while (iterator.hasNext()) {
          Map.Entry<Integer, Student> entry = iterator.next();
          if (entry.getValue().equals(student)) {
            iterator.remove();
          }
        }
        System.out.println("Student record deleted successfully.");
        break;
      } else if (ch == 'N') {
        System.out.println("Student record deleted unsuccessfully.");
        return false;
      }
      new Clrscr();
    }
    return true;
  }

  public void updateStudentsDatabase() {
    String origFilepath = "./res/data/masterlist/student_masterlist.csv";
    String tempFilepath = "./res/data/masterlist/student_masterlist_temp.csv";

    try {
      Scanner reader = new Scanner(new File(origFilepath));
      PrintWriter writer = new PrintWriter(new File(tempFilepath));

      String line = reader.nextLine();
      writer.print(line);

      students.keySet().forEach(key -> {
        Student student = students.get(key);
        int rollId = key;
        String lastName = student.getLastName().toUpperCase();
        String firstName = student.getFirstName().toUpperCase();
        String middleInitial = student.getMiddleInitial().toUpperCase();
        String studentNo = student.getStudentId().toUpperCase();
        String course = student.getCourse().toUpperCase();
        int year = student.getYear();
        char section = String.valueOf(student.getSection()).toUpperCase().charAt(0);
        String department = student.getDepartment().toUpperCase();
        writer.printf("\n%d,%s,%s,%s,%s,%s,%d,%c,%s", rollId, lastName, firstName, middleInitial, studentNo, course,
            year, section, department);
      });

      // int keyCount = 1;
      // for (Student student : students.values()) {

      // }

      reader.close();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    File originalFile = new File(origFilepath);
    File updatedFile = new File(tempFilepath);

    if (originalFile.delete()) {
      if (updatedFile.renameTo(originalFile)) {
        // 200: CSV file updated successfully
      } else {
        // 404R: Couldn't rename file
        System.out.println("[UPDATE STATUS: 404R]");
      }
    } else {
      // 404D: Couldn't delete file
      System.out.println("[UPDATE STATUS: 404D]");
    }
  }

  // GETTERS and SETTERS
  public Map<Integer, Student> getStudents() {
    return students;
  }

  public void setStudents(Map<Integer, Student> students) {
    this.students = students;
  }

}
