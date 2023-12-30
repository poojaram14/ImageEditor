import controller.CommandExecutor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.SwingUtilities;
import view.ImageProcessingGUI;

/**
 * Implementation of the ScriptProcessor interface to process and execute script commands.
 */
public class RunScript {

  /**
   * The entry point of the script processor. It decides whether to run the program in interactive
   * mode or execute a script file based on the command line arguments.
   *
   * @param args Command line arguments passed to the program.
   * @throws IOException If an I/O error occurs during script processing.
   */
  public static void main(String[] args) throws IOException {
    processCommand(args);
  }

  /**
   * Processes command line arguments to determine the mode of operation (interactive or script file
   * execution).
   *
   * @param args Array of command line arguments.
   * @throws IOException If an I/O error occurs during command processing.
   */
  public static void processCommand(String[] args) throws IOException {
    if (args.length == 2 && args[0].equals("-file")) {
      runScriptFile(args[1]);
    } else if (args.length == 1 && args[0].equals("-text")) {
      runInteractiveMode();
    } else if (args.length == 0) {
      openGUI();
    } else {
      System.out.println("Unsupported command or invalid arguments.");
    }
  }

  /**
   * Runs the program in interactive mode, accepting commands from the console.
   *
   * @throws IOException If an I/O error occurs while reading commands.
   */
  private static void runInteractiveMode() throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("Entering interactive mode. Type commands or 'exit' to quit:");
    String line;
    while (!(line = reader.readLine()).equalsIgnoreCase("exit")) {
      try {
        CommandExecutor executor = new CommandExecutor(line.trim());
        executor.executeCommand();
        System.out.println("Command executed successfully.");
      } catch (Exception e) {
        System.out.println("Error executing command: " + e.getMessage());
      }
    }
    System.out.println("Exiting interactive mode.");
  }

  /**
   * Executes commands from a script file.
   *
   * @param filename The path to the script file containing commands.
   * @throws IOException If an I/O error occurs while reading from the script file.
   */
  private static void runScriptFile(String filename) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = br.readLine()) != null) {
        try {
          CommandExecutor executor = new CommandExecutor(line.trim());
          executor.executeCommand();
          System.out.println("Command executed successfully.");
        } catch (Exception e) {
          System.out.println("Error executing command from file: " + e.getMessage());
        }
      }
      System.out.println("File executed successfully");
    } catch (FileNotFoundException e) {
      System.out.println("Script file not found: " + filename);
    }
  }

  private static void openGUI() {
    SwingUtilities.invokeLater(() -> {
      ImageProcessingGUI gui = new ImageProcessingGUI();
      gui.setVisible(true);
    });
  }
}