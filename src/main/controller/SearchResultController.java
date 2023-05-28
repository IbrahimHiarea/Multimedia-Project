package main.controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.algorithms.imageSearch.ImageColorSearch;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class SearchResultController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private GridPane grid;

    public void SearchResult(String imagePath , ArrayList<String> directories , ArrayList<Color> colors , Date date , int width , int height , int x1 , int y1 , int x2 , int y2){
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

        ArrayList<BufferedImage> result = imageSearch.start(images , targetImageBuffer , colors , x1 , y1 , x2 , y2);
        int i = 0 , j = 0;
        for(BufferedImage res : result){
            if(j >= 3){
                j = 0;
                i ++;
            }
            if(i == 2)  break;
            Image resultImage = SwingFXUtils.toFXImage(res, null);
            ImageView imageView = new ImageView(resultImage);
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            grid.add(imageView , j , i);
            j++;
        }
    }

    public void goBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/main/resources/fxml/Main.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}