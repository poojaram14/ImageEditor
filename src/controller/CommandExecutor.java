package controller;

import java.io.IOException;
import java.util.List;

import model.AdjustIntensity;
import model.AdjustLevel;
import model.Blur;
import model.ColorCorrect;
import model.CombineImages;
import model.CompressImage;
import model.Flip;
import model.Image;
import model.Pixel;
import model.Sepia;
import model.Sharpen;
import model.SplitComponents;
import model.ValueIntensityLuma;
import model.VisualizeComponents;
import model.VisualizeHistogram;
import model.enums.Channel;
import model.enums.Color;
import model.enums.Direction;
import model.enums.Intensity;

/**
 * A controller class that takes in command lines to execute specific image processing operations.
 */
public class CommandExecutor {

  private final String line;

  /**
   * Constructs a CommandExecutor object with the command.
   *
   * @param line command line to be executed
   */
  public CommandExecutor(String line) {
    this.line = line;
  }

  /**
   * Executes the command based on the initialized command line.
   *
   * @throws IOException if there is an issue in reading or writing the image files.
   */
  public void executeCommand() throws IOException {
    String[] commandParts = line.split(" ");
    if (commandParts.length < 2) {
      System.out.println("Invalid command format: " + line);
      return;
    }
    String commandType = commandParts[0];
    if (commandType.startsWith("horizontal")) {
      commandType = "horizontal";
    } else if (commandType.startsWith("vertical")) {
      commandType = "vertical";
    } else if (commandType.startsWith("red")) {
      commandType = "red";
    } else if (commandType.startsWith("green")) {
      commandType = "green";
    } else if (commandType.startsWith("blue")) {
      commandType = "blue";
    }

    switch (commandType) {
      case "load":
        handleLoad(commandParts);
        break;
      case "rgb-split":
        handleRgbSplit(commandParts);
        break;
      case "rgb-combine":
        handleRgbCombine(commandParts);
        break;
      case "brighten":
        handleBrightness(commandParts);
        break;
      case "vertical":
        handleFlip(Direction.VERTICAL, commandParts);
        break;
      case "horizontal":
        handleFlip(Direction.HORIZONTAL, commandParts);
        break;
      case "blur":
      case "sharpen":
      case "sepia":
      case "value":
      case "intensity":
      case "luma":
        handleFilterOperations(commandType, commandParts);
        break;
      case "red":
      case "green":
      case "blue":
        handleVisualizeComponent(commandType, commandParts);
        break;
      case "compress":
        handleCompress(commandParts);
        break;
      case "histogram":
        handleHistogram(commandParts);
        break;

      case "color-correct":
        handleColorCorrect(commandParts);
        break;

      case "levels-adjust":
        handleLevelsAdjust(commandParts);
        break;
      case "save":
        handleSave(commandParts);
        break;
      default:
        System.out.println("Command not found. " + line);
        break;
    }
  }

  private void handleLoad(String[] parts) throws IOException {
    if (parts.length == 3) {
      String filePath = parts[1];
      String imageName = parts[2];

      if (filePath.endsWith(".ppm")) {
        Image loadedImage = ImageUtil.readPPM(filePath);
        ImageStorage.associateImageWithName(imageName, loadedImage);
      } else if (filePath.endsWith(".jpeg") || filePath.endsWith(".png") || filePath.endsWith(
          ".jpg")) {
        Image loadedImage = ImageUtil.readJPGorPNG(filePath);
        ImageStorage.associateImageWithName(imageName, loadedImage);
      }
    } else {
      System.out.println("Invalid command format: " + line);
    }
  }

  private void handleRgbSplit(String[] commandParts) {
    if (commandParts.length == 5) {
      String srcImageName = commandParts[1];
      String destImageRed = commandParts[2];
      String destImageGreen = commandParts[3];
      String destImageBlue = commandParts[4];
      Image srcImage = ImageStorage.getImageByName(srcImageName);

      if (srcImage != null) {
        SplitComponents splitOperation = new SplitComponents();
        List<Image> splitComponents = splitOperation.apply(srcImage);
        ImageStorage.associateImageWithName(destImageRed, splitComponents.get(0));
        ImageStorage.associateImageWithName(destImageGreen, splitComponents.get(1));
        ImageStorage.associateImageWithName(destImageBlue, splitComponents.get(2));
      } else {
        System.out.println("Source image not found: " + srcImageName);
      }
    } else {
      System.out.println("Invalid command format: " + line);
    }
  }

