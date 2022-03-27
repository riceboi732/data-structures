import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Main file for running automated tests on repl.it.
 * 
 * If no command line argument is given, assumes there is a file name 
 * Tests.txt in the current directory. If a command line argument is
 * given, then that file is used as the tests file. If last argument is 
 * --verbose, then prints the stack trace for failures in addition to 
 * a message.
 *
 * Test file should have one test per line. If a # is present in the line,
 * that is assumed to separate the class name from the test method name.
 * If no # is present, then the line is assumed to represent the class name
 * and all tests in that class will be run.
 *
 * @author Anna Rafferty
 */
public class Main {
  private static boolean verbose = false;

  /**
   * Uses Junit to run the given test and print out either that the test
   * passed or information about the failure(s). If testMethodName is null,
   * this will run all tests in the class named testClassName.
   */
  public static boolean runTest(String testClassName, String testMethodName) {
    try {
      Request request;
      Class testClass = Class.forName(testClassName);
      if(testMethodName == null) {
        request = Request.aClass(testClass);
      } else {
        request = Request.method(testClass, testMethodName);
      }

      Result result = new JUnitCore().run(request);
      java.util.List<Failure> failures = result.getFailures();
      System.out.print(testClassName + getTestMethodDisplayText(testMethodName) + " ");
      if(failures.isEmpty()) {
        System.out.println("passed.");
      } else {
        System.out.println("had failures:");
        for(Failure failure : failures) {
          System.err.println(failure);
          if(verbose) {
            System.err.println(failure.getTrace());
          }
        }
      }
      return result.wasSuccessful();

    } catch(Exception e) {
      System.err.println("Failed to load a test");
      e.printStackTrace();
    }
    return false;
  }
  
  /**
   * Returns an empty string if testMethodName is null, and otherwise
   * prepends # to the string.
   */
  private static String getTestMethodDisplayText(String testMethodName) {
    if(testMethodName == null) {
      return "";
    } else {
      return "#" + testMethodName;
    }
  }

  /**
   * Returns testLine with no leading/trailing spaces and with any
   * test following // (comment) removed.
   */
  private static String removeCommentFromTest(String testLine) {
    int commentStart = testLine.indexOf("//");
    if (commentStart >= 0) {
      testLine = testLine.substring(0,commentStart);
    }
    return testLine.trim();
  }


  /**
   * Runs all tests specified in testNamesFile. See 
   * top of class for proper format for this file.
   */
  public static void runTests(String testNamesFile) {
    Scanner scanner = null;
    try {
      boolean allPassed = true;

      scanner = new Scanner(new File(testNamesFile));
      while(scanner.hasNextLine()) {
        String line = scanner.nextLine();
        line = removeCommentFromTest(line);
        if(line.length() > 0) {
          String[] testClassAndMethod = line.split("#");
          String className = testClassAndMethod[0];
          String methodName = null;
          if (testClassAndMethod.length > 1) {
            methodName = testClassAndMethod[1];
          }
          boolean passed = runTest(className, methodName);
          allPassed = allPassed && passed;
        }
        
      }
      System.out.println("All tests passed: " + allPassed);
    } catch (FileNotFoundException e) {
      System.err.println("Test file not found.");
      e.printStackTrace();
    } finally {
      if(scanner != null) {
        scanner.close();
      }
    }

  }

  /**
   * Runs test in file given by first commandline argument,
   * or if no argument is given, runs tests in Tests.txt.
   * If last argument is --verbose, then prints the stack trace
   * for failures.
   */
  public static void main(String[] args) {
    String testsFile = "Tests.txt";
    if(args.length > 0 && args[args.length - 1].equals("--verbose")) {
      Main.verbose = true;
    }
    if(args.length > 0 && !args[0].startsWith("--")) {
      testsFile = args[0];
    }
    runTests(testsFile);
  }
}