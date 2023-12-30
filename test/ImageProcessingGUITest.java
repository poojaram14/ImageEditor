import static org.junit.Assert.assertNotNull;

import java.awt.image.BufferedImage;
import javax.swing.JButton;
import org.junit.jupiter.api.Test;
import view.ImageProcessingGUI;
import java.lang.reflect.Field;

/**
 * This class represents the tests for the GUI for different Image manipulation operations like
 * blur,load,save,sharpen,sepia,greyscale etc.
 */

public class ImageProcessingGUITest {

  @Test
  void loadImageButtonTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();
    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();
    Field originalImageField = ImageProcessingGUI.class.getDeclaredField("originalImage");
    originalImageField.setAccessible(true);
    BufferedImage originalImage = (BufferedImage) originalImageField.get(gui);
    assertNotNull(originalImage);
  }

  @Test
  void blurButtonActionTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();
    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();
    Field blurBtnField = ImageProcessingGUI.class.getDeclaredField("blurBtn");
    blurBtnField.setAccessible(true);
    JButton blurBtn = (JButton) blurBtnField.get(gui);

    Field currentImageField = ImageProcessingGUI.class.getDeclaredField("currentImage");
    currentImageField.setAccessible(true);
    BufferedImage currentImage = (BufferedImage) currentImageField.get(gui);

    Field previousImageField = ImageProcessingGUI.class.getDeclaredField("previousImage");
    previousImageField.setAccessible(true);
    BufferedImage previousImage = (BufferedImage) previousImageField.get(gui);
    Field splitPercentageField = ImageProcessingGUI.class.getDeclaredField("splitPercentage");
    splitPercentageField.setAccessible(true);
    splitPercentageField.set(null, 50);
    blurBtn.doClick();
    assertNotNull(currentImage);
    assertNotNull(previousImage);
  }

  @Test
  void sharpenButtonActionTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();
    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();
    Field sharpenBtnField = ImageProcessingGUI.class.getDeclaredField("sharpenBtn");
    sharpenBtnField.setAccessible(true);
    JButton sharpenBtn = (JButton) sharpenBtnField.get(gui);

    Field currentImageField = ImageProcessingGUI.class.getDeclaredField("currentImage");
    currentImageField.setAccessible(true);
    BufferedImage currentImage = (BufferedImage) currentImageField.get(gui);

    Field previousImageField = ImageProcessingGUI.class.getDeclaredField("previousImage");
    previousImageField.setAccessible(true);
    BufferedImage previousImage = (BufferedImage) previousImageField.get(gui);
    Field splitPercentageField = ImageProcessingGUI.class.getDeclaredField("splitPercentage");
    splitPercentageField.setAccessible(true);
    splitPercentageField.set(null, 50);
    sharpenBtn.doClick();
    assertNotNull(currentImage);
    assertNotNull(previousImage);
  }

  @Test
  void sepiaButtonActionTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();
    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();
    Field sepiaBtnField = ImageProcessingGUI.class.getDeclaredField("sepiaBtn");
    sepiaBtnField.setAccessible(true);
    JButton sepiaBtn = (JButton) sepiaBtnField.get(gui);

    Field currentImageField = ImageProcessingGUI.class.getDeclaredField("currentImage");
    currentImageField.setAccessible(true);
    BufferedImage currentImage = (BufferedImage) currentImageField.get(gui);

    Field previousImageField = ImageProcessingGUI.class.getDeclaredField("previousImage");
    previousImageField.setAccessible(true);
    BufferedImage previousImage = (BufferedImage) previousImageField.get(gui);
    Field splitPercentageField = ImageProcessingGUI.class.getDeclaredField("splitPercentage");
    splitPercentageField.setAccessible(true);
    splitPercentageField.set(null, 50);
    sepiaBtn.doClick();
    assertNotNull(currentImage);
    assertNotNull(previousImage);
  }

  @Test
  void greyScaleButtonActionTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();
    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();

    Field greyBtnField = ImageProcessingGUI.class.getDeclaredField("greyBtn");
    greyBtnField.setAccessible(true);
    JButton greyBtn = (JButton) greyBtnField.get(gui);

    Field currentImageField = ImageProcessingGUI.class.getDeclaredField("currentImage");
    currentImageField.setAccessible(true);
    BufferedImage currentImage = (BufferedImage) currentImageField.get(gui);

    Field previousImageField = ImageProcessingGUI.class.getDeclaredField("previousImage");
    previousImageField.setAccessible(true);
    BufferedImage previousImage = (BufferedImage) previousImageField.get(gui);

    Field splitPercentageField = ImageProcessingGUI.class.getDeclaredField("splitPercentage");
    splitPercentageField.setAccessible(true);
    splitPercentageField.set(null, 50);
    greyBtn.doClick();
    assertNotNull(currentImage);
    assertNotNull(previousImage);
  }

  @Test
  void colorCorrectActionTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();

    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();

    Field colorCorrectBtnField = ImageProcessingGUI.class.getDeclaredField("colorCorrectBtn");
    colorCorrectBtnField.setAccessible(true);
    JButton colorCorrectBtn = (JButton) colorCorrectBtnField.get(gui);

    Field currentImageField = ImageProcessingGUI.class.getDeclaredField("currentImage");
    currentImageField.setAccessible(true);
    BufferedImage currentImage = (BufferedImage) currentImageField.get(gui);

    Field previousImageField = ImageProcessingGUI.class.getDeclaredField("previousImage");
    previousImageField.setAccessible(true);
    BufferedImage previousImage = (BufferedImage) previousImageField.get(gui);

    Field splitPercentageField = ImageProcessingGUI.class.getDeclaredField("splitPercentage");
    splitPercentageField.setAccessible(true);
    splitPercentageField.set(null, 50);
    colorCorrectBtn.doClick();

    assertNotNull(currentImage);
    assertNotNull(previousImage);

  }

  @Test
  void adjustLevelButtonActionTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();
    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();
    Field levelBtnField = ImageProcessingGUI.class.getDeclaredField("levelBtn");
    levelBtnField.setAccessible(true);
    JButton levelBtn = (JButton) levelBtnField.get(gui);

    Field currentImageField = ImageProcessingGUI.class.getDeclaredField("currentImage");
    currentImageField.setAccessible(true);
    BufferedImage currentImage = (BufferedImage) currentImageField.get(gui);

    Field previousImageField = ImageProcessingGUI.class.getDeclaredField("previousImage");
    previousImageField.setAccessible(true);
    BufferedImage previousImage = (BufferedImage) previousImageField.get(gui);
    Field splitPercentageField = ImageProcessingGUI.class.getDeclaredField("splitPercentage");
    splitPercentageField.setAccessible(true);
    splitPercentageField.set(null, 50);
    levelBtn.doClick();
    assertNotNull(currentImage);
    assertNotNull(previousImage);
  }

  @Test
  void redComponentButtonActionTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();

    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();
    Field redComponentBtnField = ImageProcessingGUI.class.getDeclaredField("RedComponentBtn");
    redComponentBtnField.setAccessible(true);
    JButton redComponentBtn = (JButton) redComponentBtnField.get(gui);

    Field currentImageField = ImageProcessingGUI.class.getDeclaredField("currentImage");
    currentImageField.setAccessible(true);
    BufferedImage currentImage = (BufferedImage) currentImageField.get(gui);

    Field previousImageField = ImageProcessingGUI.class.getDeclaredField("previousImage");
    previousImageField.setAccessible(true);
    BufferedImage previousImage = (BufferedImage) previousImageField.get(gui);

    Field splitPercentageField = ImageProcessingGUI.class.getDeclaredField("splitPercentage");
    splitPercentageField.setAccessible(true);
    splitPercentageField.set(null, 50);
    redComponentBtn.doClick();

    assertNotNull(currentImage);
    assertNotNull(previousImage);
  }

  @Test
  void greenComponentButtonActionTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();

    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();
    Field greenComponentBtnField = ImageProcessingGUI.class.getDeclaredField("greenComponentBtn");
    greenComponentBtnField.setAccessible(true);
    JButton greenComponentBtn = (JButton) greenComponentBtnField.get(gui);

    Field currentImageField = ImageProcessingGUI.class.getDeclaredField("currentImage");
    currentImageField.setAccessible(true);
    BufferedImage currentImage = (BufferedImage) currentImageField.get(gui);

    Field previousImageField = ImageProcessingGUI.class.getDeclaredField("previousImage");
    previousImageField.setAccessible(true);
    BufferedImage previousImage = (BufferedImage) previousImageField.get(gui);

    Field splitPercentageField = ImageProcessingGUI.class.getDeclaredField("splitPercentage");
    splitPercentageField.setAccessible(true);
    splitPercentageField.set(null, 50);
    greenComponentBtn.doClick();

    assertNotNull(currentImage);
    assertNotNull(previousImage);
  }

  @Test
  void blueComponentButtonActionTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();

    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();

    Field blueComponentBtnField = ImageProcessingGUI.class.getDeclaredField("blueComponentBtn");
    blueComponentBtnField.setAccessible(true);
    JButton blueComponentBtn = (JButton) blueComponentBtnField.get(gui);

    Field currentImageField = ImageProcessingGUI.class.getDeclaredField("currentImage");
    currentImageField.setAccessible(true);
    BufferedImage currentImage = (BufferedImage) currentImageField.get(gui);

    Field previousImageField = ImageProcessingGUI.class.getDeclaredField("previousImage");
    previousImageField.setAccessible(true);
    BufferedImage previousImage = (BufferedImage) previousImageField.get(gui);
    Field splitPercentageField = ImageProcessingGUI.class.getDeclaredField("splitPercentage");
    splitPercentageField.setAccessible(true);
    splitPercentageField.set(null, 50);
    blueComponentBtn.doClick();
    assertNotNull(currentImage);
    assertNotNull(previousImage);
  }

  @Test
  void compressButtonActionTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();
    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();
    Field compressBtnField = ImageProcessingGUI.class.getDeclaredField("compressBtn");
    compressBtnField.setAccessible(true);
    JButton compressBtn = (JButton) compressBtnField.get(gui);

    Field currentImageField = ImageProcessingGUI.class.getDeclaredField("currentImage");
    currentImageField.setAccessible(true);
    BufferedImage currentImage = (BufferedImage) currentImageField.get(gui);

    Field previousImageField = ImageProcessingGUI.class.getDeclaredField("previousImage");
    previousImageField.setAccessible(true);
    BufferedImage previousImage = (BufferedImage) previousImageField.get(gui);
    Field splitPercentageField = ImageProcessingGUI.class.getDeclaredField("splitPercentage");
    splitPercentageField.setAccessible(true);
    splitPercentageField.set(null, 50);
    compressBtn.doClick();

    assertNotNull(currentImage);
    assertNotNull(previousImage);
  }

  @Test
  void flipHorizontalButtonActionTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();
    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();
    Field flipHorizontalBtnField = ImageProcessingGUI.class.getDeclaredField("flipHorizontalBtn");
    flipHorizontalBtnField.setAccessible(true);
    JButton flipHorizontalBtn = (JButton) flipHorizontalBtnField.get(gui);

    Field currentImageField = ImageProcessingGUI.class.getDeclaredField("currentImage");
    currentImageField.setAccessible(true);
    BufferedImage currentImage = (BufferedImage) currentImageField.get(gui);

    Field previousImageField = ImageProcessingGUI.class.getDeclaredField("previousImage");
    previousImageField.setAccessible(true);
    BufferedImage previousImage = (BufferedImage) previousImageField.get(gui);

    Field splitPercentageField = ImageProcessingGUI.class.getDeclaredField("splitPercentage");
    splitPercentageField.setAccessible(true);
    splitPercentageField.set(null, 50);
    flipHorizontalBtn.doClick();
    assertNotNull(currentImage);
    assertNotNull(previousImage);
  }

  @Test
  void flipVerticalButtonActionTest() throws Exception {
    ImageProcessingGUI gui = new ImageProcessingGUI();
    Field loadImageBtnField = ImageProcessingGUI.class.getDeclaredField("loadImageBtn");
    loadImageBtnField.setAccessible(true);
    JButton loadImageBtn = (JButton) loadImageBtnField.get(gui);
    loadImageBtn.doClick();
    Field flipVerticalBtnField = ImageProcessingGUI.class.getDeclaredField("flipVerticalBtn");
    flipVerticalBtnField.setAccessible(true);
    JButton flipVerticalBtn = (JButton) flipVerticalBtnField.get(gui);

    Field currentImageField = ImageProcessingGUI.class.getDeclaredField("currentImage");
    currentImageField.setAccessible(true);
    BufferedImage currentImage = (BufferedImage) currentImageField.get(gui);

    Field previousImageField = ImageProcessingGUI.class.getDeclaredField("previousImage");
    previousImageField.setAccessible(true);
    BufferedImage previousImage = (BufferedImage) previousImageField.get(gui);
    Field splitPercentageField = ImageProcessingGUI.class.getDeclaredField("splitPercentage");
    splitPercentageField.setAccessible(true);
    splitPercentageField.set(null, 50);
    flipVerticalBtn.doClick();
    assertNotNull(currentImage);
    assertNotNull(previousImage);
  }


}