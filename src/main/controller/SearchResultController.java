package main.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.algorithms.imageSearch.ImageColorSearch;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class SearchResultController {

    @FXML
    private ListView listView;

    public void SearchResult(String imagePath , ArrayList<String> directories , ArrayList<Color> colors , Date date , int width , int height){
        Image targetImage = new Image(imagePath);
        BufferedImage targetImageBuffer = SwingFXUtils.fromFXImage(targetImage, null);
        ArrayList<BufferedImage> images = new ArrayList<>();

        for(String dir : directories){
            File folder = new File(dir);
            for (File file : folder.listFiles()){
                if (file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"))){
                    Image originalImage = new Image(file.getPath());
                    BufferedImage image = SwingFXUtils.fromFXImage(originalImage, null);

                    //filter by width and height
                    if(width >= 0 && Math.abs(width - image.getWidth()) > 100)    continue;
                    if(height >= 0 && Math.abs(height - image.getHeight()) > 100)    continue;

                    //filter by data
                    Date createDate = new Date(file.lastModified());
                    if(createDate.compareTo(date) < 0 ) continue;

                    images.add(image);
                }
            }
        }

        ImageColorSearch imageSearch = new ImageColorSearch();

        ArrayList<BufferedImage> result = imageSearch.start(images , targetImageBuffer , colors);
        for(BufferedImage res : result){
            Image resultImage = SwingFXUtils.toFXImage(res, null);
            ImageView imageView = new ImageView(resultImage);
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            listView.getItems().add(imageView);
        }
    }

}