package main;

import java.awt.image.BufferedImage;
import java.io.*;

public class LSBDecoder extends ImageProcessor implements Steganography {

    @Override
    public void extractFileFromImage(String imagePath, String outputFilePath) {
        try {
            // 1 Load the image
            BufferedImage image = loadImage(imagePath);

            // 2 Read hidden data from image pixels
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int bitIndex = 0;
            int currentByte = 0;
            int fileSize = 0;
            boolean readingFileSize = true;
            int fileSizeBytesRead = 0;
            int dataBytesRead = 0;

            System.out.println("Starting extraction from image: " + imagePath);

            outerLoop: for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = image.getRGB(x, y);

                    // Extract least significant bit (exactly as done in encoder)
                    int bit = pixel & 1;

                    // Add the bit to our current byte (in same order as encoder)
                    currentByte = (currentByte << 1) | bit;
                    bitIndex++;

                    if (bitIndex == 8) {
                        // We have a complete byte
                        if (readingFileSize) {
                            // Reading file size (first 4 bytes)
                            fileSize = (fileSize << 8) | (currentByte & 0xFF);
                            fileSizeBytesRead++;

                            if (fileSizeBytesRead == 4) {
                                System.out.println("Detected file size: " + fileSize + " bytes");
                                readingFileSize = false;

                                // Sanity check
                                if (fileSize <= 0 || fileSize > 10_000_000) { // 10MB max
                                    System.err.println(" Warning: Unusual file size detected: " + fileSize);
                                    if (fileSize <= 0) {
                                        System.err.println("Invalid negative file size. Attempting recovery...");
                                        fileSize = 1000;
                                    }
                                }
                            }
                        } else {
                            // Reading actual file data
                            byteStream.write(currentByte);
                            dataBytesRead++;

                            // Stop if we've read enough bytes
                            if (dataBytesRead >= fileSize) {
                                break outerLoop;
                            }
                        }

                        // Reset for next byte
                        bitIndex = 0;
                        currentByte = 0;
                    }
                }
            }

            // 3 Save the extracted data
            byte[] fileData = byteStream.toByteArray();
            try (FileOutputStream fos = new FileOutputStream(outputFilePath)) {
                fos.write(fileData);
            }

            System.out.println("✅ File extracted successfully to: " + outputFilePath);
            System.out.println("   Extracted " + fileData.length + " of " + fileSize + " expected bytes");

        } catch (IOException e) {
            System.err.println("❌ Error extracting file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void hideFileInImage(String imagePath, String filePath, String outputImagePath) {
        // This method is required by the interface but handled by LSBEncoder
        // We could either throw an UnsupportedOperationException or call LSBEncoder
        LSBEncoder encoder = new LSBEncoder();
        encoder.hideFileInImage(imagePath, filePath, outputImagePath);
    }
}