  private void handleRgbCombine(String[] commandParts) {
    if (commandParts.length == 5) {
      String destImage = commandParts[1];
      String srcImageRedName = commandParts[2];
      String srcImageGreenName = commandParts[3];
      String srcImageBlueName = commandParts[4];
      Image srcImageRed = ImageStorage.getImageByName(srcImageRedName);
      Image srcImageGreen = ImageStorage.getImageByName(srcImageGreenName);
      Image srcImageBlue = ImageStorage.getImageByName(srcImageBlueName);

      if (srcImageRed != null && srcImageGreen != null && srcImageBlue != null) {
        CombineImages combineOperation = new CombineImages();
        Image combinedImage = combineOperation.apply(srcImageRed, srcImageGreen, srcImageBlue);
        ImageStorage.associateImageWithName(destImage, combinedImage);
      } else {
        System.out.println(
            "Source images not found: " + srcImageRedName + srcImageGreenName + srcImageBlueName);
      }
    } else {
      System.out.println("Invalid command format: " + line);
    }
  }

  private void handleBrightness(String[] commandParts) {
    if (commandParts.length == 4) {
      int increment = Integer.parseInt(commandParts[1]);
      String srcImageName = commandParts[2];
      String destImageName = commandParts[3];
      Image srcImage = ImageStorage.getImageByName(srcImageName);

      if (srcImage != null) {
        AdjustIntensity brightenOperation;
        if (increment > 0) {
          brightenOperation = new AdjustIntensity(increment, Intensity.BRIGHTEN);
        } else {
          brightenOperation = new AdjustIntensity(increment, Intensity.DARKEN);
        }
        Image brightenedImage = brightenOperation.apply(srcImage);
        ImageStorage.associateImageWithName(destImageName, brightenedImage);
      } else {
        System.out.println("Source image not found: " + srcImageName);
      }
    } else {
      System.out.println("Invalid command format: " + line);
    }
  }

  private void handleFlip(Direction flipType, String[] commandParts) {
    if (findSrcImage(line) != null) {
      Image srcImage = findSrcImage(line);
      String destImageName = commandParts[2];
      Flip flipOperation = new Flip(flipType);
      Image verticalImage = flipOperation.apply(srcImage);
      ImageStorage.associateImageWithName(destImageName, verticalImage);
    }
  }

  private void handleFilterOperations(String operationType, String[] commandParts) {
    if (commandParts.length < 3) {
      System.out.println("Invalid command format for " + operationType + ": " + line);
      return;
    }

    String srcImageName = commandParts[1];
    String destImageName = commandParts[2];
    Image srcImage = ImageStorage.getImageByName(srcImageName);

    if (srcImage == null) {
      System.out.println("Source image not found: " + srcImageName);
      return;
    }

    int splitPercentage = (commandParts.length == 4) ? Integer.parseInt(commandParts[3]) : 0;
    Image transformedImage;
    switch (operationType) {
      case "blur":
        Blur blurOperation = new Blur(splitPercentage);
        transformedImage = blurOperation.apply(srcImage);
        break;
      case "sharpen":
        Sharpen sharpenOperation = new Sharpen(splitPercentage);
        transformedImage = sharpenOperation.apply(srcImage);
        break;
      case "sepia":
        Sepia sepiaOperation = new Sepia(splitPercentage);
        transformedImage = sepiaOperation.apply(srcImage);
        break;
      case "value":
        ValueIntensityLuma valueOperation = new ValueIntensityLuma(Channel.VALUE, splitPercentage);
        transformedImage = valueOperation.apply(srcImage);
        break;
      case "intensity":
        ValueIntensityLuma intensityOperation = new ValueIntensityLuma(Channel.INTENSITY,
            splitPercentage);
        transformedImage = intensityOperation.apply(srcImage);
        break;
      case "luma":
        ValueIntensityLuma lumaOperation = new ValueIntensityLuma(Channel.LUMA, splitPercentage);
        transformedImage = lumaOperation.apply(srcImage);
        break;

      default:
        System.out.println("Filter operation not recognized: " + operationType);
        return;
    }

    ImageStorage.associateImageWithName(destImageName, transformedImage);
  }


  private void handleVisualizeComponent(String componentType, String[] commandParts) {
    if (commandParts.length < 3) {
      System.out.println("Invalid command format for " + componentType + ": " + line);
      return;
    }

    String srcImageName = commandParts[1];
    String destImageName = commandParts[2];
    Image srcImage = ImageStorage.getImageByName(srcImageName);

    if (srcImage == null) {
      System.out.println("Source image not found: " + srcImageName);
      return;
    }

    VisualizeComponents operation = new VisualizeComponents(
        Color.valueOf(componentType.toUpperCase()));
    Image resultImage = operation.apply(srcImage);
    ImageStorage.associateImageWithName(destImageName, resultImage);
  }

