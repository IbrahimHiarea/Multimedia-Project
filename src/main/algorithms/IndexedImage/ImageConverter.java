package main.algorithms.IndexedImage;

import java.awt.*;
import java.awt.image.*;

public class ImageConverter {
    public BufferedImage convertToIndexed(BufferedImage srcImage, int numColors) {
        // Create a color model with the desired number of colors
        IndexColorModel colorModel = createColorModel(numColors);

        // Create a compatible BufferedImage with the same dimensions as the source image
        BufferedImage indexedImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_BYTE_INDEXED, colorModel);

        // Create a Graphics2D object and draw the source image onto the indexed image
        Graphics2D g2d = indexedImage.createGraphics();
        g2d.drawImage(srcImage, 0, 0, null);
        g2d.dispose();

        return indexedImage;
    }

    public IndexColorModel createColorModel(int numColors) {
        // Generate an RGB palette
        byte[] reds = new byte[numColors];
        byte[] greens = new byte[numColors];
        byte[] blues = new byte[numColors];

        for (int i = 0; i < numColors; i++) {
            reds[i] = (byte) ((i * 256) / numColors);
            greens[i] = (byte) ((i * 256) / numColors);
            blues[i] = (byte) ((i * 256) / numColors);
        }

        // Create an IndexColorModel with the generated palette
        return new IndexColorModel(8, numColors, reds, greens, blues);
    }
}
