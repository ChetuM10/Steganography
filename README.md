# Steganography File Hider

A Java-based steganography application that allows you to hide files within images using the Least Significant Bit (LSB) technique. This project provides both command-line and GUI interfaces for embedding and extracting files from PNG images.

## Features

- **File Hiding**: Embed any file type within a PNG image
- **File Extraction**: Retrieve hidden files from steganographic images
- **GUI Interface**: User-friendly graphical interface for easy operation
- **Command Line Support**: Programmatic access through main classes
- **LSB Steganography**: Uses Least Significant Bit technique for minimal visual impact
- **Capacity Checking**: Automatically verifies if the image can accommodate the file
- **Error Handling**: Comprehensive error checking and user feedback

## Project Structure

```
src/main/
├── FileHider.java          # Command-line interface for hiding files
├── FileRetriever.java      # Command-line interface for extracting files
├── ImageProcessor.java     # Abstract base class for image operations
├── LSBEncoder.java         # Implements file hiding using LSB technique
├── LSBDecoder.java         # Implements file extraction using LSB technique
├── Steganography.java      # Interface defining steganography operations
└── StegoGUI.java          # Graphical user interface
```

## How It Works

The application uses **Least Significant Bit (LSB) steganography**:

1. **Encoding Process**:
   - Reads the file to be hidden and converts it to bytes
   - Stores the file size (4 bytes) followed by the actual file data
   - Embeds data by modifying the least significant bit of each pixel
   - Saves the result as a new PNG image

2. **Decoding Process**:
   - Reads the steganographic image pixel by pixel
   - Extracts the least significant bits to reconstruct the hidden data
   - First 4 bytes contain the file size, followed by the actual file content
   - Saves the extracted data to the specified output file

## Requirements

- Java 8 or higher
- Input images must be in PNG format
- Sufficient image capacity (1 bit per pixel for data storage)

## Usage

### GUI Mode (Recommended)

1. **Compile and run the GUI**:
   ```bash
   javac main/*.java
   java main.StegoGUI
   ```

2. **Hide a file**:
   - Click "Select Image" to choose your cover image (PNG)
   - Click "Select File" to choose the file you want to hide
   - Click "Save As" to specify where to save the steganographic image
   - Click "Hide File" to embed the file

3. **Extract a file**:
   - Click "Select Image" to choose the steganographic image
   - Specify the output path in the "File" field for the extracted file
   - Click "Extract File" to retrieve the hidden file

### Command Line Mode

1. **Compile the project**:
   ```bash
   javac main/*.java
   ```

2. **Hide a file**:
   ```bash
   java main.FileHider
   ```
   Edit the file paths in `FileHider.java` before running.

3. **Extract a file**:
   ```bash
   java main.FileRetriever
   ```
   Edit the file paths in `FileRetriever.java` before running.

## Configuration

For command-line usage, modify the file paths in the respective classes:

**FileHider.java**:
```java
String imagePath = "path/to/your/cover/image.png";
String filePath = "path/to/file/to/hide.txt";
String outputImagePath = "path/to/output/stego.png";
```

**FileRetriever.java**:
```java
String imagePath = "path/to/stego.png";
String outputFilePath = "path/to/extracted/file.txt";
```

## Capacity Limitations

The maximum file size that can be hidden depends on the image dimensions:
- **Formula**: `max_file_size = (width × height - 32) / 8` bytes
- **Example**: A 1000×1000 pixel image can hide approximately 124,996 bytes (~122 KB)
- The application automatically checks capacity before embedding

## Supported File Types

- **Input Images**: PNG format only
- **Files to Hide**: Any file type (text, binary, documents, etc.)
- **Output**: PNG format (preserves image quality)

## Error Handling

The application includes comprehensive error handling for:
- Invalid or corrupted image files
- Insufficient image capacity for the file
- File I/O operations
- Invalid file size detection during extraction

## Technical Details

- **Steganography Method**: LSB (Least Significant Bit)
- **Data Structure**: 4-byte file size header + file content
- **Bit Order**: MSB to LSB for consistent encoding/decoding
- **Image Format**: RGB PNG images
- **Memory Efficient**: Processes images pixel by pixel

## Example Output

```
File size to embed: 1024 bytes
✅ File successfully hidden in image: output/stego.png
   Embedded 1024 bytes of data

Starting extraction from image: output/stego.png
Detected file size: 1024 bytes
✅ File extracted successfully to: output/recovered.txt
   Extracted 1024 of 1024 expected bytes
```

## Contributing

Feel free to contribute by:
- Adding support for other image formats (JPEG, BMP)
- Implementing additional steganography techniques
- Improving the GUI interface
- Adding encryption capabilities
- Optimizing performance

## License

This project is open source and available under the MIT License.

## Disclaimer

This tool is intended for educational and legitimate purposes only. Users are responsible for ensuring their use complies with applicable laws and regulations.
