import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test for the RunScript class.
 */
public class RunScriptTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void testRunInteractiveMode() throws IOException {
    String[] args = {" -text"};
    RunScript.processCommand(args);
    String expectedOutput = "Entering interactive mode. Type commands or 'exit' to quit:\n";
    assertTrue(getOutput().contains(expectedOutput));
  }

  private String getOutput() {
    return outContent.toString();
  }

  @Test
  public void testRunScriptFile() throws IOException {
    String[] args = {"-file", "/Users/poojaramakrishnan/Downloads/Assignment_6/src/split.txt"};
    RunScript.processCommand(args);
    assertTrue(getOutput().contains("File executed successfully"));
  }

  @Test
  public void testUnsupportedCommand() throws IOException {
    String[] args = {"unsupported", "command"};
    RunScript.processCommand(args);
    assertEquals("Unsupported command or invalid arguments.\n", getOutput());
  }

  @Test
  public void testLaunchGUI() throws IOException {
    String[] args = {};
    RunScript.processCommand(args);
    view.ImageProcessingGUI gui = new view.ImageProcessingGUI();
    assertTrue(gui.isInitialized());
  }
}
