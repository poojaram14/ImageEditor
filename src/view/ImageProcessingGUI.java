package view;

import controller.GUICommandHandler;
import controller.ImageUtil;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.function.BiConsumer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import model.enums.Direction;

/**
 * The ImageProcessingGUI class represents a graphical user interface for image processing or
 * manipulation operations.It has the ability to load, manipulate, and save images after applying
 * various image processing operations.The GUI has buttons for applying image manipulation
 * operations like blur, sharpen, greyscale, sepia and to adjust levels, visualize color components,
 * flip, and perform color correction.Split view is also available for the user to preview the
 * original and manipulated image.The GUI also has a histogram panel that displays the histogram of
 * whatever image is being seen on the screen.
 */
public class ImageProcessingGUI extends JFrame {

  private String imageFileExtension;
  private JButton loadImageBtn;
  private JButton saveImageBtn;
  private JButton sharpenBtn;
  private JButton blurBtn;
  private JButton sepiaBtn;
  private JButton greyBtn;
  private JButton redBtn;
  private JButton greenBtn;
  private JButton blueBtn;
  private JButton verBtn;
  private JButton horBtn;
  private JButton colorCorrectBtn;
  private JButton revertBtn;
  private JButton levelBtn;
  private JButton compressBtn;
  private JLabel imageLabel;
  private BufferedImage originalImage;
  private BufferedImage currentImage;
  private BufferedImage previousImage;
  private final Integer split = 0;
  private HistogramPanel histogramPanel;
  private BufferedImage histogramImage;

  private static int splitPercentage;
  private boolean isInitialized = false;

  GUICommandHandler commandHandler = new GUICommandHandler();

  /**
   * Constructor to create a new ImageProcessingGUI with the specified title, size etc.
   */
  public ImageProcessingGUI() {
    setTitle("Image Processing Application");
    setSize(1400, 800);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    initializeComponents();
    layoutComponents();
    isInitialized = true;
  }

  /**
   * Method to check if the GUI is initialized.
   */
  public boolean isInitialized() {
    return isInitialized;
  }


  /**
   * Updates the displayed image in the frame.
   *
   * @param currentImage The image to be displayed.
   */
  private void updateImageInFrame(BufferedImage currentImage) {
    ImageIcon imageIconPreview = new ImageIcon(currentImage);
    imageLabel.setIcon(imageIconPreview);
    imageLabel.revalidate();
    imageLabel.repaint();
  }


  /**
   * Performs the action for blurring the image.
   */
  private void blurButtonAction() {
    try {
      previousImage = ImageProcessingUtilities.deepCopy(currentImage);
      Option applyToImage = ImageProcessingUtilities.applyToWholeImage("blur");
      if (applyToImage == Option.EXIT) {
        return;
      }
      model.Image currentImageModel = ImageProcessingUtilities.convertToImageModel(currentImage);
      model.Image blurImageModel = commandHandler.applyBlur(currentImageModel, splitPercentage);
      currentImage = ImageProcessingUtilities.convertToBufferedImage(blurImageModel);

      if (applyToImage == Option.SPLIT) {
        openNewWindow(currentImage, previousImage, "Split View - Blur", (operated, previous) -> {
          try {
            model.Image blurImageModelPreview1 = commandHandler.applyBlur(
                ImageProcessingUtilities.convertToImageModel(previous), 100);
            currentImage = ImageProcessingUtilities.convertToBufferedImage(blurImageModelPreview1);
            updateImageInFrame(currentImage);
            updateHistogram(currentImage);

          } catch (Exception ex) {
            ex.printStackTrace();
            ImageProcessingUtilities.showErrorDialog(ex, "blur");
          }
        });
      } else {
        updateImageInFrame(currentImage);
      }
      updateHistogram(currentImage);
    } catch (Exception e) {
      e.printStackTrace();
      ImageProcessingUtilities.showErrorDialog(e, "blur");
    }
  }

