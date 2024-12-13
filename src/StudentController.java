import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StudentController {
  private Map<Integer, Student> students;

  public StudentController() {
    this.students = new HashMap<>();
  }

  // GETTERS
  public Map<Integer, Student> getStudents() {
    return students;
  }

  // SETTERS
  public void setStudents(Map<Integer, Student> students) {
    this.students = students;
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
          students.put(id, new Student(studentId, lastName, firstName, middleInitial, course, year, section, department));
        }
      }
      reader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void updateStudentsDatabase() {
    
  }
}
