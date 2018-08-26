package com.github.cloudomatic.gitsmart;
     
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class StudentMatch {

  // A database in Derby to run the student match examples
  String databaseFilename = "/tmp/university.db";
  String databaseUrl = "jdbc:h2:" + databaseFilename;

  public static void main(String args[]) {
    try {
      StudentMatch studentMatcher = new StudentMatch();
      if (args.length != 2) studentMatcher.showUsage();
      else if ( (new String("--run-ddl")).equals(args[0]) ) {
        studentMatcher.runDDL(args[1]);
      } 
      else if ( (new String("--run-query")).equals(args[0]) ) {
        studentMatcher.runQuery(args[1]);
       }
      else if ( (new String("--run-match")).equals(args[0]) ) {
        studentMatcher.runRoommateMatch();
      }
    } catch (Exception e) {
      System.err.println("" + e);
      e.printStackTrace();
    }

  }

  public void showUsage() {
    System.out.println("");
    System.out.println("Usage: StudentMatch [[OPTIONS]]");
    System.out.println("");
    System.out.println("Options:");
    System.out.println("             --run-ddl <ddl-file>");
    System.out.println("             --run-query <SQL query>"); 
    System.out.println("             --run-student-match");
    System.out.println("");
  }

  private Student[] getListOfAllStudents() throws java.sql.SQLException {
    java.sql.Connection databaseConnection = DriverManager.getConnection(databaseUrl);
    java.sql.Statement sqlStatement = databaseConnection.createStatement();
    java.sql.ResultSet queryResultSet = sqlStatement.executeQuery("SELECT * FROM students");
    ResultSetMetaData resultSetMetaData = queryResultSet.getMetaData();
    List<Student> studentList = new ArrayList<Student>();
    while (queryResultSet.next()) {
      Student student = new Student();
      student.studentId = queryResultSet.getInt(1);
      student.lastName = queryResultSet.getString(2);
      student.firstName = queryResultSet.getString(3);
      studentList.add(student);
    }
    return studentList.toArray(new Student[studentList.size()]);
  }

  private Room[] getListOfAllRooms() throws java.sql.SQLException {
    java.sql.Connection databaseConnection = DriverManager.getConnection(databaseUrl);
    java.sql.Statement sqlStatement = databaseConnection.createStatement(); 
    java.sql.ResultSet queryResultSet = sqlStatement.executeQuery("SELECT * FROM dormitory_rooms");
    ResultSetMetaData resultSetMetaData = queryResultSet.getMetaData();
    List<Room> roomList = new ArrayList<Room>();
    while (queryResultSet.next()) {
      Room room = new Room();
      room.roomId = queryResultSet.getInt(1);
      room.dormName = queryResultSet.getString(2);
      room.roomNumber = queryResultSet.getInt(3);
      room.capacity = queryResultSet.getInt(4);
      roomList.add(room);
    }
    return roomList.toArray(new Room[roomList.size()]);
  }

  public void runRoommateMatch() throws java.sql.SQLException {
    System.out.println("StudentMatch.runRoommateMatch(): Running student match...");


    // Get an array of all students
    System.out.println("StudentMatch.runRoommateMatch(): Retrieving list of all students...");
    Student[] allStudents = getListOfAllStudents();
    System.out.println("StudentMatch.runRoommateMatch(): Retrieved " + allStudents.length + " student records from the database");

    // Get an array of all rooms
    System.out.println("StudentMatch.runRoommateMatch(): Retrieving list of all available dorm rooms...");
    Room[] allRooms = getListOfAllRooms();
    System.out.println("StudentMatch.runRoommateMatch(): Retrieved " + allStudents.length + " dormitory room records from the database");
    
    // Create an erray of results
    RoomAssignment[] roomAssignments = new RoomAssignment[allRooms.length];
    for (int iRoom = 0; iRoom < allRooms.length; iRoom++) {
      roomAssignments[iRoom] = new RoomAssignment();
      roomAssignments[iRoom].room = allRooms[iRoom];
    }
    
    int studentArrayPointer = 0;

    // For each room, assign students
    for (RoomAssignment roomAssignment : roomAssignments) {
      System.out.println("StudentMatch.runRoommateMatch(): Assigning students to room " + roomAssignment.room.dormName + " room #" + roomAssignment.room.roomNumber + " (capacity " +  roomAssignment.room.capacity + ")");
      for (int iBed = 0; iBed < roomAssignment.room.capacity; iBed++) {
          if (studentArrayPointer < allStudents.length ) {
            Student student = allStudents[studentArrayPointer++];
            if (roomAssignment.studentList == null)  roomAssignment.studentList = new ArrayList<Student>();
            roomAssignment.studentList.add(student);
            System.out.println("StudentMatch.runRoommateMatch(): Assigning slot " + iBed + " to " + student.lastName);
          } else {
            // No more students to assign
          }
      }
    }

    System.out.println("");
    System.out.println("List of room assignments for incoming students: ");
    System.out.println("-----------------------------------------------------------------");
    for (RoomAssignment assignedRoom : roomAssignments) {
      System.out.print(assignedRoom.room.dormName + " room #" + assignedRoom.room.roomNumber + ": ");
      if (assignedRoom.studentList == null) System.out.println("(empty)");
      else {
        for (Student student : assignedRoom.studentList) {
          System.out.print(student.lastName + ", ");
        }
        System.out.println();
      }
    }
    System.out.println("\n\n");


  }

  public void runQuery(String sqlQuery) throws SQLException {

    // Get a connection to the GitUniversity database
    System.out.println("GitSmart.runQuery(): Connecting to the database....");
    java.sql.Connection databaseConnection = DriverManager.getConnection(databaseUrl);

    // Create a statement object
    java.sql.Statement sqlStatement = databaseConnection.createStatement();

    // Execute the query
    System.out.println("GitSmart.runQuery(): Executing SQL query <" + sqlQuery + ">...");
    java.sql.ResultSet queryResultSet = sqlStatement.executeQuery(sqlQuery);

    // Determine the number of columns in the result set
     System.out.println("GitSmart.runQuery(): Retrieving results......");
    ResultSetMetaData resultSetMetaData = queryResultSet.getMetaData();
    int resultSetColumnCount = resultSetMetaData.getColumnCount(); 

    // Print a header with column names
    System.out.println("");
    for (int i = 1; i <= resultSetColumnCount; i++) {
        System.out.print(String.format(
                                       "%1$-" + "30" + "s",
                                       resultSetMetaData.getColumnName(i)
                         )
        );      
    }
    System.out.println("\n-----------------------------------------------------------------------------------------------------------------");
    
    // Parse the result set and print the values to stdout
    while (queryResultSet.next()) {
      for (int i = 1; i <= resultSetColumnCount; i++)  System.out.print(
                                                                         String.format("%1$-" + "30" + "s",
                                                                         queryResultSet.getString(i)
                                                                        )  
                                                       );
      System.out.println("");

    }
    System.out.println("\n");
  }

  public void runDDL(String ddlFilename) throws SQLException {

    // Create the university database
    java.sql.Connection databaseConnection = java.sql.DriverManager.getConnection(databaseUrl + ";create=true");

    // Create a statement object
    java.sql.Statement sqlStatement = databaseConnection.createStatement();

    // Locate the DDL file
    System.out.println("StudentMatch.runDDL(): Loading <" + ddlFilename + ">...");
    Path path = Paths.get(ddlFilename);

    // Load the DDL file
    try (BufferedReader ddlFileReader = Files.newBufferedReader(path)) {
      String line = null;

      // Read each line from the file
      while ((line = ddlFileReader.readLine()) != null) {
        System.out.println("StudentMatch.runDDL(): " + line);

        // If not a comment or empty string, run executeUpdate to execute the statement
        if (
             ! line.startsWith("#") &&
             ! (new String("")).equals(line)
          ) sqlStatement.executeUpdate(line);
      }      
    } catch (IOException ioe) {
      System.err.println("StudentMatch.runDDL(): Fatal error in loading or parsing DDL file <" + ddlFilename + ">");
      return;
    }
   System.out.println("StudentMatch.runDDL(): DDL parsing complete");
  }

}
