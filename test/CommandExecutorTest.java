import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.CommandExecutor;
import controller.ImageStorage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import model.Image;
import model.Pixel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * A Junit test for the application controller and model.
 */
public class CommandExecutorTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final String validFilename = "/Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments"
      + "/Assignment4/Assignment4/src/images/test.jpg";
  private final String sampleImageName = "testImage";
  ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  PrintStream originalOut = System.out;

  private void insertSampleImageToStorage() {
    try {
      CommandExecutor executor = new CommandExecutor(
          "load " + validFilename + " " + sampleImageName);
      executor.executeCommand();

      Image loadedImage = ImageStorage.getImageByName(sampleImageName);
      assertNotNull(loadedImage);

    } catch (Exception e) {
      fail("Invalid image.");
    }
  }

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
    insertSampleImageToStorage();
    assertNotNull(ImageStorage.getImageByName(sampleImageName));
  }


  @Test
  public void testBrightenWithValidImageName() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "brighten 10 " + sampleImageName + " brightenedImage");
    executor.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image brightenedImage = ImageStorage.getImageByName("brightenedImage");
    assertNotNull(brightenedImage);
    for (int i = 0; i < originalImage.getHeight(); i++) {
      for (int j = 0; j < originalImage.getWidth(); j++) {
        Pixel originalPixel = originalImage.getPixels()[i][j];
        Pixel brightenedPixel = brightenedImage.getPixels()[i][j];

        assertTrue(brightenedPixel.getRed() - originalPixel.getRed() >= 0
            && brightenedPixel.getRed() - originalPixel.getRed() <= 10);
        assertTrue(brightenedPixel.getGreen() - originalPixel.getGreen() >= 0
            && brightenedPixel.getGreen() - originalPixel.getGreen() <= 10);
        assertTrue(brightenedPixel.getBlue() - originalPixel.getBlue() >= 0
            && brightenedPixel.getBlue() - originalPixel.getBlue() <= 10);
      }
    }
  }

  @Test
  public void testFlipHorizontal() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "horizontal " + sampleImageName + " horizontalImage");
    executor.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image horizontalImage = ImageStorage.getImageByName("horizontalImage");
    assertNotNull(horizontalImage);
    Pixel[][] originalPixels = originalImage.getPixels();
    Pixel[][] flippedPixels = horizontalImage.getPixels();
    assertEquals(originalImage.getWidth(), horizontalImage.getWidth());
    assertEquals(originalImage.getHeight(), horizontalImage.getHeight());

    for (int i = 0; i < originalImage.getHeight(); i++) {
      for (int j = 0, k = originalImage.getWidth() - 1; j < originalImage.getWidth(); j++, k--) {
        assertEquals(originalPixels[i][j], flippedPixels[i][k]);
      }
    }
  }

  @Test
  public void testFlipVertical() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "vertical-flip " + sampleImageName + " verticalImage");
    executor.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image verticalImage = ImageStorage.getImageByName("verticalImage");
    assertNotNull(verticalImage);
    Pixel[][] originalPixels = originalImage.getPixels();
    Pixel[][] flippedPixels = verticalImage.getPixels();
    assertEquals(originalImage.getWidth(), verticalImage.getWidth());
    assertEquals(originalImage.getHeight(), verticalImage.getHeight());

    for (int i = 0, k = originalImage.getHeight() - 1; i < originalImage.getHeight(); i++, k--) {
      for (int j = 0; j < originalImage.getWidth(); j++) {
        assertEquals(originalPixels[i][j], flippedPixels[k][j]);
      }
    }
  }

  @Test
  public void testHorizontalVerticalFlips() throws IOException {
    CommandExecutor executorH = new CommandExecutor(
        "horizontal " + sampleImageName + " horizontalImage");
    executorH.executeCommand();
    CommandExecutor executorV = new CommandExecutor(
        "vertical " + "horizontalImage" + " horizontalVerticalImage");
    executorV.executeCommand();

    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image flippedImage = ImageStorage.getImageByName("horizontalVerticalImage");

    assertNotNull(flippedImage);

    Pixel[][] originalPixels = originalImage.getPixels();
    Pixel[][] flippedPixels = flippedImage.getPixels();

    assertEquals(originalImage.getWidth(), flippedImage.getWidth());
    assertEquals(originalImage.getHeight(), flippedImage.getHeight());

    for (int i = 0, m = originalImage.getHeight() - 1; i < originalImage.getHeight(); i++, m--) {
      for (int j = 0, k = originalImage.getWidth() - 1; j < originalImage.getWidth(); j++, k--) {
        assertEquals(originalPixels[i][j], flippedPixels[m][k]);
      }
    }
  }


  @Test
  public void testValue() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "value " + sampleImageName + " valueImage");
    executor.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image valueImage = ImageStorage.getImageByName("valueImage");
    assertNotNull(valueImage);

    Pixel[][] originalPixels = originalImage.getPixels();
    Pixel[][] valuePixels = valueImage.getPixels();

    for (int i = 0; i < originalImage.getHeight(); i++) {
      for (int j = 0; j < originalImage.getWidth(); j++) {
        Pixel op = originalPixels[i][j];
        int pix = Math.max(op.getRed(), Math.max(op.getGreen(), op.getBlue()));
        assertEquals(valuePixels[i][j].getRed(), pix);
        assertEquals(valuePixels[i][j].getGreen(), pix);
        assertEquals(valuePixels[i][j].getBlue(), pix);
      }
    }

  }

  @Test
  public void testIntensity() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "intensity " + sampleImageName + " intensityImage");
    executor.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image intensityImage = ImageStorage.getImageByName("intensityImage");
    assertNotNull(intensityImage);

    Pixel[][] originalPixels = originalImage.getPixels();
    Pixel[][] intensityPixels = intensityImage.getPixels();

    for (int i = 0; i < originalImage.getHeight(); i++) {
      for (int j = 0; j < originalImage.getWidth(); j++) {
        Pixel op = originalPixels[i][j];
        int pix = (op.getRed() + op.getGreen() + op.getBlue()) / 3;
        assertEquals(intensityPixels[i][j].getRed(), pix);
        assertEquals(intensityPixels[i][j].getGreen(), pix);
        assertEquals(intensityPixels[i][j].getBlue(), pix);
      }
    }
  }

  @Test
  public void testLuma() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "luma " + sampleImageName + " lumaImage");
    executor.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image lumaImage = ImageStorage.getImageByName("lumaImage");
    assertNotNull(lumaImage);

    Pixel[][] originalPixels = originalImage.getPixels();
    Pixel[][] lumaPixels = lumaImage.getPixels();

    for (int i = 0; i < originalImage.getHeight(); i++) {
      for (int j = 0; j < originalImage.getWidth(); j++) {
        Pixel op = originalPixels[i][j];
        int pix = (int) (0.2126 * op.getRed() + 0.7152 * op.getGreen() + 0.0722 * op.getBlue());
        assertEquals(lumaPixels[i][j].getRed(), pix);
        assertEquals(lumaPixels[i][j].getGreen(), pix);
        assertEquals(lumaPixels[i][j].getBlue(), pix);
      }
    }
  }

  @Test
  public void testVisualizeRed() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "red " + sampleImageName + " redImage");
    executor.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image redImage = ImageStorage.getImageByName("redImage");
    assertNotNull(redImage);

    Pixel[][] originalPixels = originalImage.getPixels();
    Pixel[][] redPixels = redImage.getPixels();

    for (int i = 0; i < originalImage.getHeight(); i++) {
      for (int j = 0; j < originalImage.getWidth(); j++) {
        Pixel op = originalPixels[i][j];
        assertEquals(redPixels[i][j].getRed(), op.getRed());
        assertEquals(redPixels[i][j].getGreen(), 0);
        assertEquals(redPixels[i][j].getBlue(), 0);
      }
    }
  }

  @Test
  public void testVisualizeGreen() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "green " + sampleImageName + " greenImage");
    executor.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image greenImage = ImageStorage.getImageByName("greenImage");
    assertNotNull(greenImage);

    Pixel[][] originalPixels = originalImage.getPixels();
    Pixel[][] greenPixels = greenImage.getPixels();

    for (int i = 0; i < originalImage.getHeight(); i++) {
      for (int j = 0; j < originalImage.getWidth(); j++) {
        Pixel op = originalPixels[i][j];
        assertEquals(greenPixels[i][j].getRed(), 0);
        assertEquals(greenPixels[i][j].getGreen(), op.getGreen());
        assertEquals(greenPixels[i][j].getBlue(), 0);
      }
    }
  }

  @Test
  public void testVisualizeBlue() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "blue " + sampleImageName + " blueImage");
    executor.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image blueImage = ImageStorage.getImageByName("blueImage");
    assertNotNull(blueImage);

    Pixel[][] originalPixels = originalImage.getPixels();
    Pixel[][] bluePixels = blueImage.getPixels();

    for (int i = 0; i < originalImage.getHeight(); i++) {
      for (int j = 0; j < originalImage.getWidth(); j++) {
        Pixel op = originalPixels[i][j];
        assertEquals(bluePixels[i][j].getRed(), 0);
        assertEquals(bluePixels[i][j].getGreen(), 0);
        assertEquals(bluePixels[i][j].getBlue(), op.getBlue());
      }
    }
  }

  @Test
  public void testRGBSplitAndCombine() throws IOException {
    CommandExecutor splitExecutor = new CommandExecutor(
        "rgb-split " + sampleImageName + " redSplitImage greenSplitImage blueSplitImage");
    splitExecutor.executeCommand();

    assertNotNull(ImageStorage.getImageByName("redSplitImage"));
    assertNotNull(ImageStorage.getImageByName("greenSplitImage"));
    assertNotNull(ImageStorage.getImageByName("blueSplitImage"));

    CommandExecutor combineExecutor = new CommandExecutor(
        "rgb-combine combinedImage redSplitImage greenSplitImage blueSplitImage");
    combineExecutor.executeCommand();

    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image combinedImage = ImageStorage.getImageByName("combinedImage");

    assertNotNull(combinedImage);
    assertEquals(originalImage.getWidth(), combinedImage.getWidth());
    assertEquals(originalImage.getHeight(), combinedImage.getHeight());

    Pixel[][] originalPixels = originalImage.getPixels();
    Pixel[][] combinedPixels = combinedImage.getPixels();

    for (int i = 0; i < originalImage.getHeight(); i++) {
      for (int j = 0; j < originalImage.getWidth(); j++) {
        Pixel op = originalPixels[i][j];
        Pixel cp = combinedPixels[i][j];
        assertEquals(op, cp);
      }
    }
  }

  @Test
  public void testRGBCombineWithLessParams() throws IOException {
    try {
      CommandExecutor combineExecutor = new CommandExecutor(
          "rgb-combine combinedImage redSplitImage blueSplitImage");
      combineExecutor.executeCommand();
    } catch (Exception e) {
      fail("Invalid image.");
    }
  }

  @Test
  public void testBrightenWithInvalidImageName() throws IOException {
    String invalidImageName = "invalidImage";
    CommandExecutor executor = new CommandExecutor(
        "brighten 10 " + invalidImageName + " brightenedImage");
    executor.executeCommand();
    System.setOut(originalOut);
    assertTrue(outContent.toString().contains("Source image not found: " + invalidImageName));
  }

  @Test
  public void testInvalidCommand() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "dim 10 " + sampleImageName + " dimmedImage");
    executor.executeCommand();
    System.setOut(originalOut);
    assertTrue(outContent.toString().contains("Command not found."));
  }

  @Test
  public void testCompressWithInvalidImageName() throws IOException {
    String invalidImageName = "invalidImage";
    CommandExecutor executor = new CommandExecutor(
        "compress 40 " + invalidImageName + " compressedImage");
    executor.executeCommand();
    System.setOut(originalOut);
    assertTrue(outContent.toString().contains("Source image not found: "));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressImageWithNegativePercentage() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "compress -40 " + validFilename + " compressedImage");
    executor.executeCommand();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCompressImageWithGreaterThanHundredPercentage() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "compress 140 " + validFilename + " compressedImage");
    executor.executeCommand();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurWithNegativeSplitPercentage() throws IOException {
    CommandExecutor executor = new CommandExecutor("blur someImageName blurredImage -10");
    executor.executeCommand();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurWithSplitPercentageOver100() throws IOException {
    CommandExecutor executor = new CommandExecutor("blur 110 someImageName blurredImage");
    executor.executeCommand();
  }

  @Test
  public void tesCompressWithInvalidCommand1() throws IOException {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Invalid command format for compress: compress 50 image");

    CommandExecutor executor = new CommandExecutor("compress 50 image");
    executor.executeCommand();
  }

  @Test
  public void testCompressWithInvalidCommand2() throws IOException {
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage(
        "Invalid command format for compress: compress 50 image extraPart anotherExtraPart");

    CommandExecutor executor = new CommandExecutor("compress 50 image extraPart anotherExtraPart");
    executor.executeCommand();
  }


  @Test
  public void testExecuteCommandWithValidImageName() {
    try {
      CommandExecutor executor = new CommandExecutor(
          "someValidCommand " + sampleImageName + " someOtherPart");
      executor.executeCommand();
    } catch (Exception e) {
      fail("No exception should be thrown with a valid image name.");
    }
  }

  @Test
  public void testLoadWithValidImageName() {
    try {
      CommandExecutor executor = new CommandExecutor(
          "load " + validFilename + " " + sampleImageName);
      executor.executeCommand();
      Image loadedImage = ImageStorage.getImageByName(sampleImageName);
      assertNotNull(loadedImage);
    } catch (Exception e) {
      fail("No exception thrown when loading valid image");
    }
  }

  //test blur
  @Test
  public void testBlur() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "blur " + sampleImageName + " blurredImage");
    executor.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image blurredImage = ImageStorage.getImageByName("blurredImage");
    assertNotNull(blurredImage);
    assertEquals(originalImage.getWidth(), blurredImage.getWidth());
    assertEquals(originalImage.getHeight(), blurredImage.getHeight());
  }

  @Test
  public void testSharpen() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "sharpen " + sampleImageName + " sharpenedImage");
    executor.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image sharpenedImage = ImageStorage.getImageByName("sharpenedImage");
    assertNotNull(sharpenedImage);
    assertEquals(originalImage.getWidth(), sharpenedImage.getWidth());
    assertEquals(originalImage.getHeight(), sharpenedImage.getHeight());
  }

  @Test
  public void testSepiaSave() throws IOException {
    CommandExecutor executor = new CommandExecutor("sepia " + sampleImageName + " sepiaImage");
    executor.executeCommand();
    CommandExecutor executor2 = new CommandExecutor("save " + validFilename + " sepiaImage");
    executor2.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image sepiaImage = ImageStorage.getImageByName("sepiaImage");
    Image savedImage = ImageStorage.getImageByName("sepiaImage");
    assertNotNull(sepiaImage);
    assertNotNull(savedImage);
    //check if image is saved
    assertEquals(sepiaImage, savedImage);
    Pixel[][] sepiaImagePixels = sepiaImage.getPixels();
    Pixel[][] savedPixels = savedImage.getPixels();
    assertEquals(sepiaImagePixels, savedPixels);

    assertEquals(originalImage.getWidth(), sepiaImage.getWidth());
    assertEquals(originalImage.getHeight(), sepiaImage.getHeight());

    Pixel[][] originalPixels = originalImage.getPixels();
    Pixel[][] sepiaPixels = sepiaImage.getPixels();
    for (int i = 0; i < originalImage.getHeight(); i++) {
      for (int j = 0; j < originalImage.getWidth(); j++) {
        Pixel originalPixel = originalPixels[i][j];
        Pixel sepiaPixel = sepiaPixels[i][j];

        int expectedR = (int) (originalPixel.getRed() * 0.393 + originalPixel.getGreen() * 0.769
            + originalPixel.getBlue() * 0.189);
        int expectedG = (int) (originalPixel.getRed() * 0.349 + originalPixel.getGreen() * 0.686
            + originalPixel.getBlue() * 0.168);
        int expectedB = (int) (originalPixel.getRed() * 0.272 + originalPixel.getGreen() * 0.534
            + originalPixel.getBlue() * 0.131);

        expectedR = Math.min(255, Math.max(0, expectedR));
        expectedG = Math.min(255, Math.max(0, expectedG));
        expectedB = Math.min(255, Math.max(0, expectedB));

        assertEquals(expectedR, sepiaPixel.getRed());
        assertEquals(expectedG, sepiaPixel.getGreen());
        assertEquals(expectedB, sepiaPixel.getBlue());
      }
    }
  }

  @Test
  public void testHistogram() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "histogram " + sampleImageName + " histogramImage");
    executor.executeCommand();
    Image originalImage = ImageStorage.getImageByName(sampleImageName);
    Image sharpenedImage = ImageStorage.getImageByName("histogramImage");
    assertNotNull(sharpenedImage);
    Pixel[][] histogramPixels = originalImage.getPixels();

    for (int i = 0; i < sharpenedImage.getHeight(); i++) {
      for (int j = 0; j < sharpenedImage.getWidth(); j++) {
        int histogramValue = histogramPixels[i][j].getRed();

        assertTrue(histogramValue >= 0 && histogramValue <= 255);

      }
    }

  }

  @Test
  public void testLoadWithInvalidFilePath() {
    try {
      String invalidFilename = "/path/to/invalid/file.jpg";
      CommandExecutor executor = new CommandExecutor(
          "load " + invalidFilename + " invalidImage");
      executor.executeCommand();
      fail("Loading from an invalid file path will fail.");
    } catch (Exception e) {
      // catch
    }
  }

  //histogram
  @Test
  public void testHistogramcheck() throws IOException {
    Pixel[][] pixels = new Pixel[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        pixels[i][j] = new Pixel(255, 255, 255);
      }
    }

    Image image = new Image(5, 5);
    image.setPixels(pixels);
    ImageStorage.associateImageWithName("testHistogram", image);
    String pathName = "/Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments/Assignment4"
        + "/Assignment4/src/resOld/testhistogram.jpg";
    CommandExecutor executor = new CommandExecutor(
        "save " + pathName + " testHistogram");
    executor.executeCommand();
    Image savedImage = ImageStorage.getImageByName("testHistogram");
    CommandExecutor executor1 = new CommandExecutor(
        "load " + pathName + " loadedHistogram");
    executor1.executeCommand();
    Image loadedImage = ImageStorage.getImageByName("loadedHistogram");
    assertNotNull(loadedImage);
    CommandExecutor executor2 = new CommandExecutor(
        "histogram " + "loadedHistogram" + " histogramImage");
    executor2.executeCommand();
    Image histogramImage = ImageStorage.getImageByName("histogramImage");
    assertNotNull(histogramImage);

    assertEquals(0, histogramImage.getPixel(254, 255).getRed());
    assertEquals(0, histogramImage.getPixel(254, 255).getGreen());
    assertEquals(255, histogramImage.getPixel(254, 255).getBlue());

    assertEquals(255, histogramImage.getPixel(255, 0).getRed());
    assertEquals(255, histogramImage.getPixel(255, 0).getGreen());
    assertEquals(255, histogramImage.getPixel(255, 0).getBlue());
  }

  @Test
  public void testExecuteInvalidHistogramCommandFormat() throws IOException {
    String invalidCommand = "histogram invalidArgs";
    CommandExecutor executor = new CommandExecutor(invalidCommand);
    executor.executeCommand();
    System.setOut(originalOut);
    assertTrue(outContent.toString().contains("Invalid command format: " + invalidCommand));
  }

  @Test
  public void testExecuteImageinvalidhistogram() throws IOException {
    String invalidImageName = "invalidImage";
    CommandExecutor executor = new CommandExecutor(
        "histogram " + invalidImageName + " brightenedImage");
    executor.executeCommand();
    System.setOut(originalOut);
    assertTrue(outContent.toString().contains("Source image not found: " + invalidImageName));
  }

  //colorcorrect
  @Test
  public void testExecuteInvalidcolorcorrectCommandFormat() throws IOException {
    String invalidCommand = "color-correct invalidArgs";
    CommandExecutor executor = new CommandExecutor(invalidCommand);
    executor.executeCommand();
    System.setOut(originalOut);
    assertTrue(outContent.toString().contains("Invalid command format: " + invalidCommand));
  }

  @Test
  public void testExecuteImageinvalidcorrect() throws IOException {
    String invalidImageName = "invalidImage";
    CommandExecutor executor = new CommandExecutor(
        "color-correct " + invalidImageName + " brightenedImage");
    executor.executeCommand();
    System.setOut(originalOut);
    assertTrue(outContent.toString().contains("Source image not found: " + invalidImageName));
  }


  @Test
  public void testLoadWithInvalidFilePathname() {
    try {
      String invalidFilename = "/path/to/invalid/file.jpg";
      CommandExecutor executor = new CommandExecutor(
          "load " + invalidFilename + " invalidImage");
      executor.executeCommand();
      fail("Loading from an invalid file path will fail.");
    } catch (Exception e) {
      // catch
    }
  }


  @Test
  public void testSaveWithInvalidFilePath() {
    String invalidFilename = "/path/to/invalid/file.jpg";
    CommandExecutor executor = new CommandExecutor(
        "save " + invalidFilename + " invalidImage");
    try {
      executor.executeCommand();
      fail("Save operation to fail for an invalid file path.");
    } catch (Exception e) {
      assertTrue(e.getMessage().contains("File not found"));
    }
  }

  @Test
  public void testLoadImageFormats() throws IOException {
    CommandExecutor executorJPEG = new CommandExecutor(
        "load /Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments/Assignment4/"
            + "Assignment4/src/images/test.jpg testImageJPEG");
    executorJPEG.executeCommand();
    Image loadedImageJPEG = ImageStorage.getImageByName("testImageJPEG");
    assertNotNull(loadedImageJPEG);

    CommandExecutor executorPNG = new CommandExecutor(
        "load /Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments/Assignment4/"
            + "Assignment4/src/images/koala-square.png testImagePNG");
    executorPNG.executeCommand();
    Image loadedImagePNG = ImageStorage.getImageByName("testImagePNG");
    assertNotNull(loadedImagePNG);
  }

  @Test
  public void testCommandParsing() throws IOException {
    CommandExecutor executor = new CommandExecutor(
        "load /Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments/Assignment4/"
            + "Assignment4/src/images/test.jpg testImage");
    executor.executeCommand();
    Image loadedImage = ImageStorage.getImageByName("testImage");
    assertNotNull(loadedImage);

    executor = new CommandExecutor("brighten 10 testImage brightenedImage");
    executor.executeCommand();
    Image brightenedImage = ImageStorage.getImageByName("brightenedImage");
    assertNotNull(brightenedImage);

    executor = new CommandExecutor(
        "save /Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments/Assignment4/"
            + "Assignment4/src/images/brightenedImage.jpg brightenedImage");
    executor.executeCommand();
  }


  @Test
  public void testSaveImageInPPMFormat() {
    try {
      CommandExecutor executor = new CommandExecutor(
          "load /Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments/Assignment4/"
              + "Assignment4/src/images/samplePPM.ppm testImagePPM");
      executor.executeCommand();
      Image loadedImage = ImageStorage.getImageByName("testImagePPM");
      assertNotNull(loadedImage);

      executor = new CommandExecutor("brighten 10 testImagePPM brightenedImagePPM");
      executor.executeCommand();
      Image brightenedImage = ImageStorage.getImageByName("brightenedImagePPM");
      assertNotNull(brightenedImage);

      String testPPMFilePath = "/Users/poojaramakrishnan/NEU_Fall23/PDP/Assignments/Assignment4/"
          + "Assignment4/src/images/brightenedImagePPM.ppm";
      CommandExecutor executor1 = new CommandExecutor(
          "save " + testPPMFilePath + " " + "brightenedImagePPM");
      executor1.executeCommand();

      // Verify the file exists
      File file = new File(testPPMFilePath);
      assertTrue("File not created", file.exists());

      // Read the contents of the file
      List<String> lines = Files.readAllLines(Paths.get(testPPMFilePath));

      // Check for the PPM header
      assertEquals("PPM header missing or incorrect", "P3", lines.get(0));

    } catch (IOException e) {
      fail("Failed to save image: " + e.getMessage());
    }
  }
}

