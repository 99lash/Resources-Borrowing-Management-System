public class Student {
  private String studentId;
  private String firstName;
  private String lastName;
  private String middleInitial;
  private String course;
  private int year;
  private char section;
  private String department;
  
  Student(String studentId, String lastName, String firstName, String middleInitial, String course, int year, char section, String department) {
    this.studentId = studentId;
    this.lastName = lastName;
    this.firstName = firstName;
    this.middleInitial = middleInitial;
    this.course = course;
    this.year = year;
    this.section = section;
    this.department = department;
  }

  // GETTERS
  public String getStudentId() {
    return studentId;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getMiddleInitial() {
    return middleInitial;
  }

  public String getFullName() {
    return String.format("%s, %s %s", lastName, firstName, middleInitial);
  }

  public String getCourse() {
    return course;
  }

  public int getYear() {
    return year;
  }

  public char getSection() {
    return section;
  }

  public String getDepartment() {
    return department;
  }

  public String getStudentInfo() {
    return String.format("Student ID: %s\nFull Name: %s, %s\nCourse/Year/Section: %s %d-%c", studentId, lastName, firstName, course, year, section);
  }

  // SETTERS
  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setCourse(String course) {
    this.course = course;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public void setSection(char section) {
    this.section = section;
  }

  public void setDepartment(String department) {
    this.department = department;
  }
}

/* 
 * 2. **Student**
   - Attributes:
     - studentId: String
     - name: String
     - email: String
   - Methods:
     - getStudentInfo(): String
 */