  /**
   * Performs the action for sharpening the image.
   */
  private void sharpenButtonAction() {
    try {
      previousImage = ImageProcessingUtilities.deepCopy(currentImage);
      Option applyToImage = ImageProcessingUtilities.applyToWholeImage("sharpen");
      if (applyToImage == Option.EXIT) {
        return;
      }

      model.Image currentImageModel = ImageProcessingUtilities.convertToImageModel(currentImage);
      model.Image sharpenImageModel = commandHandler.applySharpen(currentImageModel,
          splitPercentage);
      currentImage = ImageProcessingUtilities.convertToBufferedImage(sharpenImageModel);
      if (applyToImage == Option.SPLIT) {
        openNewWindow(currentImage, previousImage, "Split View - Sharpen", (operated, previous) -> {
          try {
            model.Image sharpenImageModelPreview1 = commandHandler.applySharpen(
                ImageProcessingUtilities.convertToImageModel(previous), 100);
            currentImage = ImageProcessingUtilities.convertToBufferedImage(
                sharpenImageModelPreview1);
            updateImageInFrame(currentImage);
            updateHistogram(currentImage);
          } catch (Exception ex) {
            ex.printStackTrace();
            ImageProcessingUtilities.showErrorDialog(ex, "sharpen");
          }
        });
      } else {
        updateImageInFrame(currentImage);
      }
      updateHistogram(currentImage);
    } catch (Exception e) {
      e.printStackTrace();
      ImageProcessingUtilities.showErrorDialog(e, "sharpen");
    }
  }

  /**
   * Performs the action for converting the image to greyscale.
   */
  private void greyScaleButtonAction() {
    try {
      previousImage = ImageProcessingUtilities.deepCopy(currentImage);

      Option applyToImage = ImageProcessingUtilities.applyToWholeImage("greyscale");
      if (applyToImage == Option.EXIT) {
        return;
      }
      model.Image currentImageModel = ImageProcessingUtilities.convertToImageModel(currentImage);
      model.Image greyscaleImageModel = commandHandler.applyGreyScale(currentImageModel,
          splitPercentage);
      currentImage = ImageProcessingUtilities.convertToBufferedImage(greyscaleImageModel);
      if (applyToImage == Option.SPLIT) {
        openNewWindow(currentImage, previousImage, "Split View - Sharpen", (operated, previous) -> {
          try {
            model.Image greyscaleImageModelPreview1 = commandHandler.applyGreyScale(
                ImageProcessingUtilities.convertToImageModel(previous), 100);
            currentImage = ImageProcessingUtilities.convertToBufferedImage(
                greyscaleImageModelPreview1);

            updateImageInFrame(currentImage);
            updateHistogram(currentImage);
          } catch (Exception ex) {
            ex.printStackTrace();
            ImageProcessingUtilities.showErrorDialog(ex, "greyscale");
          }
        });
      } else {
        updateImageInFrame(currentImage);
      }
      updateHistogram(currentImage);
    } catch (Exception e) {
      e.printStackTrace();
      ImageProcessingUtilities.showErrorDialog(e, "greyscale");
    }
  }

  /**
   * Performs the action for applying a sepia tone to the image.
   */
  private void sepiaButtonAction() {
    try {
      previousImage = ImageProcessingUtilities.deepCopy(currentImage);
      Option applyToImage = ImageProcessingUtilities.applyToWholeImage("sepia");
      if (applyToImage == Option.EXIT) {
        return;
      }

      model.Image currentImageModel = ImageProcessingUtilities.convertToImageModel(currentImage);
      model.Image sepiaImageModel = commandHandler.applySepia(currentImageModel, splitPercentage);
      currentImage = ImageProcessingUtilities.convertToBufferedImage(sepiaImageModel);
      if (applyToImage == Option.SPLIT) {
        openNewWindow(currentImage, previousImage, "Split View - Sepia", (operated, previous) -> {
          try {
            model.Image sepiaImageModelPreview1 = commandHandler.applySepia(
                ImageProcessingUtilities.convertToImageModel(previous), 100);
            currentImage = ImageProcessingUtilities.convertToBufferedImage(sepiaImageModelPreview1);

            updateImageInFrame(currentImage);
            updateHistogram(currentImage);
          } catch (Exception ex) {
            ex.printStackTrace();
            ImageProcessingUtilities.showErrorDialog(ex, "sepia");

          }
        });
      } else {
        updateImageInFrame(currentImage);
      }
      updateHistogram(currentImage);
    } catch (Exception e) {
      ImageProcessingUtilities.showErrorDialog(e, "sepia");
    }
  }


