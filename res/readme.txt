How to use the program:
If compiling is required type: javac controller/*.java

The jar file is located in Assignment_6/res/Assignment_6.jar
To run the file in GUI mode run the command java -jar Assignment_6.jar

The res file can be executed in three modes
Interactive mode, Running text file with scripts mode and GUI mode.
1. java -jar Assignment_6.jar -text : In interactive mode type each command separately and type exit to quit
2. java -jar Assignment_6.jar -file res_script.txt : To run the script file (provide exact location of text file)
3. java -jar Assignment_6.jar : GUI mode

Image Processing Application
Overview

An image processing application that has the functionality to apply the specified image processing operation, modify and store the resulting image in PPM,JPG or PNG formats.


Classes and Interfaces

Interfaces:

The ScriptProcessor Interface in the controller package defines a method for processing command arguments and executing the appropriate commands specified in the input script file.
The ImageOperation Interface in the model package defines a common base for applying various types of image operations specified which will take one or more images as the input and give the result based on the specific operation.

Classes:

Package:Controller

ImageUtil- Provides methods for reading images in  PPM, JPG, and PNG and writing images in the same formats.
CommandExecutor- Acts as a controller for implementing the give image processing command. It processes commands like load, save and applying various image processing operations. It couples with the model part of the application to perform these image processing operations.
ImageStorage- Represents a storage system for images so that can retrieves an Image object associated with the given image name and also it  can associate an image name with an Image object.
RunScript- Processes and executes the script commands provided as the input. It reads the given script file and makes use of the CommandExecutor to execute each line of the script as a command.
Packages:Model

Enums:
Channel- Represents different channels for processing images like Value, Luma and Intensity
Color- Represents different colors like red, blue and green component of images
Direction- Represents different directions to flip Horizontal, Vertical.
Filter- Represents different filter operations like Blur, Sharpen
Intensity- Represents the different intensity adjustment operations like Brighten, Darken

Classes:

AbstractColor- provides a base for implementing color transformation operations by defining a common structure called processPixel which the subclasses Sepia, ValueIntensityLuma and VisualizeComponents extend.

AbstractFilter-  provides a base for implementing filter/kernel based transformation operations by defining a common structure which the subclasses Blur and Sharpen extend.

AdjustIntensity - modifies the input image by either brightening or darkening the image based on the input


Blur- represents the filter operation for applying a blur effect on the input image and it extends the AbstractFilter class

CombineImages- represents the operation to combine the three components of the  images into one image

Flip- represents the operation to flip the input image either horizontally or vertically based on the input specified.

Pixel- represents a single pixel in the image characterized by red, blue and green color component.

Image- used to represent the image characterized by its height, width and the array of pixels

Sepia- represents the sepia color transformation applied to an image and it extends the AbstractColor class

Blur- represents the filter operation for applying a sharpened effect on the input image and it extends the AbstractFilter class
SpliComponents- represents the splitting of an image into its individual red,green and blue components

ValueIntensityLuma- represents the operation to find the value, intensity and luma components of an image and extends the AbstractColor class

VisualizeComponents- represents an image operation to visualize specific color components namely red,blue and green components

ColorCorrect-performs color correction on an input image. It adjusts the intensities of color channels (red, green, and blue) in an image
VisualizeHistogram-generate and visualize histograms for the red, green, and blue color channels of an input image.
AdjustLevel-perform level adjustment on an image based on specified black, mid, and white values.
CompressImage- Compresses the  image based on the compress percentage given by the user
AbstractImageOperation- provides a base for implementing the CompressImage class, with a method padPixels that pads the pixels of an image. The unPadPixels method reverses the padding operation.

New classes in controller
GUICommandHandler-Acts as a controller for image processing operations in a graphical user interface. It has methods for applying various image manipulation operations, such as blurring, sharpening, color adjustments, and visualization.

View

Classes:
ImageProcessingGUI-represents a graphical user interface for image processing or manipulation operations.It has the ability to load, manipulate, and save images after applying
various image processing operations.The GUI has buttons for applying image manipulation
operations like blur, sharpen, greyscale, sepia and to adjust levels, visualize color components,
flip, and perform color correction.Split view is also available for the user to preview the
original and manipulated image.The GUI also has a histogram panel that displays the histogram of
whatever image is being seen on the screen.

ImageProcessingUtilities-provides utility functions for image processing. It includes methods for image comparison, file extension retrieval, error dialog display, image conversion etc.

Design changes and justification: RunScript class has been moved outside the controller since it is the main point of contact for the whole application.

Citation of the image being used
Image Credit:
Author/Photographer: Maridav
Date of publication/creation: June 29,2021
"Koala on Eucalyptus Tree Outdoor in Australia"
Available at: https://www.istockphoto.com/photo/koala-on-eucalyptus-tree-outdoor-in-australia-gm1326007751-410847409
Source: iStockphoto











