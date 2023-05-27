package main.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import main.algorithms.imageSearch.ImageColorSearch;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;
import java.time.LocalDate;
import java.util.ArrayList;

public class SearchResultController {

    @FXML
    private ListView listView;

    public void SearchResult(String imagePath , ArrayList<String> directories , ArrayList<Color> colors , LocalDate date , int width , int height){
        Image targetImage = new Image(imagePath);
        BufferedImage targetImageBuffer = SwingFXUtils.fromFXImage(targetImage, null);
        ArrayList<BufferedImage> images = new ArrayList<>();

        for(String dir : directories){
            File folder = new File(dir);
            for (File file : folder.listFiles()){
                if (file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"))){
                    Image originalImage = new Image(file.getPath());
                    BufferedImage image = SwingFXUtils.fromFXImage(originalImage, null);
                    if(Math.abs(width - image.getWidth()) < 100)    continue;
                    if(Math.abs(height - image.getHeight()) < 100)    continue;
                    images.add(image);
                }
            }
        }

        ImageColorSearch imageSearch = new ImageColorSearch();

        ArrayList<BufferedImage> result = imageSearch.start(images , targetImageBuffer);
        for(BufferedImage res : result){
            Image resultImage = SwingFXUtils.toFXImage(res, null);
            ImageView imageView = new ImageView(resultImage);
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            listView.getItems().add(imageView);
        }
    }

}