  /**
   * Performs the action for adjusting the levels of the image.
   */
  private void adjustLevelButtonAction() {
    try {
      previousImage = ImageProcessingUtilities.deepCopy(currentImage);
      Option applyToImage = ImageProcessingUtilities.applyToWholeImage("sharpen");
      if (applyToImage == Option.EXIT) {
        return;
      }

      String blackText = JOptionPane.showInputDialog(this, "Enter value for Black:");
      String whiteText = JOptionPane.showInputDialog(this, "Enter value for White:");
      String midText = JOptionPane.showInputDialog(this, "Enter value for Mid:");

      if (splitPercentage == -1 || blackText == null || whiteText == null || midText == null) {
        return;
      }

      double black = Double.parseDouble(blackText.trim());
      double white = Double.parseDouble(whiteText.trim());
      double mid = Double.parseDouble(midText.trim());

      model.Image currentImageModel = ImageProcessingUtilities.convertToImageModel(currentImage);
      model.Image adjustedImageModel = commandHandler.applyLevel(currentImageModel, splitPercentage,
          black, white, mid);
      currentImage = ImageProcessingUtilities.convertToBufferedImage(adjustedImageModel);
      if (applyToImage == Option.SPLIT) {
        openNewWindow(currentImage, previousImage, "Split View - Adjust Levels",
            (operated, previous) -> {
              try {
                model.Image adjustedImageModelPreview1 = commandHandler.applyLevel(
                    ImageProcessingUtilities.convertToImageModel(previous), 100, black, white, mid);
                currentImage = ImageProcessingUtilities.convertToBufferedImage(
                    adjustedImageModelPreview1);

                updateImageInFrame(currentImage);

                updateHistogram(currentImage);
              } catch (Exception ex) {
                ex.printStackTrace();
                ImageProcessingUtilities.showErrorDialog(ex, "adjust levels");

              }
            });
      } else {
        updateImageInFrame(currentImage);

      }
      updateHistogram(currentImage);
    } catch (Exception ex) {
      ex.printStackTrace();
      ImageProcessingUtilities.showErrorDialog(ex, "adjust levels");
    }
  }

  /**
   * Performs the action for color correction on the image.
   */
  private void colorCorrectAction() {
    try {
      previousImage = ImageProcessingUtilities.deepCopy(currentImage);
      Option applyToImage = ImageProcessingUtilities.applyToWholeImage("color correct");
      if (applyToImage == Option.EXIT) {
        return;
      }

      model.Image currentImageModel = ImageProcessingUtilities.convertToImageModel(currentImage);
      model.Image colorCorrectImageModel = commandHandler.applyColorCorrection(currentImageModel,
          splitPercentage);
      currentImage = ImageProcessingUtilities.convertToBufferedImage(colorCorrectImageModel);
      if (applyToImage == Option.SPLIT) {
        openNewWindow(currentImage, previousImage, "Split View - Sepia", (operated, previous) -> {
          try {
            model.Image colorCorrectImageModelPreview1 = commandHandler.applyColorCorrection(
                ImageProcessingUtilities.convertToImageModel(previous), 100);
            currentImage = ImageProcessingUtilities.convertToBufferedImage(
                colorCorrectImageModelPreview1);
            updateImageInFrame(currentImage);
            updateHistogram(currentImage);
          } catch (Exception ex) {
            ex.printStackTrace();
            ImageProcessingUtilities.showErrorDialog(ex, "color correct");
          }
        });
      } else {
        updateImageInFrame(currentImage);
      }
      updateHistogram(currentImage);
    } catch (Exception e) {
      e.printStackTrace();
      ImageProcessingUtilities.showErrorDialog(e, "color correct");
    }
  }


  private class HistogramPanel extends JPanel {

