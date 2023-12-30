package view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

/**
 * This class provides utility functions for image processing. It includes methods for image
 * comparison, file extension retrieval, error dialog display, image conversion, and more.
 */
public class ImageProcessingUtilities extends JFrame {

  private static JSlider splitPercentageSlider;
  private static int splitPercentage;

  /**
   * Compares two images to determine if they are different.
   *
   * @param originalImage The original image.
   * @param currentImage  The current image to compare with the original.
   * @return true if the images are different, false otherwise.
   */
  public static boolean isImageChanged(BufferedImage originalImage, BufferedImage currentImage) {
    if (originalImage == null || currentImage == null) {
      return false;
    }

    if (originalImage.getWidth() != currentImage.getWidth() ||
        originalImage.getHeight() != currentImage.getHeight()) {
      return true;
    }

    for (int y = 0; y < originalImage.getHeight(); y++) {
      for (int x = 0; x < originalImage.getWidth(); x++) {
        if (originalImage.getRGB(x, y) != currentImage.getRGB(x, y)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Retrieves the file extension of a given file.
   *
   * @param file The file whose extension is to be retrieved.
   * @return The file extension, or null if it does not exist.
   */
  public static String getFileExtension(File file) {
    String name = file.getName();
    int lastIndexOf = name.lastIndexOf(".");
    if (lastIndexOf == -1) {
      return null;
    }
    return name.substring(lastIndexOf + 1).toLowerCase();
  }

  /**
   * Displays an error dialog with a custom message.
   *
   * @param e         The exception to display in the error dialog.
   * @param operation The name of the operation during which the error occurred.
   */
  public static void showErrorDialog(Exception e, String operation) {
    JOptionPane.showMessageDialog(
        null,
        "Error during " + operation + ": " + e.getMessage(),
        "Error",
        JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Prompts the user to apply an operation to the whole image or use split view.
   *
   * @param operation The name of the operation to apply.
   * @return An Option indicating the user's choice.
   */
  public static Option applyToWholeImage(String operation) {
    int choice = JOptionPane.showOptionDialog(
        null,
        "Do you want to apply " + operation + " to the whole image or use split view?",
        operation + " Options",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        new String[]{"Whole Image", "Split View"},
        "Whole Image");

    if (choice == JOptionPane.CLOSED_OPTION) {
      return Option.EXIT;
    }

    boolean applyToWholeImage = (choice == JOptionPane.YES_OPTION);

    if (!applyToWholeImage) {
      splitPercentageSlider = showSplitSlider();
      if (splitPercentageSlider == null) {
        return Option.EXIT;
      }
      splitPercentage = splitPercentageSlider.getValue();
    } else {
      splitPercentage = 100;
    }
    ImageProcessingGUI.setPercentage(splitPercentage);
    if (applyToWholeImage) {
      return Option.FULL;
    } else {
      return Option.SPLIT;
    }
  }

  /**
   * Converts a custom Image model to a BufferedImage.
   *
   * @param image The custom Image model to convert.
   * @return The converted BufferedImage.
   */
  public static BufferedImage convertToBufferedImage(model.Image image) {
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        model.Pixel pixel = image.getPixel(j, i);
        int rgb = new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue()).getRGB();
        bufferedImage.setRGB(j, i, rgb);
      }
    }
    return bufferedImage;
  }

  /**
   * Converts a BufferedImage to a custom Image model.
   *
   * @param bufferedImage The BufferedImage to convert.
   * @return The custom Image model.
   */
  public static model.Image convertToImageModel(BufferedImage bufferedImage) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    model.Pixel[][] pixels = new model.Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rgb = bufferedImage.getRGB(j, i);
        Color color = new Color(rgb);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        pixels[i][j] = new model.Pixel(r, g, b);
      }
    }
    model.Image img = new model.Image(width, height);
    img.setPixels(pixels);
    return img;
  }

  /**
   * Creates a deep copy of a BufferedImage.
   *
   * @param bi The BufferedImage to copy.
   * @return A deep copy of the BufferedImage.
   */
  public static BufferedImage deepCopy(BufferedImage bi) {
    ColorModel cm = bi.getColorModel();
    boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
    WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
    return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
  }

  /**
   * Displays a slider for selecting a percentage value.
   *
   * @return The JSlider for percentage selection, or null if canceled.
   */
  public static JSlider showSplitSlider() {
    JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
    slider.setMajorTickSpacing(10);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    int result = JOptionPane.showOptionDialog(
        null,
        slider,
        "Select Split Percentage to be applied",
        JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.PLAIN_MESSAGE,
        null,
        null,
        null);

    if (result == JOptionPane.OK_OPTION) {
      return slider;
    } else {
      return null;
    }
  }

}