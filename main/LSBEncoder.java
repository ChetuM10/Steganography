package main;

import java.awt.image.BufferedImage;
import java.io.*;

public class LSBEncoder extends ImageProcessor implements Steganography {

    @Override
    public void hideFileInImage(String imagePath, String filePath, String outputImagePath) {
        try {
            // 1 Load the cover image
            BufferedImage image = loadImage(imagePath);

            File file = new File(filePath);
            long fileLength = file.length();

            // Check if the image has enough capacity
            int width = image.getWidth();
            int height = image.getHeight();
            int totalPixels = width * height;
            int totalBitsAvailable = totalPixels;
            long totalBitsNeeded = (4 + fileLength) * 8; // 4 bytes for size + file data

            if (totalBitsNeeded > totalBitsAvailable) {
                throw new IOException("File too large for this image. Need " +
                        totalBitsNeeded + " bits, but image only has " +
                        totalBitsAvailable + " bits available.");
            }

            // 2 Convert file to byte array
            byte[] fileData = new byte[(int) fileLength];
            try (FileInputStream fis = new FileInputStream(file)) {
                fis.read(fileData);
            }

            // 3 Hide the file in the image
            BufferedImage stegoImage = embedData(image, fileData);

            // 4 Save the new image
            saveImage(stegoImage, outputImagePath);
            System.out.println("✅ File successfully hidden in image: " + outputImagePath);
            System.out.println("   Embedded " + fileData.length + " bytes of data");

        } catch (Exception e) {
            System.err.println("❌ Error hiding file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void extractFileFromImage(String imagePath, String outputFilePath) {

        LSBDecoder decoder = new LSBDecoder();
        decoder.extractFileFromImage(imagePath, outputFilePath);
    }

    private BufferedImage embedData(BufferedImage image, byte[] fileData) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Convert file size to 4-byte array (32 bits)
        int fileSize = fileData.length;
        System.out.println("File size to embed: " + fileSize + " bytes");
        byte[] fileSizeBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            fileSizeBytes[i] = (byte) ((fileSize >> (8 * (3 - i))) & 0xFF);
        }

        // Combine file size bytes + file data
        byte[] fullData = new byte[4 + fileData.length];
        System.arraycopy(fileSizeBytes, 0, fullData, 0, 4);
        System.arraycopy(fileData, 0, fullData, 4, fileData.length);

        int dataIndex = 0, bitIndex = 0;
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);

                if (dataIndex < fullData.length) {
                    int byteData = fullData[dataIndex] & 0xFF; // Ensure byte is treated as unsigned
                    int bit = (byteData >> (7 - bitIndex)) & 1;

                    // Modify the least significant bit (LSB)
                    int newPixel = (pixel & 0xFFFFFFFE) | bit;
                    newImage.setRGB(x, y, newPixel);

                    bitIndex++;
                    if (bitIndex == 8) {
                        bitIndex = 0;
                        dataIndex++;
                    }
                } else {
                    // Copy remaining pixels without modification
                    newImage.setRGB(x, y, pixel);
                }
            }
        }
        return newImage;
    }
}