    /**
     * Constructs a new HistogramPanel with default settings. The panel includes a black border, a
     * size of 200x300 pixels, and a white background.
     */
    public HistogramPanel() {
      setBorder(BorderFactory.createLineBorder(Color.BLACK));
      setPreferredSize(new Dimension(200, 300));
      setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);

      if (histogramImage != null) {
        g.drawImage(histogramImage, 0, 0, getWidth(), getHeight(), this);
      }

      g.setColor(Color.BLACK);
      g.setFont(new Font("Arial", Font.BOLD, 16));
      String heading = "Histogram";
      int stringWidth = g.getFontMetrics().stringWidth(heading);
      int x = (getWidth() - stringWidth) / 2;
      int y = 20;
      g.drawString(heading, x, y);
    }

    @Override
    protected void paintChildren(Graphics g) {
      super.paintChildren(g);
    }

    /**
     * Updates the HistogramPanel with a new histogram based on the given image.
     *
     * @param image The BufferedImage to generate a histogram from.
     */
    public void updateHistoPanel(BufferedImage image) {
      model.Image imageModel = ImageProcessingUtilities.convertToImageModel(image);
      model.Image histogramImageModel = commandHandler.applyHistogram(imageModel, split);
      histogramImage = ImageProcessingUtilities.convertToBufferedImage(histogramImageModel);
      repaint();
    }
  }

  /**
   * Updates the histogram in the GUI based on the given image. It converts the image to an image
   * model, applies histogram processing, and then updates the histogram panel in the GUI.
   *
   * @param image The BufferedImage to generate a histogram from.
   */

  public void updateHistogram(BufferedImage image) {
    model.Image imageModel = ImageProcessingUtilities.convertToImageModel(image);
    model.Image histogramImageModel = commandHandler.applyHistogram(imageModel, split);
    histogramImage = ImageProcessingUtilities.convertToBufferedImage(histogramImageModel);
    histogramPanel.updateHistoPanel(currentImage);
    repaint();
  }


  private void redButtonAction() {
    try {
      model.Image currentImageModel = ImageProcessingUtilities.convertToImageModel(currentImage);
      model.Image blurredImageModel = commandHandler.visualizeRed(currentImageModel, split);
      currentImage = ImageProcessingUtilities.convertToBufferedImage(blurredImageModel);

      updateImageInFrame(currentImage);
      updateHistogram(currentImage);

    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "Error during visualise red component: " + e.getMessage(),
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void greenButtonAction() {
    try {
      model.Image currentImageModel = ImageProcessingUtilities.convertToImageModel(currentImage);
      model.Image blurredImageModel = commandHandler.visualizeGreen(currentImageModel, split);
      currentImage = ImageProcessingUtilities.convertToBufferedImage(blurredImageModel);

      updateImageInFrame(currentImage);
      updateHistogram(currentImage);

    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "visualise green component: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void blueButtonAction() {
    try {
      model.Image currentImageModel = ImageProcessingUtilities.convertToImageModel(currentImage);
      model.Image blurredImageModel = commandHandler.visualizeBlue(currentImageModel, split);
      currentImage = ImageProcessingUtilities.convertToBufferedImage(blurredImageModel);

      updateImageInFrame(currentImage);
      updateHistogram(currentImage);

    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this,
          "Error during visualise blue component: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void flipVerticalAction() {
    try {
      model.Image currentImageModel = ImageProcessingUtilities.convertToImageModel(currentImage);
      model.Image blurredImageModel = commandHandler.applyFlip(currentImageModel,
          Direction.VERTICAL);
      currentImage = ImageProcessingUtilities.convertToBufferedImage(blurredImageModel);

      updateImageInFrame(currentImage);
      updateHistogram(currentImage);

    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "Error during blurring: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void flipHorizontalAction() {
    try {
      model.Image currentImageModel = ImageProcessingUtilities.convertToImageModel(currentImage);
      model.Image blurredImageModel = commandHandler.applyFlip(currentImageModel,
          Direction.HORIZONTAL);
      currentImage = ImageProcessingUtilities.convertToBufferedImage(blurredImageModel);

      updateImageInFrame(currentImage);
      updateHistogram(currentImage);

    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "Error during blurring: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }


  private void compressButtonAction() {
    try {
      String input = JOptionPane.showInputDialog(this, "Enter compression percentage (0-100):",
          "Compression", JOptionPane.QUESTION_MESSAGE);
      if (input != null && !input.isEmpty()) {
        int compressPercentage = Integer.parseInt(input);
        if (compressPercentage >= 0 && compressPercentage <= 100) {
          model.Image currentImageModel = ImageProcessingUtilities.convertToImageModel(
              currentImage);
          model.Image compressedImageModel = commandHandler.applyCompress(currentImageModel,
              compressPercentage);
          currentImage = ImageProcessingUtilities.convertToBufferedImage(compressedImageModel);

          updateImageInFrame(currentImage);
        } else {
          JOptionPane.showMessageDialog(this, "Please enter a valid percentage (0-100).",
              "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
      }
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Invalid Input",
          JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, "Error during operation: " + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void openNewWindow(BufferedImage operatedImage, BufferedImage previousImage,
      String windowTitle,
      BiConsumer<BufferedImage, BufferedImage> applyAction) {
    JFrame newFrame = new JFrame(windowTitle);
    JLabel newLabel = new JLabel(new ImageIcon(operatedImage));

    JButton applyButton = new JButton("Apply");
    JButton discardButton = new JButton("Discard");
    JCheckBox toggleCheckbox = new JCheckBox("Show original image", false);

    toggleCheckbox.addItemListener(e -> {
      if (toggleCheckbox.isSelected()) {
        newLabel.setIcon(new ImageIcon(previousImage));
      } else {
        newLabel.setIcon(new ImageIcon(operatedImage));
      }
      newLabel.revalidate();
      newLabel.repaint();
    });

    applyButton.addActionListener(e -> {
      applyAction.accept(operatedImage, previousImage);
      newFrame.dispose();
    });

    discardButton.addActionListener(e -> {
      currentImage = previousImage;
      newFrame.dispose();
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(applyButton);
    buttonPanel.add(discardButton);
    buttonPanel.add(toggleCheckbox);

    newFrame.setLayout(new BorderLayout());
    newFrame.getContentPane().add(newLabel, BorderLayout.CENTER);
    newFrame.getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

    newFrame.setSize(operatedImage.getWidth(), operatedImage.getHeight() + buttonPanel.getHeight());
    newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    newFrame.setVisible(true);
  }

  /**
   * setter for split percentage.
   *
   * @param splitPercent percentage
   */
  public static void setPercentage(int splitPercent) {
    splitPercentage = splitPercent;
  }

  private void initializeComponents() {

    loadImageBtn = new JButton("Load Image");
    saveImageBtn = new JButton("Save Image");
    saveImageBtn.setEnabled(false);
    blurBtn = new JButton("Blur");
    blurBtn.setPreferredSize(new Dimension(100, 50));
    sharpenBtn = new JButton("Sharpen");
    compressBtn = new JButton("Compress");
    greyBtn = new JButton("Greyscale");
    sepiaBtn = new JButton("Sepia Tone");
    levelBtn = new JButton("Level Adjustment");
    redBtn = new JButton("Red component");
    greenBtn = new JButton("Green component");
    blueBtn = new JButton("Blue component");
    verBtn = new JButton("Flip vertical");
    horBtn = new JButton("Flip horizontal");
    colorCorrectBtn = new JButton("Color correct");
    revertBtn = new JButton("Revert to Original");

    JTextField blackField = new JTextField(5);
    JTextField whiteField = new JTextField(5);
    JTextField midField = new JTextField(5);
    blackField.setEditable(true);
    whiteField.setEditable(true);
    midField.setEditable(true);

    imageLabel = new JLabel();
    imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    imageLabel.setPreferredSize(new Dimension(400, 300));

    loadImageBtn.addActionListener(this::loadImage);
    saveImageBtn.addActionListener(this::saveImage);
    blurBtn.addActionListener(e -> blurButtonAction());
    sharpenBtn.addActionListener(e -> sharpenButtonAction());
    compressBtn.addActionListener(e -> compressButtonAction());
    greyBtn.addActionListener(e -> greyScaleButtonAction());
    sepiaBtn.addActionListener(e -> sepiaButtonAction());
    levelBtn.addActionListener(e -> adjustLevelButtonAction());
    redBtn.addActionListener(e -> redButtonAction());
    greenBtn.addActionListener(e -> greenButtonAction());
    blueBtn.addActionListener(e -> blueButtonAction());
    verBtn.addActionListener(e -> flipVerticalAction());
    horBtn.addActionListener(e -> flipHorizontalAction());
    colorCorrectBtn.addActionListener(e -> colorCorrectAction());
    revertBtn.addActionListener(e -> revertToOriginal());
  }

  private void layoutComponents() {
    setLayout(new BorderLayout());

    JScrollPane scrollPane = new JScrollPane(imageLabel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    JPanel manipulationPanel = new JPanel(new BorderLayout());

    JLabel manipulationHeading = new JLabel("Image Manipulation");
    manipulationHeading.setHorizontalAlignment(SwingConstants.CENTER);
    manipulationHeading.setFont(new Font("Arial", Font.BOLD, 16));
    manipulationPanel.add(manipulationHeading, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 10, 3));
    buttonPanel.add(blurBtn);
    buttonPanel.add(sharpenBtn);
    buttonPanel.add(colorCorrectBtn);
    buttonPanel.add(greyBtn);
    buttonPanel.add(sepiaBtn);
    buttonPanel.add(levelBtn);
    buttonPanel.add(redBtn);
    buttonPanel.add(greenBtn);
    buttonPanel.add(blueBtn);
    buttonPanel.add(verBtn);
    buttonPanel.add(horBtn);
    buttonPanel.add(compressBtn);

    manipulationPanel.add(buttonPanel, BorderLayout.CENTER);

    JPanel loadSavePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 3));

    int buttonHeight = 40;
    loadImageBtn.setPreferredSize(
        new Dimension(loadImageBtn.getPreferredSize().width, buttonHeight));
    saveImageBtn.setPreferredSize(
        new Dimension(saveImageBtn.getPreferredSize().width, buttonHeight));
    revertBtn.setPreferredSize(new Dimension(revertBtn.getPreferredSize().width, buttonHeight));

    loadSavePanel.add(loadImageBtn);
    loadSavePanel.add(saveImageBtn);
    loadSavePanel.add(revertBtn);

    histogramPanel = new HistogramPanel();
    JPanel buttonsAndHistogramPanel = new JPanel(new BorderLayout());
    buttonsAndHistogramPanel.add(manipulationPanel, BorderLayout.NORTH);
    buttonsAndHistogramPanel.add(histogramPanel, BorderLayout.CENTER);
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane,
        buttonsAndHistogramPanel);
    splitPane.setResizeWeight(1.0);
    add(splitPane, BorderLayout.CENTER);
    add(loadSavePanel, BorderLayout.SOUTH);
  }

  /**
   * Loads an image file selected by the user.
   */
  private void loadImage(ActionEvent e) {
    if (ImageProcessingUtilities.isImageChanged(originalImage, currentImage)) {
      int choice = JOptionPane.showConfirmDialog(this,
          "You have unsaved changes. Do you want to save them before loading a new image?",
          "Save Changes?",
          JOptionPane.YES_NO_CANCEL_OPTION,
          JOptionPane.WARNING_MESSAGE);

      if (choice == JOptionPane.YES_OPTION) {
        saveImage(null);
        loadNewImage();
      } else if (choice == JOptionPane.NO_OPTION) {
        loadNewImage();
      }
    } else {
      loadNewImage();
    }
  }

  private void loadNewImage() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileFilter(
        new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "png", "ppm",
            "jpeg"));

    if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      try {
        String fileName = file.getName();
        imageFileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        model.Image loadedImage;
        if (imageFileExtension.equals("ppm")) {
          loadedImage = ImageUtil.readPPM(file.getAbsolutePath());
        } else {
          loadedImage = ImageUtil.readJPGorPNG(file.getAbsolutePath());
        }

        displayImage(loadedImage);
        originalImage = currentImage;
        updateHistogram(currentImage);
      } catch (IOException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading image", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  /**
   * Saves the current image to a file selected by the user.
   */
  private void saveImage(ActionEvent e) {
    if (currentImage == null) {
      JOptionPane.showMessageDialog(this, "No image to save", "Warning",
          JOptionPane.WARNING_MESSAGE);
      return;
    }

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Image");

    fileChooser.addChoosableFileFilter(
        new javax.swing.filechooser.FileNameExtensionFilter("JPEG files", "jpg", "jpeg"));
    fileChooser.addChoosableFileFilter(
        new javax.swing.filechooser.FileNameExtensionFilter("PNG files", "png"));
    fileChooser.addChoosableFileFilter(
        new javax.swing.filechooser.FileNameExtensionFilter("PPM files", "ppm"));
    fileChooser.setAcceptAllFileFilterUsed(false);

    if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
      File fileToSave = fileChooser.getSelectedFile();
      String format = ImageProcessingUtilities.getFileExtension(fileToSave);

      if (format == null || (!format.equals("jpg") && !format.equals("png") && !format.equals(
          "ppm"))) {
        JOptionPane.showMessageDialog(this, "Unsupported file format.", "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      try {
        model.Image imageToSave = ImageProcessingUtilities.convertToImageModel(currentImage);
        String filePath = fileToSave.getAbsolutePath();

        if (!filePath.toLowerCase().endsWith("." + format)) {
          filePath += "." + format;
        }

        if (format.equals("ppm")) {
          ImageUtil.writePPM(imageToSave, filePath);
        } else {
          ImageUtil.writeJPGorPNG(imageToSave, filePath, format);
        }

        JOptionPane.showMessageDialog(this, "Image saved successfully", "Information",
            JOptionPane.INFORMATION_MESSAGE);
      } catch (IOException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error saving image", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }


  /**
   * Reverts the displayed image to the original image.
   */

  private void revertToOriginal() {
    if (originalImage != null) {
      currentImage = originalImage;
      displayImage(ImageProcessingUtilities.convertToImageModel(originalImage));
    } else {
      JOptionPane.showMessageDialog(this, "No original image to revert to", "Warning",
          JOptionPane.WARNING_MESSAGE);
    }
    updateHistogram(currentImage);
  }

  /**
   * Displays the selected image on the GUI.
   *
   * @param image The image to be displayed.
   */
  private void displayImage(model.Image image) {
    currentImage = ImageProcessingUtilities.convertToBufferedImage(image);
    ImageIcon imageIcon = new ImageIcon(currentImage);

    imageLabel.setIcon(imageIcon);
    imageLabel.setPreferredSize(new Dimension(currentImage.getWidth(), currentImage.getHeight()));

    imageLabel.revalidate();
    imageLabel.repaint();

    saveImageBtn.setEnabled(true);
    setManipulationButtonsEnabled(true);
  }

  /**
   * Enables or disables the image manipulation buttons.
   *
   * @param enabled true to enable the buttons, false to disable them.
   */
  private void setManipulationButtonsEnabled(boolean enabled) {
    sharpenBtn.setEnabled(enabled);
    compressBtn.setEnabled(enabled);
    blurBtn.setEnabled(enabled);
    sepiaBtn.setEnabled(enabled);
    levelBtn.setEnabled(enabled);
    greyBtn.setEnabled(enabled);
    redBtn.setEnabled(enabled);
    greenBtn.setEnabled(enabled);
    blueBtn.setEnabled(enabled);
    verBtn.setEnabled(enabled);
    horBtn.setEnabled(enabled);
    colorCorrectBtn.setEnabled(enabled);
    revertBtn.setEnabled(enabled);
  }

  /**
   * The main method to run the application.
   *
   * @param args Command line arguments.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      ImageProcessingGUI frame = new ImageProcessingGUI();
      frame.setVisible(true);
    });
  }
}