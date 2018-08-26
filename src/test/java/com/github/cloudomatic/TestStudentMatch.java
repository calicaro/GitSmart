package com.github.cloudomatic.gitsmart;

import junit.framework.Assert;
import org.junit.Test;
import java.io.File;
import java.sql.SQLException;

public class TestStudentMatch {

  @Test
  public void testDatabaseSetup() throws java.sql.SQLException {
    StudentMatch studentMatcher = new StudentMatch();
    studentMatcher.showUsage();

    // Locate the DDL, and run it from the /tmp folder
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("university.ddl").getFile());
    System.out.println(file.getAbsolutePath());
    studentMatcher.runDDL(file.getAbsolutePath());
    //Assert.assertEquals(output, "value");

  }

}
