package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the operation to split an image into red, green, blue.
 */
public class SplitComponents implements ImageOperation {

  /**
   * Splits the image into red, green, blue components.
   *
   * @param inputs One or more input images on which the operation will be applied.
   * @return A list of images representing red, green, blue components of the image.
   * @throws IllegalArgumentException If more than one input image is provided.
   */
  @Override
  public List<Image> apply(Image... inputs) throws IllegalArgumentException {
    if (inputs.length != 1) {
      throw new IllegalArgumentException("Only one input image is required");
    }

    Image image = inputs[0];
    int width = image.getWidth();
    int height = image.getHeight();
    Pixel[][] imagePixel = image.getPixels();
    Pixel[][] redPixel = new Pixel[height][width];
    Pixel[][] greenPixel = new Pixel[height][width];
    Pixel[][] bluePixel = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel originalPixel = imagePixel[i][j];

        int red = originalPixel.getRed();
        int green = originalPixel.getGreen();
        int blue = originalPixel.getBlue();

        redPixel[i][j] = new Pixel(red, 0, 0);
        greenPixel[i][j] = new Pixel(0, green, 0);
        bluePixel[i][j] = new Pixel(0, 0, blue);
      }
    }

    Image redImage = new Image(width, height);
    redImage.setPixels(redPixel);

    Image greenImage = new Image(width, height);
    greenImage.setPixels(greenPixel);

    Image blueImage = new Image(width, height);
    blueImage.setPixels(bluePixel);

    List<Image> images = new ArrayList<>();
    images.add(redImage);
    images.add(greenImage);
    images.add(blueImage);

    return images;
  }
}
