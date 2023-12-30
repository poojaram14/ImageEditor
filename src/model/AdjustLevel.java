package model;

import java.awt.image.BufferedImage;

/**
 * This class represents an image operation for adjusting levels in an image.
 */
public class AdjustLevel implements ImageOperation {


  private double a;
  private double b;
  private double c;
  private int splitPercentage = 100;

  /**
   * Constructs an AdjustLevel operation with specified black, mid, and white values.
   *
   * @param black The black level.
   * @param mid   The mid level.
   * @param white The white level.
   */
  public AdjustLevel(double black, double mid, double white) {
    if (black < 0 || black > 255 || mid < 0 || mid > 255 || white < 0 || white > 255) {
      throw new IllegalArgumentException("Adjustment level should be in the range 0-255");
    }
    if (!(black <= mid && mid <= white)) {
      throw new IllegalArgumentException(
          "Adjustment level should be in the order: black, mid, white");
    }
    this.splitPercentage = 100;
  }

  /**
   * Constructs an AdjustLevel operation with specified black, mid, white, and split percentage
   * values.
   *
   * @param black           The black level.
   * @param mid             The mid level.
   * @param white           The white level.
   * @param splitPercentage The split percentage for processing.
   */
  public AdjustLevel(double black, double mid, double white, int splitPercentage) {
    if (black < 0 || black > 255 || mid < 0 || mid > 255 || white < 0 || white > 255) {
      throw new IllegalArgumentException("Adjustment level should be in the range 0-255");
    }
    if (!(black <= mid && mid <= white)) {
      throw new IllegalArgumentException(
          "Adjustment level should be in the order: black, mid, white");
    }
    black = Math.max(0, Math.min(black, 255));
    mid = Math.max(0, Math.min(mid, 255));
    white = Math.max(0, Math.min(white, 255));
    this.splitPercentage = splitPercentage;

    double x =
        black * black * (mid - white) - black * (mid * mid - white * white) + mid * mid * white
            - mid * white * white;
    double aA = -black * (128 - 255) + 128 * white - 255 * mid;
    a = aA / x;
    double bA = black * black * (128 - 255) + 255 * mid * mid - 128 * white * white;
    b = bA / x;
    double cA =
        black * black * (255 * mid - 128 * white) - black * (255 * mid * mid - 128 * white * white);
    c = cA / x;
  }

  @Override
  public Image apply(Image... inputs) {
    if (inputs.length == 0 || inputs[0] == null) {
      throw new IllegalArgumentException("Input image must not be null");
    }

    Image inputImage = inputs[0];

    int width = inputImage.getWidth();
    int height = inputImage.getHeight();
    int splitPoint = (splitPercentage > 0) ? (int) (width * splitPercentage / 100.0) : width;
    BufferedImage adjustedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Pixel pixel = inputImage.getPixels()[y][x];
        int red = pixel.getRed();
        int green = pixel.getGreen();
        int blue = pixel.getBlue();

        if (x < splitPoint) {
          red = applyAdjustment(red);
          green = applyAdjustment(green);
          blue = applyAdjustment(blue);
        }

        if (splitPercentage > 0 && x == splitPoint) {
          adjustedImage.setRGB(x, y, new Pixel(0, 0, 0).toRGB()); // Black line
        } else {
          adjustedImage.setRGB(x, y, new Pixel(red, green, blue).toRGB());
        }
      }
    }

    return convertBufferedImageToImage(adjustedImage);
  }

  private Image convertBufferedImageToImage(BufferedImage bufferedImage) {
    Image image = new Image(bufferedImage.getWidth(), bufferedImage.getHeight());
    for (int x = 0; x < bufferedImage.getWidth(); x++) {
      for (int y = 0; y < bufferedImage.getHeight(); y++) {
        int rgb = bufferedImage.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        image.getPixels()[y][x] = new Pixel(red, green, blue);
      }
    }
    return image;
  }

  private int applyAdjustment(int value) {
    double input = value;
    double adjustedValue = a * input * input + b * input + c;
    adjustedValue = Math.max(0, Math.min(adjustedValue, 255));

    return (int) adjustedValue;
  }
}