  private void handleCompress(String[] commandParts) throws IllegalArgumentException {
    if (commandParts.length != 4) {
      throw new IllegalArgumentException("Invalid command format for compress: " + line);
    }

    int percentage = Integer.parseInt(commandParts[1]);
    if (percentage < 0 || percentage > 100) {
      throw new IllegalArgumentException("Compression percentage cannot be negative.");
    }
    String srcImageName = commandParts[2];
    String destImageName = commandParts[3];
    Image srcImage = ImageStorage.getImageByName(srcImageName);

    if (srcImage == null) {
      System.out.println("Source image not found: " + srcImageName);
      return;
    }

    CompressImage operation = new CompressImage(percentage);
    Image compressedImage = operation.apply(srcImage);
    ImageStorage.associateImageWithName(destImageName, compressedImage);
  }

  private void handleHistogram(String[] commandParts) {
    if (commandParts.length == 3) {
      String srcImageName = commandParts[1];
      String destImageName = commandParts[2];
      Image srcImage = ImageStorage.getImageByName(srcImageName);

      if (srcImage != null) {
        VisualizeHistogram operation = new VisualizeHistogram();
        Image histo = operation.apply(srcImage);
        ImageStorage.associateImageWithName(destImageName, histo);
      } else {
        System.out.println("Source image not found: " + srcImageName);
      }
    } else {
      System.out.println("Invalid command format: " + line);
    }
  }

  private void handleColorCorrect(String[] commandParts) {
    if (commandParts.length == 3 || commandParts.length == 4) {
      String srcImageName = commandParts[1];
      String destImageName = commandParts[2];
      Image srcImage = ImageStorage.getImageByName(srcImageName);
      int splitPercentage = (commandParts.length == 4) ? Integer.parseInt(commandParts[3]) : 0;
      if (srcImage != null) {
        ColorCorrect colorCorrectOperation = new ColorCorrect(splitPercentage);
        Image colorCorrectedImage = colorCorrectOperation.apply(srcImage);
        ImageStorage.associateImageWithName(destImageName, colorCorrectedImage);
      } else {
        System.out.println("Source image not found: " + srcImageName);
      }
    } else {
      System.out.println("Invalid command format: " + line);
    }
  }


  private void handleLevelsAdjust(String[] commandParts) {
    if (commandParts.length == 6 || commandParts.length == 7) {
      String srcImageName = commandParts[4];
      String destImageName = commandParts[5];
      Image srcImage = ImageStorage.getImageByName(srcImageName);

      if (srcImage != null) {
        int black = Integer.parseInt(commandParts[1]);
        int mid = Integer.parseInt(commandParts[2]);
        int white = Integer.parseInt(commandParts[3]);

        int splitPercentage = (commandParts.length == 7) ? Integer.parseInt(commandParts[6]) : 0;

        AdjustLevel adjuster = new AdjustLevel(black, mid, white, splitPercentage);
        Image adjustedImage = adjuster.apply(srcImage);

        if (splitPercentage > 0) {
          // Draw a vertical line only if splitPercentage is provided
          int splitPoint = (int) (adjustedImage.getWidth() * splitPercentage / 100.0);
          for (int y = 0; y < adjustedImage.getHeight(); y++) {
            adjustedImage.getPixels()[y][splitPoint] = new Pixel(0, 0, 0);
          }
        }

        ImageStorage.associateImageWithName(destImageName, adjustedImage);
      } else {
        System.out.println("Source image not found: " + srcImageName);
      }
    } else {
      System.out.println("Invalid command format: " + line);
    }
  }


  private void handleSave(String[] commandParts) throws IOException {
    if (commandParts.length == 3) {
      String filePath = commandParts[1];
      String imageName = commandParts[2];
      String format = "JPEG";
      if (filePath.endsWith(".ppm")) {
        format = "PPM";
      } else if (filePath.endsWith(".jpeg") || filePath.endsWith(".jpg")) {
        format = "JPEG";
      } else if (filePath.endsWith(".png")) {
        format = "PNG";
      }
      Image imageToSave = ImageStorage.getImageByName(imageName);
      if (imageToSave == null) {
        throw new IOException("File not found: " + filePath);
      }
      try {
        if (format.equals("PPM")) {
          ImageUtil.writePPM(imageToSave, filePath);
        } else {
          ImageUtil.writeJPGorPNG(imageToSave, filePath, format);
        }
        System.out.println("model.Image saved to: " + filePath);
      } catch (IOException e) {
        System.out.println("Error saving the image: " + e.getMessage());
      }
    } else {
      System.out.println("Invalid command format: " + line);
    }
  }

  private static Image findSrcImage(String line) {
    String[] commandParts = line.split(" ");
    String srcImageName;
    Image srcImage = null;
    if (commandParts.length == 3) {
      srcImageName = commandParts[1];
      if (srcImageName == null) {
        System.out.println("Source image not found: " + srcImageName);
      } else {
        srcImage = ImageStorage.getImageByName(srcImageName);
      }
    } else {
      System.out.println("Invalid command format: " + line);
    }
    return srcImage;
  }
}