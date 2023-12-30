package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import model.Image;
import model.Pixel;
import model.CompressImage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * JUnit test class for the Compression class.
 */
public class CompressionTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final String validFilename = "/Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments"
      + "/Assignment4/Assignment4/src/images/test.jpg";

  /**
   * Test the compression of an image using the `compressImage` class.
   *
   * @throws IOException If an error occurs while reading the image.
   */
  @Test
  public void testCompressImage() throws IOException {
    Image originalImage = ImageUtil.readJPGorPNG(validFilename);
    assertNotNull(originalImage);

    CompressImage compressor = new CompressImage(50);
    Image compressedImage = compressor.apply(originalImage);
    assertNotNull(compressedImage);

    assertTrue(compressedImage.getWidth() <= originalImage.getWidth());
    assertTrue(compressedImage.getHeight() <= originalImage.getHeight());

    boolean pixelsChanged = false;
    for (int i = 0; i < originalImage.getHeight(); i++) {
      for (int j = 0; j < originalImage.getWidth(); j++) {
        Pixel originalPixel = originalImage.getPixel(j, i);
        Pixel compressedPixel = compressedImage.getPixel(j, i);

        if (!originalPixel.equals(compressedPixel)) {
          pixelsChanged = true;
          break;
        }
      }
      if (pixelsChanged) {
        break;
      }
    }

    assertTrue("Pixels should have changed after compression", pixelsChanged);
  }

  /**
   * Test compression with a zero percentage, ensuring that the image remains unchanged.
   *
   * @throws IOException If an error occurs while reading the image.
   */
  @Test
  public void testCompressionWithZeroPercentage() throws IOException {
    Image originalImage = ImageUtil.readJPGorPNG(validFilename);
    assertNotNull(originalImage);

    CompressImage compressor = new CompressImage(0);
    Image compressedImage = compressor.apply(originalImage);
    assertNotNull(compressedImage);

    assertEquals(originalImage.getWidth(), compressedImage.getWidth());
    assertEquals(originalImage.getHeight(), compressedImage.getHeight());

    for (int i = 0; i < originalImage.getHeight(); i++) {
      for (int j = 0; j < originalImage.getWidth(); j++) {
        Pixel originalPixel = originalImage.getPixel(j, i);
        Pixel compressedPixel = compressedImage.getPixel(j, i);
        assertEquals("Pixels should be equal", originalPixel, compressedPixel);
      }
    }
  }

  /**
   * Test compression with a full percentage, ensuring that the image becomes fully black.
   *
   * @throws IOException If an error occurs while reading the image.
   */
  @Test
  public void testCompressionWithFullPercentage() throws IOException {
    Image originalImage = ImageUtil.readJPGorPNG(validFilename);
    assertNotNull(originalImage);

    CompressImage compressor = new CompressImage(100);
    Image compressedImage = compressor.apply(originalImage);
    assertNotNull(compressedImage);

    for (int i = 0; i < compressedImage.getHeight(); i++) {
      for (int j = 0; j < compressedImage.getWidth(); j++) {
        Pixel pixel = compressedImage.getPixel(j, i);
        assertEquals("Red value should be 0 for full compression", 0, pixel.getRed());
        assertEquals("Green value should be 0 for full compression", 0, pixel.getGreen());
        assertEquals("Blue value should be 0 for full compression", 0, pixel.getBlue());
      }
    }
  }

  /**
   * Test compression with a null image, expecting an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCompressionWithNullImage() {
    new CompressImage(50).apply(null);
  }

  /**
   * Test compression on different image formats (JPEG and PNG).
   *
   * @throws IOException If an error occurs while reading the image.
   */
  @Test
  public void testCompressionOnDifferentFormats() throws IOException {
    String validFilenameJpeg = "/Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments/Assignment4/"
        + "Assignment4/src/images/test.jpg";
    CommandExecutor loadExecutorJpeg = new CommandExecutor(
        "load " + validFilenameJpeg + " jpegImage");
    loadExecutorJpeg.executeCommand();

    String validFilenamePng = "/Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments/Assignment4/"
        + "Assignment4/src/images/test.jpg";
    CommandExecutor loadExecutorPng = new CommandExecutor("load " + validFilenamePng + " pngImage");
    loadExecutorPng.executeCommand();

    Image jpegImage = ImageStorage.getImageByName("jpegImage");
    assertNotNull("JPEG image should not be null", jpegImage);

    Image pngImage = ImageStorage.getImageByName("pngImage");
    assertNotNull("PNG image should not be null", pngImage);

    CompressImage compressor = new CompressImage(50);
    Image compressedJPEG = compressor.apply(jpegImage);
    Image compressedPNG = compressor.apply(pngImage);

    assertNotNull("Compressed JPEG image should not be null", compressedJPEG);
    assertNotNull("Compressed PNG image should not be null", compressedPNG);
  }

  /**
   * Test compression on both square and non-square images.
   *
   * @throws IOException If an error occurs while reading the image.
   */
  @Test
  public void testCompressionOnSquareAndNonSquareImages() throws IOException {
    String validFilenameRect = "/Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments/Assignment4/"
        + "Assignment4/src/images/test.jpg";
    CommandExecutor loadExecutorRect = new CommandExecutor(
        "load " + validFilenameRect + " nonSquareImage");
    loadExecutorRect.executeCommand();

    String validFilenameSq = "/Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments/Assignment4/"
        + "Assignment4/src/images/koala-square.png";
    CommandExecutor loadExecutorSq = new CommandExecutor(
        "load " + validFilenameSq + " squareImage");
    loadExecutorSq.executeCommand();

    // Load square image
    Image squareImage = ImageStorage.getImageByName("squareImage");
    assertNotNull(squareImage);

    // Load non-square image
    Image nonSquareImage = ImageStorage.getImageByName("nonSquareImage");
    assertNotNull(nonSquareImage);

    // Compression
    CompressImage compressor = new CompressImage(50);
    Image compressedSquare = compressor.apply(squareImage);
    Image compressedNonSquare = compressor.apply(nonSquareImage);

    assertNotNull(compressedSquare);
    assertNotNull(compressedNonSquare);

    assertEquals(squareImage.getWidth(), compressedSquare.getWidth());
    assertEquals(nonSquareImage.getWidth(), compressedNonSquare.getWidth());
  }


}
