package model;

import model.enums.Direction;

/**
 * Class that represents an image operation that flips an image horizontally or vertically based on
 * the direction given.
 */
public class Flip implements ImageOperation {

  private Direction direction;

  /**
   * Constructs a `Flip` operation with the specified flip direction.
   *
   * @param direction The direction in which the image should be flipped (HORIZONTAL or VERTICAL).
   */
  public Flip(Direction direction) {
    this.direction = direction;
  }

  /**
   * Flips the input image horizontally or vertically based on the specified direction.
   *
   * @param inputs An array containing a single input image to be flipped.
   * @return The flipped image with the specified flip direction.
   * @throws IllegalArgumentException If the number of input images is not exactly one.
   */
  @Override
  public Image apply(Image... inputs) {
    if (inputs.length != 1) {
      throw new IllegalArgumentException("Only one input image is required");
    }

    Image img = inputs[0];
    int width = img.getWidth();
    int height = img.getHeight();
    Pixel[][] imagePixel = img.getPixels();
    Pixel[][] flipPixel = new Pixel[height][width];

    if (direction == Direction.HORIZONTAL) {
      for (int i = 0; i < height; i++) {
        int k = 0;
        for (int j = width - 1; j >= 0; j--) {
          flipPixel[i][k] = imagePixel[i][j];
          k++;
        }
      }
    } else if (direction == Direction.VERTICAL) {
      int k = height - 1;
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          flipPixel[k][j] = imagePixel[i][j];
        }
        k--;
      }
    }

    Image newImage = new Image(width, height);
    newImage.setPixels(flipPixel);
    return newImage;
  }
}

