package com.github.cloudomatic.gitsmart;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StudentMatch {

  // A database in Derby, we'll call it "Git University"
  String databaseUrl = "jdbc:derby:/tmp/git_university.db;create=true";

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
      else if ( (new String("--run-student-match")).equals(args[0]) ) {
        studentMatcher.runStudentMatch();
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

  public void runStudentMatch() {
    System.out.println("Running student match...");
    System.out.println("");
  }

  public void runQuery(String sqlQuery) throws SQLException {
    // Get a connection to the GitUniversity database
    System.out.println("GitSmart.dumpTables(): Connecting to the database....");
    java.sql.Connection databaseConnection = DriverManager.getConnection(databaseUrl);

    System.out.println("GitSmart.dumpTables(): Table [students] ::::::::::::::::::::::::::::::");
    java.sql.Statement sqlStatement = databaseConnection.createStatement();
    java.sql.ResultSet queryResultSet = sqlStatement.executeQuery("SELECT * FROM students");
    while (queryResultSet.next()) {
      System.out.println(
                  queryResultSet.getInt("student_id") + " " +
                  queryResultSet.getString("last_name") + " " +
                  queryResultSet.getString("first_name")
      );
    }
  }

  public void runDDL(String ddlFilename) throws SQLException {

    // Get a connection to (or create) the GitUniversity database
    java.sql.Connection databaseConnection = java.sql.DriverManager.getConnection(databaseUrl);


    // Load the DDL containing all enrolled students
    java.sql.Statement sqlStatement = databaseConnection.createStatement();


/*

    Path path = Paths.get(ddlFilename);
    try (BufferedReader ddlFileReader = Files.newBufferedReader(path, ENCODING)){
      String line = null;
      while ((line = ddlFileReader.readLine()) != null) {
        if (! line.startsWith("#") ) sqlStatement.executeUpdate(line);
      }      
    }
*/
    //sqlStatement.executeUpdate("DROP TABLE STUDENTS");
    sqlStatement.executeUpdate("CREATE TABLE STUDENTS (student_id int primary key, last_name varchar(30), first_name varchar(30))");
    sqlStatement.executeUpdate("INSERT INTO students values (1, 'McCartney', 'Paul'");
    sqlStatement.executeUpdate("INSERT INTO students values (2, 'Harrison', 'George'");
    sqlStatement.executeUpdate("INSERT INTO students values (3, 'Lennon', 'John'");
    sqlStatement.executeUpdate("INSERT INTO students values (4, 'Starr', 'Ringo'");
  }

}
