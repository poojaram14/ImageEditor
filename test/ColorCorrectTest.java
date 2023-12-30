import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import model.ColorCorrect;
import model.Image;
import model.Pixel;

import org.junit.Test;

import model.AdjustLevel;

/**
 * JUnit test class for the ColorCorrect and AdjustLevel classes.
 */
public class ColorCorrectTest {

  /**
   * Test the color correction operation on a sample image.
   */
  @Test
  public void testColorCorrection() {
    Image inputImage = createSampleImage();
    int knownPeakPosition = 150;
    ColorCorrect colorCorrect = new ColorCorrect(50);
    int[] mockedHistogram = new int[inputImage.getWidth()];
    for (int i = 0; i < mockedHistogram.length; i++) {
      mockedHistogram[i] = knownPeakPosition;
    }
    Image resultImage = colorCorrect.correctColor(inputImage);
    Pixel[][] resultPixels = resultImage.getPixels();
    for (int i = 0; i < resultImage.getHeight(); i++) {
      for (int j = 0; j < resultImage.getWidth(); j++) {
        assertEquals(knownPeakPosition, resultPixels[i][j].getRed());
        assertEquals(knownPeakPosition, resultPixels[i][j].getGreen());
        assertEquals(knownPeakPosition, resultPixels[i][j].getBlue());
      }
    }
  }

  /**
   * Create a sample image for testing.
   *
   * @return The sample Image object.
   */
  private Image createSampleImage() {
    int width = 3;
    int height = 3;
    Pixel[][] pixels = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        pixels[i][j] = new Pixel(100, 150, 200);
      }
    }
    Image image = new Image(width, height);
    image.setPixels(pixels);
    return image;
  }

  /**
   * Test case for an invalid number of input images.
   */
  @Test
  public void testInvalidNumberOfInputs() {
    ColorCorrect colorCorrect = new ColorCorrect(50);

    Image[] inputImages = {createSampleImage(), createSampleImage()};
    assertThrows(IllegalArgumentException.class, () -> {
      colorCorrect.apply(inputImages);
    });
  }

  /**
   * Test case for a negative split percentage.
   */
  @Test
  public void testNegativeSplitPercentage() {
    ColorCorrect colorCorrect = new ColorCorrect(-10);

    assertThrows(IllegalArgumentException.class, () -> {
      Image inputImage = createSampleImage();
      colorCorrect.correctColor(inputImage);
    });
  }

  /**
   * Test case for a split percentage greater than 100.
   */
  @Test
  public void testGreaterThan100Percentage() {
    ColorCorrect colorCorrect = new ColorCorrect(120);

    assertThrows(IllegalArgumentException.class, () -> {
      Image inputImage = createSampleImage();
      colorCorrect.correctColor(inputImage);
    });
  }

  /**
   * Test case for values out of range in the AdjustLevel constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testValuesOutOfRange() {
    new AdjustLevel(-10, 128, -300, 50);
  }

  /**
   * Test case for values out of range in the AdjustLevel constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testValuesOutOfRange2() {
    new AdjustLevel(1000, 128, 300, 50);
  }

  /**
   * Test case for values out of order in the AdjustLevel constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testValuesOutOfOrder() {
    new AdjustLevel(200, 150, 100, 50);
  }

  /**
   * Test case for values out of order in the AdjustLevel constructor.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testValuesOutOfOrder2() {
    new AdjustLevel(20, 15, 100, 50);
  }
}
