public class GitSmart {

  // A database in Derby, we'll call it "Git University"
  String databaseUrl = "jdbc:derby:/tmp/git_u.db;create=true";

  public static void main(String args[]) {

    try {
      GitSmart anInstanceOfTheApplication = new GitSmart();
      anInstanceOfTheApplication.runDDL();
      anInstanceOfTheApplication.dumpTables();
    } catch (Exception e) {
      System.err.println("" + e);
      e.printStackTrace();
    }

  }

  public void dumpTables() throws java.sql.SQLException {

    // Get a connection to (or create) the GitUniversity database
    java.sql.Connection databaseConnection = java.sql.DriverManager.getConnection(databaseUrl);

    // Dump the students table
    java.sql.Statement aDatabaseStatement = databaseConnection.createStatement();
    java.sql.ResultSet queryResultSet = aDatabaseStatement.executeQuery("SELECT * FROM students");
    while (queryResultSet.next()) { 
      System.out.println(
                  queryResultSet.getInt("student_id") + " " +
                  queryResultSet.getString("last_name") + " " + 
                  queryResultSet.getString("first_name")
      );    
    }

  }

  public void runDDL() throws java.sql.SQLException {

    // Get a connection to (or create) the GitUniversity database
    java.sql.Connection databaseConnection = java.sql.DriverManager.getConnection(databaseUrl);

    // Load the DDL containing all enrolled students
    java.sql.Statement aDatabaseStatement = databaseConnection.createStatement();
    aDatabaseStatement.executeUpdate("CREATE TABLE students (student_id int primary key, last_name varchar(30), first_name varchar(30))");
    aDatabaseStatement.executeUpdate("INSERT INTO students (1, 'McCartney', 'Paul'");
    aDatabaseStatement.executeUpdate("INSERT INTO students (2, 'Harrison', 'George'");
    aDatabaseStatement.executeUpdate("INSERT INTO students (3, 'Lennon', 'John'");
    aDatabaseStatement.executeUpdate("INSERT INTO students (4, 'Starr', 'Ringo'");

  }

}
