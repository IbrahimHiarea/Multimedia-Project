package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class QuantizationController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private ChoiceBox<String> algorithm;

    private String[] algorithms = {"Octree" , "Median Cut" , "K-Means"};

    public void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File ("C:\\Users\\ASUS\\Desktop\\University\\4-Th Year\\Chapter 2\\Multimedia\\multimedia-project\\src\\main\\resources\\img"));
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null){
            Image image = new Image(file.getPath());
            System.out.println(file.getPath());
            imageView.setImage(image);
        } else {
            System.out.println("NO File");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algorithm.setValue("Octree");
        algorithm.getItems().addAll(algorithms);
    }
}
