import java.io.IOException;

/**
 * This interface represents the contract for running scripts.
 */
public interface ScriptProcessor {

  /**
   * Processes the provided command arguments to execute the appropriate command. If the first
   * argument is "run", it expects a second argument which is the path to a script file. The method
   * will read the script file and process each line using a controller.CommandExecutor. If no
   * arguments are provided or if the arguments do not follow the expected format, appropriate error
   * messages will be displayed.
   *
   * @param args An array of command-line arguments.
   * @throws IOException if there's an issue reading the script file.
   */
  void processCommand(String[] args) throws IOException;
}
