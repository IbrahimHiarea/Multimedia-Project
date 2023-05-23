package main.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.algorithms.IndexedImage.ImageConverter;
import main.algorithms.floydSteinberg.FloydSteinberg;
import main.algorithms.octree.Quantize;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static main.algorithms.floydSteinberg.FloydSteinberg.applyDitheredPalette;

public class QuantizationResultController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView imageView;

    public void octree(String imagePath) {
        Image image = new Image(imagePath);
        Quantize quantization = new Quantize();
        Image newImage = quantization.start(image);
        imageView.setImage(newImage);
    }

    public void floydSteinberg(String imagePath) {
        Image image = new Image(imagePath);
        BufferedImage original = SwingFXUtils.fromFXImage(image, null);
        FloydSteinberg floyed = new FloydSteinberg();
        BufferedImage newImage = applyDitheredPalette(original, floyed.PALETTE);
        Image result = SwingFXUtils.toFXImage(newImage, null);
        imageView.setImage(result);
    }

    public void simple(String imagePath) {
        int MASK_0 = 0x00800000; // 0 bits per channel (except red)
        int MASK_1 = 0xff808080; // 1 bit per channel
        int MASK_2 = 0xffc0c0c0; // 2 bits per channel
        int MASK_3 = 0xffe0e0e0; // 3 bits per channel
        int MASK_4 = 0xfff0f0f0; // 4 bits per channel

        Image image = new Image(imagePath);
        BufferedImage original = SwingFXUtils.fromFXImage(image, null);
        int w = original.getWidth();
        int h = original.getHeight();
        BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        // Go through every pixel of the image
        for(int x=0; x< w; x++){
            for(int y=0; y< h; y++){
                // Apply mask to original value and save it in result image
                newImage.setRGB(x,y, original.getRGB(x, y) & MASK_2);
            }
        }
        Image result = SwingFXUtils.toFXImage(newImage, null);
        imageView.setImage(result);
    }

    public void goBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/Main.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void save(ActionEvent event) throws IOException {
        Image image = imageView.getImage();
        if(image != null){
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File ("C:\\Users\\Twfek Ajeneh\\Downloads"));
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
            fileChooser.getExtensionFilters().add(imageFilter);
            File file = fileChooser.showSaveDialog(null);
            ImageIO.write(SwingFXUtils.fromFXImage(image , null), "png", file);
            root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/Main.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("There is no Image");
        }
    }

    public void IndexedImage() throws  IOException{
        Image image = imageView.getImage();
        BufferedImage original = SwingFXUtils.fromFXImage(image, null);

        ImageConverter converter = new ImageConverter();
        // you can change the number of color you want;
        BufferedImage indexedImage = converter.convertToIndexed(original , 256);
        Image result = SwingFXUtils.toFXImage(indexedImage, null);
        imageView.setImage(result);
    }

}
