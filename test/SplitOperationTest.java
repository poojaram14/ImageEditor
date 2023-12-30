import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import controller.ImageUtil;

import java.io.IOException;

import model.Image;
import model.Pixel;
import model.Blur;

import org.junit.Test;

/**
 * JUnit test class for the SplitOperation class.
 */
public class SplitOperationTest {
  private static final double TOLERANCE = 0.05; // Tolerance for comparing pixel values

  private final String validFilename = "/Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments"
          + "/Assignment4/Assignment4/src/images/test.jpg";

  /**
   * Test the creation of a Blur object with a valid split percentage.
   */
  @Test
  public void testBlurWithValidSplitPercentage() {
    try {
      Blur blur = new Blur(50);
      // Optionally check if the blur object is created correctly
      assertNotNull(blur);
    } catch (IllegalArgumentException e) {
      fail("Should not have thrown IllegalArgumentException for valid percentage");
    }
  }

  /**
   * Test the application of blur with split on an image.
   *
   * @throws IOException If an error occurs while reading the image.
   */
  @Test
  public void testBlurWithSplit() throws IOException {
    Image originalImage = ImageUtil.readJPGorPNG(validFilename);
    assertNotNull(originalImage);

    int width = originalImage.getWidth();
    int height = originalImage.getHeight();

    // Apply blur with split
    int splitPercentage = 50;
    Blur blurFilter = new Blur(splitPercentage);
    Image blurredImage = blurFilter.apply(originalImage);

    assertNotNull(blurredImage);
    assertEquals(width, blurredImage.getWidth());
    assertEquals(height, blurredImage.getHeight());
    int splitPos = (int) ((double) width * (splitPercentage / 100.0));
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel originalPixel = originalImage.getPixel(j, i);
        Pixel blurredPixel = blurredImage.getPixel(j, i);
        if (j < splitPos) {
          assertNotEquals(originalImage.getPixel(j, i), blurredImage.getPixel(j, i));
        } else {
          assertEquals(originalPixel, blurredPixel);
        }
        if (j == splitPos) {
          assertEquals(new Pixel(0, 0, 0), blurredPixel);
        }
      }
    }
  }
}
